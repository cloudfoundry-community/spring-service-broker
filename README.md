cloudfoundry-service-broker
===========================

Spring MVC framework app for V2 CloudFoundry service brokers

# Overview

The goal is to provide a boilerplate Spring MVC application that can be used to quickly implement new Service Brokers in CloudFoundry.  The boilerplate implements the restful controllers required of service brokers and provides a set of 3 simple interfaces to implement for a new service.  

## Compatibility

- cf-release-151 on bosh-lite.
- Service Broker API: v2.1

## Getting Started

To use:

1. Fork the project
2. Implement (3) interfaces in the com.pivotal.cf.broker.service package (alternatively, you can use the included BeanCatalogService and just implement the other (2) interfaces)
3. Ensure your service impls are annotated with @Service 
4. Build the project and run the tests: `gradle build`
5. Push the broker to CloudFoundry as an app: `cf push <your-broker> --path build/libs/<war>`
6. Register your service broker with CF: `cf add-service-broker <service-broker-name>`

### Security

When you register your broker with the cloud controller, you are prompted to enter a username and password.  This is used by the broker to verify requests.

By default, the broker uses Spring Security to protect access to resources.  The username and password are stored in: /src/main/webapp/WEB-INF/spring/security-context.xml".  By default, the password should be encoded using the Spring BCryptPasswordEncoder.  A utility class is included to provide encryption.  You can encrypt the password executing: 

`java com.pivotal.cf.broker.util.PasswordEncoder password-to-encrypt`

### Testing

Integration tests are included to test the controllers.  You are responsible for testing your service implementation.  You can run the tests with gradle (`gradle test`).

Endpoint tests using a RestTemplate are "coming soon."

### Model Notes

- The model is for the REST/Controller level.  It can be extended as needed.
- All models explicitly define serialization field names.

## Mongo DB example

An example implementation is included in the mongodb branch.  This implementation broker an external mongodb installation.  The mongo install could be manual, could be deployed by bosh, or other.  

This example does the following:

- Creates a new database for each new service instance
- Creates a new user in the database for each service instance binding.  NOTE: Currently each user has a default pwd of "password".  Adding a password generator is a TODO.
- Saves the service instance and binding metadata to mongo db in a default database (configured as "cfbroker")

The example uses Spring Data Mongo for persistence of service instance and binding information.f  It uses the MongoClient java driver for manipulating mongo (creating dbs, users, etc).  The configuration is separated so each capability can be used separately or be split into multiple mongos.

### Mongo Config

The following configuration files have been added for this implementation:

- src/main/webapp/WEB-INF/spring/broker-catalog.xml: Configures the "catalog" exposed by this broker used by the BeanCatalogService
- src/main/webapp/WEB-INF/spring/mongo-client.xml: Configures the connection information for the mongo installation being manipulated by this broker.
- src/main/webapp/WEB-INF/spring/spring-data-mongo.xml: Configures the connection information for the mongo installation used to persist the ServiceInstance and ServiceInstanceBindings

### Mongo Tests

The test suite includes integration tests that require a running mongo instance.  This is configured in:

src/test/java/com/pivotal/cf/broker/mongodb/MongoConfiguration

The tests cleanup after themselves but will not affect existing databases.

## To Do

* More integration testing around expected data input and output
* Version headers
* Integrate w/ NATS to allow this war to be deployed with Bosh
* Create a Bosh release
* Separate integration project to test broker endpoints



