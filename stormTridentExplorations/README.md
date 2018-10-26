Trident :

1. Trident is high level abstraction on top of Storm.

2. it provides abstraction for aggregation, join, groupings,filterings & functions.

3. Storm is event based processing and process the tuples as they get generated where as the Trident provides the capability 
   to group the tuple to batches and process the batch.
  
4. Also Trident supports Transactional processing to provide better fault tolerance and the important feature of Trident is it    supports stateful processing.

5. Trident libraries are part of standard Storm installation.

6. Trident adds primitives for doing the stateful incremental processing on any db or persistence store.

7. Trident has consistent, exactly once semantic which means each tuple will be processed only once with proper fault tolerant    mechanisms in place.


Storm Default Stateless processing vs Storm Trident Stateful Processing

Once a tuple is processed, Storm doesn't store any information about the tuple.So a tuple is received we don't know if it is 
already processed or not and this is default processing of Storm.

Whereas Trident provides the topology which automatically maintains the state information.State information will be stored either to data base or memory, so we will know if the tuple is already processed or not.


Trident supports the exactly oncee semantic by the following steps

1. Every batch is given a unique id called transaction id. if the batch is retried it will have the same transaction id. so if the db already has the transaction id it means its already processed and it will stop the retry

2. State updates for each batch follows the strict order.for eg. state updates for batch 3 will not be applied until state updates for batch 4. similarly batch 5 updates will not be applied until batch 4(a strict ordering is followed). so if you see the batch number 4 as max batch number it can be safely assumed that all the batches before 4 (inclusive of 4) are successfully updated and no need of retries and all the batches after 4 has to be processed.

so maintaining the transaction id for batch and the strong ordering while updating the state helps to achieve the exactly once processing semantic.

Exactly once semantic comes with a cost of storing the transaction id in data base otherwise it will fall under atleast one 
semantic in case of failures which can be fine for the use case and more over the logic discussed in the above two steps is not needed to implement in the topology manually as this logic is already wrapped by state abstraction and done automatically.

Storing state can be of ay strategy either stored  or data base or in-memory but backed by HDFS like hbase etc. and also this states need not be stored forever. for example you could have an in memory implementation which keeps the data which is x hrs/days older and drop remaining.






