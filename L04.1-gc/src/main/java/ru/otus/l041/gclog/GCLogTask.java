package ru.otus.l041.gclog;

import ru.otus.l041.gclog.data.GCLogData;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Optional;

public class GCLogTask {
    private final GarbageCollectorMXBean gcBean;
    private Optional<GCLogData> previous;

    public GCLogTask(GarbageCollectorMXBean gcBean) {
        this.gcBean = gcBean;
        this.previous = Optional.empty();
    }

    public GCLogData measure() {
        GCLogData currentTotal = new GCLogData(
                gcBean.getName(),
                gcBean.getCollectionCount(),
                gcBean.getCollectionTime()
        );
        GCLogData newLog = previous.map(currentTotal::subtract).orElse(currentTotal);
        previous = Optional.of(newLog);
        return newLog;
    }
}
