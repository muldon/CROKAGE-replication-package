# CROKAGE
Crowd Knowledge Answer Generator

## About

### Goal
To provide a comprehensive solution for an API related task written in natural language (query). CROKAGE provides a list of appropriate solutions that contain not only relevant code examples but also their corresponding succinct explanations.

### Input / Output
- Input: API related query in natural language.
- Output: A summary about the API usage (Java APIs) with examples in form of code snippets and explanations.

### How
CROKAGE receives as input an API related query in natural language and uses text retrieval models combined together and three state-of-art API recommender tools to retrieve the most related SO answers to that query, sorted by relevance. CROKAGE then uses natural language processing to extract the code and relevant sentences to compose a summary containing the solution for the query.  

### Features and comparison with other state-of-art works (AnswerBot and BIKER)
- [AnswerBot](https://dl.acm.org/citation.cfm?id=3155650) is limited as it does not provide code.
- [BIKER](https://dl.acm.org/citation.cfm?id=3238191) is limited as its documentation is limited to JAVA SE and does not provide code for every query.
- CROKAGE address both limitations by providing relevant code and explanations in form of summaries.

## Prerequisites

Note: all the experiments were conducted over a server equipped with 32 GB RAM, 3.1 GHz on four cores and 64-bit Linux Mint Cinnamon operating system. We strongly recommend a similar or better hardware environment. The operating system however could be changed. 

Softwares:
1. [Java 1.8] 
2. [Postgres 9.4] - Configure your DB to accept local connections. An example of *pg_hba.conf* configuration:

```
...
# TYPE  DATABASE        USER            ADDRESS                 METHOD
# "local" is for Unix domain socket connections only
local   all             all                                     md5
# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
...
```
3. [PgAdmin] (we used PgAdmin 4) but feel free to use any DB tool for PostgreSQL. 

4. [Maven 3](https://maven.apache.org/). Assert Maven is correctly installed. In a Terminal enter with the command: `mvn --version`. This should return the version of Maven. 


## Downloading files:
Download CROKAGE files [here](http://lascam.facom.ufu.br/companion/crokage/crokage-replication-package.zip) and place in a folder preferable in your home folder, ex /home/user/crokage-replication-package. 

Check your instalation. Make sure your crokage folder (ex /home/user/crokage-replication-package) contains this structure:

```.
..
./data 
./tmp (4 files)   
   ./soContentWordVec.txt
   ./bigMap.txt
   ./soIDFVocabulary.txt
   ./soUpvotedPostsWithCodeAPIsMap.txt
crokage.jar
main.properties
```

## Configuring the dataset
1. Download the Dump of SO [here](http://lascam.facom.ufu.br/companion/crokage/dump2018crokagereplicationpackage.backup) (Dump of June 2018). This is a preprocessed dump, downloaded from the [official web site](https://archive.org/details/stackexchange) containing the main tables we use. We only consider Java posts. **Postsmin** table (representing **posts** table) has extra columns with the preprocessed data used by CROKAGE (**processedtitle, processedbody, code, processedcode**). 

2. On your DB tool, create a new database named stackoverflow2018crokagereplicationpackage. This is a query example:
```
CREATE DATABASE stackoverflow2018crokagereplicationpackage
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
```
3. Restore the downloaded dump to the created database. PgAdmin has this feature. Right click on the created database -> Restore... select the dump (dump2018crokagereplicationpackage.backup).

Obs: restoring this dump would require at least 10 Gb of free space. If your operating system runs in a partition with insufficient free space, create a tablespace pointing to a larger partition and associate the database to it by replacing the "TABLESPACE" value to the new tablespace name: `TABLESPACE = tablespacename`. 

4. Assert the database is sound. Execute the following SQL command: `select id, title,body,processedtitle,processedbody,code, processedcode from postsmin po limit 10`. The return should list the main fields for 10 posts. 




## Setting Parameters

Edit `main.properties` and set the **required** following parameters: 

`CROKAGE_HOME` = the root folder of the project (ex /home/user/CROKAGE-replication-package) where you must place the jar and `main.properties`.

`TMP_DIR`      = the folder location of the models. This folder is the tmp folder from previous step (ex /home/user/crokage-replication-package/tmp).

`spring.datasource.username` = your db user

`spring.datasource.password=` = your db password

`action` = you have 2 main options. `action=runApproach`: run the retrieval mechanism and output metrics for our ground truth. CROKAGE will show metrics for the chosen data set (below). `action=extractAnswers`: build the summaries containing the solutions. 

`dataSet` = You also have 2 main options. `dataSet=selectedqueries-test48`: test (48 queries). `dataSet=selectedqueries-training49`: training (49 queries). 


The other parameters are **optional**:

`subAction` = The set of the API extractors separated by '|'. Ex: `rack|biker|NLP2Api|`.

`numberOfComposedAnswers`= number of answers used to compose summaries. (Default=3)

If you want to reproduce other baselines except CROKAGE, please refer to our paper to more details about how to set the weights.


## Running the jar 
Open a terminal, go to the folder where the jar file and main.properties are located and run the following command: `java -Xms1024M -Xmx32g -jar crokage.jar --spring.config.location=./main.properties` . This command use the file `main.properties` to overwrite the default parameters which must be set as described above.


### Results

The results are displayed in the terminal/console but also stored in the database in tables **metricsresults**. The following query should return the results:  
```
select * from metricsresults
```



## Invoking our REST interface
You can use our REST interface to obtain solutions for your programming tasks. For this, Set your Content-Type to application/json and make a POST request to http://isel.ufu.br:8080/crokage/query/getsolutions passing the following parameters in JSON format:

{
"numberOfComposedAnswers":5,
"reduceSentences":false,
"queryText":"how to insert an element array in a given position"
}

Here follows an example of making a REST call to our approach using the [RESTED](https://chrome.google.com/webstore/detail/rested/eelcnbccaccipfolokglfhhmapdchbfg) plugin for Chrome. 

![Example of REST call to CROKAGE](https://drive.google.com/open?id=1-UheFlOOSHq0uMqjPfJcSryFb0J5qycJ)



## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details


[Java 1.8]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[Postgres 9.4]: https://www.postgresql.org/download/
[PgAdmin]: https://www.pgadmin.org/download/



