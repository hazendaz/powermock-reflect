/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.InvocationHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class ProxyFrameworksTest.
 */
class ProxyFrameworksTest {

    /** The proxy frameworks. */
    private ProxyFrameworks proxyFrameworks;

    /**
     * Set up.
     */
    @BeforeEach
    void setUp() {
        proxyFrameworks = new ProxyFrameworks();
    }

    /**
     * Should throw illegal argument exception if class is null.
     */
    @Test
    void should_throw_illegal_argument_exception_if_class_is_null() {
        assertThat(proxyFrameworks.getUnproxiedType(null)).isNull();
    }

    /**
     * Should return null if object is null.
     */
    @Test
    void should_return_null_if_object_is_null() {
        assertThat(proxyFrameworks.getUnproxiedType((Object) null)).isNull();
    }

    /**
     * Should return original class if object not proxy.
     */
    @Test
    void should_return_original_class_if_object_not_proxy() {
        SomeClass someClass = new SomeClass();

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(someClass);

        assertThatOriginalTypeInstanceOf(unproxiedType, SomeClass.class);
    }

    /**
     * Should return original class if proxy created with java.
     */
    @Test
    void should_return_original_class_if_proxy_created_with_java() {
        SomeInterface someInterface = createJavaProxy(new Class[] { SomeInterface.class });

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(someInterface);

        assertThatOriginalTypeInstanceOf(unproxiedType, SomeInterface.class);
    }

    /**
     * Should return original class if proxy created with cglib.
     */
    @Test
    void should_return_original_class_if_proxy_created_with_cglib() {
        SomeClass someClass = (SomeClass) createCglibProxy(SomeClass.class);

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(someClass);

        assertThatOriginalTypeInstanceOf(unproxiedType, SomeClass.class);
    }

    /**
     * Should not detect synthetic classes as cglib proxy.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void should_not_detect_synthetic_classes_as_cglib_proxy() throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Enumeration<URL> resources = classLoader.getResources("org/powermock/reflect/internal/proxy");

        final List<URL> urls = new ArrayList<URL>();

        while (resources.hasMoreElements()) {
            urls.add(resources.nextElement());
        }

        String className = "Some$$SyntheticClass$$Lambda";
        byte[] bytes = ClassFactory.create(className);
        CustomClassLoader customClassLoader = new CustomClassLoader(urls.toArray(new URL[urls.size()]), classLoader);

        Class<?> defineClass = customClassLoader.defineClass(className, bytes);

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(defineClass.getConstructor().newInstance());

        assertThatOriginalTypeInstanceOf(unproxiedType, defineClass);
    }

    /**
     * Should return object as original class if no non no mocking interfaces.
     */
    @Test
    void should_return_object_as_original_class_if_no_non_no_mocking_interfaces() {
        Factory someClass = (Factory) createCglibProxy(Factory.class);

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(someClass);

        assertThatOriginalTypeInstanceOf(unproxiedType, Object.class);
    }

    /**
     * Should return interface as original type if only one non mocking interface.
     */
    @Test
    void should_return_interface_as_original_type_if_only_one_non_mocking_interface() {
        Factory someClass = (Factory) createCglibProxy(Factory.class, SomeInterface.class);

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(someClass);

        assertThatOriginalTypeInstanceOf(unproxiedType, SomeInterface.class);
    }

    /**
     * Should return interface and original type if proxy has interface and superclass.
     */
    @Test
    void should_return_interface_and_original_type_if_proxy_has_interface_and_superclass() {
        SomeClass someClass = (SomeClass) createCglibProxy(SomeClass.class, SomeInterface.class,
                AnotherInterface.class);

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(someClass);

        assertThatOriginalTypeInstanceOfAndInterfaces(unproxiedType, SomeClass.class,
                new Class[] { SomeInterface.class, AnotherInterface.class });
    }

    /**
     * Should return interfaces if proxy create from several interfaces.
     */
    @Test
    void should_return_interfaces_if_proxy_create_from_several_interfaces() {

        Class<?>[] interfaces = { SomeInterface.class, AnotherInterface.class };

        SomeInterface someInterface = createJavaProxy(interfaces);

        UnproxiedType unproxiedType = proxyFrameworks.getUnproxiedType(someInterface);

        assertThatOriginalIsNullAndInterfaces(unproxiedType, interfaces);
    }

    /**
     * Create cglib proxy.
     *
     * @param superclass
     *            the superclass
     * @param interfaces
     *            the interfaces
     *
     * @return the object
     */
    private Object createCglibProxy(Class<?> superclass, Class<?>... interfaces) {
        if (interfaces.length == 0) {
            return Enhancer.create(superclass, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    return method.invoke(o, objects);
                }
            });
        } else {
            return Enhancer.create(superclass, interfaces, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    return method.invoke(o, objects);
                }
            });
        }
    }

    /**
     * Create java proxy.
     *
     * @param interfaces
     *            the interfaces
     *
     * @return the some interface
     */
    private SomeInterface createJavaProxy(Class<?>[] interfaces) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return (SomeInterface) Proxy.newProxyInstance(classLoader, interfaces,
                new java.lang.reflect.InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(proxy, args);
                    }
                });
    }

    /**
     * Assert that original is null and interfaces.
     *
     * @param unproxiedType
     *            the unproxied type
     * @param expectedInterfaces
     *            the expected interfaces
     */
    private void assertThatOriginalIsNullAndInterfaces(UnproxiedType unproxiedType, Class<?>[] expectedInterfaces) {
        assertThat(unproxiedType.getOriginalType()).isNull();
        assertThat(unproxiedType.getInterfaces()).containsExactlyInAnyOrder(expectedInterfaces);
    }

    /**
     * Assert that original type instance of.
     *
     * @param unproxiedType
     *            the unproxied type
     * @param expectedClass
     *            the expected class
     */
    private void assertThatOriginalTypeInstanceOf(UnproxiedType unproxiedType, Class<?> expectedClass) {
        assertThat(unproxiedType.getOriginalType()).isEqualTo(expectedClass);
        assertThat(unproxiedType.getInterfaces()).isEmpty();
    }

    /**
     * Assert that original type instance of and interfaces.
     *
     * @param unproxiedType
     *            the unproxied type
     * @param expectedClass
     *            the expected class
     * @param expectedInterfaces
     *            the expected interfaces
     */
    private void assertThatOriginalTypeInstanceOfAndInterfaces(UnproxiedType unproxiedType, Class<?> expectedClass,
            Class<?>[] expectedInterfaces) {
        assertThat(unproxiedType.getOriginalType()).isEqualTo(expectedClass);
        assertThat(unproxiedType.getInterfaces()).containsExactlyInAnyOrder(expectedInterfaces);
    }

    /**
     * The Class CustomClassLoader.
     */
    private static class CustomClassLoader extends URLClassLoader {

        /**
         * Instantiates a new custom class loader.
         *
         * @param urls
         *            the urls
         * @param parent
         *            the parent
         */
        private CustomClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        /**
         * Define class.
         *
         * @param name
         *            the name
         * @param b
         *            the b
         *
         * @return the class
         */
        Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }

}
