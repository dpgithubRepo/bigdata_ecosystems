package com.log.analyser;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * The Class App.
 * 
 * @author Durga Prasad
 */
public class App 
{
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     * @throws InterruptedException the interrupted exception
     */
    public static void main( String[] args ) throws IOException, ClassNotFoundException, InterruptedException
    {
        Configuration conf = new Configuration();
        Path out = new Path(args[1],"out");
        Job job = new Job(conf);
        job.setJarByClass(App.class);
        job.setMapperClass(WebLogAnalysisMapper.class);
        job.setReducerClass(WebLogAnalysisReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(out,"out1"));
        
        
        job.waitForCompletion(true);
        
        Job job2 = new Job(conf);
        job2.setJarByClass(App.class);
        job2.setMapperClass(WebLogAnalysisSortMapper.class);
        job2.setSortComparatorClass(LongWritable.DecreasingComparator.class);
        //This sets up the default identity reducer and doesn't require explicit Reducer
        job2.setNumReduceTasks(1);
        job2.setOutputKeyClass(LongWritable.class);
        job2.setOutputValueClass(Text.class);
        
        job2.setInputFormatClass(SequenceFileInputFormat.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        
        FileInputFormat.addInputPath(job2, new Path(out,"out1"));
        FileOutputFormat.setOutputPath(job2, new Path(out,"out2"));
        
        
        job2.waitForCompletion(true);
        
        
        
    }
    
    
}
