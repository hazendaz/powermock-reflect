/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

import java.io.Serializable;

/**
 * The Class ClassWithSerializableState.
 */
@SuppressWarnings("unused")
public class ClassWithSerializableState {

    /** The serializable. */
    private Serializable serializable;

    /** The object array. */
    private Object[] objectArray;

    /**
     * Sets the serializable.
     *
     * @param serializable
     *            the serializable
     */
    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }

    /**
     * Sets the object array.
     *
     * @param objectArray
     *            the object array
     */
    public void setObjectArray(Object[] objectArray) {
        this.objectArray = objectArray;
    }
}
