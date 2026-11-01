/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithStaticAndInstanceInternalStateOfSameType.
 */
public class ClassWithStaticAndInstanceInternalStateOfSameType {

    /** The string state. */
    private String stringState = "String state";

    /** The static string state. */
    private static String staticStringState = "Static String state";

    /**
     * Gets the static string state.
     *
     * @return the static string state
     */
    public String getStaticStringState() {
        return staticStringState;
    }

    /**
     * Gets the string state.
     *
     * @return the string state
     */
    public String getStringState() {
        return stringState;
    }

    /**
     * Reset.
     */
    public static void reset() {
        staticStringState = "Static String state";
    }
}
