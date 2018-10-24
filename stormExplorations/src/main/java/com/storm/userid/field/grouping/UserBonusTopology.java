package com.storm.userid.field.grouping;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * The Class UserBonusTopology.
 * 
 *  @author Durga Prasad
 */
public class UserBonusTopology {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		TopologyBuilder topologyBuiler = new TopologyBuilder();
		topologyBuiler.setSpout("user_bonus_spout", new UserBonusSourceSpout(),1);
		topologyBuiler.setBolt("user_bonus_bolt", new UserBonusOutputterBolt(),5).fieldsGrouping("user_bonus_spout", new Fields("user_id"));
		
		Config config = new Config();
		config.setDebug(true);
		config.setMaxTaskParallelism(5);
		
		LocalCluster localCluster = new  LocalCluster();
		localCluster.submitTopology("user_bonus_topology", config, topologyBuiler.createTopology());
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		localCluster.shutdown();
	}

}
