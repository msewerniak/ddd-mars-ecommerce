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

## Task 2 - Value Objects

 1. Implement class Amount as Value Object in package com.mars.ecommerce.sales.domain.offer
    - Amount should express number of items
    - Should not let the negative number be stored
    - Should have method to increase amount by 1
    - Look into test: [AmountTest.groovy](src%2Ftest%2Fgroovy%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Foffer%2FAmountTest.groovy)
 2. Implement class Price as Value Object in package com.mars.ecommerce.sales.prices
    - Price should express monetary amount (alternate name would be Money, but we decided to talk about price in our domain)
    - Because we know in close future we will be expanding to the other markets with different currency, we want to be able to set prices in different currencies, so Price should have both value and currency
    - Should have methods for
      - getting lower from two prices
      - checking if price is lower than the other
      - adding and subtracting price
      - multiplying price by amount
    - Look into test: [PriceTest.groovy](src%2Ftest%2Fgroovy%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fprices%2FPriceTest.groovy)
    - Extra (if you finished earlier than your colleagues)
      - At this moment we want only permit two currencies: EUR and USD
      - Make validation that won't let using any other currency and add respective tests
 3. Let's discuss the different approaches