package ru.otus.test;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.otus.test.report.TestReporterImpl;

import java.lang.reflect.Constructor;

public final class TestEngine {

    private TestEngine() {
    }

    public static void runTestsInClassByName(String className) {
        try {
            Class<?> clz = Class.forName(className);
            TestRunner<?> testRunner = new TestRunner<>(
                    clz,
                    new TestReporterImpl(System.out),
                    () -> TestEngine.newInstance(clz)
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

    private static Object newInstance(Class<?> clz) {
        try {
            Constructor<?> constructor = clz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new TestException(e);
        }

    }

}
