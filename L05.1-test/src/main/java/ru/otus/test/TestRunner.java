package ru.otus.test;

import ru.otus.test.report.TestReporter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

class TestRunner {

    private final Class<?> clz;
    private final Object instance;
    private final TestReporter testReporter;

    TestRunner(Object instance, TestReporter testReporter) {
        this.clz = instance.getClass();
        this.instance = instance;
        this.testReporter = testReporter;
    }

    public void runTests() {
        List<Method> before = getAnnotated(Before.class);
        before.sort(comparing(Method::getName));
        List<Method> after = getAnnotated(After.class);
        after.sort(comparing(Method::getName));

        getAnnotated(Test.class)
            .stream()
            .sorted(comparing(Method::getName))
            .forEach(t -> {
                before.forEach(this::invoke);
                boolean result = invoke(t);
                report(t, result);
                after.forEach(this::invoke);
            });
    }

    private List<Method> getAnnotated(Class<? extends Annotation> annotationClz) {
        return Stream.of(clz.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotationClz))
                .collect(toList());
    }

    private boolean invoke(Method method) {
        try {
            method.setAccessible(true);
            method.invoke(instance);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void report(Method method, Boolean result) {
        if (result) {
            testReporter.reportSuccess(method);
        } else {
            testReporter.reportFailure(method);
        }
    }
}
