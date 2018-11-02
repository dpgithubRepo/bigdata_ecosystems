# bigdata_ecosystems
contains projects / examples related to big data processing



Apache Storm :

 1. Distributed real time big data processing system (where as Hadoop suits for batch processing)
 2. Fault tolerant
 3. Horizontal Scalable
 4. Streaming data framework meaning it is an event/activity based i.e when ever there is some activity happens/triggered the data is created and ingested to the target systems.
 5. Storm is stateless but it takes the help of zookeeper to maintain the master and slave nodes and helps them to work in tandem
 6. can execute all types of processing on real time data in parallel

Storm Architecture:

   -->  contains Master and Slave / worker nodes
   
   --> zoo keeper manages the master and slave nodes
   
   --> master node is similar to hadoop's name node
   
   --> Nimbus process is run in master node and supervisor process in the slave nodes
   
   --> each supervisor process contains spouts and bolts
   
   --> Storm jobs are called Topology
   
   --> Real time scenarios the Topology is not an ending job
   
   --> Nimbus takes care of distributing the code across the cluster, assigning jobs to the slaves and monitoring them and take care of failures
   
   --> Each worker node runs a daemon called Supervisor
   
  --> Each supervisor runs one or more process which are separate JVM process
  
  --> Each worker process (i.e jvm process) can run one or more tasks in parallel(spouts & bolts)
  
  --> Each supervisor listens to Nimbus and then starts or stops the process as necessary
  
 --> Each worker process executes a subset of topology
 
  --> A running topology consists of many number of worker process spread across the cluster
 
 Spout and Bolt 

    --> Spout is the source of data stream (can be unreliable(fire and forget) or reliable( can replay the failed tuples))
   --> Bolt consumes one or more streams and creates new stream(from running functions, filters etc)

Stream:

     --> Stream is core abstraction of storm
     --> unbounded sequence of the tuples

Topology

   --> topology network of spouts and bolts
   --> spouts act as data receiver from external resources and provide the streams to bolts
    -->bolts can be processed sequentially or in parallel as required.
  --> DAG (Direct acyclic Graph)


Storm Topology Patterns

    --> 1. Streaming Joins  
    --> 2. Batching
    --> 3. Basic Bolt
    --> 4. In memory caching + fields grouping combo
    --> 5. Streaming TOP N
    --> 6. Time cache map for efficiently keeping a cache of things that have been recently updated
    -->7. CoordinatedBolt and KeyedFairBolt for Distributed RPC

Installation on a single standalone machine:

     --> Standard installation process : Detailed steps shall be documented

Installation of Kafka :

    --> Standard installation process : Detailed steps shall be documented

1. Storm - Word Count example : Refer Storm Explorations package: com.storm.wordcount.stormExplorations (Topology contains a single spout and two bolts)


More details on Spout :

--> Spout is the source of stream
--> Reliable Spout can replay the tuples where as the unreliable spout cannot replay the tuple
--> with reliable spout the failed tuples can be tried to process again where as it is not possible with unreliable spout
--> Reliable spout helps in achieving at least once message processing semantic where as unreliable spout follows at most once message processing semantic
--> Spout hierarchy
   
                ISpout (intf)   <-------- IRichSpout(intf) <------------ BaseRichSpout(abstract class)

--> important methods of the spout : open, nextTuple, declareOutputFields,ack ,fail
--> ack , fail are mainly for reliable spouts


More details on Bolt:

--> Bolt is where the processing logic happens in the topology
--> Bolt can filter, functions, aggregate, mapping, communication to db etc
--> Bolts do the stream transformations
--> there can be one or more bolts based on the use case and complexity
--> Spout output acts as input to bolt and output bolt forms the input to other bolts

 Hierarchy of Bolt:

  IBolt(intf)  <----------IRichBolt(intf)  <------BaseRichBolt(abstract)
  
  IBolt(intf)  <----------IBasicBolt(intf)  <------BaseBasicBolt(abstract)

BaseBasicBolt  does the ack of tuples automatically where as the BaseRichBolt implementation needs to have the ack method explicitly. ack method of BaseRichbolt has more control where the implementation can decide whether to ack or not

Important methods of the Bolt:
 1. execute()
 2. declareOutputFields()


Stream Groupings: decides how the stream must be partitioned across the tasks

Types of Grouping

1. Shuffle Grouping  : Tuples are randomly distributed across the bolt tasks but the number of tuples each task gets is distributed equal in number.
2. Field Grouping :  Tuples with same field value will be distributed to the same Bolt task (eg: tuples with field itemid having same values will go to same bolt task )
3. All Grouping : Stream is replicated across all the bolt tasks.
4. Global Grouping : Entire stream goes to single bolt(mostly to the task with lowest bolt id)
5. Partial key Grouping :  This is same like the Fields grouping but is load balanced between two downstream bolts . enhances the performance
6. None Grouping : Don't care. randomly distributed across the bolt tasks.
7. Direct Grouping : Producer of the tuple will decide which bolt task should consume the tuple.(works only on direct strem). The tuple is directed by the producer.
8. Local or shuffle grouping : if the target bolt has one or more in-process tasks, tuples will be shuffled across the local process.


More on Topology :

1. Actual topology will be created by system-topology! function. actual topology has implicit acker bolt and implicit streams.
2. system-topology! is used  1. when nimbus is creating tasks 2. worker - so worker node will know  where to route the messages


Life cycle of Topology :

Once StormSubmitter submitTopology() is called following steps happen

First Step uploading JAR
 1. Stormuploads the jar if its not uploaded earlier  (jar is where the entire topology i.e the bolt spout code is present)
 2. jar uploading is done via Nimbus Thrift Service 
3.  beginFileulpoad returns a path in Nimbu's inbox
4. 15kb is upload at a time in chunk
5. finishFileUpload is called once the jar is uploaded

other steps in sequence
  --> StormSubmitter calls the submitTopology on the Nimbus Thrift Service
  
  --> Topology configuration is serialized using json
  
  -->Thrift SubmitTopology takes the nimbus inbox path where the jar was uploaded
  
   --> Nimbus Normalizes the topology configuration. Normalization is important bcos it ensures every single task  have same    serialization registration for getting the serializations work properly.

--> nimbus sets up the static state of the topology 

        jars and configs are put in the local directory of nimbus  {nimbus local directort}/stormdist/{topology id} 
         
        setup-storm-static writes task -> component mapping to ZK
        
        setup-hearbeats creates a directory in ZK where tasks can heartbeat


WORK IN PROGRESS--------

 








