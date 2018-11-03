Sqoop :
-------

This write up summarizes the usage of sqoop.

Sqoop is used to transform data from RDBMS to HDFS and also from HDFS to RDBMS.

for eg: we can cosider there is some legacy system which uses RDBMS for storage and let us consider the amount of data is big and need to run some kind of MR process. we can use sqoop to connect to the RDBMS and load data from RDBMS to HDFS

similarly, say there is data on hdfs let us consider it to be the resultant data of MR and need that in RDBMS for some reporting purpose we can use sqoop to achieve the same (move data from HDFS to RDBMS)


This write up assumes that we are using Mysql, sqoop, mysql connector to sqoop are installed already on your machine along with core hadoop installation. You can follow any standard documentation to get the installation and the set up
running on your machine



Command to start the Mysql as Service:
--------------------------------------

service mysqld start


Command to connect to the Mysql:
----------------------------------
Once the mysql is started we can connect to the mysql

mysql -u root -p



create new Database in mysql:
-----------------------------

Let us create new database by name TRANSACTIONS

mysql> create database transactions;


create table under the schema transactions:
------------------------------------------
let us create table by name Transaction_Log under the transactions schema

mysql> use transactions;

mysql> create table Transaction_Log( trans_id int not null AUTO_INCREMENT, trans_desc varchar(512), trans_date Date not null, trans_status varchar(20)not null, created_by varchar(50) not null, created_at Date not null, PRIMARY KEY(trans_id) );
Query OK, 0 rows affected (0.02 sec)


Insert data to the transaction_log table:
----------------------------------------

mysql> insert into  Transaction_Log values(1,'high valued transaction', curdate(),'COMPLETE','robin ruskus',curdate());
Query OK, 1 row affected (0.00 sec)

mysql> insert into  Transaction_Log values(2,'medium valued  transaction', curdate(),'IN_PROGRESS','mian curius',curdate());
Query OK, 1 row affected (0.00 sec)

mysql> insert into  Transaction_Log values(3,'Low  valued  transaction', curdate(),'IN_PROGRESS','jose kuto',curdate());
Query OK, 1 row affected (0.00 sec)



Verify Sqoop installation:
---------------------------

open a terminal and hit the following command

> sqoop version

and this show up the version installed


Connecting to Mysql through sqoop:
-----------------------------------

let us list the mysql databases from sqoop
------------------------------------------

sqoop list-databases --connect jdbc:mysql://localhost/ --username root --password XXXXXXXXXX

this should show up the database which we created i.e transactions


let us list the mysql tables from sqoop under schema transactions
------------------------------------------------------------------

sqoop list-tables --connect jdbc:mysql://localhost/transactions --username root --password XXXXXXXXXX

transaction_log



let us create new directory in hdfs called sqooped_data:
--------------------------------------------------------

> hdfs dfs -mkdir sqooped_data

> hdfs dfs -ls

drwxr-xr-x   - hadoopgrp  supergroup          0 2018-11-03 12:35 sqooped_data


import data from mysql transactions.Transaction_log to hdfs sqooped_data directory:
-----------------------------------------------------------------------------------

sqoop import --connect jdbc:mysql://localhost/transactions --table Transaction_Log --target-dir sqooped_data --fields-terminated-by '\t' -m 1 --username root --password XXXXXXXXXX



--table option for specifying the table name
--target-dir option for specifying the target directory on hdfs where the import has to be done
--fields terminated-by format of the table data to copied


The above command will generate and  trigger a MR job and the option -m 1 specifies one connection to mysql. default value of this is 4.


let us inspect the imported data in hdfs:
----------------------------------------

> hdfs dfs -ls sqooped_data
---------------------------

-rw-r--r--   1 hadoopgrp supergroup          0 2018-11-03 12:52 sqooped_data/_SUCCESS
-rw-r--r--   1 hadoopgrp supergroup        216 2018-11-03 12:52 sqooped_data/part-m-00000

The above structure is similar to the output of a MR job by which we can confirm that the sqoop import did run the
MR

>  hdfs dfs -cat sqooped_data/part-m-00000
------------------------------------------

let us see the content of the part-m-00000

1       high valued transaction 2018-11-03      COMPLETE        robin ruskus    2018-11-03

2       medium valued  transaction      2018-11-03      IN_PROGRESS     mian curius     2018-11-03

3       Low  valued  transaction        2018-11-03      IN_PROGRESS     jose kuto       2018-11-03


all the three rows from mysql table is now made available on hdfs and the values are tab seperated as we mentioned in
the command



let us do the same exercise with ',' seperated and check the output:
-------------------------------------------------------------------


delete the sqooped_data directory on hdfs
------------------------------------------

>  hdfs dfs -rm -r sqooped_data


run the import command with field seperator as ',':
---------------------------------------------------

sqoop import --connect jdbc:mysql://localhost/transactions --table Transaction_Log --target-dir sqooped_data --fields-terminated-by ',' -m 1 --username root --password XXXXXXXXXX


>  hdfs dfs -cat sqooped_data/part-m-00000
------------------------------------------

let us see the content of the part-m-00000

1,high valued transaction,2018-11-03,COMPLETE,robin ruskus,2018-11-03

2,medium valued  transaction,2018-11-03,IN_PROGRESS,mian curius,2018-11-03

3,Low  valued  transaction,2018-11-03,IN_PROGRESS,jose kuto,2018-11-03

