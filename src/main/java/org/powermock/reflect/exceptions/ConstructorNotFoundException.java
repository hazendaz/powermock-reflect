/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.exceptions;

/**
 * A run-time exception that may be thrown to indicate that a constructor was not found.
 */
public class ConstructorNotFoundException extends RuntimeException {
    /** The serial version uid. */
    private static final long serialVersionUID = -7329106318739007850L;

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     *
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *            method.
     */
    public ConstructorNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
     * exception's detail message.
     *
     * @param message
     *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <code>null</code>
     *            value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
