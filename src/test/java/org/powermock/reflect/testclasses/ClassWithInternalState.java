/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ClassWithInternalState {

    private static int staticState = 5;

    private static final int staticFinalIntState = 15;

    private static final Integer staticFinalIntegerState = 15;

    private static final String staticFinalStringState = "Some String";

    private int internalState = 0;

    private int anotherInternalState = -1;

    private final String finalString = "hello";

    private long internalLongState = 17;

    private String[] stringArray = new String[0];

    private Set<String> genericState = new HashSet<String>();

    private ClassWithPrivateMethods classWithPrivateMethods;

    public String getFinalString() {
        return finalString;
    }

    public void increaseInteralState() {
        internalState++;
    }

    public void decreaseInteralState() {
        internalState--;
    }

    public int getAnotherInternalState() {
        return anotherInternalState;
    }

    public static int getStaticState() {
        return staticState;
    }

    public static Integer getStaticFinalIntegerState() {
        return staticFinalIntegerState;
    }

    public ClassWithPrivateMethods getClassWithPrivateMethods() {
        return classWithPrivateMethods;
    }

    public long getInternalLongState() {
        return internalLongState;
    }

    public Set<String> getGenericState() {
        return genericState;
    }

    public static String methodWithArgument(InputStream inputStream) {
        return "";
    }

    public String[] getStringArray() {
        return stringArray;
    }
}
