/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

import java.sql.Connection;

/**
 * The Class ClassWithAMethod.
 */
public class ClassWithAMethod {

    /**
     * Connect.
     *
     * @param connection
     *            the connection
     */
    public void connect(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("param was null");
        }
    }
}
