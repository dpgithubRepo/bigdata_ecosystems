package com.storm.call.log.analysis.field.grouping;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * The Class CallLogFormatterBolt translates the input call log stream to the required tuple
 * 
 * output tuple will be of the form ("from_mob:::to_mob", call_duration)
 * 
 * @author Durga Prasad
 */
public class CallLogFormatterBolt extends BaseBasicBolt{
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2042308329292666433L;
	private BasicOutputCollector basicOutputCollector;

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IBasicBolt#execute(org.apache.storm.tuple.Tuple, org.apache.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector1) {
		this.basicOutputCollector = basicOutputCollector1;
		basicOutputCollector.emit(new Values(tuple.getString(0)+"::"+tuple.getString(1), tuple.getInteger(2)));
		
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("from_to_call_log","call_duration"));
		
	}

}
