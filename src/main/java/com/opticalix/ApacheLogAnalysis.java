package com.opticalix;

import com.opticalix.mapper.ApacheLogMapper;
import com.opticalix.reducer.ApacheLogReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.util.Arrays;

public class ApacheLogAnalysis {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if(otherArgs.length != 3) {
            System.err.println("Usage: ApacheLogAnalysis <in> <out>"+", otherArgs.length="+otherArgs.length+",otherArgs="+ Arrays.toString(otherArgs));
            System.exit(2);
        }


        Job job = new Job(conf, "ApacheLogAnalysis");
        job.setJarByClass(ApacheLogAnalysis.class);
        job.setMapperClass(ApacheLogMapper.class);
        job.setCombinerClass(ApacheLogReducer.class);//combiner?
        job.setReducerClass(ApacheLogReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
