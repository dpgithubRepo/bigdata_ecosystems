This example is to demonstrate the field grouping and doesn't have any business logic in particular.

Grouping in storm decides how the input stream must be distributed across the bolt tasks.

Field grouping makes sure that fields having the same value will be sourced to the same bolt task.


UserBonusSourceSpout.java
--------------------------

Spout emitts 5 users and their bonus, three times for each user. 
In the driver class we are creating the 5 instances of the spout so each instance will emit the data related to a particular user


UserBonusOutputterBolt.java
---------------------------

The output file is created based on the task id. The number of instances created in the driver class is 5 we will have 
5 tasks running and since we are using the field grouping based on user id. The five user data created in the spout will get a seperate
bolt task and 5 output files for each user.

UserBonusTopology.java
----------------------

Topology driver class where the spout and bolt instances are configured to 5 and also the max parallelism is also set to 5

also field grpuping based on user_id is configured.


Output Files:
------------

As expected there were 5 files created and each file will contain information of a single user
(each file will have the  three records user bonus data of a single user )

refer user_bonus_file_2, user_bonus_file_3, user_bonus_file_4, user_bonus_file_5, user_bonus_file_6


Shuffle Grouping:
-------------------

when the field grouping is changed to shuffle grouping the output will be different and the same user bonus data may 
get distributed across files

This can be tested by 
1. commenting out line 48 and un-commenting the line 49 in the UserBonusOutputterBolt.java
2. commenting out line 24 and un-commenting the line 25 in the UserBonusTopology.java

The output files for shuffle grouping are also checked in 

refer: user_bonus_file_shuffle2, user_bonus_file_shuffle3, user_bonus_file_shuffle4, user_bonus_file_shuffle5, user_bonus_file_shuffle6



mvn command to run the stom topology :

mvn compile exec:java -Dstorm.topology=com.storm.userid.field.grouping.UserBonusTopology
