/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class ClassWithInternalState.
 */
public class ClassWithInternalState {

    /** The static state. */
    private static int staticState = 5;

    /** The static final int state. */
    private static final int staticFinalIntState = 15;

    /** The static final integer state. */
    private static final Integer staticFinalIntegerState = 15;

    /** The static final string state. */
    private static final String staticFinalStringState = "Some String";

    /** The internal state. */
    private int internalState = 0;

    /** The another internal state. */
    private int anotherInternalState = -1;

    /** The final string. */
    private final String finalString = "hello";

    /** The internal long state. */
    private long internalLongState = 17;

    /** The string array. */
    private String[] stringArray = new String[0];

    /** The generic state. */
    private Set<String> genericState = new HashSet<String>();

    /** The class with private methods. */
    private ClassWithPrivateMethods classWithPrivateMethods;

    /**
     * Gets the final string.
     *
     * @return the final string
     */
    public String getFinalString() {
        return finalString;
    }

    /**
     * Increase interal state.
     */
    public void increaseInteralState() {
        internalState++;
    }

    /**
     * Decrease interal state.
     */
    public void decreaseInteralState() {
        internalState--;
    }

    /**
     * Gets the another internal state.
     *
     * @return the another internal state
     */
    public int getAnotherInternalState() {
        return anotherInternalState;
    }

    /**
     * Gets the static state.
     *
     * @return the static state
     */
    public static int getStaticState() {
        return staticState;
    }

    /**
     * Gets the static final integer state.
     *
     * @return the static final integer state
     */
    public static Integer getStaticFinalIntegerState() {
        return staticFinalIntegerState;
    }

    /**
     * Gets the class with private methods.
     *
     * @return the class with private methods
     */
    public ClassWithPrivateMethods getClassWithPrivateMethods() {
        return classWithPrivateMethods;
    }

    /**
     * Gets the internal long state.
     *
     * @return the internal long state
     */
    public long getInternalLongState() {
        return internalLongState;
    }

    /**
     * Gets the generic state.
     *
     * @return the generic state
     */
    public Set<String> getGenericState() {
        return genericState;
    }

    /**
     * Method with argument.
     *
     * @param inputStream
     *            the input stream
     *
     * @return the string
     */
    public static String methodWithArgument(InputStream inputStream) {
        return "";
    }

    /**
     * Gets the string array.
     *
     * @return the string array
     */
    public String[] getStringArray() {
        return stringArray;
    }
}
