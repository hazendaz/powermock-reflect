/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.internal;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powermock.reflect.internal.comparator.ComparatorFactory;

/**
 * This class search the best candidate in the given class to invoke constructor with given parameters.
 *
 * @param <T>
 *            the generic type
 */
class CandidateConstructorSearcher<T> {
    /** The class that contains the constructor to test. */
    private final Class<T> classThatContainsTheConstructorToTest;
    /** The argument types. */
    private final Class<?>[] argumentTypes;

    /**
     * Instantiates a new candidate constructor searcher.
     *
     * @param classThatContainsTheConstructorToTest
     *            the class that contains the constructor to test
     * @param argumentTypes
     *            the argument types
     */
    public CandidateConstructorSearcher(Class<T> classThatContainsTheConstructorToTest, Class<?>[] argumentTypes) {

        this.classThatContainsTheConstructorToTest = classThatContainsTheConstructorToTest;
        this.argumentTypes = argumentTypes;
    }

    /**
     * Find constructor.
     *
     * @return the constructor
     */
    public Constructor<T> findConstructor() {
        final Constructor<T>[] constructors = getConstructors();
        if (constructors.length == 0) {
            return null;
        }
        if (constructors.length == 1) {
            return constructors[0];
        } else {
            return findBestCandidate(constructors);

        }
    }

    /**
     * Find best candidate.
     *
     * @param constructors
     *            the constructors
     *
     * @return the constructor
     */
    private Constructor<T> findBestCandidate(Constructor<T>[] constructors) {
        // We've found overloaded constructor, we need to find the best one to invoke.
        Arrays.sort(constructors, ComparatorFactory.createConstructorComparator());
        return constructors[0];
    }

    /**
     * Gets the constructors.
     *
     * @return the constructors
     */
    @SuppressWarnings("unchecked")
    private Constructor<T>[] getConstructors() {

        try {
            Constructor<?>[] declaredConstructors = classThatContainsTheConstructorToTest.getDeclaredConstructors();
            List<Constructor<?>> constructors = new ArrayList<>();
            for (Constructor<?> constructor : declaredConstructors) {
                if (argumentsApplied(constructor)) {
                    constructors.add(constructor);
                }
            }
            return constructors.toArray(new Constructor[constructors.size()]);
        } catch (Exception e) {
            return new Constructor[0];
        }

    }

    /**
     * Arguments applied.
     *
     * @param constructor
     *            the constructor
     *
     * @return true, if successful
     */
    private boolean argumentsApplied(Constructor<?> constructor) {
        Class<?>[] constructorArgumentTypes = constructor.getParameterTypes();
        if (constructorArgumentTypes.length != argumentTypes.length) {
            return false;
        }

        for (int index = 0; index < argumentTypes.length; index++) {
            if (!constructorArgumentTypes[index].isAssignableFrom(argumentTypes[index])) {
                return false;
            }
        }

        return true;
    }

}
