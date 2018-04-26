package ru.otus.l031.report;

public interface SizeReporter {
    void report(String name, long size);

    static SizeReporter consoleReporter() {
        return new ConsoleReporter();
    }
}
