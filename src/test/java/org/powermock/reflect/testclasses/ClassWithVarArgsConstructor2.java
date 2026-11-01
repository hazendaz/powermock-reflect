/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

public class ClassWithVarArgsConstructor2 {

    private final String[] strings;

    private ClassWithVarArgsConstructor2(String required, String... strings) {
        this.strings = new String[strings.length + 1];
        this.strings[0] = required;
        System.arraycopy(strings, 0, this.strings, 1, strings.length);
    }

    public String[] getStrings() {
        return strings;
    }
}
