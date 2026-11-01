/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal;

/**
 * The Class Constructor.
 */
class Constructor {

    /** The parameter types. */
    private final Class<?>[] parameterTypes;
    /** The constructor. */
    private java.lang.reflect.Constructor constructor;
    /** The is var args. */
    private boolean isVarArgs;

    /**
     * Instantiates a new constructor.
     *
     * @param constructor
     *            the constructor
     */
    Constructor(java.lang.reflect.Constructor constructor) {
        this.constructor = constructor;
        this.parameterTypes = constructor.getParameterTypes();
        this.isVarArgs = constructor.isVarArgs();
    }

    /**
     * Can be invoke with.
     *
     * @param arguments
     *            the arguments
     *
     * @return true, if successful
     */
    boolean canBeInvokeWith(Object[] arguments) {
        return new ParametersMatcher(isVarArgs, parameterTypes, arguments).match();
    }

    /**
     * Gets the java constructor.
     *
     * @return the java constructor
     */
    public java.lang.reflect.Constructor<?> getJavaConstructor() {
        return constructor;
    }

    /**
     * Checks if is var arg.
     *
     * @return true, if successful
     */
    public boolean isVarArg() {
        return isVarArgs;
    }
}
