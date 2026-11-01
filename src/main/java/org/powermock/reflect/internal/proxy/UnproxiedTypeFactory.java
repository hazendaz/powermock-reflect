/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class UnproxiedTypeFactory.
 */
class UnproxiedTypeFactory {

    /** The restricted interfaces. */
    private static final String[] RESTRICTED_INTERFACES = { "cglib.proxy.Factory" };

    /**
     * Create from interfaces.
     *
     * @param interfaces
     *            the interfaces
     *
     * @return the unproxied type
     */
    UnproxiedType createFromInterfaces(Class<?>[] interfaces) {
        Class<?>[] filteredInterfaces = filterInterfaces(interfaces);
        if (filteredInterfaces.length == 0) {
            return new UnproxiedTypeImpl(Object.class);
        }
        if (filteredInterfaces.length == 1) {
            return new UnproxiedTypeImpl(filteredInterfaces[0]);
        }
        return new UnproxiedTypeImpl(filteredInterfaces);
    }

    /**
     * Create from superclass and interfaces.
     *
     * @param superclass
     *            the superclass
     * @param interfaces
     *            the interfaces
     *
     * @return the unproxied type
     */
    UnproxiedType createFromSuperclassAndInterfaces(Class<?> superclass, Class<?>[] interfaces) {
        if (Object.class.equals(superclass)) {
            return createFromInterfaces(interfaces);
        }

        Class<?>[] filteredInterfaces = filterInterfaces(interfaces);

        return new UnproxiedTypeImpl(superclass, filteredInterfaces);
    }

    /**
     * Create from type.
     *
     * @param type
     *            the type
     *
     * @return the unproxied type
     */
    UnproxiedType createFromType(Class<?> type) {
        return new UnproxiedTypeImpl(type);
    }

    /**
     * Filter interfaces.
     *
     * @param interfaces
     *            the interfaces
     *
     * @return the class
     */
    private Class<?>[] filterInterfaces(Class<?>[] interfaces) {
        List<Class<?>> filtered = new ArrayList<Class<?>>();

        for (Class<?> anInterface : interfaces) {
            if (!isMockFrameworkInterface(anInterface)) {
                filtered.add(anInterface);
            }
        }

        return filtered.toArray(new Class[filtered.size()]);
    }

    /**
     * Is mock framework interface.
     *
     * @param anInterface
     *            the an interface
     *
     * @return true, if successful
     */
    private boolean isMockFrameworkInterface(Class<?> anInterface) {
        String name = anInterface.getName();
        for (String restrictedInterface : RESTRICTED_INTERFACES) {
            if (name.contains(restrictedInterface)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The Class UnproxiedTypeImpl.
     */
    private static class UnproxiedTypeImpl implements UnproxiedType {

        /** The type. */
        private final Class<?> type;
        /** The interfaces. */
        private final Class<?>[] interfaces;

        /**
         * Instantiates a new unproxied type impl.
         *
         * @param type
         *            the type
         */
        private UnproxiedTypeImpl(Class<?> type) {
            this.type = type;
            this.interfaces = new Class[0];
        }

        /**
         * Instantiates a new unproxied type impl.
         *
         * @param interfaces
         *            the interfaces
         */
        private UnproxiedTypeImpl(Class<?>[] interfaces) {
            this.type = null;
            this.interfaces = interfaces;
        }

        /**
         * Instantiates a new unproxied type impl.
         *
         * @param type
         *            the type
         * @param interfaces
         *            the interfaces
         */
        private UnproxiedTypeImpl(Class<?> type, Class<?>[] interfaces) {
            this.type = type;
            this.interfaces = interfaces;
        }

        @Override
        public Class<?> getOriginalType() {
            return type;
        }

        @Override
        public Class<?>[] getInterfaces() {
            return interfaces;
        }
    }
}
