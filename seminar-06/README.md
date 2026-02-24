# Seminar OpenAPI

This seminar shows how to describe an API (Application Programming Interface) in OpenAPI document,
and use that document to generate both a client and a server in Java.

# Tasks

## Task 1 - build and start a server

Let's start with a working example which is prepared in the public GitLab repository TBD
(thanks to Martin Kuba for providing this application which was transformed to Quarkus).
Clone the repository into a local directory and build the project:
```bash
git clone https://github.com/xstefank/pa165-quarkus.git
cd seminar-06/muni-chat-service-quarkus/
mvn install
```
Then start the server:
```bash
./mvnw quarkus:dev
```
and visit the URL http://localhost:8080/ with your browser.

You have just started a service for sharing chat messages. Keep the terminal window open.

## Task 2 - call API from command line

Open the URL http://localhost:8080/chat in a new tab of your browser. It displays
all chat messages. There should be exactly one now. The page reloads every few seconds.

Open a new terminal window and issue the following command in it:

```bash
curl --verbose \
  'http://localhost:8080/api/messages?author=Joe' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "text": "Hello! 😀"
}'
```

A new message appeared in the browser tab that displays all messages. Check it out.

The `curl` command did an HTTP POST request to the server, providing a parameter `author` in the URL
and a parameter `text` in the body of the request formatted as JSON. 
The server responded with HTTP status 201 "Created" and a JSON document that
contains data about the created message.

Make several more calls, changing the message text. See the browser tab to display them.

## Task 3 - call API from Swagger UI

Open a new tab in your browser and visit there the URL http://localhost:8080/q/swagger-ui
It displays a Swagger UI, a web page automatically generated from the document [openapi.yaml](http://localhost:8080/q/openapi).

Click on the rectangle titled "POST /api/messages". A page section for calling the operation appears. Click "Try it out" button and fill a value in the text field for **author**, and change the value for the key "text" in the area for request body.
Click the big blue button titled **Execute**.

A new message was created. Check it in the browser tab displaying all messages.

## Task 4 - call a remote API

Now let's have some fun. Ask a student sitting next to you for the IP address of their computer. Type the IP address
into the text field titled **server** in the Swagger UI page in the section **Server variables** instead of the original
value `localhost`. Post a message to the remote computer (use the steps from Task 3).

You can do it also from the command line by exchanging "localhost" in the URL for the IP address.

Please note that this works only among the LAN-connected PCs. Computers connected through Wi-Fi eduroam
cannot communicate with each other. 

## Task 5 - call API from generated Java code

The project contains the module [chat-client-java](https://gitlab.fi.muni.cz/makub/muni-chat-service/-/tree/main/chat-client-java)
that contains code in the class [Main](https://gitlab.fi.muni.cz/makub/muni-chat-service/-/blob/main/chat-client-java/src/main/java/cz/muni/chat/client/Main.java)

The module produces an executable JAR file, or can be run using Maven. Let's run it using Maven. In a terminal
window, go to the directory `chat-client-java` and issue the command that runs the client application:
```bash
cd chat-client-java
mvn spring-boot:run
```
Change the code in the Main.java file to create a new message and run it again.

## Task 6 - create your own service

Now it is time to create your own service. 

First some cleanup. Stop the running chat server (CTRL+C in the terminal window) and close the browser
tabs with chat messages and with Swagger UI of the chat service. Close the terminal windows. 

Open a new terminal window. Clone the repository of this seminar
using ssh connection, then build the project:
```bash
git clone git@gitlab.fi.muni.cz:pa165/seminar-open-api.git
cd seminar-open-api
mvn install
```

The repo contains a minimal Maven project with a basic [openapi.yaml](openapi.yaml?plain=1) document
and two modules: [server](server) with a generated server and [client](client) with a generated client.

Open the cloned project in Intellij IDEA development environment. Open the file [openapi.yaml](openapi.yaml?plain=1)
and see its content. It is minimal, defines just a single operation `hello` that returns a JSON object
containing a string property `message` when the URL `http://localhost:8080/api/hello` is called with HTTP GET request.
The implementation always returns `Hello World!` in the message.

See the class [MyServiceServer.java](server/src/main/java/cz/muni/pa165/generated/server/MyServiceServer.java)
which implements the `hello` operation. That class is the only one that you need to implement for the server.
All the other classes are generated by OpenAPI Generator, see them in the folder
**server/target/generated-sources/openapi/src/main/java/cz/muni/pa165/generated/server/**.

There is also an integration test written for the `hello` operation in the class
[IT.java](server/src/test/java/cz/muni/pa165/generated/server/IT.java), it can be
run by issuing the command `mvn verify`.

Run the server by issuing
```bash
cd server
mvn
```
then visit the Swagger UI for the service at http://localhost:8080/swagger-ui/index.html and call the hello
operation (click on "Try it out" and then on "Execute").

## Task 7 - define your own object type

Open the file [openapi.yaml](openapi.yaml?plain=1). Under the `components/schemas` create a new key for your object
and create several properties. Choose an entity like a car, a pet, a song, a secret agent, a piece of food
or whatever else and define a string property, an integer property, a number property, a boolean property, 
an array property and an enum property. 
Something like the following (you must not use User as your entity and same names for properties! Invent your own!):
```yaml
# OpenAPI 3.1 nullable style
components:
  schemas:
    User:
      title: A user
      description: User object
      required:
        - name
      properties:
        name: { type: string, description: full name, example: John Doe }
        height: { type: [ "integer", "null" ], description: height in cm, example: 170 }
        age: { type: [ "number", "null" ], description: age in years, example: 20 }
        vampire: { type: boolean, description: flag for vampires, example: true }
        childrenNames: { type: [ "array", "null"], items: { type: string }, nullable: true, example: [ 'Jan','Petr','Eva' ] }
        hairColor: { type: [ "string", "null"], enum: [ 'dark', 'blonde', 'ginger', 'bald' ] }
```

Please note that the difference between OpenAPI 3.0 and OpenAPI 3.1 is how types that may contain null value
should be specified. The code above is for OpenAPI 3.1, using `type: [ "string", "null"]` for a string
that may be null. The code below si for OpenAPI 3.0, using `type: string, nullable: true`.
The OpenAPI Generator as of version 7.12 seems to accept both, but it is not correct according to the specifications
and may be fixed in later versions.

```yaml
# OpenAPI 3.0 nullable style
components:
  schemas:
    User:
      title: A user
      description: User object
      required:
        - name
      properties:
        name: { type: string, description: full name, example: John Doe }
        height: { type: integer, description: height in cm, example: 170, nullable: true}
        age: { type: number, description: age in years, example: 20, nullable: true }
        vampire: { type: boolean, description: flag for vampires, example: true }
        childrenNames: { type: array, items: { type: string }, example: ['Jan','Petr','Eva'] }
        hairColor: { type: string, enum: [ 'dark', 'blonde', 'ginger', 'bald'], nullable: true }
```

For specification of data types you may want to see:
 - [OpenAPI 3.0 Specification - Data Types](https://swagger.io/docs/specification/v3_0/data-models/data-types/)
 - [OpenAPI 3.1 Specification - Data Types](https://swagger.io/specification/#data-types)

Compile the project to check that the definition is correct by issuing `mvn install` in the server folder.

## Task 8 - define your own operation

In the file [openapi.yaml](openapi.yaml?plain=1) in the section `paths` define a new operation
for creating and returning the object defined in the previous task. Use at least three parameters
as input. Something like:
```yaml
paths:
  /api/users:
    post:
      tags: ['MyService']
      summary: creates a user
      operationId: createUser
      parameters:
        - { name: name, in: query, required: true, schema: { type: string }, description: "user's full name" }
        - { name: height, in: query, required: false, schema: { type: integer }, description: "user's height in cm" }
        - { name: age, in: query, required: false, schema: { type: number }, description: "user's age in years" }
      responses:
        "200":
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
```
For specification of paths, operations, parameters and responses you may want to see
 - OpenAPI 3.0 Guide 
   - [Paths and Operations](https://swagger.io/docs/specification/v3_0/paths-and-operations/)
   - [Describing Parameters](https://swagger.io/docs/specification/v3_0/describing-parameters/)
   - [Describing Responses](https://swagger.io/docs/specification/v3_0/describing-responses/)
 - OpenAPI 3.1 Specification
   - [Path item object](https://swagger.io/specification/#path-item-object)
   - [Parameter object](https://swagger.io/specification/#parameter-object)
   - [Responses object](https://swagger.io/specification/#responses-object)


Regenerate the classes by issuing `mvn install`, then implement the operation in MyServiceServer.java.
(Press Alt+Insert, choose "Implement methods..." and select the method from the list that appears,
the method has a default implementation in the interface MyServiceApiDelegate.)
Something like:
```java
public class MyServiceServer implements MyServiceApiDelegate {
    //...
    @Override
    public ResponseEntity<User> createUser(String name, Integer height, BigDecimal age) {
        User user = new User()
                .name(name)
                .height(height)
                .age(age)
                .vampire(false)
                .hairColor(User.HairColorEnum.DARK)
                .childrenNames(List.of("Petr", "Zuzana"));
        log.debug("createUser({},{},{}) returns {}", name, height, age, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
```

Run the server again with `mvn` (this runs `mvn spring-boot:run` as specified by `project/build/defaultGoal` in pom.xml)
and call the operation from the Swagger UI at http://localhost:8080/swagger-ui/index.html

You may also call it from the command line, e.g:
```bash
curl -v -X POST 'http://localhost:8080/api/users?name=James+Bond&height=183&age=32'
```
## Task 9 - call your operation from Java client

In the module for client, edit the class 
[MyServiceClient.java](client/src/main/java/cz/muni/pa165/generated/client/MyServiceClient.java)
and add a call to your operation. Something like:
```java
public class MyServiceClient implements CommandLineRunner {
//...
    @Override
    public void run(String... args) throws Exception {
        log.info("running...");
        MyServiceApi myServiceApi = new MyServiceApi(new ApiClient());
    
        // call my operation
        User user = myServiceApi.createUser("John Doe", 180, BigDecimal.valueOf(21.4));
        log.info("user: {}", user);
    }
}
```
Run the client by issuing `mvn` in a new terminal window in the client folder (the server must be still running):
```bash
cd client
mvn
```

## Task 10 - write a test for your operation

In the class [IT.java](server/src/test/java/cz/muni/pa165/generated/server/IT.java), write a test
for your operation.

You can see examples of integration tests in 
[GeneratedServerIT.java](https://gitlab.fi.muni.cz/makub/muni-chat-service/-/blob/main/chat-server-generated/src/test/java/cz/muni/chat/generated/server/GeneratedServerIT.java)
and 
[ChatIT.java](https://gitlab.fi.muni.cz/makub/muni-chat-service/-/blob/main/chat-server/src/test/java/cz/muni/chat/server/rest/ChatIT.java)
The project is meant as a complete example showing many features.