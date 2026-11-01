/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.matcherstrategies;

import java.lang.reflect.Field;

/**
 * The Class AssignableToFieldTypeMatcherStrategy.
 */
public class AssignableToFieldTypeMatcherStrategy extends FieldTypeMatcherStrategy {

    /**
     * Instantiates a new assignable to field type matcher strategy.
     *
     * @param fieldType
     *            the field type
     */
    public AssignableToFieldTypeMatcherStrategy(Class<?> fieldType) {
        super(fieldType);
    }

    @Override
    public boolean matches(Field field) {
        return expectedFieldType.isAssignableFrom(field.getType());
    }
}
