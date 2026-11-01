/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.context;

/**
 * The Class OneInstanceAndOneStaticFieldOfSameTypeContext.
 */
public class OneInstanceAndOneStaticFieldOfSameTypeContext {

    /** The my string state. */
    private String myStringState = "myString";

    /** The my static string state. */
    private static String myStaticStringState = "21L";

    /**
     * Gets the my string state.
     *
     * @return the my string state
     */
    public String getMyStringState() {
        return myStringState;
    }

    /**
     * Gets the my static string state.
     *
     * @return the my static string state
     */
    public static String getMyStaticStringState() {
        return myStaticStringState;
    }
}
