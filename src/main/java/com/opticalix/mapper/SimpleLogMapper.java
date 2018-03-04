package com.opticalix.mapper;

import com.opticalix.util.LogRegexParser;
import com.opticalix.util.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;

/**
 * 用于日志分析
 * https://www.cnblogs.com/edisonchou/p/4449082.html
 */
public class SimpleLogMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        super.map(key, value, context);
        Matcher matcher = LogRegexParser.match(value.toString());
        if (matcher.matches()) {
            String result = StringUtils.parseLog(value.toString());
            context.write(key, new Text(result));
        }
    }

}
