/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.primitivesupport;

import java.util.HashMap;
import java.util.Map;

/**
 * The purpose of the Primitive Wrapper is to provide methods that deals with translating wrapper types to its related
 * primitive type.
 */
public class BoxedWrapper {
    /** The boxed wrapper. */
    private static final Map<Class<?>, Class<?>> boxedWrapper = new HashMap<Class<?>, Class<?>>();

    static {
        boxedWrapper.put(int.class, Integer.class);
        boxedWrapper.put(long.class, Long.class);
        boxedWrapper.put(float.class, Float.class);
        boxedWrapper.put(double.class, Double.class);
        boxedWrapper.put(boolean.class, Boolean.class);
        boxedWrapper.put(byte.class, Byte.class);
        boxedWrapper.put(short.class, Short.class);
        boxedWrapper.put(char.class, Character.class);
    }

    /**
     * Get the wrapped counter part from a primitive type. For example:
     * <p>
     * {@code getBoxedFromPrimitiveType(int.class)} will return {@code Integer.class}.
     *
     * @param primitiveType
     *            The primitive type to convert to its wrapper counter part.
     *
     * @return The boxed counter part or {@code null} if the class did not have a boxed counter part.
     */
    public static Class<?> getBoxedFromPrimitiveType(Class<?> primitiveType) {
        return boxedWrapper.get(primitiveType);
    }

    /**
     * Returns {@code true} if {@code type} has a primitive counter-part. E.g. if {@code type} if {@code Integer} then
     * this method will return {@code true}.
     *
     * @param type
     *            The type to check whether or not it has a primitive counter-part.
     *
     * @return {@code true} if this type has a primitive counter-part.
     */
    public static boolean hasBoxedCounterPart(Class<?> type) {
        return boxedWrapper.containsKey(type);
    }
}
