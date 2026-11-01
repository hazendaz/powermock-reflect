/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithSeveralMethodsWithSameNameOneWithoutParameters.
 */
public class ClassWithSeveralMethodsWithSameNameOneWithoutParameters {

    /**
     * Gets the double.
     *
     * @return the double
     */
    public double getDouble() {
        return Double.MAX_VALUE;
    }

    /**
     * Get double.
     *
     * @param value
     *            the value
     *
     * @return the double
     */
    public double getDouble(double value) {
        return value;
    }

    /**
     * Get double.
     *
     * @param value1
     *            the value1
     * @param value2
     *            the value2
     *
     * @return the double
     */
    public double getDouble(double value1, double value2) {
        return value1 + value2;
    }
}
