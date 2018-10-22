package com.storm.wordcount.stormExplorations;

import java.text.BreakIterator;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * The Class SentenceSplitterBolt.
 * 
 *  @author Durga Prasad
 */
public class SentenceSplitterBolt extends BaseBasicBolt {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IBasicBolt#execute(org.apache.storm.tuple.Tuple, org.apache.storm.topology.BasicOutputCollector)
	 */
	public void execute(Tuple tuple, BasicOutputCollector outputCollector) {

		String sentence = tuple.getString(0);
		BreakIterator breakIter = BreakIterator.getWordInstance();
		breakIter.setText(sentence);
		int start = breakIter.first();
		for (int end = breakIter.next(); end != breakIter.DONE; start = end, end = breakIter
				.next()) {

			String word = sentence.substring(start, end);
			word = word.replaceAll("\\s+", "");
			if (!word.equals("") && word.length() > 0) {
				outputCollector.emit(new Values(word));
			}

		}

	}

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(new String("word")));

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {

		String sentence = "Hello Hello this is word";
		BreakIterator breakIter = BreakIterator.getWordInstance();
		breakIter.setText(sentence);

		int start = breakIter.first();

		int count = 0;
		for (int end = breakIter.next(); end != breakIter.DONE; start = end, end = breakIter
				.next()) {
			System.out.println(++count);
			System.out.println(sentence.substring(start, end));
		}

	}

}
