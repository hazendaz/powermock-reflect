/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.testclasses.Child;
import org.powermock.reflect.testclasses.ClassWithMethodUsingBothPrimitiveTypeAndWrappedTypeArguments;
import org.powermock.reflect.testclasses.ClassWithOverloadedMethods;
import org.powermock.reflect.testclasses.ClassWithStandardMethod;

/**
 * Unit tests specific to the WhiteboxImpl.
 */
class WhiteboxImplTest {

    /**
     * Asserts that a previous bug was fixed.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void assertThatClassAndNotStringIsNotSameWhenInvokingCheckIfTypesAreSame() throws Exception {
        Method method = WhiteboxImpl.getMethod(WhiteboxImpl.class, "checkIfParameterTypesAreSame", boolean.class,
                Class[].class, Class[].class);
        boolean invokeMethod = (Boolean) method.invoke(WhiteboxImpl.class, false, new Class<?>[] { Class.class },
                new Class<?>[] { String.class });
        assertThat(invokeMethod).isFalse();
    }

    /**
     * Assert that class and class is same when invoking check if types are same.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void assertThatClassAndClassIsSameWhenInvokingCheckIfTypesAreSame() throws Exception {
        Method method = WhiteboxImpl.getMethod(WhiteboxImpl.class, "checkIfParameterTypesAreSame", boolean.class,
                Class[].class, Class[].class);
        boolean invokeMethod = (Boolean) method.invoke(WhiteboxImpl.class, false, new Class<?>[] { Class.class },
                new Class<?>[] { Class.class });
        assertThat(invokeMethod).isTrue();
    }

    /**
     * Gets the best candidate method returns matching method when no overloading.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void getBestCandidateMethodReturnsMatchingMethodWhenNoOverloading() throws Exception {
        final Method expectedMethod = ClassWithStandardMethod.class.getDeclaredMethod("myMethod", double.class);
        final Method actualMethod = WhiteboxImpl.getBestMethodCandidate(ClassWithStandardMethod.class, "myMethod",
                new Class<?>[] { double.class }, false);
        assertThat(actualMethod).isEqualTo(expectedMethod);
    }

    /**
     * Gets the best candidate method returns matching method when overloading.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void getBestCandidateMethodReturnsMatchingMethodWhenOverloading() throws Exception {
        final Method expectedMethod = ClassWithOverloadedMethods.class.getDeclaredMethod("overloaded", double.class,
                Child.class);
        final Method actualMethod = WhiteboxImpl.getBestMethodCandidate(ClassWithOverloadedMethods.class, "overloaded",
                new Class<?>[] { double.class, Child.class }, false);
        assertThat(actualMethod).isEqualTo(expectedMethod);
    }

    /**
     * Default methods are found.
     */
    @Test
    void defaultMethodsAreFound() {
        Method[] methods = WhiteboxImpl.getAllMethods(Collection.class);

        assertThat(methods).extracting(Method::getName).contains("stream");
    }

    /**
     * Test get method not exact parameter type match.
     *
     * @throws NoSuchMethodException
     *             the no such method exception
     */
    @Test
    void testGetMethodNotExactParameterTypeMatch() throws NoSuchMethodException {
        Method[] methods = WhiteboxImpl.getMethods(ClassWithMethodUsingBothPrimitiveTypeAndWrappedTypeArguments.class,
                "methodHavingBothPrimitiveTypeAndWrappedTypeArguments", new Class<?>[] { Integer.class, Integer.class },
                false);
        Method method = ClassWithMethodUsingBothPrimitiveTypeAndWrappedTypeArguments.class
                .getMethod("methodHavingBothPrimitiveTypeAndWrappedTypeArguments", Integer.class, int.class);
        assertEquals(methods[0], method);
    }
}
