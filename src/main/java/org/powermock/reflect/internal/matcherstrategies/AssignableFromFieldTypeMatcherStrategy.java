/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.matcherstrategies;

import java.lang.reflect.Field;

import org.powermock.reflect.exceptions.FieldNotFoundException;
import org.powermock.reflect.internal.primitivesupport.PrimitiveWrapper;

/**
 * The Class AssignableFromFieldTypeMatcherStrategy.
 */
public class AssignableFromFieldTypeMatcherStrategy extends FieldTypeMatcherStrategy {

    /** The primitive counterpart. */
    private final Class<?> primitiveCounterpart;

    /**
     * Instantiates a new assignable from field type matcher strategy.
     *
     * @param fieldType
     *            the field type
     */
    public AssignableFromFieldTypeMatcherStrategy(Class<?> fieldType) {
        super(fieldType);
        primitiveCounterpart = PrimitiveWrapper.getPrimitiveFromWrapperType(expectedFieldType);
    }

    @Override
    public boolean matches(Field field) {
        Class<?> actualFieldType = field.getType();
        return actualFieldType.isAssignableFrom(expectedFieldType)
                || (primitiveCounterpart != null && actualFieldType.isAssignableFrom(primitiveCounterpart));
    }

    @Override
    public void notFound(Class<?> type, boolean isInstanceField) throws FieldNotFoundException {
        throw new FieldNotFoundException(
                "No %s field assignable from \"%s\" could be found in the class hierarchy of %s.".formatted(
                        isInstanceField ? "instance" : "static", expectedFieldType.getName(), type.getName()));
    }

    @Override
    public String toString() {
        return "type " + (primitiveCounterpart == null ? expectedFieldType.getName() : primitiveCounterpart.getName());
    }
}
