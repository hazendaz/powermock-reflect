/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.proxy;

/**
 * The Interface UnproxiedType.
 */
public interface UnproxiedType {
    /**
     * Gets the original type.
     *
     * @return the original type
     */
    Class<?> getOriginalType();

    /**
     * Gets the interfaces.
     *
     * @return the interfaces
     */
    Class<?>[] getInterfaces();
}
