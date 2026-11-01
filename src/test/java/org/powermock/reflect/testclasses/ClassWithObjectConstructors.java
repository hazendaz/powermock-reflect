/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithObjectConstructors.
 */
public class ClassWithObjectConstructors {

    /** The name. */
    private final Object name;

    /**
     * Instantiates a new class with object constructors.
     *
     * @param name
     *            the name
     */
    protected ClassWithObjectConstructors(Object name) {

        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public Object getName() {
        return name;
    }

}
