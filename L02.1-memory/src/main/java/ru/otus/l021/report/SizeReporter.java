package ru.otus.l021.report;

public interface SizeReporter {
    void report(String name, long size);

    static SizeReporter consoleReporter() {
        return new ConsoleReporter();
    }
}
