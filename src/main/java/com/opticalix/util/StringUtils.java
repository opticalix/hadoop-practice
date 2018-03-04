package com.opticalix.util;

import java.util.regex.Matcher;

public class StringUtils {
    /**
     * parse log
     * @param logLine sample: 27.19.74.143 - - [30/May/2013:17:38:20 +0800] "GET /static/image/common/faq.gif HTTP/1.1" 200 1127
     * @return string
     */
    public static String parseLog(String logLine) {
        Matcher matcher = LogRegexParser.match(logLine);
        StringBuilder sb = new StringBuilder();
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                sb.append(group);
                if (i < matcher.groupCount()) {
                    sb.append("\t");
                }
            }
            return sb.toString();
        }
        return "";
    }
}
