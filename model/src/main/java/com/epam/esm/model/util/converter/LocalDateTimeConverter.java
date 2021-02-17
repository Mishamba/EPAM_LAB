package com.epam.esm.model.util.converter;

import com.epam.esm.model.util.exception.UtilException;
import com.epam.esm.model.util.parser.DateTimeParser;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;

@Converter
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {
    @Override
    public String convertToDatabaseColumn(LocalDateTime dateTime) {
        try {
            return DateTimeParser.parseFrom(dateTime);
        } catch (UtilException e) {
            return null;
        }
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String s) {
        try {
            return DateTimeParser.parseTo(s);
        } catch (UtilException e) {
            return null;
        }
    }
}
