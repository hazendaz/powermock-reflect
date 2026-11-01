/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithInterfaceConstructors.
 */
public class ClassWithInterfaceConstructors {

    /** The constructor interface. */
    private final ConstructorInterface constructorInterface;

    /**
     * Instantiates a new class with interface constructors.
     *
     * @param constructorInterface
     *            the constructor interface
     */
    public ClassWithInterfaceConstructors(ConstructorInterface constructorInterface) {
        this.constructorInterface = constructorInterface;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return constructorInterface.getValue();
    }

    /**
     * The Interface ConstructorInterface.
     */
    public interface ConstructorInterface {
        /**
         * Gets the value.
         *
         * @return the value
         */
        String getValue();
    }

    /**
     * The Class ConstructorInterfaceImpl.
     */
    public static class ConstructorInterfaceImpl implements ConstructorInterface {
        /** The value. */
        private final String value;

        /**
         * Instantiates a new constructor interface impl.
         *
         * @param value
         *            the value
         */
        public ConstructorInterfaceImpl(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}
