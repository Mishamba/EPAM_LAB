package com.epam.esm.util.parser;

import com.epam.esm.model.exception.UtilException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeParser {
    private final String parseFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(parseFormat);

    public String parseFrom(LocalDateTime dateTime) throws UtilException {
        try {
            return dateFormatter.format(dateTime);
        } catch (NullPointerException exception) {
            throw new UtilException("given dateTime is null", exception);
        }
    }

    public LocalDateTime parseTo(String stringDateTime) throws UtilException {
        try {
            return LocalDateTime.parse(stringDateTime, dateFormatter);
        } catch (NullPointerException exception) {
            throw new UtilException("given string is null", exception);
        }
    }
}
