/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.matching;

/**
 * Defines strategies for field matching.
 */
public enum FieldMatchingStrategy {
    /**
     * All fields in the context must match <i>exactly</i> the fields in the target instance or class. This means that
     * an exception will be thrown unless all fields in the context are found in the target.
     */
    STRICT,
    /**
     * All fields in the context are copied to the target instance or class. The context may contain additional fields
     * not present in the target. Only fields that may be copied from the context to the target are taken into
     * consideration. An exception will not be thrown if a field exists in the context but is non-existent in the
     * target.
     */
    MATCHING
}
