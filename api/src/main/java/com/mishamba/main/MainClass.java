package com.mishamba.main;

import com.mishamba.module1_2.Utils;

public class MainClass {
    public static void main(String[] args) {
        String[] numbers = {"+1234", " -1234", "asdf"};

        System.out.println(Utils.isAllPositiveNumbers(numbers));
    }
}
