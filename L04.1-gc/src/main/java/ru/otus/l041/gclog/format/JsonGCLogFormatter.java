package ru.otus.l041.gclog.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.l041.gclog.data.GCLogData;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

class JsonGCLogFormatter implements GCLogFormatter {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.setVisibility(FIELD, ANY);
    }

    @Override
    public String format(GCLogData gcLogData) {
        try {
            return MAPPER.writeValueAsString(gcLogData);
        } catch (JsonProcessingException e) {
            throw new GCLogFormatException(e);
        }
    }
}
