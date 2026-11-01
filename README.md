# PowerMock Reflect

[![Java CI](https://github.com/hazendaz/powermock-reflect/actions/workflows/ci.yaml/badge.svg)](https://github.com/hazendaz/powermock-reflect/actions/workflows/ci.yaml)
[![Coverage Status](https://coveralls.io/repos/github/hazendaz/powermock-reflect/badge.svg?branch=master)](https://coveralls.io/github/hazendaz/powermock-reflect?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.hazendaz/powermock-reflect.svg)](https://central.sonatype.com/artifact/com.github.hazendaz/powermock-reflect)
[![Apache 2](https://img.shields.io/github/license/hazendaz/powermock-reflect?logo=apache)](https://www.apache.org/licenses/LICENSE-2.0)

![hazendaz](src/site/resources/images/hazendaz-banner.jpg)

PowerMock Reflect is a Java reflection utility library that simplifies access to fields, methods, constructors, and other implementation details that are normally hidden by Java access controls.

It is particularly useful when writing unit tests that need to inspect or manipulate internal state without changing production code solely to make that code easier to test.

PowerMock Reflect can be used independently of the PowerMock mocking framework.

## Features

PowerMock Reflect provides utilities for:

- **Field access** – Read and modify private, protected, package-private, and inherited fields.
- **Method invocation** – Invoke private and otherwise non-public methods, including methods with overloaded and variable-argument signatures.
- **Constructor invocation** – Instantiate objects through constructors that may not be publicly accessible.
- **Object instantiation** – Create objects without invoking their constructors using Objenesis.
- **Class hierarchy inspection** – Discover fields, methods, and constructors throughout class hierarchies.
- **Internal state manipulation** – Simplify working with object and static field values in test scenarios.

The primary API is exposed through the `Whitebox` utility class.

## Requirements

- Java 17 or newer.
- Maven for building the project.

Java's module system enforces access boundaries that were not present in earlier Java releases. Reflective access to members in strongly encapsulated packages may require the relevant package to be opened to the code performing the reflection.

PowerMock Reflect does not automatically bypass Java module boundaries.

## Installation

Add the following dependency to your Maven project:

```xml
<dependency>
    <groupId>com.github.hazendaz</groupId>
    <artifactId>powermock-reflect</artifactId>
    <version>3.0.0</version>
</dependency>
```

## Usage

### Reading and modifying internal state

Use `Whitebox.getInternalState()` to retrieve private field values and `Whitebox.setInternalState()` to modify them.

```java
public class Example {
    private String internalValue = "initial";
}
```

```java
Example example = new Example();

String value = Whitebox.getInternalState(example, "internalValue");

Whitebox.setInternalState(example, "internalValue", "updated");
```

### Invoking private methods

`Whitebox.invokeMethod()` allows methods to be invoked without changing their visibility.

```java
public class Example {
    private String createMessage(String name) {
        return "Hello, " + name;
    }
}
```

```java
Example example = new Example();

String message = Whitebox.invokeMethod(example, "createMessage", "World");
```

### Accessing static fields

Static fields can also be inspected and modified.

```java
public class Example {
    private static String configuration = "default";
}
```

```java
String value = Whitebox.getInternalState(Example.class, "configuration");

Whitebox.setInternalState(Example.class, "configuration", "updated");
```

### Instantiating objects

PowerMock Reflect supports creating instances without invoking their constructors. This is useful when testing objects whose constructors have external dependencies or other side effects.

```java
Example example = Whitebox.newInstance(Example.class);
```

The underlying object-instantiation support is provided by Objenesis.

## Java Module Access

Since Java 9, the module system restricts reflective access to packages that are not open to the caller.

When accessing members in a package belonging to a named module, that package may need to be opened explicitly.

For example:

```text
--add-opens java.base/java.lang=ALL-UNNAMED
```

For application modules, replace `java.base` and `java.lang` with the appropriate module and package names.

PowerMock Reflect uses modern reflection access APIs and explicitly reports failures when reflective access is required but cannot be obtained.

## Building

Clone the repository and build with Maven:

```shell
git clone https://github.com/hazendaz/powermock-reflect.git
cd powermock-reflect
mvn verify
```

## Origin

PowerMock Reflect originated as the reflection utility module of the [PowerMock project](https://github.com/powermock/powermock).

This project maintains PowerMock Reflect as an independently buildable and distributable library, with a modernized codebase and a Java 17 minimum runtime requirement.

Original upstream source:

- Repository: https://github.com/powermock/powermock
- Module: `powermock-reflect`
- Original source revision: [a0be5c4b](https://github.com/powermock/powermock/commit/a0be5c4bbeb04f13a6aec2653c1f5663cece1316)

The original project copyright notices and applicable license terms are retained. This project is distributed under the Apache License, Version 2.0.

## License

PowerMock Reflect is licensed under the [Apache License, Version 2.0](LICENSE).
