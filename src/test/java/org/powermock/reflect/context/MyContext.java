/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.context;

public class MyContext {
    private int myIntState = 42;

    private String myStringState = "myString";

    private static long myLongState = 21L;

    public long getMyLongState() {
        return myLongState;
    }

    public int getMyIntState() {
        return myIntState;
    }

    public String getMyStringState() {
        return myStringState;
    }
}
