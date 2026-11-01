/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

@SuppressWarnings("unused")
public class ClassWithPrivateMethods {

    private boolean primitiveMethod(double value) {
        return true;
    }

    private boolean wrappedMethod(Double value) {
        return true;
    }

    private String methodWithPrimitiveIntAndString(int myInt, String aString) {
        return aString + Integer.toString(myInt);
    }

    private int methodWithPrimitiveAndWrappedInt(int myInt, Integer myInt2) {
        return myInt + myInt2;
    }

    private String evilConcatOfStrings(String[] strings) {
        String returnValue = "";
        for (String string : strings) {
            returnValue += string;
        }
        return returnValue;
    }

    private int varArgsMethod(int... ints) {
        int sum = 0;
        for (int i : ints) {
            sum += i;
        }
        return sum;
    }

    private ClassWithInternalState methodWithObjectArgument(ClassWithInternalState c) {
        return c;
    }

    private Class<? super ClassWithInternalState> methodWithClassArgument(Class<? super ClassWithInternalState> c) {
        return c;
    }

    private int varArgsMethod(int value) {
        return value * 2;
    }

    private int varArgsMethod2(int value, int... moreValues) {
        return value * 2 + moreValues.length;
    }
}
