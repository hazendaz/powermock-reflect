/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.context;

public class OneInstanceAndOneStaticFieldOfSameTypeContext {

    private String myStringState = "myString";

    private static String myStaticStringState = "21L";

    public String getMyStringState() {
        return myStringState;
    }

    public static String getMyStaticStringState() {
        return myStaticStringState;
    }
}
