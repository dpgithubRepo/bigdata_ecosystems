package com.storm.call.log.analysis.field.grouping;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;



/**
 * The Class ClassLogAggregatorBolt translates the stream from the callLogFormater 
 * 
 * callLogAggregateMap will contain the call duration information for the key from_mob::to_mob 
 * 
 * @author Durga Prasad
 */
public class CallLogAggregatorBolt extends BaseBasicBolt{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4548142926961405185L;
	
	private Map<String,Integer> callLogAggregatorMap = new HashMap<String, Integer>();

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IBasicBolt#execute(org.apache.storm.tuple.Tuple, org.apache.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
		
		String callLogKey = tuple.getString(0);
		int call_duration = tuple.getInteger(1);
		Integer callDurationVal = callLogAggregatorMap.get(callLogKey)==null?call_duration:callLogAggregatorMap.get(callLogKey)+call_duration;
		callLogAggregatorMap.put(callLogKey, callDurationVal);
		basicOutputCollector.emit(new Values(callLogKey, callDurationVal));
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		
	}

}
