package com.opticalix.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;

public class StringUtils {
    /**
     * parse log
     * @param logLine sample: 27.19.74.143 - - [30/May/2013:17:38:20 +0800] "GET /static/image/common/faq.gif HTTP/1.1" 200 1127
     * @return string
     */
    public static String parseLogByRegex(String logLine) {
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

    /**
     * parse log
     * @param logLine sample: 27.19.74.143 - - [30/May/2013:17:38:20 +0800] "GET /static/image/common/faq.gif HTTP/1.1" 200 1127
     * @return string {ip}\t{time}\t{host}\t{status}
     */
    public static String parseLog(String logLine) {
        String logFmt = "dd/MMM/yyyy:HH:mm:ss Z";
        String pntFmt = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter readFmtter = DateTimeFormatter.ofPattern(logFmt);
        DateTimeFormatter pntFmtter = DateTimeFormatter.ofPattern(pntFmt);
        if (logLine == null) {
            return "";
        }
        String ip = logLine.substring(0, logLine.indexOf(" - - ")).trim();
        String time = logLine.substring(logLine.indexOf("[") + 1, logLine.indexOf("]"));
        String coarseUrl = logLine.substring(logLine.indexOf("\"") + 1, logLine.lastIndexOf("\""));
        String host = coarseUrl.substring(coarseUrl.indexOf("/"), coarseUrl.indexOf("HTTP")).trim();
        String status = logLine.substring(logLine.lastIndexOf("\"") + 1, logLine.lastIndexOf(" ")).trim();

        LocalDateTime ldt = LocalDateTime.parse(time, readFmtter);
        String fmtTime = pntFmtter.format(ldt);
        long seconds = Timestamp.valueOf(ldt).getTime() / 1000;
        return ip + "\t" + fmtTime + "\t" + host + "\t" + status;
    }
}
