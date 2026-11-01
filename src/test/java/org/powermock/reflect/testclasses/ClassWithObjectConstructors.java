/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 *
 */
public class ClassWithObjectConstructors {

    private final Object name;

    protected ClassWithObjectConstructors(Object name) {

        this.name = name;
    }

    public Object getName() {
        return name;
    }

}
