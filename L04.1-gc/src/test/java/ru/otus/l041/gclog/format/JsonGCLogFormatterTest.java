package ru.otus.l041.gclog.format;

import org.junit.jupiter.api.Test;
import ru.otus.l041.gclog.data.GCLogData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonGCLogFormatterTest {

    @Test
    void format() {
        GCLogData gcLogData = new GCLogData("name1", 101, 205);
        GCLogFormatter gcLogFormatter = GCLogFormatters.jsonGCLogFormatter();
        assertEquals(
                "{\"gcName\":\"name1\",\"count\":101,\"spentTime\":205}",
                gcLogFormatter.format(gcLogData)
        );
    }
}