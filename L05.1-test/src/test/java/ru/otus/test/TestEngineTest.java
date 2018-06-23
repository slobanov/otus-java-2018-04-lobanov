package ru.otus.test;

import org.junit.jupiter.api.Test;

class TestEngineTest {

    @Test
    void runTestsInClassByName() {
        TestEngine.runTestsInClassByName("ru.otus.test.probes.TestProbe1");
    }

    @Test
    void runTestsInPackageByName() {
        TestEngine.runTestsInPackageByName("ru.otus.test.probes");
    }
}