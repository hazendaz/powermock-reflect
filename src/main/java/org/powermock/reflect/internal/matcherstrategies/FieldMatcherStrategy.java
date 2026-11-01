/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.matcherstrategies;

import java.lang.reflect.Field;

import org.powermock.reflect.exceptions.FieldNotFoundException;

/**
 * Class that should be implemented by field matching strategies.
 */
public abstract class FieldMatcherStrategy {

    /**
     * A field matcher that checks if a field matches a given criteria.
     *
     * @param field
     *            The field to check whether it matches the strategy or not.
     *
     * @return {@code true} if this field matches the strategy, {@code false} otherwise.
     */
    public abstract boolean matches(Field field);

    /**
     * Throws an {@link FieldNotFoundException} if the strategy criteria could not be found.
     *
     * @param type
     *            The type of the object that was not found.
     * @param isInstanceField
     *            {@code true} if the field that was looked after was an instance field or {@code false} if it was a
     *            static field.
     *
     * @throws FieldNotFoundException
     *             the field not found exception
     */
    public abstract void notFound(Class<?> type, boolean isInstanceField) throws FieldNotFoundException;
}
