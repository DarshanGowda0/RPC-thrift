## Overview

Multithreaded client-server system to store a set of key-value pairs.

### Instructions to build
* Extract the project zip and import the project into the desired IDE as a maven project.
* Import all the maven dependencies as defined in the pom.xml.
* `mvn clean install`


### Instructions to run
* Run the ServerApp.java file in the server package to start the server, with port number as an
 argument.
* Run the ClientApp.java file in the client package to start the client, with host-name and
 port-number as system arguments. 
* The client operations are to be input to the terminal where the client.jar was executed.

##### Example:

```
$ java -jar client.jar localhost 5555
Enter the operation to be performed:
PUT <key> <value>
GET <key>
DELETE <key>
>put sampleKey sampleValue
Enter the operation to be performed:
PUT <key> <value>
GET <key>
DELETE <key>
>get sampleKey
```


## Executive Summary

### I.	Project Overview

The scope of this project is to implement a multi-threaded client-server system to store a set of key-value pairs. The requirement of the project states that the server can accept three types of requests PUT, GET and DELETE. The server and client communication should happen through remote procedure calls (RPC). RPC solves the easy parts of the Distributed Systems, that is marshalling and unmarshalling data. Since RPCs are on the application layer of the network model. It’s the easiest to implement with certain trade-offs. There are various choices to implement an RPC like Java RMIs or Thrift from Facebook (now apache). Java RMIs implicitly handle multithreading and Thrift gives the developer the flexibility to handle the threading part of the RPC. In thrift, we again have three ways of creating a multi-threaded server, namely TThreadedServer, TNonblockingServer and TThreadPoolServer. Each of these implementations have their own perks and trade-offs. TNonblockingServer has one thread dedicated for network I/O, it can use the same thread for processing requests or create a separate pool of worker threads to handle the request processing, whereas TThreadedServer and TThreadPoolServer spawns a new thread for each new client connection, the difference between the two is that TThreadPoolServer has limited number of threads which will be reused. So, one of the design choices in this project was to choose between these and considering the trade-offs for this project, TThreadPoolServer implementation is used. Since. The server and client communicate using remote procedure calls, there is no need for a protocol to have a common language independent notation when communicating (such as JSON). We can directly use the struct/objects to communicate the requests and responses without having to lose the Object-oriented design trade-off for encoding and decoding to a linear object notation like JSON or XML.

### II.	Technical Impressions

#### Features
* The client is designed to take the input from the user in the form of
`<REQUEST-TYPE> <KEY> <VALUE>`, it chooses the respective remote procedure to be called based on the request type and makes the call.
* The server on the other hand serves the remote procedures on a respective port number which can
 be called from the client directly and return a Response object.
* A static logger is used on both the server and the client applications to log all the requests, their responses and errors or exceptions if any, with timestamps up to millisecond precision.
* In case the server is busy to handle the incoming client requests, the client has a timeout of
 5s set to stop trying after this timeout. This can further be enhanced in the future to support a way where the clients can queue the lost requests and hit the server again once it becomes responsive. 

#### Assumptions and Design choices
* Design choice for the implementation of RPC was to use thrift instead of Java RMIs, so that the
 developer has more control over the handling of threads rather than implicit threading.
* Also, thrift is a cross-platform RPC architecture, meaning the client and the server can be
 written in different languages and the thrift uses a special definition file which defines all the signatures of the remote procedure calls.
* The datastore used in the server application to store the key-value pairs have to be thread safe
 data-structure to handle multiple concurrent calls, so a thread safe implementation of the hash-map has to be used. Either the Collections.synchronizedMap(HashMap) or ConcurrentHashmap. ConcurrentHashmap was used in this project.
* As defined during the thrift definition of the file, we use a Response object to communicate
 from the server to client. This response object has a status code and a message which can be used to encapsulate our responses in a structured way.
 
#### Enhancements

* One of the assumptions made in implementation is that the server is always running, hence the
 data store is implemented as a HashMap which is always going to stay in the memory of the server. This could potentially lead to out of memory exception after certain number of requests. So, there needs to be a clarification of requirement on persistence of this data to some form of disk storage or database. 
* In terms of confidentiality of the data, as a further enhancement the data can be encrypted
 using some of the standard light weight encryption techniques increasing the security of the entire communication.
* Since we use RPCs to perform the communications between the server and client, the drawbacks of
 this approach is that any change required on the server will have to be propagated to the client implementations as well.

