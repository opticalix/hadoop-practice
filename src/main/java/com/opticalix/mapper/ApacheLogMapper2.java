package com.opticalix.mapper;

import com.opticalix.util.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 用于日志分析
 * https://www.cnblogs.com/edisonchou/p/4449082.html
 */
public class ApacheLogMapper2 extends Mapper<LongWritable, Text, LongWritable, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        try {
            String parseLog = StringUtils.parseLog(value.toString());
            if (!"".equals(parseLog)) {
                context.write(key, new Text(parseLog));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
