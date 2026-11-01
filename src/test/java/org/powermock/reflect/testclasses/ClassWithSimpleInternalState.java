/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithSimpleInternalState.
 */
public class ClassWithSimpleInternalState {

    /** The some int state. */
    private int someIntState = -1;

    /** The some string state. */
    private String someStringState = "-1";

    /** The some static long state. */
    private static long someStaticLongState = -1L;

    /**
     * Gets the long.
     *
     * @return the long
     */
    public static long getLong() {
        return someStaticLongState;
    }

    /**
     * Gets the some static long state.
     *
     * @return the some static long state
     */
    public long getSomeStaticLongState() {
        return someStaticLongState;
    }

    /**
     * Gets the some int state.
     *
     * @return the some int state
     */
    public int getSomeIntState() {
        return someIntState;
    }

    /**
     * Gets the some string state.
     *
     * @return the some string state
     */
    public String getSomeStringState() {
        return someStringState;
    }

    /**
     * Sets the long.
     *
     * @param state
     *            the state
     */
    public static void setLong(long state) {
        someStaticLongState = state;
    }
}
