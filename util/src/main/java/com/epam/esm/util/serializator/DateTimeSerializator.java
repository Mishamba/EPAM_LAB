package com.epam.esm.util.serializator;

import com.epam.esm.util.exception.UtilException;
import com.epam.esm.util.parser.DateTimeParser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

public class DateTimeSerializator extends JsonSerializer<LocalDateTime> {
    private final DateTimeParser parser;

    public DateTimeSerializator() {
        this.parser = new DateTimeParser();
    }

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            jsonGenerator.writeString(parser.parseFrom(dateTime));
        } catch (UtilException e) {
            throw new RuntimeException("can't parse date", e);
        }
    }
}
