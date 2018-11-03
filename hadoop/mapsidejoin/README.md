
Map Side Join :

This write up contains the theory about map side join and little of explanation about sample code.

In the MR  mapper code will take care of join logic in Map side Join

let us consider the basic use case where there are two input files.

1. transactions.txt contains user id and transaction related information (let us assume the size is 384 mb)
2. user.txt contains the user info like user_id,user_name,user_email etc

and let us say we need output in the following format

     transaction_id user_email
     
To achieve the above output format we need to join both the above input files


Map Side Join process/steps:

1. Let us assume that transaction input file is divided to 3 blocks(128 mb each) on hdfs

2. To leverage map side join we should load the user file which is relatively small will be loaded to memory of Mapper task

3. Since the transaction file we assumed to be divided to 3 blocks 3 mapper tasks will be assigned and the user file is loaded to memory. The user file will be availabe in memory of each of the mapper container.

when we compare the above two files user file will be of low size compared to that of transaction file and also the growth rate of user file would be less compared to that of transaction file.

4. Job tracker will load the user file to distributed cache of the every task tracker node.Distributed cache is an area in local file system of the slave(task tracker node) and it will be loaded to the memory of map task

5. Hence the user file is made available to all the nodes where the mapper task is running(in our case 3 nodes) which helps to achieve the join for all the blocks(of transaction file) which are getting processed.

6. Map task can only read files from Distributed cache.



Code :  

1. TransactionMapper.java   : This is same as any traditional mapper class, except here we have overriden the setup method to 
                              read the users.txt file from distributed cache and the required info is loaded to a Map ds which 
                              is further used in map method to achieve the join

2. TransactionReducer.java  : This use case doesn't require the reducer.

3. App.java : This is the driver class which loads the users.txt to the distributed cache and also we are setting the number                  of reducers to zer0 as this use case doesn't need the reducer phase.






