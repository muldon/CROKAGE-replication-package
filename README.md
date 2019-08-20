# CROKAGE
Crowd Knowledge Answer Generator. Feel free to try our [tool](http://isel.ufu.br:9000/). For more details about the technique, please check our [paper](https://dl.acm.org/citation.cfm?id=3339130).

## About

### Goal
To provide comprehensive solutions for daily programming tasks containing code examples and succinct explanations (limited to Java in this initial vesion). 

### Input / Output
- Input: API related query in natural language.
- Output: Code examples containing explanations.  

### How
CROKAGE receives as input a query written in natural language and uses state-of-art text retrieval models combined with three state-of-art API recommender tools to retrieve the most related Stack Overflow answers to that query, sorted by relevance. CROKAGE then uses natural language processing to extract the code and relevant sentences to compose a summary containing the solution for the query.  

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


## Running the tool mode 1 - replication package

### Downloading files:
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
Note: for now we only provide the replication package. The complete source code will be released soon. 

### Configuring the dataset
1. Download the Dump of SO [here](http://lascam.facom.ufu.br/companion/crokage/dump2018crokagereplicationpackage.backup) (Dump of June 2018). This is a preprocessed dump, downloaded from the [official web site](https://archive.org/details/stackexchange) containing the main tables we use (we only consider Java posts in this initial version). **Postsmin** table (representing **posts** table) has extra columns with the preprocessed data used by CROKAGE (**processedtitle, processedbody, code, processedcode**). 

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




### Setting Parameters

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


### Running the jar 
Open a terminal, go to the folder where the jar file and main.properties are located and run the following command: `java -Xms1024M -Xmx32g -jar crokage.jar --spring.config.location=./main.properties` . This command use the file `main.properties` to overwrite the default parameters which must be set as described above.


### Results

The results are displayed in the terminal/console but also stored in the database in tables **metricsresults**. The following query should return the results:  
```
select * from metricsresults
```



## Running the tool mode 2 - Obtaining the solutions via REST interface
We provide a REST interface to enable other researchers to use CROKAGE as a baseline or repeat, improve or refute our results. If you are interested in obtaining the solutions for your programming tasks, you can call this interface from within your applications. For this, make a POST request to http://isel.ufu.br:8080/crokage/query/getsolutions, set in the header the "Content-Type" to "application/json" and pass the following parameters in JSON format:

```
{
"numberOfComposedAnswers":5,
"reduceSentences":false,
"queryText":"how to insert an element array in a given position"
}
```

### Example 1 
This is an example of making a REST call to CROKAGE using the [RESTED](https://chrome.google.com/webstore/detail/rested/eelcnbccaccipfolokglfhhmapdchbfg) plugin for Chrome. 

![Example of REST call to CROKAGE](https://github.com/muldon/CROKAGE-replication-package/blob/master/RESTED-POST.png)

The result is a JSON containing the answers with explanations. 

### Example 2
We provide a Java implementation for invoking the rest interface. For this, follow the following steps:

1. Clone this project into your local machine: git clone https://github.com/muldon/CROKAGE-replication-package.git

2. Import the maven project into your IDE (i.e., Eclipse).

3. Right click on CrokageInitializer.java on Package Explorer-> Run As -> Java Application. 

If you desire to change the parameters, access `application.properties` file under `src/main/resources/config/`. Once you run the application, the demo client will invoke the remove REST api and obtain the solutions for the queries in the list. 



## Tool
We implemented our approach in form of a [tool](http://isel.ufu.br:9000/) to assist developers with their daily programming issues. The figure below shows the tool architecture. We follow a REST (Representational State Transfer) architecture. The tool is in beta version and only provide solutions for Java language, but we expect to release the full version soon.  

![CROKAGE's architecture](https://github.com/muldon/CROKAGE-replication-package/blob/master/tool-architecture.png)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details


[Java 1.8]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[Postgres 9.4]: https://www.postgresql.org/download/
[PgAdmin]: https://www.pgadmin.org/download/



