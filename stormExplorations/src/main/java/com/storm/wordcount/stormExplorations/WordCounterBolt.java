package com.storm.wordcount.stormExplorations;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordCounterBolt extends BaseBasicBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Map<String, Integer> wordCountMap = new HashMap<String, Integer>();

	public void execute(Tuple tuple, BasicOutputCollector collector) {
		String word = tuple.getString(0);
		Integer count = wordCountMap.get(word);
		count = (count == null) ? 0 : count;
		++count;
		wordCountMap.put(word, count);
		collector.emit(new Values(word, count));

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

		declarer.declare(new Fields("word", "count"));

	}

}
