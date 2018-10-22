/*
 * 
 */
package com.storm.wordcount.stormExplorations;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

/**
 * The Class WordDataSourceSpout.
 *
 * @author Durga Prasad
 */

public class SentenceDataSourceSpout extends BaseRichSpout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The output collector. */
	SpoutOutputCollector outputCollector;
	
	/** The random. */
	Random random;

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#nextTuple()
	 */
	public void nextTuple() {
		Utils.sleep(100);
		String sentenceSourceArray[] = {
				"Apache Storm is a distributed stream processing computation"
						+ " framework written predominantly in the Clojure programming language",
				"the project was open sourced after being acquired by Twitter",
				"A system for processing streaming data in real time Apache™ Storm" };

		String randomSentence = sentenceSourceArray[random
				.nextInt(sentenceSourceArray.length)];
		outputCollector.emit(new Values(randomSentence));

	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#open(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.spout.SpoutOutputCollector)
	 */
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector outputCollector) {

		this.outputCollector = outputCollector;
		random = new Random();

	}

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("senetence"));

	}

}
