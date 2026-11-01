/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithPrimitiveConstructors.
 */
public class ClassWithPrimitiveConstructors {

    /** The value. */
    private final long value;

    /**
     * Instantiates a new class with primitive constructors.
     *
     * @param value
     *            the value
     */
    public ClassWithPrimitiveConstructors(long value) {

        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public long getValue() {
        return value;
    }
}
