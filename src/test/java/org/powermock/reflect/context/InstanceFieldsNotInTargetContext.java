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
    private String something = "something";

    private Map<?, ?> map = new HashMap<Object, Object>();

    public String getString() {
        return something;
    }

    public Map<?, ?> getMap() {
        return map;
    }
}
