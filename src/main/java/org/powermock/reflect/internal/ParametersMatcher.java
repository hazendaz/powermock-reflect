/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal;

/**
 * The Class ParametersMatcher.
 */
class ParametersMatcher {
    /** The parameter types. */
    private final Class<?>[] parameterTypes;
    /** The arguments. */
    private final Object[] arguments;
    /** The is var args. */
    private boolean isVarArgs;

    /**
     * Instantiates a new parameters matcher.
     *
     * @param isVarArgs
     *            the is var args
     * @param parameterTypes
     *            the parameter types
     * @param arguments
     *            the arguments
     */
    public ParametersMatcher(boolean isVarArgs, Class<?>[] parameterTypes, Object[] arguments) {
        this.isVarArgs = isVarArgs;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    /**
     * Match.
     *
     * @return true, if successful
     */
    public boolean match() {
        if ((arguments != null && (parameterTypes.length == arguments.length))) {
            if (parameterTypes.length == 0) {
                return true;
            }
            return checkArgumentTypesMatchParameterTypes(isVarArgs, parameterTypes, arguments);
        } else if (doesParameterTypesMatchForVarArgsInvocation(arguments)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check argument types match parameter types.
     *
     * @param isVarArgs
     *            the is var args
     * @param parameterTypes
     *            the parameter types
     * @param arguments
     *            the arguments
     *
     * @return true, if successful
     */
    boolean checkArgumentTypesMatchParameterTypes(boolean isVarArgs, Class<?>[] parameterTypes, Object[] arguments) {
        if (parameterTypes == null) {
            throw new IllegalArgumentException("parameter types cannot be null");
        } else if (!isVarArgs && arguments.length != parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];
            if (argument == null) {
                final int index;
                if (i >= parameterTypes.length) {
                    index = parameterTypes.length - 1;
                } else {
                    index = i;
                }
                final Class<?> type = parameterTypes[index];
                if (type.isPrimitive()) {
                    // Primitives cannot be null
                    return false;
                } else {
                    continue;
                }
            } else if (i >= parameterTypes.length) {
                if (WhiteboxImpl.isAssignableFrom(parameterTypes[parameterTypes.length - 1],
                        WhiteboxImpl.getType(argument))) {
                    continue;
                } else {
                    return false;
                }
            } else {
                boolean assignableFrom = WhiteboxImpl.isAssignableFrom(parameterTypes[i],
                        WhiteboxImpl.getType(argument));
                final boolean isClass = parameterTypes[i].equals(Class.class) && WhiteboxImpl.isClass(argument);
                if (!assignableFrom && !isClass) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Does parameter types match for var args invocation.
     *
     * @param arguments
     *            the arguments
     *
     * @return true, if successful
     */
    boolean doesParameterTypesMatchForVarArgsInvocation(Object[] arguments) {
        if (isVarArgs && arguments != null && arguments.length >= 1 && parameterTypes != null
                && parameterTypes.length >= 1) {
            final Class<?> componentType = parameterTypes[parameterTypes.length - 1].getComponentType();
            final Object lastArgument = arguments[arguments.length - 1];
            if (lastArgument != null) {
                final Class<?> lastArgumentTypeAsPrimitive = WhiteboxImpl.getTypeAsPrimitiveIfWrapped(lastArgument);
                final Class<?> varArgsParameterTypeAsPrimitive = WhiteboxImpl
                        .getTypeAsPrimitiveIfWrapped(componentType);
                isVarArgs = varArgsParameterTypeAsPrimitive.isAssignableFrom(lastArgumentTypeAsPrimitive);
            }
        }
        return isVarArgs && checkArgumentTypesMatchParameterTypes(isVarArgs, parameterTypes, arguments);

    }
}
