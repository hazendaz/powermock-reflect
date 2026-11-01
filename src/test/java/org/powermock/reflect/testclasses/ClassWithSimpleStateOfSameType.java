/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

public class ClassWithSimpleStateOfSameType {

    private String someStringState = "-1";
    private String someOtherStringState = "-1";

    public String getSomeStringState() {
        return someStringState;
    }

    public String getSomeOtherStringState() {
        return someOtherStringState;
    }
}
