# simple-web-angular
Simple spring mvc security app with angularjs as a frontend

### How to run
To run the web app from command line, use:
> mvn clean install jetty:run

It will compile and run embedded Jetty on port 8080. To open app in your web browser, navigate to
> http://localhost:8080/

In order to login, use the following credentials:
> Username: sponge

> Password: bob

In order to deploy to a different servlet container, such as Tomcat, just run

> mvn clean install

And grab the `simplewebangular.war` file from target directory.

### Technology stack

The web app uses the following technology stack:

- Spring MVC 
- Spring security
- Angular JS
- WRO (Web Resource Optimization) for merging static resources
- JUnit/Hamcrest for testing
- JPA2/Hibernate
- H2 in-memory database