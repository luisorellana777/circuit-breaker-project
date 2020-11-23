# Circuit Breaker Project

It has two microservices.
The server microservice makes a delay of two second, in order to test it.
On the other hand, there is client microservice, which allows handling communications delay between these two services.
It's possible to adjust the supported delay handled by RestTemplate through [this file propertie](https://github.com/luisorellana777/circuit-breaker-project/blob/master/client/src/main/resources/application.yml).
The property is "resttemplate.timeout.read". If this property is less than 11 seconds, it will throw an exception.
This exception will be caught by the circuit breaker mechanism within client microservice.
