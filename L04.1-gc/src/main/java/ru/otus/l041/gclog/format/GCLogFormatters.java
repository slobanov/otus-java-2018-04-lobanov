package ru.otus.l041.gclog.format;

public enum GCLogFormatters {
    ;

    public static GCLogFormatter jsonGCLogFormatter() {
        return new JsonGCLogFormatter();
    }
}
