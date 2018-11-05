
Multi Input Format, Mutliple Mapper and Single Reducer:
------------------------------------------------------

There can be an use case which needs more than one mapper and a single reducer.In solving such case we have to take care of the
following which we didn't face in a normal Single MR program


1. when we have multiple mappers the input file types and formats may be different. how to handle multi file inputs and also 
   Input file formats may be different.
   
2. Identify the key which can be used to join the two input files

3. How would reducer identify/differentiate the outputs from different mappers (since we have a single reducer, it has to know from which reducer the input is coming from)


Let us consider a use case where there is a file which contains 

      account_number, account_holder_name, account_holder_mob_no, account_holder_mail_id
      ----------------------------------------------------------------------------------
      
Let us consider the bank sends some notification via mobile to the account holders and the log file is created with following data

      account_holder_mob_no   mob_notification_status
      --------------------------------------------
      
      
 There can be cases where the mobile notification may be failed and the bank wants to notify the users through mail and for that 
 bank initially wants the data in the following format
 
      account_holder_name account_holder_mob_no, account_holder_mail_id,mob_notification_status
      --------------------------------------------------------------------------------------
