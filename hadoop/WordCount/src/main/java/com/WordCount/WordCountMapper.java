package com.WordCount;

import java.io.IOException;
import java.text.BreakIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * The Class WordCountMapper.
 * 
 * @author Durga Prasad
 */
public class WordCountMapper  extends Mapper<LongWritable, Text, Text, IntWritable>{
	
	/** The Constant ONE. */
	private static final IntWritable ONE = new IntWritable(1);
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		BreakIterator breakIterator = BreakIterator.getWordInstance();
		String s = value.toString();
		breakIterator.setText(s);
		int first = breakIterator.first();
		for(int end = breakIterator.next();end != breakIterator.DONE;first = end,end = breakIterator.next()){
			String word = s.substring(first, end);
			if(StringUtils.isNotEmpty(s)){
				context.write(new Text(word), ONE);
			}
		 
	}
	
	 }
}
