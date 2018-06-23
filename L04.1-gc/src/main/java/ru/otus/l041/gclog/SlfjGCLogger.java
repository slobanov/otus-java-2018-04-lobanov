package ru.otus.l041.gclog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l041.gclog.data.GCLogData;
import ru.otus.l041.gclog.format.GCLogFormatter;

public class SlfjGCLogger implements GCLogger {

    private final Logger LOGGER = LoggerFactory.getLogger(SlfjGCLogger.class);

    private final GCLogFormatter gcLogFormatter;

    public SlfjGCLogger(GCLogFormatter gcLogFormatter) {
        this.gcLogFormatter = gcLogFormatter;
    }

    @Override
    public void log(GCLogData gcLogData) {
        LOGGER.info(gcLogFormatter.format(gcLogData));
    }
}
