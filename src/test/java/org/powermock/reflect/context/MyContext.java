/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.context;

/**
 * The Class MyContext.
 */
public class MyContext {
    /** The my int state. */
    private int myIntState = 42;

    /** The my string state. */
    private String myStringState = "myString";

    /** The my long state. */
    private static long myLongState = 21L;

    /**
     * Gets the my long state.
     *
     * @return the my long state
     */
    public long getMyLongState() {
        return myLongState;
    }

    /**
     * Gets the my int state.
     *
     * @return the my int state
     */
    public int getMyIntState() {
        return myIntState;
    }

    /**
     * Gets the my string state.
     *
     * @return the my string state
     */
    public String getMyStringState() {
        return myStringState;
    }
}
