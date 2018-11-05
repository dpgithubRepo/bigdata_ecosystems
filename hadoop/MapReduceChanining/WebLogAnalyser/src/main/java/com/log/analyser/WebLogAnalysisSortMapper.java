package com.log.analyser;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * The Class WebLogAnalysisSortMapper.
 * 
 * @author Durga Prasad
 */
public class WebLogAnalysisSortMapper extends Mapper<Text, LongWritable,LongWritable,Text>{
	
/* (non-Javadoc)
 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
 */
@Override
protected void map(Text key, LongWritable value, Mapper<Text, LongWritable, LongWritable, Text>.Context context)
		throws IOException, InterruptedException {
	
	context.write(value, key);
}
}
