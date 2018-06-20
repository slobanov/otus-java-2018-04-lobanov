package ru.otus.l041.gclog;

import ru.otus.l041.gclog.format.GCLogFormatter;

public enum GCLoggers {
    ;

    public static GCLogger Slf4GCLogger(GCLogFormatter gcLogFormatter) {
        return new Slf4jGCLogger(gcLogFormatter);
    }
}
