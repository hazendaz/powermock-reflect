/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithOverloadedMethods.
 */
public class ClassWithOverloadedMethods {

    /**
     * Overloaded.
     *
     * @param value
     *            the value
     * @param parent
     *            the parent
     *
     * @return the string
     */
    public String overloaded(double value, Parent parent) {
        return "parent";
    }

    /**
     * Overloaded.
     *
     * @param value
     *            the value
     * @param child
     *            the child
     *
     * @return the string
     */
    public String overloaded(double value, Child child) {
        return "child";
    }
}
