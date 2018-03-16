package com.opticalix.reducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ApacheLogReducer extends Reducer<Text, Text, Text, Text> {
    Text result = new Text();

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		for (Text val: values) {
			context.write(key, val);
		}
	}

//	public void reduce(Text	key, Iterable<MapWritable> values, Context context) throws IOException,InterruptedException {
//		StringBuilder sb = new StringBuilder();
//		for (MapWritable val: values) {
//			//字段平铺
//			for (LogPart lp : LogPart.values()) {
//				sb.append(val.get(lp.name()));
//				sb.append("\t");
//			}
//		}
////		System.out.println("reduce print: "+sb.toString());
//		result.set(sb.toString());
//		context.write(key, result);
//	}
}