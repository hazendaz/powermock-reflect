/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.context;

import java.util.HashMap;
import java.util.Map;

/**
 * The purpose of this context is that it should define fields not available in the target object to where the state is
 * supposed to be copied.
 */
public class ClassFieldsNotInTargetContext {
    /** The something. */
    private static long something = 42L;

    /** The map. */
    private static Map<?, ?> map = new HashMap<Object, Object>();

    /**
     * Gets the long.
     *
     * @return the long
     */
    public static long getLong() {
        return something;
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public static Map<?, ?> getMap() {
        return map;
    }
}
