

Call Log Topology
-----------------
CallLogSourceSpout  ------> CallLogFormatterBolt  -------> CallLogAggregatorBolt


CallLogTopology is the Driver class which submits the topology.



The input to the CallLogFormatterBolt is shuffle grouped where as the input to CallLogAggregatorBolt is field grouped based on the field call_log_formatter_bolt.
This makes sure that the field call_log_formatter_bolt with same values are streamed to the same CallLogAggregatorBolt.



Command to run :  

mvn compile exec:java -Dstorm.topology=com.storm.call.log.analysis.field.grouping.CallLogTopology