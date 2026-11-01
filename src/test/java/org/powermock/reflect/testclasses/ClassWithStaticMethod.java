/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

import java.util.Arrays;

/**
 * The Class ClassWithStaticMethod.
 */
public class ClassWithStaticMethod {

    /**
     * A static method.
     *
     * @param something
     *            the something
     *
     * @return the string
     */
    @SuppressWarnings({ "UnusedDeclaration" })
    private static String aStaticMethod(byte[] something) {
        return "hello";
    }

    /**
     * Another static method.
     *
     * @param object
     *            the object
     * @param something
     *            the something
     *
     * @return the string
     */
    @SuppressWarnings({ "UnusedDeclaration" })
    private static String anotherStaticMethod(Object object, byte[] something) {
        return object + " " + Arrays.toString(something);
    }
}
