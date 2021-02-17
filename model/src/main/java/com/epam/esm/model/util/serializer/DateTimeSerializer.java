package com.epam.esm.model.util.serializer;

import com.epam.esm.model.util.exception.UtilException;
import com.epam.esm.model.util.parser.DateTimeParser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

public class DateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private final DateTimeParser parser;

    public DateTimeSerializer() {
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
