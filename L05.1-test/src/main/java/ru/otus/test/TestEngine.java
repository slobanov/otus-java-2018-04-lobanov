package ru.otus.test;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.otus.test.report.TestReporterImpl;

import java.lang.reflect.Constructor;

public class TestEngine {

    public static void runTestsInClassByName(String className) {
        try {
            Class<?> clz = Class.forName(className);
            Constructor<?> constructor = clz.getDeclaredConstructor();
            constructor.setAccessible(true);
            TestRunner testRunner = new TestRunner(
                    constructor.newInstance(),
                    new TestReporterImpl(System.out)
            );
            testRunner.runTests();
        } catch (ReflectiveOperationException e) {
            throw new TestException(e);
        }
    }

    public static void runTestsInPackageByName(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        reflections.getAllTypes().forEach(TestEngine::runTestsInClassByName);
    }

}
