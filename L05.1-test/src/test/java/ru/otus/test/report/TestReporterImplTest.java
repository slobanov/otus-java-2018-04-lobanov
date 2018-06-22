package ru.otus.test.report;

import org.junit.jupiter.api.Test;
import ru.otus.test.probes.TestProbe1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestReporterImplTest {

    @Test
    void reportSingleSuccess() throws NoSuchMethodException {
        assertEquals(
                "Success: test1\n",
                getReportSameResult(
                        reporter -> reporter.reportSuccess(testProbeMethod("test1"))
                )
        );
    }

    @Test
    void reportSingleFailure() throws NoSuchMethodException {
        assertEquals(
                "Failure: test1\n",
                getReportSameResult(
                        reporter -> reporter.reportFailure(testProbeMethod("test1"))
                )
        );
    }

    @Test
    void reportDoubleFailure() {
        assertEquals(
                "Failure: test1\nFailure: test2\n",
                getReportSameResult(
                        reporter -> {
                            reporter.reportFailure(testProbeMethod("test1"));
                            reporter.reportFailure(testProbeMethod("test2"));
                        }
                )
        );
    }

    @Test
    void reportDoubleSuccess() {
        assertEquals(
                "Success: test1\nSuccess: test2\n",
                getReportSameResult(
                        reporter -> {
                            reporter.reportSuccess(testProbeMethod("test1"));
                            reporter.reportSuccess(testProbeMethod("test2"));
                        }
                )
        );
    }

    @Test
    void reportSuccessThenFailure() throws NoSuchMethodException {
        assertEquals(
                "Success: test1\nFailure: test2\n",
                getReportSameResult(
                        reporter -> {
                            reporter.reportSuccess(testProbeMethod("test1"));
                            reporter.reportFailure(testProbeMethod("test2"));
                        }
                )
        );
    }

    private static String getReportSameResult(Consumer<TestReporter> report) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        TestReporter testReporter = new TestReporterImpl(printStream);

        report.accept(testReporter);

        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

    private static Method testProbeMethod(String name) {
        try {
            return TestProbe1.class.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }
}