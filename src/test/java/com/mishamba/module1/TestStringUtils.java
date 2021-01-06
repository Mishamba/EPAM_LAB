package com.mishamba.module1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStringUtils {
    @Test
    public void testPositiveNumber() {
        String number = "+12346";
        StringUtils utils = new StringUtils();
        assertTrue(utils.isPositiveNumber(number));
    }

    @Test
    public void testNegativeNumber() {
        String number = "-1234";
        StringUtils utils = new StringUtils();
        assertFalse(utils.isPositiveNumber(number));
    }
}
