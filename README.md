cloudfoundry-service-broker
===========================

Spring MVC framework app for V2 CloudFoundry service brokers

# Overview

The goal is to provide a boilerplate Spring MVC application that can be used to quickly implement new Service Brokers in CloudFoundry.  The boilerplate implements the restful controllers required of service brokers and provides a set of 3 simple interfaces to implement for a new service.  

*NOTE*: I built this for the experience of integrating new services into CF.  It is therefore very immature but the hope is that this can grow into a more useful feature set for Spring developers.

## Compatibility

Last tested on cf-release-147 on bosh-lite.

## Getting Started

To use:

1. Fork the project
2. Implement (3) interfaces in the com.pivotal.cf.broker.service package
3. Ensure your service impls are annotated with @Service 
4. Package your war file: 'mvn clean install'
5. Push the broker to CloudFoundry as an app: 'cf push --path <your-war>
6. Register your service broker with CF: 'cf add-service-broker <service-broker-name>'

A sample dummy implementation that simply logs actions is included in com.pivotal.cf.broker.service.mock.  You should delete this or remove the @Service annotation before deploying your app.

Integration tests are included to test the controllers.  You are responsible for testing your service implementation.  You can run the tests with maven.

### Model Notes

- The model is for the REST/Controller level.  It can be extended as needed.
- All models explicitly define serialization field names.
- Currently, ServiceInstance is used internally and not exposed by any controllers.

## To Do

* Test on cf-release-149 
* More integration testing around expected data input and output
* Authentication headers
* Version headers
* Integrate w/ NATS to allow this war to be deployed with Bosh
* Create a Bosh release
* A lot more, I am sure...



