# Testing Seminar

This seminar will guide you through a few test cases and things related to testing in Quarkus.

As always, Quarkus provides an comprehensive guide for testing that you can use as a reference: https://quarkus.io/guides/getting-started-testing.

# Tasks

## Task 1 - Clone and open the project for the Spring Boot basic tasks

You should be familiar with the code-base as we will extend test cases for project developed in the previous seminar.

1. Clone the repository into a local directory.

```shell
$ git clone https://github.com/xstefank/pa165-quarkus.git
cd pa165-quarkus/seminar-04/quarkus-testing
```

2. Open the project in IntelliJ IDEA.

3. Build the project in the project root directory

```shell
$ mvn clean install
```

## Task 2 - Intro to test annotations

In this section, we will explain basic testing annotations. We will also apply them in the `PersonFacadeTest`
one by one in order to have also hands-on experience with them.

### [@Test](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Test.html)
It indicates that the annotated method is a test method. These annotated methods can subsequently be processed by other
tools, such as the [Surefire plugin](https://maven.apache.org/surefire/maven-surefire-plugin/), which executes these methods.

This annotation comes from JUnit 5, which is a testing framework for unit tests, not only Spring Boot-specific. It is
widely used also for testing Java SE programs.

1. Run `PersonFacadeTest`, e.g. by running:
```shell
mvn test -Dtest="PersonFacadeTest"
```

In case you are running the command via Idea, do not use quotation marks, i.e., use the following:
```shell
mvn test -Dtest=PersonFacadeTest
```

What is happening? Fix the problem.

2. Run the test again, but this time, with continuous testing in Dev mode - https://quarkus.io/guides/continuous-testing. You can probably see the benefits of this functionality right away.

### [@Mock](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#9)
This annotation marks a field as a mock.

Look at the `PersonFacadeTest#setUp`:
```java
@BeforeEach
void setUp() {
    personService = Mockito.mock(PersonService.class);
    personMapper = Mockito.mock(PersonMapper.class);
    personFacade = new PersonFacade(personService, personMapper);
}
```
Can we make this method more readable? Of course, we can! In fact, in the following parts of this task, we will try to
fully get rid of this method.

3. Mark `personService` and `personMapper` as mocks.

4. Do not forget to delete appropriate LoC from the `setUp` method.

5. Run `PersonFacadeTest` again. Feel free to experiment with continuous testing in Dev mode again.

Hmm, **NPE** (Null Pointer Exception), but why?! Unlike [Mockito#mock](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#mock(java.lang.Class)),
`@Mock` just marks the field as a mock, it does **NOT** initialize it.

6. Fix the NPE. Use [MockitoAnnotations#openMocks](https://www.javadoc.io/static/org.mockito/mockito-core/5.16.0/org.mockito/org/mockito/MockitoAnnotations.html#openMocks(java.lang.Object)).

### [@ExtendWith](https://junit.org/junit5/docs/5.0.3/api/org/junit/jupiter/api/extension/ExtendWith.html)

Even though the solution from the previous point is correct, the best practice is to use `@ExtendWith(MockitoExtension.class)`,
which calls `MockitoAnnotations#openMocks` internally. Besides that, it also does another stuff for us, e.g. validates
the correct usage of the Mockito framework.

7. Use the suggested solution.

   **Checkpoint:** Only the line
```java
personFacade = new PersonFacade(personService, personMapper);
```
should be present in the `setUp` method.

### [@InjectMocks](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/InjectMocks.html)
This annotation is used to inject required mocks into the class under test.

8. Use the annotation. Do not forget to remove `PersonFacadeTest#setUp`.

9. Ensure that everything is working as expected by running `PersonFacadeTest` again.

## Task 3 - Test case naming
As you can see, existing unit tests in [PersonServiceTest](src/test/java/cz/muni/fi/pa165/socialnetwork/service/PersonServiceTest.java)
have a different naming as tests you have seen in the previous seminars. The main reason for that is to show you
that one test case per method is not always enough.

- This section should make you aware that there are many conventions you can use to name your test cases.
    - These conventions are not strictly tied to any programming language
      or framework.
    - It _may_ be personal preference, but keep in mind you want to have the naming unified across whole project.
- In this seminar we will be using convention `methodName_stateUnderTest_expectedBehavior`, e.g. `isAdult_ageLessThan18_returnsFalse`
- Important feature of the naming should be the possibility to express:
    - **which method** is being tested
        - in `isAdult_ageLessThan18_returnsFalse` the `isAdult` method is being tested;
    - **what state** we are covering in the test
        - in `isAdult_ageLessThan18_returnsFalse` the `ageLessThan18` is state;
    - how the method **should react** (returns/throws/...);
        - in `isAdult_ageLessThan18_returnsFalse` the `returnsFalse` is how the method **should** react;
- Read more about the naming and come up with the best approach for your project:
    - https://www.baeldung.com/java-unit-testing-best-practices#3-test-case-naming-convention
    - https://medium.com/@stefanovskyi/unit-test-naming-conventions-dd9208eadbea

## Task 4 - Service tests
In the previous task we were exploring unit tests for service layer.
Let's implement our own!

In this and following two tasks we prepared diagrams for you in order to help you visualize what is going on in the test. In every test, we are testing (quite obviously) exactly one layer and mock needed dependencies from the layer below.

In this case, we are testing `Service layer` (in particular, `PersonService`) and mock `PersonRepository` (since it's the only dependency `PersonService` needs).
You will learn how to create repository layer in the lectures about persistence.


<img alt="Service test diagram" src="images/service-test.svg" alt="drawing" width="50%"/>

1. Check out [service implementation](src/main/java/cz/muni/fi/pa165/socialnetwork/service/PersonService.java);
2. Implement `findByEmail(String email)`;
    1. Use repository method `findByEmail` implemented in
       [Person Repository](src/main/java/cz/muni/fi/pa165/socialnetwork/data/repository/PersonRepository.java);
3. Write test for the new method in [PersonServiceTest](src/test/java/cz/muni/fi/pa165/socialnetwork/service/PersonServiceTest.java);
    1. Name of the test could be `findByEmail_personFound_returnsPerson` which should give you a lead what to test;
    2. Run the test;
        1. Test it through IDE (look for a green button next to the test definition);
        2. Or use maven;
    ```shell
     mvn test -Dtest="PersonServiceTest#findByEmail_personFound_returnsPerson"
    ```

4. Take a look at `updateEmail` implementation;
5. Write test with following signature `updateEmail_personFoundAndEmailIsValid_setEmailCalled`;
    1. Now, it should be straightforward to write **Act** and **Arrange**;

       **Hint:** `PersonRepository#setEmail` returns the number of affected lines (since using `@Query`).
    2. For **Assert** phase use `Mockito.verify()` and `Mockito.times()`
       (take an inspiration for both methods [here](https://www.baeldung.com/mockito-verify#cookbook));

       **Note:** [Mockito#verify](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#verify(java.lang.Object,org.mockito.verification.VerificationMode))
       has the default `times(1)`. Hence, it can be potentially omitted.

## Task 5 - Facade tests
Let's write quick tests for facade layer. Take an inspiration from existing tests.

<img alt="Facade test diagram" src="images/facade-test.svg" width="50%">

**Note:** You always have to mock all dependencies of object you want to test. Facade has two dependencies - service layer and mapper.
You do not see the mapper in the picture above because it is not an architectural layer, it is just dependency of the class under test.

1. Implement `findByEmail` in facade layer;
2. Write the test for it
3. Run it!
4. Write test for happy path scenario for `updateEmail` method;
    1. What do you think? What name would be appropriate for such test?
    2. Think about different scenarios (take a look at `updateEmail` implementation in service layer);

**Hint:** `Mockito.doNothing()` could be of use.

## Task 6 - REST Controller tests
Now we will proceed with the same approach as for facade and service unit tests.

<img alt="Controller test diagram" src="images/controller-test.svg" width="50%">

1. Add new file for REST Controller unit tests;
2. Mock all dependencies;
3. Implement `findByEmail()` in REST Controller;
4. Write test `findByEmail_personFound_returnsPerson`
    1. Keep the structure: Arrange, Act, Assert
    2. Check that:
        1. The response ended up with status code `200`;
        2. The response body is not null;
        3. Found person email is the same as requested;
5. Write `updateEmail_validRequestBody_callsUpdateOnFacade` test;
    1. Check that method returns expected status code.

## (Optional) Task 7 - Repository tests
**Note:** You will go through the repository pattern in future weeks. This section should give you a brief introduction
on how to test custom methods in the repository.

As we should be aware, the repository layer is the bottom layer. Therefore, it cannot inject any other services from the
layers below it. Among other things, this implies that the test for the repository layer cannot be written like the
tests above, as there is nothing that we could mock. However, this shouldn't pose a problem for us: we will simply call
the method from the injected repository we want to test and then make the assertions we need to check.

1. Look at the `PersonRepositoryTest`.

There are two things worth mentioning:

- New annotation [@DataJpaTest](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/DataJpaTest.html)

Example of a [test slice](https://www.diffblue.com/blog/java/software%20development/testing/spring-boot-test-slices-overview-and-usage/).
Simply put: it instantiates only database-related part from the whole application context. It also enables SQL output
during this test. All the tests are transactional and rolled back at the end of each test. They also use embedded
in-memory database.

- [TestEntityManager](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/TestEntityManager.html)

When using repository pattern in the Spring Boot, behind the hood, we are using an [entity manager](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1#a1062).
This manager controls the lifecycle of entities in the persistence context, which is a cache with the direct impact on
the DB when the transaction is committed. (Don't feel disappointed in case you did not fully understand two previous
sentences. In fact, this is a non-trivial topic, which is going to be covered in the following weeks, when we are going to talk about
persistence.)

When using `@DataJpaTest`, `TestEntityManager` is used instead of `EntityManager`.

**Note:** It's not rare to come into situation when we need to check whether our repository methods work correctly with
the persistence context. That's exactly when we want to use the injected instance of `TestEntityManager` in order to
check the desired behavior.

2. Write the test for `findByEmail`. Simulate the situation when the person with the requested email is present.

3. Write the test for `findByEmail`. Simulate the situation when the person with the requested email is **NOT** present.

## Task 8 - Testing exceptions
Get back to the start. Find the implementation of `updateEmail` in the [service implementation](src/main/java/cz/muni/fi/pa165/socialnetwork/service/PersonService.java).
Check the implementation and look at what kind of two custom exceptions can this method throw.
1. Write test `updateEmail_emailIsInvalid_throwsEmailValidationFailedException`
    1. Check that lower layer would not be called;
    2. Do not forget to check also the exception message.
2. Write test `updateEmail_personNotFound_throwsResourceNotFoundException`
    1. Check that lower layer was called;
    2. Do not forget to check also the exception message.

**Hint:** Use `assertThrows` from JUnit5, documentation [here](https://junit.org/junit5/docs/5.9.1/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html#assertThrows(java.lang.Class,org.junit.jupiter.api.function.Executable)).

## Task 9 - Web context test slice
Let's write another unit test for rest controller. Unlike in task 6, we won't invoke requests programatically, e.g. `personRestController.findByIdl(id)`, but directly using HTTP invocations, e.g. `GET /persons/{id}` (hence, also checking whether endpoint handlers really handle what we suppose to handle).

1. Look at the `PersonRestControllerWebMvcTest`.

You should spot the following new annotations:
- [@WebMvcTest](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/WebMvcTest.html)

Example of a [test slice](https://www.diffblue.com/blog/java/software%20development/testing/spring-boot-test-slices-overview-and-usage/)
which instantiates only the web context of the whole application context. Applied to our case, this injects the
following beans **defined by us**: _PersonRestController_, _App_, _CustomRestGlobalExceptionHandling_.

**Note:** Feel free to experiment with the `PersonRestControllerWebMvcTest#seekMyBeans`.

- [@MockBean](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/mock/mockito/MockBean.html)

This annotation does **not** come from the Mockito framework, unlike `@Mock`, but it is Spring Boot-specific annotation.
It registers the field to which it is applied into the Spring's application context. Since `PersonFacade` (to which is
this annotation applied) is not part of the filtered web context, this annotation is really required, i.e., `@Mock` is
not enough in this case.

2. (**OPTIONAL**) Write the test for `findByEmail`. Simulate the situation when the person with the requested email is present.

3. Write the test for `findByEmail`. Simulate the situation when the person with the requested email is **NOT** present.

   **Hint:** Part of the context is also the bean `CustomRestGlobalExceptionHandling`. Find out what it does. Use this
   knowledge in the solution.

## Task 10 - Integration test

So far, we have tested parts of the application separately. Now, it's time to test it as a whole!

1. Look at the `PersonRestControllerIT`.

You should spot the following two new annotations:

- [@SpringBootTest](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/context/SpringBootTest.html)
  This annotation is used when we want to create an integration test, since it instantiates all the beans from the
  application.

- [@AutoConfigureMockMvc](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/AutoConfigureMockMvc.html)
  This annotation is used when we want to enable auto-configuration of the `MockMvc`.

  **Note:** Why didn't we need it in the Task 8? Because the `@WebMvcTest` has the `@AutoConfigureMockMvc` already in
  it.

2. Create an integration test for `findByEmail` method. Simulate the case when the person with the requested email is
   present.

3. Create an integration test for `findByEmail` method. Simulate the case when the person with the requested email is
   **NOT** present.

   **Hint:** What DTO does the application return in case of any error?

**Note:** One of the preferred ways to call the integration tests is to suffix them with 'IT', what's exactly the case
for our IT of the Person controller, _PersonRestController**IT**_. Since it is named this way, surefire plugin
does not execute tests in such test class (which is completely fine, since surefire should be used to execute only unit
tests). In order to execute tests in this test class, we use [failsafe plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/),
which is used for executing integration tests. Unlike surefire, failsafe automatically executes test classes suffixed
with 'IT'.

# Task 11 - Run all the tests

Run all the tests, e.g. by running:
```shell
mvn clean verify
```

Double check all the tests were run and are passing. In case you are getting an error now, but didn't get any error when
running the tests separately, chances are you were doing some unwanted side effects, e.g. test for updating the
email really updated the email and didn't use the mock (which should do nothing in that case).
