package com.storm.call.log.analysis.field.grouping;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;


//mvn compile exec:java -Dstorm.topology=com.storm.call.log.analysis.field.grouping.CallLogTopology
public class CallLogTopology {
	
	public static void main(String[] args) {
		
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		topologyBuilder.setSpout("call_log_spout", new CallLogSourceSpout(),2);
		topologyBuilder.setBolt("call_log_formatter_bolt", new CallLogFormatterBolt(),10).shuffleGrouping("call_log_spout");
		topologyBuilder.setBolt("call_log_aggregator", new CallLogAggregatorBolt(),10).fieldsGrouping("call_log_formatter_bolt", new Fields("from_to_call_log"));
		
		
		Config conf = new  Config();
		conf.setDebug(true);
		conf.setMaxTaskParallelism(3);
		
		LocalCluster localCluster = new  LocalCluster();
		localCluster.submitTopology("call_log_topology", conf, topologyBuilder.createTopology());
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		localCluster.shutdown();
		
	}

}
