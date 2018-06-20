package ru.otus.l041;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import ru.otus.l041.gclog.GCLogger;
import ru.otus.l041.gclog.GCLoggers;
import ru.otus.l041.gclog.format.GCLogFormatter;
import ru.otus.l041.gclog.format.GCLogFormatters;
import ru.otus.l041.leak.Leakers;
import ru.otus.l041.leak.MemoryLeaker;

public class Main {
    public static void main(String[] args) {
        Config conf = ConfigFactory.load();
        long timeout = conf.getLong("gclogger.timeout");
        int addedSize = conf.getInt("gclogger.leaker.addedSize");

        GCLogFormatter gcLogFormatter = GCLogFormatters.jsonGCLogFormatter();
        GCLogger gcLogger = GCLoggers.Slf4GCLogger(gcLogFormatter);
        MemoryLeaker memoryLeaker = Leakers.listLeaker(addedSize, timeout);

        new GCLogApplication(
                gcLogger,
                memoryLeaker
        ).runApplication();
    }

}
