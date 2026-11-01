/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

public class ClassWithSeveralMethodsWithSameNameOneWithoutParameters {

    public double getDouble() {
        return Double.MAX_VALUE;
    }

    public double getDouble(double value) {
        return value;
    }

    public double getDouble(double value1, double value2) {
        return value1 + value2;
    }
}
