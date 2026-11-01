/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * The Class ProxyFrameworks.
 */
public class ProxyFrameworks {

    /** The unproxied type factory. */
    private static final UnproxiedTypeFactory UNPROXIED_TYPE_FACTORY = new UnproxiedTypeFactory();

    /**
     * Get unproxied type.
     *
     * @param type
     *            the type
     *
     * @return the unproxied type
     */
    public UnproxiedType getUnproxiedType(Class<?> type) {

        if (type == null) {
            return null;
        }

        if (isJavaProxy(type)) {
            return UNPROXIED_TYPE_FACTORY.createFromInterfaces(type.getInterfaces());
        }

        if (isCglibProxyClass(type)) {
            return UNPROXIED_TYPE_FACTORY.createFromSuperclassAndInterfaces(type.getSuperclass(), type.getInterfaces());
        }

        return UNPROXIED_TYPE_FACTORY.createFromType(type);
    }

    /**
     * Get unproxied type.
     *
     * @param o
     *            the o
     *
     * @return the unproxied type
     */
    public UnproxiedType getUnproxiedType(Object o) {
        if (o == null) {
            return null;
        }
        return getUnproxiedType(o.getClass());
    }

    /**
     * Is java proxy.
     *
     * @param clazz
     *            the clazz
     *
     * @return true, if successful
     */
    private boolean isJavaProxy(Class<?> clazz) {
        return (clazz != null && Proxy.isProxyClass(clazz));
    }

    /**
     * Is cglib proxy class.
     *
     * @param clazz
     *            the clazz
     *
     * @return true, if successful
     */
    private boolean isCglibProxyClass(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (isCglibCallbackMethod(m)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is cglib callback method.
     *
     * @param m
     *            the m
     *
     * @return true, if successful
     */
    private boolean isCglibCallbackMethod(Method m) {
        return "CGLIB$SET_THREAD_CALLBACKS".equals(m.getName()) && m.getParameterTypes().length == 1;
    }
}
