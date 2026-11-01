/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.context.ClassFieldsNotInTargetContext;
import org.powermock.reflect.context.InstanceFieldsNotInTargetContext;
import org.powermock.reflect.context.MyContext;
import org.powermock.reflect.context.MyIntContext;
import org.powermock.reflect.context.MyStringContext;
import org.powermock.reflect.context.OneInstanceAndOneStaticFieldOfSameTypeContext;
import org.powermock.reflect.exceptions.FieldNotFoundException;
import org.powermock.reflect.exceptions.MethodNotFoundException;
import org.powermock.reflect.exceptions.TooManyFieldsFoundException;
import org.powermock.reflect.exceptions.TooManyMethodsFoundException;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.powermock.reflect.matching.FieldMatchingStrategy;
import org.powermock.reflect.testclasses.AbstractClass;
import org.powermock.reflect.testclasses.AnInterface;
import org.powermock.reflect.testclasses.Child;
import org.powermock.reflect.testclasses.ClassWithAMethod;
import org.powermock.reflect.testclasses.ClassWithChildThatHasInternalState;
import org.powermock.reflect.testclasses.ClassWithInterfaceConstructors;
import org.powermock.reflect.testclasses.ClassWithInterfaceConstructors.ConstructorInterface;
import org.powermock.reflect.testclasses.ClassWithInterfaceConstructors.ConstructorInterfaceImpl;
import org.powermock.reflect.testclasses.ClassWithInternalState;
import org.powermock.reflect.testclasses.ClassWithList;
import org.powermock.reflect.testclasses.ClassWithObjectConstructors;
import org.powermock.reflect.testclasses.ClassWithOverloadedConstructors;
import org.powermock.reflect.testclasses.ClassWithOverloadedMethods;
import org.powermock.reflect.testclasses.ClassWithOverriddenMethod;
import org.powermock.reflect.testclasses.ClassWithPrimitiveConstructors;
import org.powermock.reflect.testclasses.ClassWithPrivateMethods;
import org.powermock.reflect.testclasses.ClassWithSerializableState;
import org.powermock.reflect.testclasses.ClassWithSeveralMethodsWithSameName;
import org.powermock.reflect.testclasses.ClassWithSeveralMethodsWithSameNameOneWithoutParameters;
import org.powermock.reflect.testclasses.ClassWithSimpleInternalState;
import org.powermock.reflect.testclasses.ClassWithStaticAndInstanceInternalStateOfSameType;
import org.powermock.reflect.testclasses.ClassWithStaticMethod;
import org.powermock.reflect.testclasses.ClassWithUniquePrivateMethods;
import org.powermock.reflect.testclasses.ClassWithVarArgsConstructor;
import org.powermock.reflect.testclasses.ClassWithVarArgsConstructor2;

/**
 * Tests the WhiteBox's functionality.
 */
class WhiteBoxTest {

    /**
     * Test find method class containing method with no parameters.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testFindMethod_classContainingMethodWithNoParameters() throws Exception {
        Method expected = ClassWithSeveralMethodsWithSameNameOneWithoutParameters.class.getMethod("getDouble");
        Method actual = WhiteboxImpl
                .findMethodOrThrowException(ClassWithSeveralMethodsWithSameNameOneWithoutParameters.class, "getDouble");
        assertEquals(expected, actual);
    }

    /**
     * Test find method class containing only methods with parameters.
     */
    @Test
    void testFindMethod_classContainingOnlyMethodsWithParameters() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            WhiteboxImpl.findMethodOrThrowException(ClassWithSeveralMethodsWithSameName.class, "getDouble");

        }, "Should throw runtime exception!");
    }

    /**
     * Test find method no method found.
     */
    @Test
    void testFindMethod_noMethodFound() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            WhiteboxImpl.findMethodOrThrowException(ClassWithSeveralMethodsWithSameName.class, "getDouble2");

        }, "Should throw runtime exception!");
    }

    /**
     * Test get internal state object.
     */
    @Test
    void testGetInternalState_object() {
        ClassWithInternalState tested = new ClassWithInternalState();
        tested.increaseInteralState();
        Object internalState = Whitebox.getInternalState(tested, "internalState");
        assertTrue(internalState instanceof Integer, "InternalState should be instanceof Integer");
        assertEquals(1, internalState);
    }

    /**
     * Test get internal state parmaterized type.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetInternalState_parmaterizedType() {
        ClassWithInternalState tested = new ClassWithInternalState();
        tested.increaseInteralState();
        int internalState = Whitebox.getInternalState(tested, "internalState", tested.getClass(), int.class);
        assertEquals(1, internalState);
    }

    /**
     * Test set internal state.
     */
    @Test
    void testSetInternalState() {
        ClassWithInternalState tested = new ClassWithInternalState();
        tested.increaseInteralState();
        Whitebox.setInternalState(tested, "anotherInternalState", 2);
        assertEquals(2, tested.getAnotherInternalState());
    }

    /**
     * Test set internal state with multiple values.
     */
    @Test
    void testSetInternalStateWithMultipleValues() {
        ClassWithInternalState tested = new ClassWithInternalState();
        final ClassWithPrivateMethods classWithPrivateMethods = new ClassWithPrivateMethods();
        final String stringState = "someStringState";
        Whitebox.setInternalState(tested, classWithPrivateMethods, stringState);
        assertEquals(stringState, Whitebox.getInternalState(tested, String.class));
        assertSame(classWithPrivateMethods, Whitebox.getInternalState(tested, ClassWithPrivateMethods.class));
    }

    /**
     * Test set internal state super class.
     */
    @Test
    void testSetInternalState_superClass() {
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        tested.increaseInteralState();
        Whitebox.setInternalState(tested, "anotherInternalState", 2, ClassWithInternalState.class);
        assertEquals(2, tested.getAnotherInternalState());
    }

    /**
     * Test get internal state super class object.
     */
    @Test
    void testGetInternalState_superClass_object() {
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Object internalState = Whitebox.getInternalState(tested, "internalState", ClassWithInternalState.class);
        assertEquals(0, internalState);
    }

    /**
     * Test get internal state super class parameterized.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetInternalState_superClass_parameterized() {
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        int internalState = Whitebox.getInternalState(tested, "internalState", ClassWithInternalState.class, int.class);
        assertEquals(0, internalState);
    }

    /**
     * Test invoke private method primtive type.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethod_primtiveType() throws Exception {
        assertTrue(Whitebox.<Boolean> invokeMethod(new ClassWithPrivateMethods(), "primitiveMethod", 8.2));
    }

    /**
     * Test invoke private method primtive type without specifying method name.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethod_primtiveType_withoutSpecifyingMethodName() throws Exception {
        assertTrue((Boolean) Whitebox.invokeMethod(new ClassWithUniquePrivateMethods(), 8.2d, 8.4d));
    }

    /**
     * This test actually invokes the <code>finalize</code> method of <code>java.lang.Object</code> because we supply no
     * method name and no arguments. <code>finalize</code> is thus the first method found and it'll be executed.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    @Disabled("Invokes different methods on PC and MAC (hashCode on mac)")
    void testInvokePrivateMethod_withoutSpecifyingMethodName_noArguments() throws Exception {
        assertNull(Whitebox.invokeMethod(new ClassWithUniquePrivateMethods()));
    }

    /**
     * Test invoke private method without specifying method name assert that null works.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethod_withoutSpecifyingMethodName_assertThatNullWorks() throws Exception {
        assertTrue(Whitebox.invokeMethod(new ClassWithUniquePrivateMethods(), 8.2d, 8.3d, null) instanceof Object);
    }

    /**
     * This test should actually fail since equals takes an Object and we pass in a primitive wrapped as a Double. Thus
     * PowerMock cannot determine whether to invoke the single argument method defined in
     * {@link ClassWithUniquePrivateMethods} or the {@link Object#equals(Object)} method because we could potentially
     * invoke equals with a Double.
     */
    @Test
    void testInvokePrivateMethod_withoutSpecifyingMethodName_onlyOneArgument() {
        Assertions.assertThrows(TooManyMethodsFoundException.class, () -> {
            Whitebox.invokeMethod(new ClassWithUniquePrivateMethods(), 8.2d);
        });
    }

    /**
     * Test invoke static private method without specifying method name only one argument.
     */
    @Test
    void testInvokeStaticPrivateMethod_withoutSpecifyingMethodName_onlyOneArgument() {
        Assertions.assertThrows(TooManyMethodsFoundException.class, () -> {
            Whitebox.invokeMethod(ClassWithUniquePrivateMethods.class, 8.2d);
        });
    }

    /**
     * Test invoke private method primtive type wrapped.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethod_primtiveType_Wrapped() throws Exception {
        assertTrue(
                (Boolean) Whitebox.invokeMethod(new ClassWithPrivateMethods(), "primitiveMethod", Double.valueOf(8.2)));
    }

    /**
     * Test invoke private method wrapped type.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethod_wrappedType() throws Exception {
        assertTrue(
                (Boolean) Whitebox.invokeMethod(new ClassWithPrivateMethods(), "wrappedMethod", Double.valueOf(8.2)));
    }

    /**
     * Test invoke private method wrapped type primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethod_wrappedType_primitive() throws Exception {
        assertTrue((Boolean) Whitebox.invokeMethod(new ClassWithPrivateMethods(), "wrappedMethod", 8.2));
    }

    /**
     * Test method with primitive int and string primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testMethodWithPrimitiveIntAndString_primitive() throws Exception {
        assertEquals("My int value is: " + 8, Whitebox.invokeMethod(new ClassWithPrivateMethods(),
                "methodWithPrimitiveIntAndString", 8, "My int value is: "));
    }

    /**
     * Test method with primitive int and string wrapped.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testMethodWithPrimitiveIntAndString_Wrapped() throws Exception {
        assertEquals("My int value is: " + 8, Whitebox.invokeMethod(new ClassWithPrivateMethods(),
                "methodWithPrimitiveIntAndString", Integer.valueOf(8), "My int value is: "));
    }

    /**
     * Test method with primitive and wrapped int primtive wrapped.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testMethodWithPrimitiveAndWrappedInt_primtive_wrapped() throws Exception {
        assertEquals(Integer.valueOf(17), Whitebox.invokeMethod(new ClassWithPrivateMethods(),
                "methodWithPrimitiveAndWrappedInt", new Class[] { int.class, Integer.class }, 9, Integer.valueOf(8)));
    }

    /**
     * Test static state.
     */
    @Test
    void testStaticState() {
        final Integer expected = Integer.valueOf(123);
        Whitebox.setInternalState(ClassWithInternalState.class, "staticState", expected);
        assertEquals(expected, ClassWithInternalState.getStaticState());
        assertEquals(expected, Whitebox.getInternalState(ClassWithInternalState.class, "staticState"));
    }

    /**
     * Test static final primitive state.
     */
    @Test
    void testStaticFinalPrimitiveState() {
        Whitebox.setInternalState(ClassWithInternalState.class, "staticFinalIntState", 123);
        assertEquals(Integer.valueOf(123),
                Whitebox.getInternalState(ClassWithInternalState.class, "staticFinalIntState"));
    }

    /**
     * Test static final string state.
     */
    @Test
    void testStaticFinalStringState() {
        Whitebox.setInternalState(ClassWithInternalState.class, "staticFinalStringState", "Brand new string");
        assertEquals("Brand new string",
                Whitebox.getInternalState(ClassWithInternalState.class, "staticFinalStringState"));
    }

    /**
     * Test static final object.
     *
     * @throws NoSuchFieldException
     *             the no such field exception
     */
    @Test
    void testStaticFinalObject() throws NoSuchFieldException {
        int modifiersBeforeSet = ClassWithInternalState.class.getDeclaredField("staticFinalIntegerState")
                .getModifiers();
        Integer newValue = ClassWithInternalState.getStaticFinalIntegerState() + 1;

        Whitebox.setInternalState(ClassWithInternalState.class, "staticFinalIntegerState", newValue);

        int modifiersAfterSet = ClassWithInternalState.class.getDeclaredField("staticFinalIntegerState").getModifiers();
        assertEquals(newValue, ClassWithInternalState.getStaticFinalIntegerState());
        assertEquals(modifiersBeforeSet, modifiersAfterSet);
    }

    /**
     * Verifies that the http://code.google.com/p/powermock/issues/detail?id=6 is fixed.
     */
    @Test
    void testInvokeMethodWithNullParameter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.invokeMethod(null, "method");
        });
    }

    /**
     * Test invoke constructor with null parameter.
     */
    @Test
    void testInvokeConstructorWithNullParameter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.invokeConstructor(null, "constructor");
        });
    }

    /**
     * Test get internal with null parameter.
     */
    @Test
    void testGetInternalWithNullParameter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.getInternalState(null, "state");
        });
    }

    /**
     * Test set internal with null parameter.
     */
    @Test
    void testSetInternalWithNullParameter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.setInternalState(null, "state", new Object());
        });
    }

    /**
     * Test instantiate var args only constructor.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInstantiateVarArgsOnlyConstructor() throws Exception {
        final String argument1 = "argument1";
        final String argument2 = "argument2";
        ClassWithVarArgsConstructor instance = Whitebox.invokeConstructor(ClassWithVarArgsConstructor.class, argument1,
                argument2);
        String[] strings = instance.getStrings();
        assertEquals(2, strings.length);
        assertEquals(argument1, strings[0]);
        assertEquals(argument2, strings[1]);
    }

    /**
     * Test instantiate var args only constructor no arguments.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInstantiateVarArgsOnlyConstructor_noArguments() throws Exception {
        ClassWithVarArgsConstructor instance = Whitebox.invokeConstructor(ClassWithVarArgsConstructor.class);
        String[] strings = instance.getStrings();
        assertEquals(0, strings.length);
    }

    /**
     * Test invoke var args method multiple values.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokeVarArgsMethod_multipleValues() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        assertEquals(Integer.valueOf(6), Whitebox.invokeMethod(tested, "varArgsMethod", 1, 2, 3));
    }

    /**
     * Test invoke var args method no arguments.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokeVarArgsMethod_noArguments() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        assertEquals(Integer.valueOf(0), Whitebox.invokeMethod(tested, "varArgsMethod"));
    }

    /**
     * Test invoke var args method one argument.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokeVarArgsMethod_oneArgument() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        assertEquals(Integer.valueOf(4), Whitebox.invokeMethod(tested, "varArgsMethod", 2));
    }

    /**
     * Test invoke var args method invoke var args with one argument.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokeVarArgsMethod_invokeVarArgsWithOneArgument() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        assertEquals(Integer.valueOf(1),
                Whitebox.invokeMethod(tested, "varArgsMethod", new Class<?>[] { int[].class }, 1));
    }

    /**
     * Test invoke private method with a sub type of the argument type.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethodWithASubTypeOfTheArgumentType() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        ClassWithChildThatHasInternalState argument = new ClassWithChildThatHasInternalState();
        assertSame(argument, Whitebox.invokeMethod(tested, "methodWithObjectArgument", argument));
    }

    /**
     * Test invoke private method with a class argument.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethodWithAClassArgument() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        assertEquals(ClassWithChildThatHasInternalState.class,
                Whitebox.invokeMethod(tested, "methodWithClassArgument", ClassWithChildThatHasInternalState.class));
    }

    /**
     * Test set internal state in child class without specifying the child class.
     */
    @Test
    void testSetInternalStateInChildClassWithoutSpecifyingTheChildClass() {
        final Integer value = Integer.valueOf(22);
        final String fieldName = "internalState";
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState() {
        };
        Whitebox.setInternalState(tested, fieldName, value);
        assertEquals(value, Whitebox.getInternalState(tested, fieldName));
    }

    /**
     * Test set internal state in class and make sure that the child class is not affected even though it has a field
     * with the same name.
     */
    @Test
    void testSetInternalStateInClassAndMakeSureThatTheChildClassIsNotAffectedEvenThoughItHasAFieldWithTheSameName() {
        final Integer value = Integer.valueOf(22);
        final String fieldName = "anotherInternalState";
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState() {
        };
        Whitebox.setInternalState(tested, fieldName, value);
        assertEquals(value, Whitebox.getInternalState(tested, fieldName));
        assertEquals(Integer.valueOf(-1), Whitebox.getInternalState(tested, fieldName, ClassWithInternalState.class));
    }

    /**
     * Test set internal state with invalid argument type.
     */
    @Test
    void testSetInternalStateWithInvalidArgumentType() {
        final Integer value = Integer.valueOf(22);
        final String fieldName = "internalState";
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState() {
        };
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.setInternalState(tested, fieldName, new Object());
        });
        assertNotEquals(value, Whitebox.getInternalState(tested, fieldName));
    }

    /**
     * Test set internal state with null.
     */
    @Test
    void testSetInternalStateWithNull() {
        final Integer value = Integer.valueOf(22);
        final String fieldName = "internalState";
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState() {
        };
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.setInternalState(tested, fieldName, (Object) null);
        });
        assertNotEquals(value, Whitebox.getInternalState(tested, fieldName));
    }

    /**
     * Test set and get internal state based on field type.
     */
    @Test
    void testSetAndGetInternalStateBasedOnFieldType() {
        final Integer value = Integer.valueOf(22);
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, int.class, value);
        assertEquals(value, (int) Whitebox.getInternalState(tested, int.class));
        assertEquals(value, Whitebox.getInternalState(tested, "anotherInternalState"));
        assertEquals(value,
                Whitebox.getInternalState(tested, "anotherInternalState", ClassWithChildThatHasInternalState.class));
    }

    /**
     * Test set and get internal state at a specific place in the hierarchy based on field type.
     */
    @Test
    void testSetAndGetInternalStateAtASpecificPlaceInTheHierarchyBasedOnFieldType() {
        final Integer value = Integer.valueOf(22);
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, int.class, value, ClassWithInternalState.class);
        assertEquals(42, (int) Whitebox.getInternalState(tested, int.class));
        assertEquals(value, (int) Whitebox.getInternalState(tested, int.class, ClassWithInternalState.class));
        assertEquals(value, Whitebox.getInternalState(tested, "staticState", ClassWithInternalState.class));
    }

    /**
     * Test set internal state based on object type.
     */
    @Test
    void testSetInternalStateBasedOnObjectType() {
        final String value = "a string";
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, value);
        assertEquals(value, Whitebox.getInternalState(tested, String.class));
    }

    /**
     * Test set internal state based on object type when argument is a primitive type.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testSetInternalStateBasedOnObjectTypeWhenArgumentIsAPrimitiveType() {
        final int value = 22;
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, value);
        assertEquals((Integer) value, Whitebox.getInternalState(tested, "anotherInternalState",
                ClassWithChildThatHasInternalState.class, Integer.class));
    }

    /**
     * Test set internal state based on object type when argument is a primitive type using generics.
     */
    @Test
    void testSetInternalStateBasedOnObjectTypeWhenArgumentIsAPrimitiveTypeUsingGenerics() {
        final int value = 22;
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, value);
        assertEquals((Integer) value, Whitebox.<Integer> getInternalState(tested, "anotherInternalState",
                ClassWithChildThatHasInternalState.class));
    }

    /**
     * Test set internal state based on object type at a specific place in the class hierarchy.
     */
    @Test
    void testSetInternalStateBasedOnObjectTypeAtASpecificPlaceInTheClassHierarchy() {
        final String value = "a string";
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, (Object) value, ClassWithInternalState.class);
        assertEquals(value, Whitebox.getInternalState(tested, "finalString"));
    }

    /**
     * Test set internal state based on object type at a specific place in the class hierarchy for primitive type.
     */
    @Test
    void testSetInternalStateBasedOnObjectTypeAtASpecificPlaceInTheClassHierarchyForPrimitiveType() {
        final long value = 31;
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, value, ClassWithInternalState.class);
        assertEquals(value, tested.getInternalLongState());
    }

    /**
     * Test set internal state based on object type at a non specific place in the class hierarchy for primitive type.
     */
    @Test
    void testSetInternalStateBasedOnObjectTypeAtANonSpecificPlaceInTheClassHierarchyForPrimitiveType() {
        final long value = 31;
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, value);
        assertEquals(value, tested.getInternalLongState());
    }

    /**
     * Test set internal multiple of same type on specific place in hierarchy.
     */
    @Test
    void testSetInternalMultipleOfSameTypeOnSpecificPlaceInHierarchy() {
        final int value = 31;
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Assertions.assertThrows(TooManyFieldsFoundException.class, () -> {
            Whitebox.setInternalState(tested, value, ClassWithInternalState.class);

        }, "should throw TooManyFieldsFoundException!");
    }

    /**
     * Test set internal multiple of same type.
     */
    @Test
    void testSetInternalMultipleOfSameType() {
        final int value = 31;
        ClassWithInternalState tested = new ClassWithInternalState();
        Assertions.assertThrows(TooManyFieldsFoundException.class, () -> {
            Whitebox.setInternalState(tested, value);

        }, "should throw TooManyFieldsFoundException!");
    }

    /**
     * Test set internal state based on object sub class type at a specific place in the class hierarchy.
     */
    @Test
    void testSetInternalStateBasedOnObjectSubClassTypeAtASpecificPlaceInTheClassHierarchy() {
        final ClassWithPrivateMethods value = new ClassWithPrivateMethods() {
        };
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState();
        Whitebox.setInternalState(tested, value, ClassWithInternalState.class);
        assertSame(value, tested.getClassWithPrivateMethods());
    }

    /**
     * Test set internal state based on object sub class type.
     */
    @Test
    void testSetInternalStateBasedOnObjectSubClassType() {
        final ClassWithPrivateMethods value = new ClassWithPrivateMethods() {
        };
        ClassWithChildThatHasInternalState tested = new ClassWithChildThatHasInternalState() {
        };
        Whitebox.setInternalState(tested, value);
        assertSame(value, tested.getClassWithPrivateMethods());
    }

    /**
     * Test get all instance fields.
     */
    @Test
    void testGetAllInstanceFields() {
        Set<Field> allFields = Whitebox.getAllInstanceFields(new ClassWithChildThatHasInternalState());
        assertEquals(8, allFields.size());
    }

    /**
     * Test get all instance fields on class.
     */
    @Test
    void testGetAllInstanceFieldsOnClass() {
        Set<Field> allFields = Whitebox.getAllInstanceFields(ClassWithChildThatHasInternalState.class);
        assertEquals(8, allFields.size());
    }

    /**
     * Test get all static fields assert no fields from parent.
     */
    @Test
    void testGetAllStaticFields_assertNoFieldsFromParent() {
        Set<Field> allFields = Whitebox.getAllStaticFields(ClassWithChildThatHasInternalState.class);
        assertEquals(0, allFields.size());
    }

    /**
     * Test get all static fields.
     */
    @Test
    void testGetAllStaticFields() {
        Set<Field> allFields = Whitebox.getAllStaticFields(ClassWithInternalState.class);
        assertEquals(4, allFields.size());
    }

    /**
     * Test method with no method name no method found.
     */
    @Test
    void testMethodWithNoMethodName_noMethodFound() {
        Assertions.assertThrows(MethodNotFoundException.class, () -> {
            Whitebox.getMethod(ClassWithInternalState.class, int.class);

        }, "Should throw MethodNotFoundException");
    }

    /**
     * Test method with no method name too many methods found.
     */
    @Test
    void testMethodWithNoMethodName_tooManyMethodsFound() {
        Assertions.assertThrows(TooManyMethodsFoundException.class, () -> {
            Whitebox.getMethod(ClassWithSeveralMethodsWithSameName.class);

        }, "Should throw TooManyMethodsFoundException");
    }

    /**
     * Test method with no method name ok.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testMethodWithNoMethodName_ok() throws Exception {
        final Method method = Whitebox.getMethod(ClassWithSeveralMethodsWithSameName.class, double.class);
        assertEquals(method, ClassWithSeveralMethodsWithSameName.class.getDeclaredMethod("getDouble", double.class));
    }

    /**
     * Test get two methods when none of them are found.
     */
    @Test
    void testGetTwoMethodsWhenNoneOfThemAreFound() {
        try {
            Whitebox.getMethods(ClassWithSeveralMethodsWithSameName.class, "notFound1", "notFound2");
        } catch (MethodNotFoundException e) {
            assertEquals(
                    "No methods matching the name(s) notFound1 or notFound2 were found in the class hierarchy of class org.powermock.reflect.testclasses.ClassWithSeveralMethodsWithSameName.",
                    e.getMessage());
        }
    }

    /**
     * Test get three methods when none of them are found.
     */
    @Test
    void testGetThreeMethodsWhenNoneOfThemAreFound() {
        try {
            Whitebox.getMethods(ClassWithSeveralMethodsWithSameName.class, "notFound1", "notFound2", "notFound3");
        } catch (MethodNotFoundException e) {
            assertEquals(
                    "No methods matching the name(s) notFound1, notFound2 or notFound3 were found in the class hierarchy of class org.powermock.reflect.testclasses.ClassWithSeveralMethodsWithSameName.",
                    e.getMessage());
        }
    }

    /**
     * Asserts that <a href="http://code.google.com/p/powermock/issues/detail?id=118">issue 118</a> is fixed. Thanks to
     * cemcatik for finding this.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokeConstructorWithBothNormalAndVarArgsParameter() throws Exception {
        ClassWithVarArgsConstructor2 instance = Whitebox.invokeConstructor(ClassWithVarArgsConstructor2.class, "first",
                "second", "third");
        assertArrayEquals(new String[] { "first", "second", "third" }, instance.getStrings());
    }

    /**
     * Asserts that <a href="http://code.google.com/p/powermock/issues/detail?id=118">issue 118</a> is fixed. Thanks to
     * cemcatik for finding this.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokeMethodWithBothNormalAndVarArgsParameter() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        assertEquals(Integer.valueOf(4), Whitebox.invokeMethod(tested, "varArgsMethod2", 1, 2, 3));
    }

    /**
     * Test invoke private method with array argument.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInvokePrivateMethodWithArrayArgument() throws Exception {
        ClassWithPrivateMethods tested = new ClassWithPrivateMethods();
        assertEquals("Hello World", Whitebox.invokeMethod(tested, "evilConcatOfStrings",
                new Object[] { new String[] { "Hello ", "World" } }));
    }

    /**
     * Test set internal state from context all states in same one context.
     */
    @Test
    void testSetInternalStateFromContext_allStatesInSameOneContext() {
        ClassWithSimpleInternalState tested = new ClassWithSimpleInternalState();
        MyContext context = new MyContext();
        Whitebox.setInternalStateFromContext(tested, context);
        assertEquals(context.getMyStringState(), tested.getSomeStringState());
        assertEquals(context.getMyIntState(), tested.getSomeIntState());
    }

    /**
     * Test set internal state from context states in different context.
     */
    @Test
    void testSetInternalStateFromContext_statesInDifferentContext() {
        ClassWithSimpleInternalState tested = new ClassWithSimpleInternalState();
        MyIntContext myIntContext = new MyIntContext();
        MyStringContext myStringContext = new MyStringContext();
        Whitebox.setInternalStateFromContext(tested, myIntContext, myStringContext);
        assertEquals(myStringContext.getMyStringState(), tested.getSomeStringState());
        assertEquals(myIntContext.getSimpleIntState(), tested.getSomeIntState());
    }

    /**
     * Test set internal state from context context is a class.
     */
    @Test
    void testSetInternalStateFromContext_contextIsAClass() {
        ClassWithSimpleInternalState tested = new ClassWithSimpleInternalState();
        Whitebox.setInternalStateFromContext(tested, MyContext.class);
        assertEquals(Whitebox.getInternalState(MyContext.class, long.class), (Long) tested.getSomeStaticLongState());
    }

    /**
     * Test set internal state from context context is a class and an instance.
     */
    @Test
    void testSetInternalStateFromContext_contextIsAClassAndAnInstance() {
        ClassWithSimpleInternalState tested = new ClassWithSimpleInternalState();
        MyContext myContext = new MyContext();
        Whitebox.setInternalStateFromContext(tested, MyContext.class, myContext);
        assertEquals(myContext.getMyStringState(), tested.getSomeStringState());
        assertEquals(myContext.getMyIntState(), tested.getSomeIntState());
        assertEquals((Long) myContext.getMyLongState(), (Long) tested.getSomeStaticLongState());
    }

    /**
     * Test set internal state from context context has one instance and one static field of same type only instance
     * context.
     */
    @Test
    void testSetInternalStateFromContext_contextHasOneInstanceAndOneStaticFieldOfSameType_onlyInstanceContext() {
        ClassWithStaticAndInstanceInternalStateOfSameType.reset();
        ClassWithStaticAndInstanceInternalStateOfSameType tested = new ClassWithStaticAndInstanceInternalStateOfSameType();
        OneInstanceAndOneStaticFieldOfSameTypeContext context = new OneInstanceAndOneStaticFieldOfSameTypeContext();
        Whitebox.setInternalStateFromContext(tested, context);
        assertEquals(context.getMyStringState(), tested.getStringState());
        assertEquals("Static String state", tested.getStaticStringState());
    }

    /**
     * Test set internal state from context context has one instance and one static field of same type only static
     * context.
     */
    @Test
    void testSetInternalStateFromContext_contextHasOneInstanceAndOneStaticFieldOfSameType_onlyStaticContext() {
        ClassWithStaticAndInstanceInternalStateOfSameType.reset();
        ClassWithStaticAndInstanceInternalStateOfSameType tested = new ClassWithStaticAndInstanceInternalStateOfSameType();
        Whitebox.setInternalStateFromContext(tested, OneInstanceAndOneStaticFieldOfSameTypeContext.class);
        assertEquals(OneInstanceAndOneStaticFieldOfSameTypeContext.getMyStaticStringState(),
                tested.getStaticStringState());
        assertEquals("String state", tested.getStringState());
    }

    /**
     * Set internal state from instance context copies matching context fields to target object by default.
     */
    @Test
    void setInternalStateFromInstanceContextCopiesMatchingContextFieldsToTargetObjectByDefault() {
        ClassWithSimpleInternalState tested = new ClassWithSimpleInternalState();
        InstanceFieldsNotInTargetContext fieldsNotInTargetContext = new InstanceFieldsNotInTargetContext();
        assertThat(tested.getSomeStringState()).isNotEqualTo(fieldsNotInTargetContext.getString());

        Whitebox.setInternalStateFromContext(tested, fieldsNotInTargetContext);
        assertEquals(tested.getSomeStringState(), fieldsNotInTargetContext.getString());
    }

    /**
     * Set internal state from instance context copies matching context fields to target object when specifying matching
     * strategy.
     */
    @Test
    void setInternalStateFromInstanceContextCopiesMatchingContextFieldsToTargetObjectWhenSpecifyingMatchingStrategy() {
        ClassWithSimpleInternalState tested = new ClassWithSimpleInternalState();
        InstanceFieldsNotInTargetContext fieldsNotInTargetContext = new InstanceFieldsNotInTargetContext();
        assertThat(tested.getSomeStringState()).isNotEqualTo(fieldsNotInTargetContext.getString());
        Whitebox.setInternalStateFromContext(tested, fieldsNotInTargetContext, FieldMatchingStrategy.MATCHING);
        assertEquals(tested.getSomeStringState(), fieldsNotInTargetContext.getString());
    }

    /**
     * Set internal state from instance context throws exception when context contains fields not defined in target
     * object when specifying strict strategy.
     */
    @Test
    void setInternalStateFromInstanceContextThrowsExceptionWhenContextContainsFieldsNotDefinedInTargetObjectWhenSpecifyingStrictStrategy() {
        ClassWithSimpleInternalState tested = new ClassWithSimpleInternalState();
        InstanceFieldsNotInTargetContext fieldsNotInTargetContext = new InstanceFieldsNotInTargetContext();
        assertThat(tested.getSomeStringState()).isNotEqualTo(fieldsNotInTargetContext.getString());
        Assertions.assertThrows(FieldNotFoundException.class, () -> {
            Whitebox.setInternalStateFromContext(tested, fieldsNotInTargetContext, FieldMatchingStrategy.STRICT);
        });
        assertEquals(tested.getSomeStringState(), fieldsNotInTargetContext.getString());
    }

    /**
     * Set internal state from class context copies matching context fields to target object by default.
     */
    @Test
    void setInternalStateFromClassContextCopiesMatchingContextFieldsToTargetObjectByDefault() {
        long state = ClassWithSimpleInternalState.getLong();
        try {
            assertThat(state).isNotEqualTo(ClassFieldsNotInTargetContext.getLong());
            Whitebox.setInternalStateFromContext(ClassWithSimpleInternalState.class,
                    ClassFieldsNotInTargetContext.class);
            assertEquals(ClassFieldsNotInTargetContext.getLong(), ClassWithSimpleInternalState.getLong());
        } finally {
            // Restore the state
            ClassWithSimpleInternalState.setLong(state);
        }
    }

    /**
     * Set internal state from class context copies matching context fields to target object when specifying matching
     * strategy.
     */
    @Test
    void setInternalStateFromClassContextCopiesMatchingContextFieldsToTargetObjectWhenSpecifyingMatchingStrategy() {
        long state = ClassWithSimpleInternalState.getLong();
        try {
            assertThat(state).isNotEqualTo(ClassFieldsNotInTargetContext.getLong());
            Whitebox.setInternalStateFromContext(ClassWithSimpleInternalState.class,
                    ClassFieldsNotInTargetContext.class, FieldMatchingStrategy.MATCHING);
            assertEquals(ClassFieldsNotInTargetContext.getLong(), ClassWithSimpleInternalState.getLong());
        } finally {
            // Restore the state
            ClassWithSimpleInternalState.setLong(state);
        }
    }

    /**
     * Set internal state from class context throws exception when context contains fields not defined in target object
     * when specifying strict strategy.
     */
    @Test
    void setInternalStateFromClassContextThrowsExceptionWhenContextContainsFieldsNotDefinedInTargetObjectWhenSpecifyingStrictStrategy() {
        long state = ClassWithSimpleInternalState.getLong();
        try {
            assertThat(state).isNotEqualTo(ClassFieldsNotInTargetContext.getLong());
            Assertions.assertThrows(FieldNotFoundException.class, () -> {
                Whitebox.setInternalStateFromContext(ClassWithSimpleInternalState.class,
                        ClassFieldsNotInTargetContext.class, FieldMatchingStrategy.STRICT);
            });
        } finally {
            // Restore the state
            ClassWithSimpleInternalState.setLong(state);
        }
    }

    /**
     * Assert that error message is correct when no instance field found.
     */
    @Test
    void assertThatErrorMessageIsCorrectWhenNoInstanceFieldFound() {
        ClassWithInternalState classWithInternalState = new ClassWithInternalState();
        Assertions.assertThrows(FieldNotFoundException.class, () -> {
            Whitebox.setInternalState(classWithInternalState, (byte) 23);

        }, "Should throw a FieldNotFoundException.");
    }

    /**
     * Assert that error message is correct when no static field found.
     */
    @Test
    void assertThatErrorMessageIsCorrectWhenNoStaticFieldFound() {
        Assertions.assertThrows(FieldNotFoundException.class, () -> {
            Whitebox.setInternalState(ClassWithInternalState.class, (byte) 23);

        }, "Should throw a FieldNotFoundException.");
    }

    /**
     * Assert that whitebox works with generics when specifying field name.
     */
    @Test
    void assertThatWhiteboxWorksWithGenericsWhenSpecifyingFieldName() {
        ClassWithInternalState object = new ClassWithInternalState();
        Set<String> state = Whitebox.getInternalState(object, "genericState");
        assertSame(object.getGenericState(), state);
    }

    /**
     * Whitebox get method with correct method name but wrong parameter type returns error message reflecting the wrong
     * parameter.
     */
    @Test
    void whiteboxGetMethodWithCorrectMethodNameButWrongParameterTypeReturnsErrorMessageReflectingTheWrongParameter() {
        Assertions.assertThrows(MethodNotFoundException.class, () -> {
            Whitebox.getMethod(ClassWithInternalState.class, "methodWithArgument", String.class, InputStream.class);

        }, "Should throw MethodNotFoundException");
    }

    /**
     * Whitebox set internal state works on arrays when defining method name.
     */
    @Test
    void whiteboxSetInternalStateWorksOnArraysWhenDefiningMethodName() {
        ClassWithInternalState tested = new ClassWithInternalState();
        final String[] expected = new String[] { "string1", "string2" };
        Whitebox.setInternalState(tested, "stringArray", expected);
        assertArrayEquals(expected, tested.getStringArray());
    }

    /**
     * Whitebox set internal state works on arrays when not defining method name.
     */
    @Test
    void whiteboxSetInternalStateWorksOnArraysWhenNotDefiningMethodName() {
        ClassWithInternalState tested = new ClassWithInternalState();
        final String[] expected = new String[] { "string1", "string2" };
        Whitebox.setInternalState(tested, expected);
        assertArrayEquals(expected, tested.getStringArray());
    }

    /**
     * Gets the internal state throws iae when instance is null.
     */
    @Test
    void getInternalStateThrowsIAEWhenInstanceIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.getInternalState(null, String.class);

        }, "Should throw IllegalArgumentException");
    }

    /**
     * Gets the internal state supports object array when sut contains serializable.
     */
    @Test
    void getInternalStateSupportsObjectArrayWhenSUTContainsSerializable() {
        ClassWithSerializableState tested = new ClassWithSerializableState();
        tested.setSerializable(new Serializable() {
            private static final long serialVersionUID = -1850246005852779087L;
        });
        tested.setObjectArray(new Object[0]);
        assertNotNull(Whitebox.getInternalState(tested, Object[].class));
    }

    /**
     * Gets the internal state uses assignable to when looking for object.
     */
    @Test
    void getInternalStateUsesAssignableToWhenLookingForObject() {
        ClassWithList tested = new ClassWithList();
        assertNotNull(Whitebox.getInternalState(tested, Object.class));
    }

    /**
     * Gets the internal state throws too many fields found when too many fields match the supplied type.
     */
    @Test
    void getInternalStateThrowsTooManyFieldsFoundWhenTooManyFieldsMatchTheSuppliedType() {
        ClassWithInternalState tested = new ClassWithInternalState();
        Assertions.assertThrows(TooManyFieldsFoundException.class, () -> {
            assertNotNull(Whitebox.getInternalState(tested, Object.class));
        });
    }

    /**
     * Invoke method invokes overriden methods.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void invokeMethodInvokesOverridenMethods() throws Exception {
        assertTrue(Whitebox.<Boolean> invokeMethod(new ClassWithOverriddenMethod(), 2.0d));
    }

    /**
     * New instance throws iae when class is abstract.
     */
    @Test
    void newInstanceThrowsIAEWhenClassIsAbstract() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.newInstance(AbstractClass.class);
        });
    }

    /**
     * New instance returns java proxy when interface.
     */
    @Test
    void newInstanceReturnsJavaProxyWhenInterface() {
        AnInterface instance = Whitebox.newInstance(AnInterface.class);
        assertTrue(Proxy.isProxyClass(instance.getClass()));
    }

    /**
     * New instance creates an empty array when class is array.
     */
    @Test
    void newInstanceCreatesAnEmptyArrayWhenClassIsArray() {
        byte[] newInstance = Whitebox.newInstance(byte[].class);
        assertEquals(0, newInstance.length);
    }

    /**
     * Invoke method supports null parameters.
     */
    @Test
    void invokeMethodSupportsNullParameters() {
        ClassWithAMethod classWithAMethod = new ClassWithAMethod();
        Connection connection = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Whitebox.invokeMethod(classWithAMethod, "connect", connection);
        });
    }

    /**
     * Invoke overridden method with null parameter throws iae.
     */
    @Test
    void invokeOverriddenMethodWithNullParameterThrowsIAE() {
        ClassWithOverloadedMethods tested = new ClassWithOverloadedMethods();
        Child child = null;
        Assertions.assertThrows(MethodNotFoundException.class, () -> {
            Whitebox.invokeMethod(tested, "overloaded", 2, child);
        });
    }

    /**
     * Can pass null param to private static method.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void canPassNullParamToPrivateStaticMethod() throws Exception {
        assertEquals("hello", Whitebox.invokeMethod(ClassWithStaticMethod.class, "aStaticMethod", (Object[]) null));
    }

    /**
     * Can pass null param to private static method when defining parameter types.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void canPassNullParamToPrivateStaticMethodWhenDefiningParameterTypes() throws Exception {
        assertEquals("hello", Whitebox.invokeMethod(ClassWithStaticMethod.class, "aStaticMethod",
                new Class<?>[] { byte[].class }, (Object[]) null));
    }

    /**
     * Can pass null primitive arrays to a private static method.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void canPassNullPrimitiveArraysToAPrivateStaticMethod() throws Exception {
        assertEquals("hello", Whitebox.invokeMethod(ClassWithStaticMethod.class, "aStaticMethod", (byte[]) null));
    }

    /**
     * Can pass multiple null values to static method.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void canPassMultipleNullValuesToStaticMethod() throws Exception {
        assertEquals("null null", Whitebox.invokeMethod(ClassWithStaticMethod.class, "anotherStaticMethod",
                (Object) null, (byte[]) null));
    }

    /**
     * Test object constructors.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testObjectConstructors() throws Exception {
        String name = "objectConstructor";
        ClassWithObjectConstructors instance = Whitebox.invokeConstructor(ClassWithObjectConstructors.class, name);

        assertEquals(instance.getName(), name);
    }

    /**
     * Test interface constructors.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testInterfaceConstructors() throws Exception {
        ConstructorInterface param = new ConstructorInterfaceImpl("constructorInterfaceSomeValue");
        ClassWithInterfaceConstructors instance = Whitebox.invokeConstructor(ClassWithInterfaceConstructors.class,
                param);

        assertEquals(instance.getValue(), param.getValue());
    }

    /**
     * Test primitive constructors argument boxed.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testPrimitiveConstructorsArgumentBoxed() throws Exception {
        Long arg = 1L;
        ClassWithPrimitiveConstructors instance = Whitebox.invokeConstructor(ClassWithPrimitiveConstructors.class, arg);

        assertEquals(instance.getValue(), arg.longValue());
    }

    /**
     * Test overloaded constructors.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testOverloadedConstructors() throws Exception {
        String name = "overloadedConstructor";
        ClassWithOverloadedConstructors instance = Whitebox.invokeConstructor(ClassWithOverloadedConstructors.class,
                true, name);

        assertEquals(instance.getName(), name);
        assertTrue(instance.isBool());

        assertThat(instance.isBool1()).isFalse();
    }

    /**
     * Test final state.
     */
    @Test
    @Disabled("Reflection and direct call returns different values.")
    void testFinalState() {
        ClassWithInternalState state = new ClassWithInternalState();
        String expected = "changed";
        Whitebox.setInternalState(state, "finalString", expected);
        assertEquals(expected, state.getFinalString());
        assertEquals(expected, Whitebox.getInternalState(state, "finalString"));
    }
}
