# eEvents
Spring + Hibernate + Primefaces Demo Web Application

Tech stack:
- Java 8
- Spring 5
- Hibernate 5
- Primefaces 7
- MySQL
- Glassfish 4

Java Web Application for managing all kinds of events, using Spring framework and Hibernate framework as ORM mapper. Project usese Java 8 as development platform (using features like Lambda expressions). Primefaces framework is used for GUI, and Glassfish as application server. Corresponding relational model is at MySQL database.

Why Spring framework? Because it is well-designed Web framework, its consistency of transaction management, modularity and flexibility for configuring. Bad sides would be its complexity, tons of parallel mechanisms and lots of XMLs.
Why Hibernate framework as ORM mapper? Well it is standard ORM solution and it supports JPA, it generates the SQL on the fly and then automatically executes the necessary SQL statements, it is layers architecture and it is database independent. Disadvantages of Hibernate is that it is a bit slower then pure JDBC and it generates a lots of SQL statements in runtime (that are sometimes hard to read), and it also has some debugging and performance tuning difficulties.
Why Primefaces framework for GUI? It is easy to use and it has good UI components, it is lightweight library and it works good with Spring framework. Bad thing with Primefaces is that it is UI component library for JSF so manipulating with components can be hard.

At location src/main/resources/db/database.sql you can find SQL script for database creation as well as tables creations and corresponding inserts.

War file can be found at location target/eEvents-0.0.1.war and it can be deployed at application server (Glassfish, Tomcat, etc.).
