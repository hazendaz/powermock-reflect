/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithSimpleStateOfSameType.
 */
public class ClassWithSimpleStateOfSameType {

    /** The some string state. */
    private String someStringState = "-1";
    /** The some other string state. */
    private String someOtherStringState = "-1";

    /**
     * Gets the some string state.
     *
     * @return the some string state
     */
    public String getSomeStringState() {
        return someStringState;
    }

    /**
     * Gets the some other string state.
     *
     * @return the some other string state
     */
    public String getSomeOtherStringState() {
        return someOtherStringState;
    }
}
