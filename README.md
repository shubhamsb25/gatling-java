# Gatling json feeder example

## Main module
Main method has defined a simple http endpoint which just echos whatever json body it receives

* uri: POST localhost:8080/user
* body (raw json): { "id":"1", "user":"Joe" }

## Gatling module
Defines two simulations

* jsonFeederScenario: Using feeder values we create request body on the fly
* jsonFeederScenarioWithTemplate: Using feeder values we create body using a template file which looks much more cleaner

# How to run

* Run main method in main module (starts web server)
* In a terminal run ``./gradlew gatlingRun``