/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithVarArgsConstructor2.
 */
public class ClassWithVarArgsConstructor2 {

    /** The strings. */
    private final String[] strings;

    /**
     * Instantiates a new class with var args constructor2.
     *
     * @param required
     *            the required
     * @param strings
     *            the strings
     */
    private ClassWithVarArgsConstructor2(String required, String... strings) {
        this.strings = new String[strings.length + 1];
        this.strings[0] = required;
        System.arraycopy(strings, 0, this.strings, 1, strings.length);
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
