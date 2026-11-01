/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

/**
 * The Class ClassWithPrivateMethods.
 */
@SuppressWarnings("unused")
public class ClassWithPrivateMethods {

    /**
     * Primitive method.
     *
     * @param value
     *            the value
     *
     * @return true, if successful
     */
    private boolean primitiveMethod(double value) {
        return true;
    }

    /**
     * Wrapped method.
     *
     * @param value
     *            the value
     *
     * @return true, if successful
     */
    private boolean wrappedMethod(Double value) {
        return true;
    }

    /**
     * Method with primitive int and string.
     *
     * @param myInt
     *            the my int
     * @param aString
     *            the a string
     *
     * @return the string
     */
    private String methodWithPrimitiveIntAndString(int myInt, String aString) {
        return aString + Integer.toString(myInt);
    }

    /**
     * Method with primitive and wrapped int.
     *
     * @param myInt
     *            the my int
     * @param myInt2
     *            the my int2
     *
     * @return the int
     */
    private int methodWithPrimitiveAndWrappedInt(int myInt, Integer myInt2) {
        return myInt + myInt2;
    }

    /**
     * Evil concat of strings.
     *
     * @param strings
     *            the strings
     *
     * @return the string
     */
    private String evilConcatOfStrings(String[] strings) {
        String returnValue = "";
        for (String string : strings) {
            returnValue += string;
        }
        return returnValue;
    }

    /**
     * Var args method.
     *
     * @param ints
     *            the ints
     *
     * @return the int
     */
    private int varArgsMethod(int... ints) {
        int sum = 0;
        for (int i : ints) {
            sum += i;
        }
        return sum;
    }

    /**
     * Method with object argument.
     *
     * @param c
     *            the c
     *
     * @return the class with internal state
     */
    private ClassWithInternalState methodWithObjectArgument(ClassWithInternalState c) {
        return c;
    }

    /**
     * Method with class argument.
     *
     * @param c
     *            the c
     *
     * @return the class
     */
    private Class<? super ClassWithInternalState> methodWithClassArgument(Class<? super ClassWithInternalState> c) {
        return c;
    }

    /**
     * Var args method.
     *
     * @param value
     *            the value
     *
     * @return the int
     */
    private int varArgsMethod(int value) {
        return value * 2;
    }

    /**
     * Var args method2.
     *
     * @param value
     *            the value
     * @param moreValues
     *            the more values
     *
     * @return the int
     */
    private int varArgsMethod2(int value, int... moreValues) {
        return value * 2 + moreValues.length;
    }
}
