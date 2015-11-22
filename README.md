# simple-web-angular
Simple spring mvc security backend with angularjs as a frontend. Frontend and backend are completely independent projects, they can be deployed to different hosts and developed independently, but at the same time benefiting from having alltogether packed into one maven project.

Front-end developers can run backend locally and developer front-end using their familiar tools such as GruntJS. Back-end developers can run frontend+backend from maven and develop back-end as they usually do.

## How to run
### Quick setup
To run the web app from command line, use:

```shell
mvn install
```

It will compile frontend and backend.

```shell
cd backend
mvn jetty:run
```

It will run embedded Jetty on port 8080. To open app in your web browser, navigate to
> http://localhost:8080/

In order to login, use the following credentials:

```
Username: sponge
Password: bob
```

### For back-end developers
Compile the whole project by running

```shell
mvn install
```

Then you can compile only backend

```shell
cd backend
mvn install 
```

You can run/deploy backend either using embedded tomcat7/jetty, or deploy to your favorite servlet containers of your choice. Both modes are supported and welcome.

To run using embedded jetty:

```shell
mvn jetty:run-war
```

In order to deploy to a different servlet container, such as Tomcat, just run

```shell
mvn clean install
```

And grab the `backend/simplewebangular.war` file from target directory.

For testing access the following locaiton:

> http://localhost:8080/

### For front-end developers
Compile the project

```shell
mvn install
```

Navigate to the backend folder 

```shell
cd backend
mvn jetty:run-war
```

This will run backend locally on your machine. Keep this process running since you do not need to make backend modifications.

Now you can focus only on front-end development in `frontend` project  folder by running the following grunt tasks:

#### Run frontend using GruntJS

```shell
grunt run --endpoint=http://localhost:8080
```

This task compiles the frontend in debug mode (non optimized js and css files, not concatenated, not compressed, etc) and runs local server on port 4000. Notice that `endpoint` parameter has to be supplied, it should point to an URL where the backend is deployed. It can be a completely different location, not necesserily localhost and port 8080.

#### Other GruntJS tasks
Grunt build result appears after running any of the following tasks in `frontend/build` directory.
##### Make a release build of the frontend

```shell
grunt dist --endpoint=http://localhost:8080
```

Makes a release build with the front-end pointing to the backend at localhost:8080. Notice that end-point url is injected into javascript/html files, so you cannot modify it after the release is made.

##### Make a debug build of the frontend

```shell
grunt build --endpoint=http://localhost:8080
```

Makes a debug build (js and css files not optimized, not concatenated, not compressed, not uglified, etc).

## Technology stack

The web app uses the following technology stack:

- Spring MVC 
- Spring security
- Angular JS
- JUnit/Hamcrest for testing
- JPA2/Hibernate
- H2 in-memory database