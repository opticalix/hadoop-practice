package com.opticalix.job;

import com.opticalix.Main;
import com.opticalix.mapper.ApacheLogMapper;
import com.opticalix.mapper.ApacheLogMapper2;
import com.opticalix.reducer.ApacheLogReducer;
import com.opticalix.reducer.ApacheLogReducer2;
import com.opticalix.util.LogUtil;
import com.opticalix.util.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Locale;

public class ApacheLogAnalysis {
    public static void start(String[] args) throws Exception {
//        LogUtil.p(parseLog);
        logAnalysis2(args);
    }

    private static void logAnalysis1(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 3) {
            System.err.println("Usage: ApacheLogAnalysis <in> <out>" + ", otherArgs.length=" + otherArgs.length + ",otherArgs=" + Arrays.toString(otherArgs));
            System.exit(2);
        }


        Job job = new Job(conf, "logAnalysis1");
        job.setJarByClass(Main.class);
        job.setMapperClass(ApacheLogMapper.class);
        job.setCombinerClass(ApacheLogReducer.class);//combiner?
        job.setReducerClass(ApacheLogReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    private static void logAnalysis2(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 3) {
            System.err.println("Usage: ApacheLogAnalysis <in> <out>" + ", otherArgs.length=" + otherArgs.length + ",otherArgs=" + Arrays.toString(otherArgs));
            System.exit(2);
        }


        Job job = new Job(conf, "logAnalysis2");
        job.setJarByClass(Main.class);
        job.setMapperClass(ApacheLogMapper2.class);
        job.setReducerClass(ApacheLogReducer2.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        String input = otherArgs[1];
        String output = otherArgs[2];
        Path outputPath = new Path(output);
        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, outputPath);

        FileSystem fs = FileSystem.get(new URI(input), conf);
        if (fs.exists(outputPath)) {
            System.out.print(String.format(Locale.getDefault(), "Delete old dir: %s", outputPath));
            fs.delete(outputPath, true);
        }
        boolean success = job.waitForCompletion(true);
        if (success) {
            System.out.println("Clean process success!");
            System.exit(0);
        } else {
            System.out.println("Clean process failed!");
            System.exit(1);
        }
    }
}
