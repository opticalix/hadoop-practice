package com.opticalix.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogRegexParser {
    private static final String MY_REG = "([0-9.]+)" + "(?:[\\s\\S]+)"
            + "(\\[[^\\[\\]]+])" + "(?:[\\s\\S]*)"
            + "\"((?:[^\"]|\")+)\"" + "(?:[\\s\\S]*)"
            + "(\\d{3})\\s" + "(?:[\\s\\S]*)";

    private static Pattern sCompile;

    static {
        sCompile = Pattern.compile(MY_REG);
    }

    public static Matcher match(String s) {
        return sCompile.matcher(s);
    }
}
