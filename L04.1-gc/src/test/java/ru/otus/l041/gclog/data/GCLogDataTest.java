package ru.otus.l041.gclog.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GCLogDataTest {

    @Test
    void subtractHappyPath() {
        GCLogData gcLogData = new GCLogData("name", 10, 20);
        GCLogData previous = new GCLogData("name", 5, 10);
        GCLogData result = gcLogData.subtract(previous);

        assertEquals(5, result.getCount());
        assertEquals(10, result.getSpentTime());
    }

    @Test
    void subtractFail() {
        GCLogData gcLogData = new GCLogData("name", 10, 20);
        GCLogData previous = new GCLogData("otherName", 5, 10);
        assertThrows(IllegalArgumentException.class, () -> gcLogData.subtract(previous));
    }
}