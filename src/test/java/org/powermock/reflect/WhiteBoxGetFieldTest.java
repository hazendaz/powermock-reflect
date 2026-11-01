/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.internal.WhiteboxImpl;

/**
 * A test case to demonstrate an issue {@link org.powermock.reflect.internal.WhiteboxImpl#getField(Class, String)}. As
 * the class hierarchy for the given Class is ascended, if the field is not found in the parent class, the interfaces
 * are checked before the grandparent class. The problem is that the getSuperclass() method will always return null for
 * the Class representing the interface. The current implementation of WhiteboxImpl blindly adds this null to the
 * {@code examine} LinkedList.
 *
 * @author Ben Chatelain
 */
class WhiteBoxGetFieldTest {

    interface SomeInterface {
    }

    class GrandParentClass {
        @SuppressWarnings("unused")
        private String fieldA;
    }

    class ParentClass extends GrandParentClass {
    }

    class ClassUnderTest extends ParentClass implements SomeInterface {
        @SuppressWarnings("unused")
        private String fieldB;
    }

    /**
     * Verifies that <a href="http://code.google.com/p/powermock/issues/detail?id=149">issue 149</a> has been resolved.
     */
    @Test
    void testGetField() {
        Field fieldA = WhiteboxImpl.getField(ClassUnderTest.class, "fieldA");
        assertThat(fieldA).isNotNull();
    }
}
