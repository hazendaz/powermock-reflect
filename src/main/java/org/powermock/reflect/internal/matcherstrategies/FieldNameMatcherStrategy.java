/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.matcherstrategies;

import java.lang.reflect.Field;

import org.powermock.reflect.exceptions.FieldNotFoundException;

public class FieldNameMatcherStrategy extends FieldMatcherStrategy {

    private final String fieldName;

    public FieldNameMatcherStrategy(String fieldName) {
        if (fieldName == null || fieldName.equals("") || fieldName.startsWith(" ")) {
            throw new IllegalArgumentException("field name cannot be null.");
        }
        this.fieldName = fieldName;
    }

    @Override
    public boolean matches(Field field) {
        return fieldName.equals(field.getName());
    }

    @Override
    public void notFound(Class<?> type, boolean isInstanceField) throws FieldNotFoundException {
        throw new FieldNotFoundException(
                String.format("No %s field named \"%s\" could be found in the class hierarchy of %s.",
                        isInstanceField ? "instance" : "static", fieldName, type.getName()));
    }

    @Override
    public String toString() {
        return "fieldName " + fieldName;
    }
}
