package ru.otus.test.report;

import java.lang.reflect.Method;

public interface TestReporter {
    void reportSuccess(Method method);
    void reportFailure(Method method);
}
