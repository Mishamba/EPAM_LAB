package com.mishamba.module1;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StringUtils {
    public boolean isPositiveNumber(String str) {
        String number = org.apache.commons.lang3.StringUtils.strip(str);
        Pattern pattern = Pattern.compile("(-|\\+)[0-9]+");
        Matcher matcher = pattern.matcher(number);
        return Integer.parseInt(number) > 0 && matcher.find();
    }
}
