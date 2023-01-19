# ddd-mars-ecommerce

## Purpose

Example e-commerce application build based on Domain Driven Design principles.
The purpose is to present most of DDD building blocks e.g. Value Object, Entity, Aggregate, Domain Event, etc.

## Preparation

I assume you already have Java 17, if not, please install it on your machine.
It is also required to have Groovy installed.

You can install both with [Sdk man](https://sdkman.io)

To install groovy run command `sdk install gradle 4.8`
To install java 17 run command `sdk install java 17.0.2-zulu`

If you prefer to run your tests from IntelliJ IDEA it's useful to install: [Spock IntelliJ IDEA Plugin](https://www.jetbrains.com/idea/guide/tutorials/writing-tests-with-spock/spock-intellij-plugin/)

## Build

Standard `mvn clean install` should do the job!

# Workshop DDD - tactical design

## Task 1 - Project structure

 - Check out the project, look at the package structure and how the code is organized
 - Focus on packages and class names, not on the details (some things are intentionally not finished or not used)

 1. What architecture it resembles?
 2. What are the pros and cons of applying this architecture?

 - Look into [ProjectStructureTest.groovy](src%2Ftest%2Fgroovy%2Fcom%2Fmars%2Fecommerce%2Fsales%2FProjectStructureTest.groovy)

 1. What does it check?
 2. How this can be used in the future?