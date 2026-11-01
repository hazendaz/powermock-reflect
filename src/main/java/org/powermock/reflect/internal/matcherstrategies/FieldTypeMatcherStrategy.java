/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.matcherstrategies;

import java.lang.reflect.Field;

import org.powermock.reflect.exceptions.FieldNotFoundException;

/**
 * The Class FieldTypeMatcherStrategy.
 */
public class FieldTypeMatcherStrategy extends FieldMatcherStrategy {

    /** The expected field type. */
    final Class<?> expectedFieldType;

    /**
     * Instantiates a new field type matcher strategy.
     *
     * @param fieldType
     *            the field type
     */
    public FieldTypeMatcherStrategy(Class<?> fieldType) {
        if (fieldType == null) {
            throw new IllegalArgumentException("field type cannot be null.");
        }
        this.expectedFieldType = fieldType;
    }

    @Override
    public boolean matches(Field field) {
        return expectedFieldType.equals(field.getType());
    }

    @Override
    public void notFound(Class<?> type, boolean isInstanceField) throws FieldNotFoundException {
        throw new FieldNotFoundException("No %s field of type \"%s\" could be found in the class hierarchy of %s."
                .formatted(isInstanceField ? "instance" : "static", expectedFieldType.getName(), type.getName()));
    }

    @Override
    public String toString() {
        return "type " + expectedFieldType.getName();
    }
}
