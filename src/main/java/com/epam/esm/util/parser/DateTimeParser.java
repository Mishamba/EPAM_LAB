package com.epam.esm.util.parser;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeParser {
    private final String parseFormat = "yyyy-mm-dd'T'HH:mm:ss.SSS";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(parseFormat);

    public String parseFrom(LocalDateTime dateTime) {
        return dateFormatter.format(dateTime);
    }

    public LocalDateTime parseTo(String stringDateTime) {
        return LocalDateTime.parse(stringDateTime, dateFormatter);
    }
}
