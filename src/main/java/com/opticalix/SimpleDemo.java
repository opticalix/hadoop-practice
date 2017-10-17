package com.opticalix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDemo {

    public static final String MY_REG = "([0-9.]+)" + "(?:[\\s\\S]+)"
            + "\\[([^\\[\\]]+)]" + "(?:[\\s\\S]*)"
            + "\"((?:[^\"]|\")+)\"" + "(?:[\\s\\S]*)"
            + "(\\d{3})\\s" + "(?:[\\s\\S]*)";
    //https://yq.aliyun.com/articles/44341, apache access log regex

    public void main(String args[]) {
        String testLog = "64.242.88.10 - - [07/Mar/2004:16:05:49 -0800] \"GET /twiki/bin/edit/Main/Double_bounce_sender?topicparent=Main.ConfigurationVariables HTTP/1.1\" 401 12846";

        Matcher match = getPattern().matcher(testLog);
        System.out.println("match group count=" + match.groupCount() + ", matches=" + match.matches());
        for (int i = 1; i <= match.groupCount(); i++) {
            System.out.println(match.group(i));
        }

    }

    private static Pattern getPattern() {
        return Pattern.compile(MY_REG);
    }
}
