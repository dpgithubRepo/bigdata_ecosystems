package com.transaction.mapsidejoin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class App 
{
    public static void main( String[] args ) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException
    {
    	
    	Configuration config = new Configuration();
    	Job job = new Job(config, "transaction-map-side-join");
    	
    	job.setJarByClass(App.class);
    	job.setMapperClass(TransactionMapper.class);
    	job.setNumReduceTasks(0);
    	
    	
    	//The following lines puts the users.txt to distributed cache.make sure the files is
    	//present in hdfs at the said location
    	DistributedCache.addCacheFile(new URI("/data/cache/users.txt"), job.getConfiguration());
    	
    	job.setMapOutputKeyClass(IntWritable.class);
    	job.setMapOutputValueClass(Text.class);
    	
    	FileInputFormat.addInputPath(job, new Path(args[0]));
    	FileOutputFormat.setOutputPath(job, new Path(args[1]));
    	
    	job.waitForCompletion(true);
    	
    	
    }
}
