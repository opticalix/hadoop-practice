package com.opticalix.mapper;

import com.opticalix.model.LogPart;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApacheLogMapper extends Mapper<Object, Text, Text, Text> {

    private static final String MY_REG = "([0-9.]+)" + "(?:[\\s\\S]+)"
            + "(\\[[^\\[\\]]+])" + "(?:[\\s\\S]*)"
            + "\"((?:[^\"]|\")+)\"" + "(?:[\\s\\S]*)"
            + "(\\d{3})\\s" + "(?:[\\s\\S]*)";
    private Text mText;

    private static Pattern getPattern() {
        return Pattern.compile(MY_REG);
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String valueStr = value.toString();
//        MapWritable mapWritable = new MapWritable();
        mText = new Text();

        Matcher match = getPattern().matcher(valueStr);
        if (!match.matches()) {//第一部分有可能是host
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= match.groupCount(); i++) {
            if (match.matches()) {
//                mapWritable.put(new Text(LogPart.getNameByIndex(i)), new Text(match.group(i)));
                sb.append(match.group(i)).append("\t");
            } else {
//                mapWritable.put(new Text(LogPart.getNameByIndex(i)), new Text(""));
            }
        }

        //keep original key
        mText.set(sb.toString());
        context.write(new Text(match.group(LogPart.IP.getIndex())), mText);
    }
}
