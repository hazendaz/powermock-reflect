/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.exceptions;

/**
 * A run-time exception that may be thrown to indicate that too many fields were found.
 */
public class TooManyFieldsFoundException extends RuntimeException {
    private static final long serialVersionUID = 1564231184610341053L;

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     *
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *            method.
     */
    public TooManyFieldsFoundException(String message) {
        super(message);
    }

}
