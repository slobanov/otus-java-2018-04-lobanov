package ru.otus.l041.gclog.data;

import static java.lang.String.format;

public class GCLogData {
    private final String gcName;
    private final long count;
    private final long spentTime;

    public GCLogData(String gcName, long totalCount, long spentTime) {
        this.gcName = gcName;
        this.count = totalCount;
        this.spentTime = spentTime;
    }

    public GCLogData subtract(GCLogData previous) {
        if (!gcName.equals(previous.gcName)) {
            throw new IllegalArgumentException(
                    format("Can't subtract data for %s from data for %s", gcName, previous.gcName)
            );
        }
        return new GCLogData(
                gcName,
                count - previous.count,
                spentTime - previous.spentTime
        );
    }

    long getCount() {
        return count;
    }

    long getSpentTime() {
        return spentTime;
    }
}
