package ru.otus.test.probes;

import ru.otus.test.After;
import ru.otus.test.Before;
import ru.otus.test.Test;

public class TestProbe2 {

    @Before
    public void before1() {
        System.out.println("before1");
    }

    @Before
    public void before2() {
        System.out.println("before2");
    }

    @Before
    @After
    public void beforeAndAfter() {
        System.out.println("beforeAndAfter");
    }

    @Test
    public void test1() {
        System.out.println("test1");
        throw new AssertionError("?");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @After
    public void after1() {
        System.out.println("after1");
    }

    @After
    public void after2() {
        System.out.println("after2");
    }

}
