/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

public class ClassWithStaticAndInstanceInternalStateOfSameType {

    private String stringState = "String state";

    private static String staticStringState = "Static String state";

    public String getStaticStringState() {
        return staticStringState;
    }

    public String getStringState() {
        return stringState;
    }

    public static void reset() {
        staticStringState = "Static String state";
    }
}
