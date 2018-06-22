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
        System.out.println("failureTest");
        throw new AssertionError("fail");
    }

}
