package com.opticalix.mapper;

import com.opticalix.ApacheLogAnalysis;
import com.opticalix.LogPart;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApacheLogMapper extends Mapper<Object, Text, Object, MapWritable> {

    private static final String MY_REG = "([0-9.]+)" + "(?:[\\s\\S]+)"
            + "(\\[[^\\[\\]]+])" + "(?:[\\s\\S]*)"
            + "\"((?:[^\"]|\")+)\"" + "(?:[\\s\\S]*)"
            + "(\\d{3})\\s" + "(?:[\\s\\S]*)";

    private static Pattern getPattern() {
        return Pattern.compile(MY_REG);
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String valueStr = value.toString();
        MapWritable mapWritable = new MapWritable();

        Matcher match = getPattern().matcher(valueStr);
//        System.out.println("match group count=" + match.groupCount() + ", matches=" + match.matches());
        for (int i = 1; i <= match.groupCount(); i++) {
            mapWritable.put(new Text(LogPart.getNameByIndex(i)), new Text(match.group(i)));
        }

        //keep original key
        context.write(key, mapWritable);
    }
}
