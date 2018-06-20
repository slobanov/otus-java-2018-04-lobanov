package ru.otus.l041.gclog.format;

import ru.otus.l041.gclog.data.GCLogData;

public interface GCLogFormatter {
    String format(GCLogData gcLogData);
}
