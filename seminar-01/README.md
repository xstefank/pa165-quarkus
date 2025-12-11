# Seminar - Introduction to the Development Environment & Maven

This seminar is an introduction to the software development environment used in the course PA165. We will use IntelliJ IDEA and play with Maven for the creation of projects and management of dependencies.

## Task 1 - General configuration

The main IDE for this course is IntelliJ IDEA.
This IDE is installed on all computers where our seminars take place (or should be installed on your personal PC under the school license (see below) in the case of virtual seminars) and is accessible through modules.
Use the following commands to add and run the IDE.

```shell
  module add idea
  idea.sh &
```
**Note**: it's a good idea to put the `module add` command into your `~/.bashrc

IntelliJ IDEA needs a licence. If you don't already have one, just visit https://www.jetbrains.com/student/ and create a new JetBrains account with an email from the domain `@mail.muni.cz`, you will get a student licence for all JetBrains products for one year.

## Task 2 - Maven configuration

Find out which version of Maven is installed on your machine using `mvn --version`. Your local Maven repo is in `~/.m2`. Please note that the version of Maven accessed from the command line might be different from the one used in IntelliJ IDEA 
(you can check and modify it from `File->Settings->Build,Execution,Deployment->Build Tools->Maven`).

## Task 3 - Using Maven to create & run projects 

Create a Hello World Java application using Maven from command line.
Use `archetype` plugin ([look at the documentation](https://maven.apache.org/archetype/maven-archetype-plugin/usage.html)) and the goal `generate`. \
It is recommended to narrow down the archetypes with a filter (try to list the possible archetypes
with and without the filter to see the difference in possibilities): `mvn archetype:generate -Dfilter=org.apache.maven.archetypes:`\
**Hint**: Feel free to choose the archetype with artifactId `maven-archetype-quickstart`.

**Note**: IDEA also offers the creation of Maven projects with similar filtering in place.
Another option is using [code.quarkus.io web starter](https://code.quarkus.io/) which, after definition, provides a `.zip` with the project files as a download. 

With every Maven plugin, including the archetype, it's beneficial to review [usage page of documentation](https://maven.apache.org/archetype/maven-archetype-plugin/usage.html#). \
Run this application from commandline using Maven `exec` plugin goal java [mvn exec:java](https://www.mojohaus.org/exec-maven-plugin/usage.html). \
**Note**: You must firstly compile the source code using `mvn compile`.\
**Hint**: The documentation of the `exec` plugin contains information on the configuration that needs to be put into `pom.xml`\
**Hint 2**: For the `exec` plugin, we suggest you choose the example POM configuration where the `<mainClass>` is declared.


## Task 4 - Create a repository on GitLab

By now you should be logged in to your GitLab account on https://gitlab.fi.muni.cz. If not:\
The username is your faculty login. e.g. `xnovak`\
The password is your faculty (secondary) password. e.g. `not123456`

Create a new repository. If you create an empty repository (without readme), you will get the instructions and commands to use in git to initialize and import your project.\
Commit and push the Hello World application from **Task 3** to this repository.

**Hint**: If you are not familiar, GitLab is just a different skin for GitHub. You can find more help in [Gitlab documentation](https://gitlab.fi.muni.cz/help) or some [online tutorial](https://www.w3schools.com/git/git_remote_getstarted.asp?remote=gitlab). 


## Task 5 - Cloning a repository

Now that you see that working with GitLab is fine, we can start a new task. You can clone the repository containing this README in a new directory:
```shell
git clone https://github.com/xstefank/pa165-quarkus.git
cd pa165-quarkus/seminar-01
```

The project contains one directory per each seminar. This is different from the Spring materials you can find on GitLab where each seminar is split into a separate repository.

## Task 6 - Maven Wrapper

Navigate to `seminar-01/hello-java21` that contains our test project.

Check the mvn version with `mvn --version` from different locations: Ubuntu's terminal or the terminal in IDEA, from IDEA (e.g., by double tapping CTRL and typing `mvn --version` into _"Run Anything"_).

They likely will not match (see [here](https://www.baeldung.com/maven-different-jdk) for an explanation).  Check also which is the associated JVM. You should get similar output as the following:

```
Apache Maven 3.9.5
Maven home: /opt/idea/idea-IU-203.7148.57/plugins/maven/lib/maven3
Java version: 21.0.2, vendor: Oracle Corporation, runtime: /usr/lib/jvm/java-21-openjdk-x64
```

Note that you can configure the Maven version in IDEA from the menu _File->Settings... Build, Execution, Deployment->Build Tools->Maven_ (you can search for _"maven"_ in the search bar in the settings).

We will show how to create a wrapper for the Maven command, so that the build can be reproducible in other systems (see the [documentation](https://maven.apache.org/wrapper/) for more details).

From the directory in which you have the project (_"hello-java21"_, the one in which you have the _pom.xml_ file), you can run:

```
mvn wrapper:wrapper
```

This will create a _mvnw_, a _mvnw.cmd_ and _.mvn_ directory (check the documentation for details). Verify the version of the wrapper command:

```
./mvnw --version
```

Note that you can ask for a specific version of _Maven_ in the wrapper as in the following example `mvn wrapper:wrapper -Dmaven=3.8.4`
  
For the next tasks, to build the project you can either use a specific version of mvn (command line or IDE) or use the wrapper (use a version that is working with JDK21).

## Task 7 - Maven Java version configuration

Try to clean and compile `hello-java21` using: `mvn clean compile` (or `./mvnw clean compile` to use the wrapper).

It should pass. So if it fails, reach out to your tutor to figure out what you might be missing.

However, you should see following message in the log:

```
[INFO] Compiling 1 source file with javac [debug target 1.8] to target/classes
```

However, we want to base our code on JDK 21. We need to configure compiler plugin in `pom.xml` so that target and source versions of Java are set to the version we want to use. In our case, it will be 21. So set the source and target to version 21.

You can think of this file as a `CMakeLists.txt` (C/C++) or `MyProject.csproj` (C#) equivalent.\
[See documentation and configure your pom.xml](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-source-and-target.html) or the newer option [release](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-release.html) which replaces both source and target. 

After you successfully configure the `pom.xml` you should be able to re-compile the projectand see:

```
[INFO] Compiling 1 source file with javac [debug target 21] to target/classes
```

or

```
[INFO] Compiling 1 source file with javac [debug release 21] to target/classes
```

## Task 8 - IntelliJ IDEA code formatting & shortcuts

Open the `App.java`(the main java file of the project).

The code formatting is broken. This is exactly how **NOT** to write your project. Find the IDE shortcuts to 'Reformat Code' and 'Optimize Imports' and apply them.

**Note**: Remember these and use them! It will help you, your teammates and us when going through your submissions. If you are not satisfied with the default, the recommended approach is to use [EditorConfig](https://editorconfig.org/) so the team has a common formatting style.

Try to take some time at the main [IntelliJ IDEA shortcuts](https://www.jetbrains.com/help/idea/mastering-keyboard-shortcuts.html) and see which additional shortcuts would be helpful.

And if you want to remember how to correctly write Java Main classes, enjoy some great metal music - https://www.youtube.com/watch?v=yup8gIXxWDU.

## Task 9 - IntelliJ code indentation style

In fact lets set up a simple `.editorconfig` file right now (see [here](https://www.jetbrains.com/help/idea/editorconfig.html)). Look at the bottom right of the editor (IntelliJ) and change the indentation style using the `.editorconfig` to the opposite style.
*spaces* -> *tabs* or *tabs* -> *spaces*. Apart from a standard set of configurable rules, each editor may have its own specific set of additional ones.

## Task 10 - Maven useful commands

We will now to play with Maven commands for a bit.

Add a JUnit 5 dependency to the project. Find the appropriate part of the `pom.xml` file of `hello-Java21`. Please note that we specify `test` as the _scope_ of the dependency. Replace the existing `org.junit.jupiter:junit-jupiter` if 

```xml
   <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.14.1</version>
      <scope>test</scope>
    </dependency>
```

Try to build the project with `mvn compile` and see the dependency tree with `mvn dependency:tree`. Also, take a look at the output of `mvn dependency:build-classpath` - both commands can be useful in case you need to see the included dependencies in your project.

Try to use `mvn help:effective-pom` to see how the effective pom configuration looks like, considering all the properties inherited from the super-pom. _Convention over configuration_ helps in keeping the config files shorter (however, be aware that there are hidden params). It can be especially useful to look at the plugins and the associated phases.

Try to check if there are unused dependencies (now that you added some). If you compile the project with `mvn compile` and then run `mvn dependency:analyze` you should get some warnings - which is fine, since we did not use these dependencies yet :) - something similar to the following:
```
[INFO] --- dependency:3.7.0:analyze (default-cli) @ hello-java21 ---
[WARNING] Unused declared dependencies found:
[WARNING]    org.junit.jupiter:junit-jupiter:jar:5.14.1:test
```

## Task 11 - Add some functionality to the application

Our `hello-java21` is very simple, so let's first modify the main method so that it will output the inverse of each word in the string `"hello world"` to make it more interesting.

Create a class called `ReverseString` with a _static method_ that can return the reverse of a string. You can modify your `main` to call such methods:
```java
...
messages.add(ReverseString.reverse("Hello"));
messages.add(ReverseString.reverse("World"));
...
```

If you build the project and run it, it should now output this:
```
[olleH, dlroW]
```

## Task 12 - Testing Configuration with Maven & JUnit5

The project `hello-java21` as we have now, is missing test directories. Under the `src` folder, create the `test/java` directories. Note that IntelliJ IDEA is so smart it will already give you this option to select the right combination without 
typing anything. It will also mark the directory as `Test Sources Root` (if this is not the case, you can right-click on the `test/java` folder and select `Mark Directory as...Test Sources Root`).

Let's try to write the first test. Maven assumes the following  [directory structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) - anything different needs to be configured.

Also for the JUnit tests, the following [conventions](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html) apply in Maven. If you need anything different, you will need to configure the Surefire plugin. 
Documentation for the Surefire plugin and configuration for JUnit5 is available [here](https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html). You shouldn't need to manually configure this plugin in this project 
since it is automatically included.

Open the `ReverseString` class in IntelliJ IDEA and select `Generate...Test...`: in the pop-up window, mark the single method you have in the class. IDEA should create a new class for you called `ReverseStringTest` with one method that you can fill.

Try to write one or more test methods for some corner-cases (and to test for `null` / `""` as well!) - for example, you can use [assertAll](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html) to test multiple inputs.

```java
  assertAll(
        () -> assertEquals("anna", ReverseString.reverse("anna")),
        () -> assertEquals("annA", ReverseString.reverse("Anna"))
        );
```

Build the project and run the tests. If tests are not running, reach out to your tutor.

Check the version of the Maven Surefire Plugin with `mvn -Dplugin=org.apache.maven.plugins:maven-surefire-plugin help:describe` - alternatively you can check with `mvn help:effective-pom` to see the list of all plugins. If you have a ***version < 2.22.0*** you will need to use a newer one to support JUnit5. Let's do it - add to your `pom.xml`:

```xml
[....]
  <build>
    <plugins>
     <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
      </plugin>
    </plugins>
  </build>
  [....]
```

This way, we are requesting a specific version of the plugin. After updating and building the project, you can check the version of the Surefire plugin with `mvn -Dplugin=org.apache.maven.plugins:maven-surefire-plugin help:describe` - it should be `Version: 3.2.5`.

Check that the tests run fine - try with `mvn clean install`.

In case of further issues (like tests not found - given correct naming conventions), you might have to configure the Surefire Plugin to make the dependencies of the plugin to JUnit5 explicit. You can define the plugin config as follows (how is this _dependency_ different from the one we already included in the `pom.xml`?)

```xml
[....]
  <build>
    <plugins>
     <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
        <dependencies>
             <dependency>
                 <groupId>org.junit.jupiter</groupId>
                 <artifactId>junit-jupiter</artifactId>
                 <version>${junit5.version}</version>
             </dependency>
         </dependencies>
      </plugin>
    </plugins>
  </build>
  [....]
```

Please note that we defined a property `<junit5.version>5.14.1</junit5.version>` in the `<properties>` part of the `pom.xml` file so that you can use the variable for `org.junit.jupiter:junit-jupiter` dependency. You can also replace the hardcoded value in the project dependencies with the variable.

## Task 13 - Maven Surefire conventions

Now, try to refactor your tests so that your ***test class name*** does not comply with [Surefire conventions](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html). For example, pick `Refactor->rename...` for 
the `ReverseStringTest` class and rename it to `ReverseStringValidation`.

Try to build the project again and run the tests. No tests should be running. Configure the Surefire plugin in the `<configuration>` block of the plugin so that your test class is picked-up when running the tests.

## Task 14 - Maven multi-module project

We will now structure our project as a [Maven multi-module project](https://maven.apache.org/guides/mini/guide-multiple-modules.html).

We will make our `hello-java21` the parent module and create two submodules `module-main` and `module-reverse`. From `module-main` we will print the results based on reversing the strings and the two modules can be built independently (please note, this is just an example: in your project you might have one module for the REST API, another for DAOs, etc...).
- `hello-java21` will be our parent pom. Change the packaging of the parent `pom.xml` to "pom". Since we did not specify this (but remember there is the super-pom for the conventions!), we need to add the following to the `pom.xml`: `<packaging>pom</packaging>`.
-  From the root of the project (`hello-java21`) directory, create the two submodules (you can do it in IntelliJ IDEA or via command line). Via command line:
    ```shell
     mvn archetype:generate -DgroupId=cz.muni.fi.pa165 -DartifactId=module-main -Dversion=1.0-SNAPSHOT -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
     ```
   Repeat the same but with `-DartifactId=module-reverse` to create the second module.
- You should see that in the root `pom.xml` file the following `<modules>` have been added - if not, add them :)
  ```xml
  <modules>
      <module>module-main</module>
      <module>module-reverse</module>
  </modules>
  ```
  Also, each `pom.xml` file in each submodule will have a `<parent>` section that you can check.
- Now, since we did not create the project from scratch, we need to move existing things around a bit. We will move `App.java` into the `module-main` and the reverse string part with the test to the `module-reverse`. Once you have added everything, you can delete the `src` folder from the root `hello-java21`. Your project structure should look like this (full package names omitted):
  ```
  Hello-java21
    -- module-main
       -- src
          -- main  (App.java)
          -- test
       pom.xml
    -- module-reverse
       -- src
          -- main  (ReverseString.java)
          -- test  (ReverseStringValidation.java)
       pom.xml
    pom.xml
  ```
- Run the command `mvn install` from `module-reverse` the module should have been built and tests ran successfully (if they were green in the previous version of the project that is).
- Now try to do the same from `module-main`: this should fail, as there is a dependency to the other module (`"Cannot resolve symbol ReverseString"`). Add the dependency `module-main` to `module-reverse`.
  ```xml
     <dependency>
        <groupId>cz.muni.fi.pa165</groupId>
        <artifactId>module-reverse</artifactId>
        <version>1.0-SNAPSHOT</version>
      </dependency>
   ```
  Run the command `mvn package` from `module-reverse` -> this time it should succeed.
- Now from `hello-java21` you can try to rebuild the whole project with `mvn install`. You can then try running the `App` class from the `module-main`, it should print everything as before: `[olleH, dlroW]`.
- You can refactor the generated `pom.xml` files further: 
  - introducing `<dependencyManagement>` in the root `pom.xml` with the `org.junit.jupiter` dependency (you will need to define the dependency in `module-reverse`'s `pom.xml` without the need to define the version);
  - removing `groupId` from the sub-modules as they are inherited from the parent (IDEA should even give you some suggestions);
  - introducing `<pluginManagement>` for the Surefire plugin in the root `pom.xml`;
  - removing any unnecessary dependency (e.g., `JUnit3` that might have been added when generating the pom files automatically) - these are not needed;


## Task 15 Podman

This task is just here to double-check that you have a valid Podman (or Docker) environment on your machine. We need this because Quarkus uses a feature (called Dev Services) that automates some of the containers running if your application needs it.  Run:

```
podman version
```

If not found - https://podman.io/docs/installation.
