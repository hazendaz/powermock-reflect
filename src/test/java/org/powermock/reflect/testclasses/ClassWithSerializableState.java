/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ClassWithSerializableState {

    private Serializable serializable;

    private Object[] objectArray;

    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }

    public void setObjectArray(Object[] objectArray) {
        this.objectArray = objectArray;
    }
}
