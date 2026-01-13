# Seminar Quarkus Basics

This seminar shows one of the possible structures of enterprise-based projects in Quarkus using N-tier architecture. Further, it tests the usage of basic annotations and principles of injecting beans altogether.

Quarkus provides a very nice set of guides for the individual features - https://quarkus.io/guides/. Feel free to use this resource during the seminar.

# Tasks

## Task 1 - Clone and open the project for the Quarkus basics tasks.

Clone the repository into a local directory (in practice use SSH):
```shell
$ git clone https://github.com/xstefank/pa165-quarkus.git
cd pa165-quarkus/seminar-02
```

Open the project in IntelliJ IDEA:
```shell
$ module add idea
$ idea.sh &
```

**Note from the first seminar**: IntelliJ IDEA needs a licence. If you don't already have one, just visit https://www.jetbrains.com/student/ and create a new
JetBrains account with an email from the domain `@mail.muni.cz`, you will get a student licence for all JetBrains products for one year.
It's a good idea to put the `module add` command into your `~/.bashrc`.

## Task 2 - Review the structure of the project

1. Review the structure of the project, data, service, facade, rest layer, and its classes.
   Check what are the naming conventions for the classes in a particular "tier".

2. Check project dependencies in `pom.xml`, focus on the `quarkus` ones.

3. Create a file named `answers.txt` and write down the benefits of N-tier architecture for enterprise-based projects. Optionally, you can also mention drawbacks of this architecture.

**Note**: this file will be used for the follow-up answers, so do not delete it).

## Task 3 - Run and fix the project

The project was modified so it does not contain several required annotations to create and inject CDI (Context and Dependency injection) Beans.


1. Run the project via the *Dev mode* - https://quarkus.io/guides/getting-started#running-the-application. See what error it throws. Notice also that the dev mode continues to run even if there are compilation errors. It will also automatically 
   restart on your code changes. Don't stop it during the whole seminar.
2. Add annotations `@ApplicationScoped`, `@Transactional`, `@Path`, and `@GET` to suitable classes and methods and add the annotation `@Inject` on top of constructors injecting beans or injected fields.
3. When everything is correctly annotated, you can trigger dev mode reload either by calling any HTTP endpoint of the application or by manually triggering reload in the dev mode terminal by pressing `s` (yes, the dev mode terminal is interactive,
   type `h` to see all options) For now, trigger the reload manually by pressing `s` in the terminal. It should print an instance of a `PersonDetailViewDto` id 5.
4. Fill-up `answers.txt` with the explanation of you think each annotation `@ApplicationScoped`, `@Transactional`, `@Path`, and `@GET` does.

## Task 4 - Check created Beans

1. The easiest way is to open the Dev UI in your browser. Find where it is running and different ways how you can open it. Document these ways and the URL of where we can check all created beans in `answers.txt`. 
2. Optionally, implement also finding of all beans programmatically in the `StartupObserver#onStart` method. Hint: `Arc.container().beanManager().getBeans`.
3. Write in `answers.txt` what is the difference between different CDI scopes you find in your beans list above (e.g., `@ApplicationScoped`, `@RequestScoped`, ...).
4. Write in `answers.txt` whether Quarkus creates *singleton* beans eagerly during start-up or it creates beans lazily (just when they are injected somewhere).

## Task 5 - Create a custom Quarkus CDI Bean
Creation of custom beans is necessary when you want to include/create a Java object that will be managed by Quarkus CDI container which is called ArC (https://quarkus.io/guides/cdi-reference). Using this, you can inject (`@Inject`) this Bean anywhere in the project.

1. Add the following dependency to the `seminar-quarkus` project (`pom.xml`):

```xml
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-rest-jackson</artifactId>
</dependency> 
```

If you are running Dev mode, Quarkus will automatically pick up the new dependency and download it, and restart your application.

Notice the version doesn't need to be defines because it is inherited from the Quarkus BOM defined in the same `pom.xml`. Jackson (https://github.com/FasterXML/jackson) is a very popular library for JSON serialization/deserialization that is the main JSON library used in Quarkus REST 
layer.

Dependencies in the `io.quarkus` package are also called Quarkus extensions. You can find more about available extensions here: https://quarkus.io/extensions/. There are more than 800 extensions available, so you have plenty to choose from in 
your future projects. We can use extension and dependency in this case interchangeably.

2. Just by adding this `quarkus-rest-client` extension, Quarkus automatically serializes all our REST endpoint responses to JSON format using Jackson library. Verify this by calling the REST endpoint `GET /persons/5` in your browser or using a 
   tool like Postman, curl, or HTTPie.

**Note** If you are using curl (tutors prefer HTTPie), we recommend also installing `jq` (https://jqlang.org/download/) for pretty-printing JSON responses in the terminal. You should have it in your package manager. Example command:
```shell
$ curl -s http://localhost:8080/persons/5 | jq
```

3. Create a custom bean that will have only one method that returns current
   time. Make it implement the provided `TimestampService` interface. Inject
   this bean in the `PersontResource` and add log the timestamp of the request
   to the `findById` method. Hint Quarkus provides `Log` class with a bunch of
   static methods.

4. Write to `answers.txt` what happens when you create two beans with the same
name. Optionally, try it by changing the injected type of your custom bean in
`PersonResource` from previous step to the interface `TimestampService` and
create another class implementing this interface. Annotate both classes as
beans. Try to run the application and see what error it throws.

## Task 6 - Create and inject additional Beans

1. Create additional classes `ContactRepository`, `AddressRepository`, `ContactService`, `AddressService`.
2. Appropriately annotate these classes.
3. In service classes, inject repository classes (try constructor, field, and setter injection). **Note**: The best practice is constructor injection.
4. Inject `ContactService` and `AddressService` in `PersonFacade` class via constructor injection.

## Task 7 - Improve project start-up

Modify `@ComponentScan` annotation in (`App` class) so it scans only suitable packages (e.g., omit `api` package with DTO classes).

## Task 8 - Create additional Bean implementations

1. Create another class named `PersonService` in a new package with arbitrary name.
2. Annotate this class suitably so the code does not fail in runtime.
3. Try to inject that class in `PersonFacade`. **Note**: Check `@Qualifier` annotation and declaration of custom bean names.

## Task 9 - Transactions

1. Check method chain for `findById` (in `PersonFacade` and `PersonService` classes). How many transactions are created when `@Transactional` annotation is added above `findById` method in both classes?
2. Set the `@Transactional` annotation so the `findById` method in PersonService creates a new transaction.

**Note**:
- Check `isolation` and `propagation` options for Spring transactions.
- To apply the transactional logic behind `@Transactional` annotation, it's essential to add `@EnableTransactionManagement` to the Spring configuration class.

## Task 10 - Create methods for `Address`

Create `findById` method for repository, service, facade, and rest layer for `Address` class (do it analogously as it is for Person).

## Task 11 - Dependencies and its licenses

In an enterprise-based project, it's crucial to check the licenses of used libraries so they do not break any company-defined rules.

In the project root directory run the following command in terminal:
```shell
$ mvn project-info-reports:dependencies
```

Go to the `target/site` folder and open `dependencies.html` in your browser of choice and check used libraries and their licenses.

## Task 12 - Optional task (mapping entities to DTOs and vice versa)

Use ModelMapper library that you previously integrated to map domain classes to your DTO classes.