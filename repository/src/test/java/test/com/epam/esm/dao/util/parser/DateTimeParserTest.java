package test.com.epam.esm.dao.util.parser;

import com.epam.esm.model.util.exception.UtilException;
import com.epam.esm.model.util.parser.DateTimeParser;
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
        try {
            String actualParsedLocalDateTime = dateTimeParser.parseFrom(localDateTimeToParse);
            String expectedLocalDateTime = "2018-08-29T06:12:15.156";

            assertEquals(expectedLocalDateTime, actualParsedLocalDateTime);
        } catch (UtilException exception) {
            fail(exception);
        }
    }

    @Test
    void parseTo() {
        DateTimeParser dateTimeParser = new DateTimeParser();
        String dateTimeToParse = "2018-08-29T06:12:15.156";
        try {
            LocalDateTime actualLocalDateTime = dateTimeParser.parseTo(dateTimeToParse);
            LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, Month.AUGUST, 29, 6, 12, 15,
                    156 * nanosecondsMultiplier);
            assertEquals(expectedLocalDateTime, actualLocalDateTime);
        } catch (UtilException exception) {
            fail(exception);
        }
    }
}