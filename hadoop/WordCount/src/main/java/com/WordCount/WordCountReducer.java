package com.WordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * The Class WordCountReducer.
 * 
 * @author Durga Prasad
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	@Override
	protected void reduce(Text text, Iterable<IntWritable> iterable,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int total = 0;
		for(IntWritable intWritable: iterable){
			total += intWritable.get();
		}
		context.write(text, new IntWritable(total));
	}

}
