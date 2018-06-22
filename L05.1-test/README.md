# L05.1-test

This is a toy testing framework, which supports three annotations - `@Test`, `@Before` and `@After`.
Framework can be executed by providing class or package name with tests.

Example:

```java
package ru.otus.test.example;

import ru.otus.test.After;
import ru.otus.test.Before;
import ru.otus.test.Test;

public class Example {

    @Before
    void before() {
        System.out.println("Calling before");
    }

    @After
    void after() {
        System.out.println("Calling after");
    }

    @Test
    void successfulTest() {
        System.out.println("successfulTest");
    }

    @Test
    void failureTest() {
        System.out.println("successfulTest");
        throw new AssertionError("fail");
    }

}
```

This test can be executed in following ways:

`TestEngine.runTestsInClassByName("ru.otus.test.example.Example");`
`TestEngine.runTestsInPackageByName("ru.otus.test.example");`

Both executions will yield the same result:

```
Calling before
failureTest
Failure: failureTest
Calling after
Calling before
successfulTest
Success: successfulTest
Calling after
```

__N.B.__: Methods in the same group (before, after or test) are called in order of their names.

How to build:
* run `$ mvn clean package` - this will run tests and outputs final artifact with toy test framework in it.

- - - -

Sergey Lobanov
[s.y.lobanov@yandex.ru](mailto:s.y.lobanov@yandex.ru?Subject=otus-java-2018-04-lobanov)
