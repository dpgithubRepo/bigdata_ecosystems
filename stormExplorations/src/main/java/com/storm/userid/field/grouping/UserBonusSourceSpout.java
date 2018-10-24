package com.storm.userid.field.grouping;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * The Class UserBonusSourceSpout.
 * 
 *  @author Durga Prasad
 */
public class UserBonusSourceSpout extends BaseRichSpout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6868858515007693495L;
	
	/** The random. */
	private Random random;
	
	/** The spout output collector. */
	private SpoutOutputCollector spoutOutputCollector;
	
	/** The count. */
	private static int count =0;

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#nextTuple()
	 */
	@Override
	public void nextTuple() {
		while(count<5){
			count++;
			//lets consider the tuple(0) as user id and tuple(1) as bonus he gets
			//for each user let us consider he gets the bonus three times
			spoutOutputCollector.emit(new Values("USER::"+count, random.nextInt(80)));
			spoutOutputCollector.emit(new Values("USER::"+count, random.nextInt(80)));
			spoutOutputCollector.emit(new Values("USER::"+count, random.nextInt(80)));
			
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#open(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.spout.SpoutOutputCollector)
	 */
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector spoutOutputCollector) {
		random = new Random();
		this.spoutOutputCollector = spoutOutputCollector;
	
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		
		outputFieldsDeclarer.declare(new Fields("user_id","bonus"));
		
	}

}
