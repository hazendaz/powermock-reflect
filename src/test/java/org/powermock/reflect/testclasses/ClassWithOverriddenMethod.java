/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2011-2026 Hazendaz
 */
package org.powermock.reflect.testclasses;

public class ClassWithOverriddenMethod extends ClassWithStandardMethod {

    @Override
    public boolean myMethod(double value) {
        return true;
    }
}
