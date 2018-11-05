Core hadoop installation comes with the following components
-----------------------------------------------------------

1. HDFS
2. YARN
3. Map-Reduce

The other hadoop eco system components must be installed and configured as per the needs

Following are the some of the important hadoop eco system projects.
------------------------------------------------------------------

1. Sqoop : tool to transfer data from RDBMS to Hadoop or  from Hadoop to RDBMS
2. Flume : utility to transfer large amounts of real time logs into HDFS
3. Oozie : hadoop workflows
4. Pig : Pl/sql on hdfs
5. Hive : SQL on hdfs
6. Impala : SQL on hdfs (faster than hive, but fail over mechanism is not available. on the failure of even single node jobs               will have to be resubmitted)
7. Spark : faster than MR with more capabilities like Streaming etc
8. HUE : Hadoop on browser
9. Sentry : ACL(Access control) on hive tables
