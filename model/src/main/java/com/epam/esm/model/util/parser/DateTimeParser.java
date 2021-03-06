package com.epam.esm.model.util.parser;

import com.epam.esm.model.util.exception.UtilException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Util class used to parse LocalDateTime to String and String to LocalDateTime.
 * String (out/in)put example:
 *      2018-08-29T06:12:15.156
 *
 * @version 1.0
 * @author mishamba
 *
 * @see LocalDateTime
 */
@Component
public class DateTimeParser {

    /**
     * Stores date parse format.
     */
    private static final String parseFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * Parser
     */
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(parseFormat);

    /**
     * This parse LocalDateTime to String using parseFormat format.

     *
     * @param dateTime This parameter is parsed to String.
     * @return Parsed LocalDateTime to String
     * @throws UtilException
     */
    public static String parseFrom(LocalDateTime dateTime) throws UtilException {

        try {
            return dateFormatter.format(dateTime);
        } catch (NullPointerException exception) {
            throw new UtilException("given dateTime is null", exception);
        }
    }

    /**
     * This parse String to LocalDateTime using parseFormat format.
     *
     * @param stringDateTime This parameter is parsed to LocalDateTime
     * @return Parsed String to LocalDateTime
     * @throws UtilException
     */
    public static LocalDateTime parseTo(String stringDateTime) throws UtilException {

        try {
            return LocalDateTime.parse(stringDateTime, dateFormatter);
        } catch (NullPointerException exception) {
            throw new UtilException("given string is null", exception);
        }
    }
}
