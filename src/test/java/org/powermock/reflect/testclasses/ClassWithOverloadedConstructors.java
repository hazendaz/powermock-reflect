/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithOverloadedConstructors.
 */
public class ClassWithOverloadedConstructors {

    /** The name. */
    private final String name;
    /** The bool. */
    private final boolean bool;
    /** The bool1. */
    private final boolean bool1;

    /**
     * Instantiates a new class with overloaded constructors.
     *
     * @param name
     *            the name
     */
    protected ClassWithOverloadedConstructors(String name) {

        this(name, false, false);
    }

    /**
     * Instantiates a new class with overloaded constructors.
     *
     * @param bool
     *            the bool
     * @param name
     *            the name
     */
    protected ClassWithOverloadedConstructors(boolean bool, String name) {
        this(name, bool, false);
    }

    /**
     * Instantiates a new class with overloaded constructors.
     *
     * @param name
     *            the name
     * @param bool
     *            the bool
     * @param bool1
     *            the bool1
     */
    protected ClassWithOverloadedConstructors(String name, boolean bool, boolean bool1) {
        this.name = name;
        this.bool = bool;
        this.bool1 = bool1;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if is bool.
     *
     * @return true, if successful
     */
    public boolean isBool() {
        return bool;
    }

    /**
     * Checks if is bool1.
     *
     * @return true, if successful
     */
    public boolean isBool1() {
        return bool1;
    }
}
