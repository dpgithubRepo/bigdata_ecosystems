
consider the following input format
-----------------------------------

2011-10-26 06:11:35 user1 210.77.23.12

2011-10-26 06:11:45 user2 210.77.23.17

2011-10-26 06:11:46 user3 210.77.23.12

2011-10-26 06:11:47 user2 210.77.23.89

2011-10-26 06:11:48 user2 210.77.23.12

2011-10-26 06:11:52 user3 210.77.23.12

2011-10-26 06:11:53 user2 210.77.23.12


and let us say that we need the output in the following format sorted based on the number of times the 
user has logged in descending order
------------------------------------------

4 user2

2 user3

1 user1



Map Reduce chaining
--------------------

This can be achieved by chaining two MR jobs. The first MR job does the task of counting the number of times 
the user has logged in. Output of the first MR job will be like with out sort

User3 2

User2 4

user1 1

The second Map job does the swap of key and value i.e the value will be the number of login counts and the value will be the 
username and Longwritables sorting method is used along with implicit identity reducer with out explicit implementation of the 
second reducer job

Implicit Identity Reducer :
---------------------------

Refer the drive class App.java for chaining and implicit identity reducer details.Also the output of the first MR is stored as 
sequence file format which is the input to the second MR job.
