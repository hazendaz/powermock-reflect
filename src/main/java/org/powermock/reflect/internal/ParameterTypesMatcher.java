/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal;

/**
 *
 */
class ParameterTypesMatcher {
    private boolean isVarArgs;
    private Class<?>[] expectedParameterTypes;
    private Class<?>[] actualParameterTypes;

    public ParameterTypesMatcher(boolean isVarArgs, Class<?>[] expectedParameterTypes,
            Class<?>... actualParameterTypes) {
        this.isVarArgs = isVarArgs;
        this.expectedParameterTypes = expectedParameterTypes;
        this.actualParameterTypes = actualParameterTypes;
    }

    private boolean isRemainParamsVarArgs(int index, Class<?> actualParameterType) {
        return isVarArgs && index == expectedParameterTypes.length - 1
                && actualParameterType.getComponentType().isAssignableFrom(expectedParameterTypes[index]);
    }

    private boolean isParameterTypesNotMatch(Class<?> actualParameterType, Class<?> expectedParameterType) {
        if (actualParameterType == null) {
            return false;
        }
        if (expectedParameterType == null) {
            return false;
        }
        return !actualParameterType.isAssignableFrom(expectedParameterType);
    }

    public boolean match() {
        assertParametersTypesNotNull();
        if (isParametersLengthMatch()) {
            return false;
        } else {
            return isParametersMatch();
        }
    }

    private boolean isParametersLengthMatch() {
        return expectedParameterTypes.length != actualParameterTypes.length;
    }

    private void assertParametersTypesNotNull() {
        if (expectedParameterTypes == null || actualParameterTypes == null) {
            throw new IllegalArgumentException("parameter types cannot be null");
        }
    }

    private Boolean isParametersMatch() {
        for (int index = 0; index < expectedParameterTypes.length; index++) {
            final Class<?> actualParameterType = actualParameterTypes[index];
            if (isRemainParamsVarArgs(index, actualParameterType)) {
                return true;
            } else {
                final Class<?> expectedParameterType = expectedParameterTypes[index];
                if (isParameterTypesNotMatch(actualParameterType, expectedParameterType)) {
                    return false;
                }
            }
        }
        return true;
    }
}
