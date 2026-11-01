/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

public class ClassWithSimpleInternalState {

    private int someIntState = -1;

    private String someStringState = "-1";

    private static long someStaticLongState = -1L;

    public static long getLong() {
        return someStaticLongState;
    }

    public long getSomeStaticLongState() {
        return someStaticLongState;
    }

    public int getSomeIntState() {
        return someIntState;
    }

    public String getSomeStringState() {
        return someStringState;
    }

    public static void setLong(long state) {
        someStaticLongState = state;
    }
}
