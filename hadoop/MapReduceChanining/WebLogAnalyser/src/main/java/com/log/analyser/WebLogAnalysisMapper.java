package com.log.analyser;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * The Class WebLogAnalysisMapper.
 * 
 * @author Durga Prasad
 */
public class WebLogAnalysisMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
	
	/** The Constant ONE. */
	private static final LongWritable ONE = new LongWritable(1);
	
	/* Following is the sample input file
	2011-10-26 06:11:35 user1 210.77.23.12
	2011-10-26 06:11:45 user2 210.77.23.17
	2011-10-26 06:11:46 user3 210.77.23.12
	2011-10-26 06:11:47 user2 210.77.23.89
	2011-10-26 06:11:48 user2 210.77.23.12
	2011-10-26 06:11:52 user3 210.77.23.12
	2011-10-26 06:11:53 user2 210.77.23.12...*/
	
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		
		if(value !=null && StringUtils.isNotEmpty(value.toString())){
			String line = value.toString();
			context.write(new Text(line.split("\\s+")[2]), ONE);
		}		
	}

}
