package ru.otus.test;

import ru.otus.test.report.TestReporter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

class TestRunner<T> {

    private final Class<? extends T> clz;
    private final TestReporter testReporter;
    private final Supplier<T> instanceSup;

    TestRunner(Class<? extends T> clz, TestReporter testReporter, Supplier<T> instanceSup) {
        this.clz = clz;
        this.testReporter = testReporter;
        this.instanceSup = instanceSup;
    }

    public void runTests() {
        List<Method> before = getAnnotated(Before.class);
        before.sort(comparing(Method::getName));
        List<Method> after = getAnnotated(After.class);
        after.sort(comparing(Method::getName));

        getAnnotated(Test.class)
            .stream()
            .sorted(comparing(Method::getName))
            .forEach(t -> report(t, executeTest(before, after, t)));
    }

    private List<Method> getAnnotated(Class<? extends Annotation> annotationClz) {
        return Stream.of(clz.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotationClz))
                .collect(toList());
    }

    private boolean invoke(Method method, Object instance) {
        try {
            method.setAccessible(true);
            method.invoke(instance);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean executeTest(
            List<Method> before,
            List<Method> after,
            Method test
    ) {
            Object instance = instanceSup.get();
            boolean beforeResult = before.stream().allMatch(m -> invoke(m, instance));
            boolean testResult = invoke(test, instance);
            boolean afterResult = after.stream().allMatch(m -> invoke(m, instance));
            return beforeResult && testResult && afterResult;
    }

    private void report(Method method, Boolean result) {
        if (result) {
            testReporter.reportSuccess(method);
        } else {
            testReporter.reportFailure(method);
        }
    }

}
