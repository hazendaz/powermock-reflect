/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal;

/**
 * The Class ParameterTypesMatcher.
 */
class ParameterTypesMatcher {
    /** The is var args. */
    private boolean isVarArgs;
    /** The expected parameter types. */
    private Class<?>[] expectedParameterTypes;
    /** The actual parameter types. */
    private Class<?>[] actualParameterTypes;

    /**
     * Instantiates a new parameter types matcher.
     *
     * @param isVarArgs
     *            the is var args
     * @param expectedParameterTypes
     *            the expected parameter types
     * @param actualParameterTypes
     *            the actual parameter types
     */
    public ParameterTypesMatcher(boolean isVarArgs, Class<?>[] expectedParameterTypes,
            Class<?>... actualParameterTypes) {
        this.isVarArgs = isVarArgs;
        this.expectedParameterTypes = expectedParameterTypes;
        this.actualParameterTypes = actualParameterTypes;
    }

    /**
     * Is remain params var args.
     *
     * @param index
     *            the index
     * @param actualParameterType
     *            the actual parameter type
     *
     * @return true, if successful
     */
    private boolean isRemainParamsVarArgs(int index, Class<?> actualParameterType) {
        return isVarArgs && index == expectedParameterTypes.length - 1
                && actualParameterType.getComponentType().isAssignableFrom(expectedParameterTypes[index]);
    }

    /**
     * Is parameter types not match.
     *
     * @param actualParameterType
     *            the actual parameter type
     * @param expectedParameterType
     *            the expected parameter type
     *
     * @return true, if successful
     */
    private boolean isParameterTypesNotMatch(Class<?> actualParameterType, Class<?> expectedParameterType) {
        if (actualParameterType == null) {
            return false;
        }
        if (expectedParameterType == null) {
            return false;
        }
        return !actualParameterType.isAssignableFrom(expectedParameterType);
    }

    /**
     * Match.
     *
     * @return true, if successful
     */
    public boolean match() {
        assertParametersTypesNotNull();
        if (isParametersLengthMatch()) {
            return false;
        } else {
            return isParametersMatch();
        }
    }

    /**
     * Checks if is parameters length match.
     *
     * @return true, if successful
     */
    private boolean isParametersLengthMatch() {
        return expectedParameterTypes.length != actualParameterTypes.length;
    }

    /**
     * Assert parameters types not null.
     */
    private void assertParametersTypesNotNull() {
        if (expectedParameterTypes == null || actualParameterTypes == null) {
            throw new IllegalArgumentException("parameter types cannot be null");
        }
    }

    /**
     * Checks if is parameters match.
     *
     * @return true, if successful
     */
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
