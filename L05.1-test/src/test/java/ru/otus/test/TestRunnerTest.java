package ru.otus.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import ru.otus.test.probes.TestProbe1;
import ru.otus.test.report.TestReporter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TestRunnerTest {

    @Test
    void runTests() throws NoSuchMethodException {
        TestProbe1 testProbe1 = spy(TestProbe1.class);
        TestReporter testReporter = mock(TestReporter.class);
        TestRunner<?> testRunner = new TestRunner<>(
                TestProbe1.class,
                testReporter,
                () -> testProbe1
        );
        testRunner.runTests();

        InOrder inOrder = inOrder(testProbe1);
        inOrder.verify(testProbe1).before1();
        inOrder.verify(testProbe1).before2();
        inOrder.verify(testProbe1).beforeAndAfter();
        inOrder.verify(testProbe1).test1();
        inOrder.verify(testProbe1).after1();
        inOrder.verify(testProbe1).after2();
        inOrder.verify(testProbe1).beforeAndAfter();
        inOrder.verify(testProbe1).before1();
        inOrder.verify(testProbe1).before2();
        inOrder.verify(testProbe1).beforeAndAfter();
        inOrder.verify(testProbe1).test2();
        inOrder.verify(testProbe1).after1();
        inOrder.verify(testProbe1).after2();
        inOrder.verify(testProbe1).beforeAndAfter();
        inOrder.verifyNoMoreInteractions();

        verify(testReporter, times(1))
                .reportFailure(TestProbe1.class.getMethod("test1"));
        verify(testReporter, times(1))
                .reportSuccess(TestProbe1.class.getMethod("test2"));
    }

    @Test
    void runTestsDifferentObjects() throws NoSuchMethodException {
        List<TestProbe1> probes = new ArrayList<>();
        TestReporter testReporter = mock(TestReporter.class);
        TestRunner<?> testRunner = new TestRunner<>(
                TestProbe1.class,
                testReporter,
                () -> {
                    TestProbe1 testProbe1 = spy(TestProbe1.class);
                    probes.add(testProbe1);
                    return testProbe1;
                }
        );
        testRunner.runTests();

        Assertions.assertEquals(2, probes.size());

        TestProbe1 probe1 = probes.get(0);
        InOrder inOrder = inOrder(probe1);
        inOrder.verify(probe1).before1();
        inOrder.verify(probe1).before2();
        inOrder.verify(probe1).beforeAndAfter();
        inOrder.verify(probe1).test1();
        inOrder.verify(probe1).after1();
        inOrder.verify(probe1).after2();
        inOrder.verify(probe1).beforeAndAfter();
        inOrder.verifyNoMoreInteractions();

        TestProbe1 probe2 = probes.get(1);
        inOrder = inOrder(probe2);
        inOrder.verify(probe2).before1();
        inOrder.verify(probe2).before2();
        inOrder.verify(probe2).beforeAndAfter();
        inOrder.verify(probe2).test2();
        inOrder.verify(probe2).after1();
        inOrder.verify(probe2).after2();
        inOrder.verify(probe2).beforeAndAfter();
        inOrder.verifyNoMoreInteractions();

        verify(testReporter, times(1))
                .reportFailure(TestProbe1.class.getMethod("test1"));
        verify(testReporter, times(1))
                .reportSuccess(TestProbe1.class.getMethod("test2"));

        Assertions.assertNotSame(probe1, probe2);
    }
}