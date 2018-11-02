
WordCount is like hello world program of hadoop MR.

WordCountDriver.java	 Driver Class 

WordCountMapper.java	 Mapper class 

WordCountReducer.java  Reducer class



Steps to Run:

1. Create the Jar out of this Project and let us call the output jar as wordcount_yarn.jar
2. Create the input file call it as input.txt in your local and copy it to hdfs using the following command
    hdfs dfs -copyFromLocal /path/of/the/inputfile/onlocal/input.txt /
    
3.let us store the output of this MR to a folder called word_output

4. Command to start the MR program

     hadoop jar /pathtojar/wordcount_yarn.jar com.wordcount.WordCountDriver /input.txt word_output
     
     
     
  Result:
  
  Once the mr is run word_output folder will have two files

  1. _SUCCESS   : This is a zero byte file just by reading the name of the file we can be conclude on final result                                the MR program(either success or failure). This can be helpful during automation steps to determine the                       final state of MR execution
  
  2. part-r-00000  : This contains the actual result of the MR program 
                     output will be depend on the input text but format of the result would look like following (word  count)
    
                     Big 234
                     Data 245
                     an 354
    
