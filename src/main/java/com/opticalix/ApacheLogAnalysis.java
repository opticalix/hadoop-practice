package com.opticalix;

import com.opticalix.mapper.ApacheLogMapper;
import com.opticalix.mapper.SimpleLogMapper;
import com.opticalix.reducer.ApacheLogReducer;
import com.opticalix.reducer.FlatValuesReducer;
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
    public static void main(String[] args) throws Exception {
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
        job.setJarByClass(ApacheLogAnalysis.class);
        job.setMapperClass(SimpleLogMapper.class);
        job.setReducerClass(FlatValuesReducer.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        String inputPath = otherArgs[1];
        String outputPath = otherArgs[2];
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        FileSystem fs = FileSystem.get(new URI(inputPath), conf);
        Path outPath = new Path(args[1]);
        if (fs.exists(outPath)) {
            System.out.print(String.format(Locale.getDefault(), "Delete old dir: %s", outPath));
            fs.delete(outPath, true);
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
