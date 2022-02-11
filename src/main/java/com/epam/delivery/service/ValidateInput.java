package com.epam.delivery.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidateInput {
    public static boolean isValid(String input, String regex) {
        boolean result = false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) result = true;
        return result;
    }
}
