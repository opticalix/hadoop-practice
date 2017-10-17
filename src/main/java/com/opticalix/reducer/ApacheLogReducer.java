package com.opticalix.reducer;

import com.opticalix.LogPart;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ApacheLogReducer extends Reducer<Text, MapWritable, Text, Text> {
    Text result = new Text();

	public void reduce(Text	key, Iterable<MapWritable> values, Context context) throws IOException,InterruptedException {
		StringBuilder sb = new StringBuilder();
		for (MapWritable val: values) {
			//字段平铺
			for (LogPart lp : LogPart.values()) {
				sb.append(val.get(lp.name()));
				sb.append("\t");
			}
		}
		result.set(sb.toString());
		context.write(key, result);
	}
}