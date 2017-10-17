package com.opticalix.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * jars involved
 * 	hadoop-client
 * 	common
 * 	hdfs
 */
public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
    IntWritable one = new IntWritable(1);
	Text word = new Text();
	
	public void map(Object key, Text value, Context context) throws IOException,InterruptedException {
		//ignore keyIn, split keyValues
		//其value值存储的是文本文件中的一行（以回车符为行结束标记）而key值为该行的首字母相对于文本文件的首地址的偏移量
		StringTokenizer itr = new StringTokenizer(value.toString());
		while(itr.hasMoreTokens()) {
			word.set(itr.nextToken());
			context.write(word, one);//write to output. 将<word,1>作为map方法的结果输出
		}
	}
}