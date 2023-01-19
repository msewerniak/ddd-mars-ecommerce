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

## Task 3 - Domain Entities

 1. Implement class Client as Domain Entity in package com.mars.ecommerce.sales.domain.client
    - Client should have unique identifier and name (only)
      - Let's create client identifier as a separate VO with internal identifier value of UUID
      - Client identifier should let generate its value randomly
      - Client name cannot be blank and must be shorter than 50 characters
    - Look into test: [ClientTest.groovy](src%2Ftest%2Fgroovy%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fclient%2FClientTest.groovy)
 2. No more Domain Entities here - we will implement some more in the exercise for Aggregates
 3. Why Client was implemented as Domain Entity, can it be Value Object as well?

## Task 4 - Aggregates

 1. Look into class [Shipment.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fshipping%2Fdomain%2Fwarehouse%2FShipment.java) that represents concept of Aggregate
    - What can you tell about encapsulation
    - What business rules it fulfills
    - What DDD building block would you recognise in ShipmentItem
 2. Implement class Cart as Aggregate in package com.mars.ecommerce.sales.domain.cart
    - Class Cart represents customer's shopping cart but there are special requirements
      - Our company in order to attract customers has shopping gift system: for some products customer receives other products for free, for example: if customer buys laptop 'Dell XPS' they receives laptop 'Bag' for free
      - Customer can add product to the cart, if product has free gift product it is automatically added to the cart
      - Customer can remove free product from the cart because they don't want that gift
      - Customer can add multiple products and receive multiple free gift products: each for each product
      - Customer can remove free product and then revert it back
      - Customer can remove product for which they received a gift but then both product and gift are removed from the cart
    - Some classes were already provided to make things easier and speed up the development
      - CartId - VO for cart identifier ([CartId.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCartId.java))
      - CartItem - VO that represents the product which was added to the cart ([CartItem.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCartItem.java))
      - CartRepository and CartHashMapRepository - interface and simple implementation of Cart storage that can be used for saving and retrieving Cart ([CartRepository.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCartRepository.java), [CartHashMapRepository.java](src%2Ftest%2Fgroovy%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCartHashMapRepository.java))
      - ExtraItemsPolicy and ExtraItemsPolicyInMemory - interface and simple implementation of DDD Policy that us used to determine rules for free gift products ([ExtraItemsPolicy.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FExtraItemsPolicy.java), [ExtraItemsPolicyInMemory.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fsales%2Finfrastructure%2Frepository%2Fhashmap%2FExtraItemsPolicyInMemory.java))
    - Look into test: [CartTest.groovy](src%2Ftest%2Fgroovy%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCartTest.groovy)
    - Hint:
      - Cart aggregate will encapsulate items collections for free and non-free items
      - There may be needed for some higher level service that will trigger proper method on the Cart (that service can be implemented as DomainService or CommandHandler, feel free to implement it the way it's easier for you, doesn't need to be one of the mentioned ones). That service will be supplied with ExtraItemsPolicy so Cart will not know it. That service can be also responsible for retrieving and storing the Cart data by using CartRepository
    - You can look into the next commit if you have no idea how to start - this assignment is the most difficult from the whole workshop - it's totally fine if you look into easier version of this task!
    - Simpler version of the above task
      - Look into [CartService.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCartService.java)
      - Look into [CartTest.groovy](src%2Ftest%2Fgroovy%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCartTest.groovy)
      - Run tests - most of them are failing
      - Implement what's needed in [Cart.java](src%2Fmain%2Fjava%2Fcom%2Fmars%2Fecommerce%2Fsales%2Fdomain%2Fcart%2FCart.java)
      - Run again, fix, test (...) - should be all green