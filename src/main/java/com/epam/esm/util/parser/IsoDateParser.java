package com.epam.esm.util.parser;

import com.epam.esm.model.exception.UtilException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class IsoDateParser {
    // if current format is wrong
    // yyyy-mm-ddThh:mm:ss[.mmm]
    private final String isoFormat = "yyyy-mm-dd'T'hh:mm:ss.sss";

    public String parseToIso(Date date) throws UtilException {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(isoFormat,
                Locale.ENGLISH);
        return format.format(date);
    }

    public Date parseToDate(String isoDate) throws UtilException {
        if (isoDate == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat(isoFormat,
                Locale.ENGLISH);
        try {
            return format.parse(isoDate);
        } catch (ParseException e) {
            throw new UtilException("can't parse this date", e);
        }
    }
}
