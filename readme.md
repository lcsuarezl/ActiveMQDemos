


# ActiveMQ 

Project source code from 

https://www.manning.com/books/activemq-in-action

Updated to run with JDK 17

## Run ActiveMQ locally:

To execute ActiveMQ please download the following image 

dockerhub-ilcb.travelclick.com/tc-activemq:5.15.8-3

and run 

`
podman run -it --rm -p 61616:61616 -p 8161:8161 dockerhub-ilcb.travelclick.com/tc-activemq:5.15.8-3
`

WebConsole ActiveMQ: http://localhost:8161/

Web console hawtio: http://localhost:8161/hawtio/auth/login

More about https://hawt.io/


