package com.opticalix.reducer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Flat values
 */
public class ApacheLogReducer2 extends Reducer<LongWritable, Text, Text, NullWritable> {
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        values.forEach(v -> {
            try {
                context.write(v, NullWritable.get());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
