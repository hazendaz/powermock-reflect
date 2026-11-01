/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

public class ClassWithStaticMethod {

    @SuppressWarnings({ "UnusedDeclaration" })
    private static String aStaticMethod(byte[] something) {
        return "hello";
    }

    @SuppressWarnings({ "UnusedDeclaration" })
    private static String anotherStaticMethod(Object object, byte[] something) {
        return object + " " + something;
    }
}
