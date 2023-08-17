# spring-graphql-example

The purpose of this simple project is to demonstrate the usage of GraphQL via Spring
GraphQL.

## Tech Stack

Spring GraphQl, Spring Web, Spring Core, Gradle

## Description

The project contains examples on how to perform CRUD operations by using Spring GraphQl.
Examples of GraphQL operations (queries and mutations) for corresponding types are provided
in [author.graphql](src/main/resources/graphql/author.graphql)
and [book.graphql](src/main/resources/graphql/book.graphql).

All operations are covered with proper integration tests.  
After starting the app the GraphQL UI is available under http://localhost:8080/graphiql