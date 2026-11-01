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
public class InstanceFieldsNotInTargetContext {
    /** The something. */
    private String something = "something";

    /** The map. */
    private Map<?, ?> map = new HashMap<Object, Object>();

    /**
     * Gets the string.
     *
     * @return the string
     */
    public String getString() {
        return something;
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Map<?, ?> getMap() {
        return map;
    }
}
