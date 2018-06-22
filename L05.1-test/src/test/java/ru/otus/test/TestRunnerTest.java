package ru.otus.test;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import ru.otus.test.probes.TestProbe1;
import ru.otus.test.report.TestReporter;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class TestRunnerTest {

    @Test
    void runTests() {
        TestProbe1 testProbe1 = spy(TestProbe1.class);
        TestRunner testRunner = new TestRunner(testProbe1, mock(TestReporter.class));
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
    }
}