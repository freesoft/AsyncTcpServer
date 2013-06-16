This project was originally coded to practice Java7 asynchronous socket as well as some code challenge,
but now I'm changing it to be more Java based game server.

So far, this project just has basic structure as Maven/Spring framework/Google Protobuf project.

== Author

* Wonhee Jung

== Requirement

* Need JDK 1.7+ to be installed in the PC.
* Java should be in PATH the environment variable to make execution script runnable.
* To build project from source code, it requires Maven to be installed. ( Project also includes .project and .classpath for Eclipse Juno)
* Server and Client should located in the same server for client testing since client will try to connecdt "localhost".
* Network connection may need to be allowed to rebuild project with Maven due to library repository access.
* Google Protobuf compiler need to be installed.     


== How to run

* Run server.bat in MS Window ( or server.sh in Linux, didn't test it. Requires bash. Please grant execution permission. ) for server side first.
* Run client.bat in MS Window ( or client.bat in Linux, didn't test it. Requires bash Please grant execution permission. ) for client side after server start up. ( This is written just for test purpose ).
* To rebuild project, run 
      mvn package
* To run JUnit test, do it either in Eclipse or  
      mvn test
      
 
== Google Protobuf compile
 
 * This project use Google's Protobuf as message protocol between server and client. 
 You need to run protobuf.sh ( in Unix or OS X or change it properly for your OS ) every time you change packet.proto definition.
 If your client codebase is C++ or Python, generate new client side Packet code with com/potatosoft/protobuf/packet.proto file.