/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithVarArgsConstructor.
 */
public class ClassWithVarArgsConstructor {

    /** The strings. */
    private final String[] strings;

    /**
     * Instantiates a new class with var args constructor.
     *
     * @param strings
     *            the strings
     */
    private ClassWithVarArgsConstructor(String... strings) {
        this.strings = strings;
    }

    /**
     * Gets the strings.
     *
     * @return the strings
     */
    public String[] getStrings() {
        return strings;
    }
}
