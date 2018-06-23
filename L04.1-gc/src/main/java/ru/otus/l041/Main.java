package ru.otus.l041;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import ru.otus.l041.gclog.GCLogger;
import ru.otus.l041.gclog.SlfjGCLogger;
import ru.otus.l041.gclog.format.GCLogFormatter;
import ru.otus.l041.gclog.format.GCLogFormatters;
import ru.otus.l041.leak.ListMemoryLeaker;
import ru.otus.l041.leak.MemoryLeaker;

public class Main {
    public static void main(String[] args) {
        Config conf = ConfigFactory.load();
        long timeout = conf.getLong("gclogger.timeout");
        int addedSize = conf.getInt("gclogger.leaker.addedSize");

        GCLogFormatter gcLogFormatter = GCLogFormatters.jsonGCLogFormatter();
        GCLogger gcLogger = new SlfjGCLogger(gcLogFormatter);
        MemoryLeaker memoryLeaker = new ListMemoryLeaker(addedSize, timeout);

        new GCLogApplication(
                gcLogger,
                memoryLeaker
        ).runApplication();
    }

}
