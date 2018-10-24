package com.storm.userid.field.grouping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

/**
 * The Class UserBonusOutputterBolt.
 * 
 * @author Durga Prasad
 */
public class UserBonusOutputterBolt extends BaseRichBolt {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3191245080259874409L;
	
	/** The user_bonus_file. */
	File user_bonus_file;
	
	/** The buffered writer. */
	BufferedWriter bufferedWriter;
	
	/* (non-Javadoc)
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector outputCollector) {
		
		/**
		 * task id of the bolt instance is used in the file name to demonstrate the field grouping. 
		 * In the spout we are creating 5 users and in the topology driver class we shall create 5 instances of the bolt for
		 * the field grouping.
		 * 
		 * Grouping is done based on user_id since we have 5 users and each user is assumed to get bonus three times.
		 * if the grouping works properly then there would be 5 files generated and each file should only contain the bonus information of 
		 * a single user thrice.
		 * 
		 * maxparallelism of task is set to 5 in driver and also the spout instances are configured to 5
		 */
		user_bonus_file = new File("user_bonus_file_"+context.getThisTaskId());
		try {
			user_bonus_file.createNewFile();
			bufferedWriter = new BufferedWriter(new FileWriter(user_bonus_file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {
		try {
			bufferedWriter.write(tuple.getString(0)+" -- "+tuple.getInteger(1));
			bufferedWriter.write("\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	


}
