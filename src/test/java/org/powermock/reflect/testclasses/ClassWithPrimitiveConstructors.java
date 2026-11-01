/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 *
 */
public class ClassWithPrimitiveConstructors {

    private final long value;

    public ClassWithPrimitiveConstructors(long value) {

        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
