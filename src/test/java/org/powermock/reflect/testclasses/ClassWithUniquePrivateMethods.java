/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

@SuppressWarnings("unused")
public class ClassWithUniquePrivateMethods {

    private boolean primitiveMethodWithOnlyOneArgument(double value) {
        return true;
    }

    private static boolean primitiveStaticMethodWithOnlyOneArgument(double value) {
        return true;
    }

    private boolean primitiveMethod(double value, double value2) {
        return true;
    }

    private Object twoArgs(double value, double value2, Object obj) {
        return new Object();
    }
}
