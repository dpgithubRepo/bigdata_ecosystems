package com.storm.call.log.analysis.field.grouping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;


/**
 * The Class CallLogSourceSpout - act as the source of mobile data call log stream.
 *
 * Output tuple of this spout will be tuple of format (from_mob, to_mob, call_duration)
 *
 * @author Durga Prasad
 */
public class CallLogSourceSpout extends BaseRichSpout{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8088721647296535529L;
	
	/** The spout output collector. */
	private SpoutOutputCollector spoutOutputCollector;
	
	/** The random. */
	private Random random;
	
	/** The mobile numbers. */
	private List<String> mobileNumbers;

	private int counter = 0;
	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#nextTuple()
	 */
	@Override
	public void nextTuple() {
		
		
		while(counter<=100){
			String from_mob = mobileNumbers.get(random.nextInt(mobileNumbers.size()));
			String to_mob = mobileNumbers.get(random.nextInt(mobileNumbers.size()));
			if(!from_mob.equals(to_mob)){
				counter++;
				spoutOutputCollector.emit(new Values(from_mob,to_mob,random.nextInt(100)));
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#open(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.spout.SpoutOutputCollector)
	 */
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector spoutOutputCollector) {
		
		this.spoutOutputCollector = spoutOutputCollector;
		random = new Random();
		mobileNumbers = new ArrayList<String>();
		mobileNumbers.add("9876543210");
		mobileNumbers.add("7890654321");
		mobileNumbers.add("8901234567");
		mobileNumbers.add("1234567890");
		mobileNumbers.add("2345678901");
		mobileNumbers.add("3333333333");
		mobileNumbers.add("5656666666");
		mobileNumbers.add("2222222222");
		
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("src_mob","to_mob","call_duration"));
		
	}
	
	/**
	 * The main method. Just to test the random mobile call log generator. This has nothing to do with the topology.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		List<String> mobileNumbers = new ArrayList<String>();
		Random random = new Random();
		mobileNumbers.add("9876543210");
		mobileNumbers.add("7890654321");
		mobileNumbers.add("8901234567");
		mobileNumbers.add("1234567890");
		mobileNumbers.add("2345678901");
		mobileNumbers.add("3333333333");
		mobileNumbers.add("5656666666");
		mobileNumbers.add("2222222222");
		
		
		int counter = 0;
		while(counter<=200){
			String from_mob = mobileNumbers.get(random.nextInt(mobileNumbers.size()));
			String to_mob = mobileNumbers.get(random.nextInt(mobileNumbers.size()));
			System.out.println(from_mob + " --- >> " + to_mob + " --> "+ random.nextInt(60));
			counter++;
		}
	}

}
