package com.log.analyser;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * The Class WebLogAnalysisReducer.
 * 
 * @author Durga Prasad
 */
public class WebLogAnalysisReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
	
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	@Override
	protected void reduce(Text key, Iterable<LongWritable> values,
			Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
		int loginCount =0;
		for(LongWritable value : values){
			loginCount++;
		}
		context.write(key, new LongWritable(loginCount));
	}

}
