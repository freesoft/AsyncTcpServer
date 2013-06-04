This project is written by Wonhee jung to practice Java7 async socket.

Requirement

* Need JDK 1.7+ to be installed in the PC.
* Java should be in PATH the environment variable to make execution script runnable.
* To build project from source code, it requires Maven to be installed. ( Project also includes .project and .classpath for Eclipse Juno)
* Server and Client should located in the same server for client testing since client will try to connecdt "localhost".
* Network connection may need to be allowed to rebuild project with Maven due to library repository access.    


How to run

* Run server.bat in MS Window ( or server.sh in Linux, didn't test it. Requires bash. Please grant execution permission. ) for server side first.
* Run client.bat in MS Window ( or client.bat in Linux, didn't test it. Requires bash Please grant execution permission. ) for client side after server start up. ( This is written just for test purpose ).
* To rebuild project, run 
      mvn package
* To run JUnit test, do it either in Eclipse or  
      mvn test
      
      