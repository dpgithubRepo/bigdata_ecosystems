package com.transaction.mapsidejoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TransactionMapper extends Mapper<LongWritable, Text, Text, Text>{

	Map<String, String> userInfo = new HashMap<String, String>();
	
	
	//load the users file from distributed cache to memory
	@Override
	protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		
		Path[] files = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		for(Path file : files){
			if(file.getName().equals("users.txt")){
				BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
				String line = null;
				while((line= reader.readLine())!=null){
					String[] cols = line.split("\\s+");
					String usrId = cols[0];
					String emailId = cols[2];
					userInfo.put(usrId, emailId);
				}
				
			}
		}
	}
	
	
	//001 Robin robin_stuart1@gmail.com   -- user file
	//t002 001 Food debit NYC -- transaction file
	// let us build out put like transactionid usermailid
	// t001 stuart1@gmail.com 
		
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		String[] transactionCols = value.toString().split("\\s+");
		if(transactionCols!=null && transactionCols.length >2)
			context.write(new Text(transactionCols[0]), new Text(userInfo.get(transactionCols[1])));
	}
	
	
}
