/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal.comparator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * This comparator factory is used to create Comparators for {@link org.powermock.reflect.Whitebox} which are used to
 * find best candidates for constructor and method invocation.
 *
 * @see org.powermock.reflect.internal.WhiteboxImpl#getBestMethodCandidate(Class, String, Class[], boolean)
 * @see org.powermock.reflect.internal.WhiteboxImpl#getBestCandidateConstructor(Class, Class[], Object[])
 */
public class ComparatorFactory {

    /**
     * Instantiates a new comparator factory.
     */
    private ComparatorFactory() {
    }

    /**
     * Create constructor comparator.
     *
     * @return the comparator
     */
    public static Comparator<Constructor> createConstructorComparator() {
        return new ConstructorComparator(new ParametersComparator());
    }

    /**
     * Create method comparator.
     *
     * @return the comparator
     */
    public static Comparator<Method> createMethodComparator() {
        return new MethodComparator(new ParametersComparator());
    }

    /**
     * The Class ConstructorComparator.
     */
    public static class ConstructorComparator implements Comparator<Constructor> {
        /** The parameters comparator. */
        private final ParametersComparator parametersComparator;

        /**
         * Instantiates a new constructor comparator.
         *
         * @param parametersComparator
         *            the parameters comparator
         */
        private ConstructorComparator(ParametersComparator parametersComparator) {

            this.parametersComparator = parametersComparator;
        }

        @Override
        public int compare(Constructor constructor1, Constructor constructor2) {
            final Class<?>[] parameters1 = constructor1.getParameterTypes();
            final Class<?>[] parameters2 = constructor2.getParameterTypes();
            return parametersComparator.compare(parameters1, parameters2);
        }
    }

    /**
     * The Class MethodComparator.
     */
    public static class MethodComparator implements Comparator<Method> {
        /** The parameters comparator. */
        private final ParametersComparator parametersComparator;

        /**
         * Instantiates a new method comparator.
         *
         * @param parametersComparator
         *            the parameters comparator
         */
        private MethodComparator(ParametersComparator parametersComparator) {

            this.parametersComparator = parametersComparator;
        }

        @Override
        public int compare(Method m1, Method m2) {
            final Class<?>[] typesMethod1 = m1.getParameterTypes();
            final Class<?>[] typesMethod2 = m2.getParameterTypes();
            return parametersComparator.compare(typesMethod1, typesMethod2);

        }
    }

    /**
     * The Class ParametersComparator.
     */
    private static class ParametersComparator implements Comparator<Class[]> {

        @Override
        public int compare(Class[] params1, Class[] params2) {
            final int size = params1.length;
            for (int i = 0; i < size; i++) {
                Class<?> type1 = params1[i];
                Class<?> type2 = params2[i];
                if (!type1.equals(type2)) {
                    if (type1.isAssignableFrom(type2)) {
                        if (!type1.isArray() && type2.isArray()) {
                            return -1;
                        }
                        return 1;
                    } else {
                        if (type1.isArray() && !type2.isArray()) {
                            return 1;
                        }
                        return -1;
                    }
                }
            }
            return 0;
        }
    }
}
