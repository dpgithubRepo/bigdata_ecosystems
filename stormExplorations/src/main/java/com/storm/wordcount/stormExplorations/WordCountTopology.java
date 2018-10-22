package com.storm.wordcount.stormExplorations;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountTopology {

	public static void main(String[] args) {

		TopologyBuilder tBuilder = new TopologyBuilder();
		tBuilder.setSpout("spout", new SentenceDataSourceSpout(), 5);
		tBuilder.setBolt("bolt1", new SentenceSplitterBolt(), 8)
				.shuffleGrouping("spout");
		tBuilder.setBolt("bolt2", new WordCounterBolt(), 12).fieldsGrouping(
				"bolt1", new Fields("word"));

		Config conf = new Config();
		conf.setDebug(true);
		conf.setMaxTaskParallelism(3);
		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("word-count", conf,
				tBuilder.createTopology());

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		localCluster.shutdown();

	}

}
