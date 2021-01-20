package com.epam.esm.util.parser;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeParserTest {

    private final int nanosecondsMultiplier = (int) Math.pow(10, 6);

    @Test
    void parseFrom() {
        DateTimeParser dateTimeParser = new DateTimeParser();
        LocalDateTime localDateTimeToParse = LocalDateTime.of(2018, Month.AUGUST, 29, 6, 12, 15,
                156 * nanosecondsMultiplier);
        String actualParsedLocalDateTime = dateTimeParser.parseFrom(localDateTimeToParse);
        String expectedLocalDateTime  = "2018-08-29T06:12:15.156";

        assertEquals(actualParsedLocalDateTime, expectedLocalDateTime);
    }

    @Test
    void parseTo() {
        DateTimeParser dateTimeParser = new DateTimeParser();
        String dateTimeToParse = "2018-08-29T06:12:15.156";
        LocalDateTime actualLocalDateTime = dateTimeParser.parseTo(dateTimeToParse);
        LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, Month.AUGUST, 29, 6, 12, 15,
                156 * nanosecondsMultiplier);
        assertEquals(actualLocalDateTime, expectedLocalDateTime);
    }
}