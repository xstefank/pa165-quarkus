# Seminar Quarkus Continued

This seminar demonstrates the creation of a basic Quarkus project via the generator website, 
the configuration of custom annotations, and the basics of all the Developer experience enhancements that Quarkus provides.

# Tasks

## Task 1 - Create a Quarkus project via code.quarkus.io and create an n-tier structure

0. Create the `answers.txt` file, where you will later provide some answers.

1. Run IntelliJ IDEA:

```shell
$ module add idea
$ idea.sh &
```

**Note from the first seminar**: IntelliJ IDEA requires a licence. If you don't already have one, simply
visit https://www.jetbrains.com/student/ and create a new JetBrains account with an email from the `@mail.muni.cz` domain. 
You will receive a student license for all JetBrains products valid for one year.
It's advisable to include the `module add` command into your `~/.bashrc`.

2. Create a simple Quarkus project via `https://code.quarkus.io/`; this will
generate a .zip file that you can later import into your chosen IDE. Choose the
latest regular version. For Project Metadata, set suitable `groupId`,
`artifactId`, `version`, and `build tool` as you prefer.

3. Select the REST service preset. That should give you at least the `quarkus-rest` extension. You can also add other extensions if you want, but we can always add them later, so there is no need to add anything more now. You can download the ZIP file and unzip it into your preferred directory.

4. Open the project in your IntelliJ IDEA IDE.

5. Complete the `answers.txt` by explaining the purpose of the files `mvnw` and `mvnw.cmd`. 
   Additionally, discuss whether they are beneficial for Continuous Delivery and Continuous Integration (CI&CD) configurations?

6. Optionally, you can also try different ways to create a Quarkus project, e.g., via the Quarkus CLI tool or the maven plugin - https://quarkus.io/guides/getting-started#bootstrapping-the-project (you can switch tabs in this section to see the 
   commands).

## Task 2 - Create n-tier project structure

0. Remember that you can keep you project running in Dev mode all the time!

1. Create the following packages with the following classes:

- `api`
    - Create class `PersonDto(id, givenName, familyName)`
- `rest`
    - Create class `PersonResource` (on top of the class add `@Path("/person")` annotation)
- `facade`
    - Create class `PersonFacade` (on top of the class add `@ApplicationScoped` annotation)
- `service`
    - Create class `PersonService` (on top of the class add `@ApplicationScoped` annotation)
- `data`
    - Create class `PersonRepository` (on top of the class add `@ApplicationScoped` annotation)

**In each tier inject the class from the lower tier** (e.g., in `PersonService` inject `PersonRepository `via the constructor injection / field injection, etc.).

In `GreetingResource.java` class (the default that will be created) `hello` method, print the created beans to verify that all the annotated classes were created as CDI Beans (reuse the implementation from the previous seminar).

**Note:** The intention of this task is to improve your ability to quickly create a basic project template.

**Note:** This approach uses a `package-by-layer` design, however, `package-by-feature` might be also the viable alternative to design a Quarkus service/project. It is up to you which option you would prefer, but you should stay consistent.

**Important:** Show the resulting project to your seminar tutor for confirmation that you have successfully created a basic Quarkus project.

## Task 3 - Clone and open the project for the Spring Boot basic tasks

1. Clone the repository into a local directory (in practice use SSH):

```shell
$ git clone https://github.com/xstefank/pa165-quarkus.git
cd pa165-quarkus/seminar-03
```

2. Open the project in IntelliJ IDEA and check its structure and code.

3. Build the project in the project root directory

```shell
$ mvn clean install
```

4. Run the project

```shell
$ java -jar target/quarkus-app/quarkus-run.jar
```

5. Access the dummy REST API, open arbitrary browser and visit the following site:

```shell
http://localhost:8080/persons
```

This site should return a paginated list of persons from the backend system. You can check or modify these pages by
suffixing the URL by, e.g., "?page=1&size=5".

Among other things, you can review the details of a specific person along with their contact information.
```shell
http://localhost:8080/persons/1
```

## Task 4 - Experience the Quarkus's Magic!

Quarkus is doing a lot of processing in the background to make our lives easier. We can split them to two main categories:

- Developer Joy - features that help developers to be more productive during development (https://quarkus.io/developer-joy/)
- Performance - features that help to create fast and efficient applications (https://quarkus.io/performance/)

We will talk about all of these during seminars. Your main focus will be on the Developer Joy features like Dev mode, Live reload, Dev UI, etc. But you should know also how Quarkus performs its magic under the hood.

Very often, you run into situations where Quarkus automatically configures things for you based on the dependencies you have in your project. We call this "Auto Configuration". It provides sensible defaults for many things, and you can always 
override them by providing your own configuration. You can also sometimes find this under "convention over configuration".

*Important:* If you are not already, run the project in Dev mode:

```shell
$ mvn quarkus:dev
```

1. Add the following dependency into your `pom.xml` file in the module `quarkus-social-network`.

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-oidc</artifactId>
</dependency>
```

After adding this dependency, a few things will happen automatically:

- If you are running Dev mode, Quarkus will automatically restart the application.
- Quarkus will automatically configure OIDC (OpenID Connect) authentication for your application.
- This also means that it will try to start your first Dev Service. Dev Services are usually Docker/Podman containers that Quarkus automatically starts for you to provide required services (like databases, message brokers, etc.) during 
  development. In this case, it will try to start a Keycloak (https://www.keycloak.org/) container for you. At least, we will see if you have Docker/Podman running on your machine (you should).
- Keykloak Dev Service automatically injects some dynamic configuration into your application, so it is automatically connected and configured to use this Keycloak container for authentication.

2. You can find the information (included the injected configuration) about the started Dev Services by pressing `c` in the Dev mode terminal. You can also find them in Dev UI under the "Dev Services" section. It should look something like this:
```shell

== Dev Services

h2
  Injected config:  - quarkus.datasource.db-kind=h2
                    - quarkus.datasource.devservices.reuse=true
                    - quarkus.datasource.devservices.show-logs=false
                    - quarkus.datasource.jdbc.url=jdbc:h2:tcp://localhost:46665/mem:quarkus;DB_CLOSE_DELAY=-1
                    - quarkus.datasource.password=quarkus
                    - quarkus.datasource.username=quarkus

keycloak
  Container:        839b58d782d6/nifty_cartwright  quay.io/keycloak/keycloak:26.4.7
  Network:          bridge - 0.0.0.0:32837->8080/tcp, :::32837->8080/tcp
  Exec command:     docker exec -it 839b58d782d6 /bin/bash
  Injected config:  - client.quarkus.oidc.auth-server-url=http://localhost:32837/realms/quarkus
                    - keycloak.realms=quarkus
                    - keycloak.url=http://localhost:32837
                    - oidc.users=alice=alice,bob=bob
                    - quarkus.oidc.application-type=service
                    - quarkus.oidc.auth-server-url=http://localhost:32837/realms/quarkus
                    - quarkus.oidc.client-id=quarkus-app
                    - quarkus.oidc.credentials.secret=secret
```

As you can see, our in-memory H2 database is also started as a Dev Service. But since it is only in memory, we don't need to start a Docker container for it. So you see, Dev Services are very often containers, but not always.

Dev services start for the duration of the Dev mode session. When you stop the Dev mode, all Dev Services are also stopped and removed. They are also started (in new instances) for test runs.

3. Run the project and revisit the following site (preferably in a private browser window).:

```shell
http://localhost:8080/persons
```

You should see the normal JSON response as previously. Now add the following configuration into your main configuration file located at `src/main/resources/application.properties`:

```properties
quarkus.oidc.application-type=web-app
quarkus.http.auth.permission.authenticated.paths=/*
quarkus.http.auth.permission.authenticated.policy=authenticated
```

And now just reload the page. The Dev mode automatically reloads your application including this new configuration and you are automatically redirected to the Keycloak Dev Service login page (running in the container started for you by Quarkus). 
You can login with `alice:alice` or `bob:bob` (these users are automatically created for you in the Keycloak Dev Service). After successful login, you are redirected back to the original page and you can see the JSON response because you are now authenticated.

4. You can always check all possible configuration properties at https://quarkus.io/guides/all-config. However, if you run in Dev mode, you can also see just the configuration properties you can configure in you current project (based on the 
   Quarkus extensions that you are including) in the Dev UI under the "Configuration" section. You can also directly edit them there and they are properly propagated to your `application.properties`. This is very useful for exploring all the 
   possible configuration options you can set in your project.

5. Feel free to remove the OIDC dependency and the configuration above so you don't need to log in anymore.

6. Try to change some different configuration. For instance, try to add either directly in `src/main/resources/application.properties` file or in the Dev UI the following property:

```shell
quarkus.jackson.property-naming-strategy=SNAKE_CASE
```

And you should witness the Quarkus's magic; it's sufficient to add a property like this, and Quarkus will automatically 
create and configure the ObjectMapper Bean (from the Jackson library used to convert Java objects into JSON and vice versa) while
setting the property naming strategy to SNAKE_CASE. You can verify this change, for instance, by calling
`http://localhost:8080/persons` and verifying that field `givenName` inside person object was changed to `given_name`.


## Task 5 - Run the project with externalized properties

Investigate the property `quarkus.config.locations` and run the project in command line (`java -D... -jar ...`)  with the externalized property
file located in the `/etc` folder in the project root directory (**Note:** it can be located anywhere in the system). **Do not add** the property to the properties file. If everything goes well, the application should run on port `8081`. It should also be darker :metal:!

Verify that you run the service with externalized property file by visiting the site:

```shell
http://localhost:8081/persons
```

**Note:** you can examine the precedence of the fields by setting, for example, a different `quarkus.http.port`
in `application.properties` and in the externalized properties to determine the property precedence. Additionally, you can set the 
`quarkus.http.port` property via the command line and verify the property precedence (`java -D... -jar ...`).

Complete the `answers.txt` file with the specific command detailing how to run the project with externalized properties.

## Task 6 - Create your own Java annotation

Similar to Aspect Oriented Programming (AOP) in Spring, CDI has the concept of interceptors. Each method in a CDI bean can be intercepted if associated with an annotation - https://quarkus.io/guides/cdi#interceptors.

Create package named `annotations`, and within it, define your own annotation named `MethodExecutionTimeTracker` (**Note:** Custom
annotations in Java are created by defining an interface prefixed with `"@"`
-> `@interface MethodExecutionTimeTracker`). Annotate your custom annotation with `@Target`, `@Retention`, `@Documented`
, `@Inherited`. Set the target to `"METHOD"` and `"TYPE"`, and retention to `"RUNTIME"`.

Now also add the `@InterceptorBinding` annotation on top of your custom annotation to define it as an interceptor binding annotation. Otherwise, CDI will not recognize it for this purpose.

Complete the `answers.txt` file with an explanation of the differences between the various RetentionPolicy options (`runtime`, `class`, `source`).

## Task 7 - Create your custom CDI interceptor

1. Create the package `interceptor` (within the `cz.muni.fi.pa165.socialnetwork` package).
2. Within the `interceptor` package, create the class `LoggingInterceptor` and annotate it with your annotation `@MethodExecutionTimeTracker`, and `@Interceptor`. Optionally, you can also annotate it with `@Priority` to define the order of 
   multiple interceptors. Write in `answers.txt` what is the purpose of the `@Priority` annotation.
3. Add a method to this class with the following signature: `Object logMethodExecutionTime(InvocationContext context) throws Exception`
4. Annotate this method with `@AroundInvoke`
5. Implement the `logMethodExecutionTime` method to log the time before the method is called and after the method is called into the console.
6. Apply your annotation `@MethodExecutionTimeTracker` to the `findAll` method in the `PersonFacade` class, then run the project and observe the results.
7. In the print statement inside the `logMethodExecutionTime` method, also retrieve the called method signature from the `InvocationContext` object.

**Note:** at that point you should see time execution of the findAll method whenever you will visit the following site (the application port might be different):

```shell
http://localhost:8080/persons
```

## Task 8 - Create additional CDI Interceptor

1. Create a new interceptor binding annotation `@TransactionCounter` in the `annotation` package. Target only `"TYPE"` this time.
2. Create a new interceptor class `TransactionCounterInterceptor` in the `interceptor` package.
3. Implement this interceptor to log and count (it is enough to do this before `context.proceed` once) transactions in any CDI bean annotated with `@TransactionCounter`.
4. Add `@TransactionCounter` annotation to the `PersonFacade`.
5. Call the `http://localhost:8080/persons` endpoint multiple times and observe the counting in the console.
6. Add a `@Priority` so `TransactionCounterInterceptor` is executed after `LoggingInterceptor`, meaning that the transaction count output should be between start and end logs from `LoggingInterceptor`.
7. Check with tutor if your implementation is correct.

## Task 9 - Implement Scheduling

Guide - https://quarkus.io/guides/scheduler-reference

1. Add Quarkus Scheduler extension - https://quarkus.io/extensions/io.quarkus/quarkus-scheduler/. Note that you don't need to stop Dev mode for Quarkus to pick up the new dependency.
2. Create class `MyDummyScheduler` in new `scheduler` package.
3. In `MyDummyScheduler` class implement a method that will print to the console each 15 seconds the String "Wohoo, scheduling in Quarkus is easy.". Hint: use `@Scheduled` annotation with `every` attribute.

**Note:** Scheduling might be employed, for example, to check every midnight whether users' privileges to specific resources are still valid.
Another common use case for scheduling is cache revalidation. For more complex features, consider scheduling via `Quartz` (https://quarkus.io/guides/quartz).

## Task 10 (Optional) - Implement Caching

https://quarkus.io/guides/cache

1. Add `quarkus-cache` extension that brings caching capabilities to Quarkus applications (Caffeine library).
2. Cache method `findById` in `PersonFacade` class.
3. Try to call person detail with different keys (ids) and observe that the either the transaction is not started or just put any print statement in the method to see that the method is not executed again for the same id.
4. Try to invalidate the cache after some time (e.g., 30 seconds) and verify that the after the invalidation the method is executed again.

