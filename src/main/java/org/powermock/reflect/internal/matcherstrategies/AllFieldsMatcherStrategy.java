/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.matcherstrategies;

import java.lang.reflect.Field;

import org.powermock.reflect.exceptions.FieldNotFoundException;

public class AllFieldsMatcherStrategy extends FieldMatcherStrategy {

    @Override
    public boolean matches(Field field) {
        return true;
    }

    @Override
    public void notFound(Class<?> type, boolean isInstanceField) throws FieldNotFoundException {
        throw new FieldNotFoundException(
                "No %s fields were declared in %s.".formatted(isInstanceField ? "instance" : "static", type.getName()));
    }
}
