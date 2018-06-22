package ru.otus.test.report;

import java.io.PrintStream;
import java.lang.reflect.Method;

public class TestReporterImpl implements TestReporter {

    private final PrintStream printWriter;

    public TestReporterImpl(PrintStream outputStream) {
        this.printWriter = outputStream;
    }

    @Override
    public void reportSuccess(Method method) {
        printWriter.println("Success: " + method.getName());
    }

    @Override
    public void reportFailure(Method method) {
        printWriter.println("Failure: " + method.getName());
    }
}
