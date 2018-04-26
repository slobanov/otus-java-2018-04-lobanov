package ru.otus.l031.report;


import static java.lang.String.format;

class ConsoleReporter implements SizeReporter {

    @Override
    public void report(String name, long size) {

        System.out.println(format("%-70s | memory = %s [bytes]", name, size));
    }
}
