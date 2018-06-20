package ru.otus.l041;

import ru.otus.l041.gclog.GCLogTask;
import ru.otus.l041.gclog.GCLogger;
import ru.otus.l041.leak.MemoryLeaker;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.defaultThreadFactory;
import static java.util.concurrent.Executors.newScheduledThreadPool;

class GCLogApplication {

    private final GCLogger gcLogger;
    private final MemoryLeaker memoryLeaker;

    private final ScheduledExecutorService scheduler = newScheduledThreadPool(
            1,
            r -> {
                Thread t = defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
    );

    GCLogApplication(GCLogger gcLogger, MemoryLeaker memoryLeaker) {
        this.gcLogger = gcLogger;
        this.memoryLeaker = memoryLeaker;
    }

    void runApplication() {
        List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        gcMXBeans.stream()
                 .map(GCLogTask::new)
                 .forEach(this::scheduleLogTask);

        memoryLeaker.leak();
    }

    private void scheduleLogTask(GCLogTask task) {
        scheduler.scheduleAtFixedRate(
                () -> gcLogger.log(task.measure()),
                1,
                1,
                TimeUnit.MINUTES
        );
    }
}
