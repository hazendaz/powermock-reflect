/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithUniquePrivateMethods.
 */
@SuppressWarnings("unused")
public class ClassWithUniquePrivateMethods {

    /**
     * Primitive method with only one argument.
     *
     * @param value
     *            the value
     *
     * @return true, if successful
     */
    private boolean primitiveMethodWithOnlyOneArgument(double value) {
        return true;
    }

    /**
     * Primitive static method with only one argument.
     *
     * @param value
     *            the value
     *
     * @return true, if successful
     */
    private static boolean primitiveStaticMethodWithOnlyOneArgument(double value) {
        return true;
    }

    /**
     * Primitive method.
     *
     * @param value
     *            the value
     * @param value2
     *            the value2
     *
     * @return true, if successful
     */
    private boolean primitiveMethod(double value, double value2) {
        return true;
    }

    /**
     * Two args.
     *
     * @param value
     *            the value
     * @param value2
     *            the value2
     * @param obj
     *            the obj
     *
     * @return the object
     */
    private Object twoArgs(double value, double value2, Object obj) {
        return new Object();
    }
}
