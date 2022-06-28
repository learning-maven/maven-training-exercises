# Exercise 8

This is a Maven project with a parent aggregator POM and 4 modules named `a`, `b`, `c`, and `d`.

## Step 1: Reactor Build Order

To see the build order, there is no need to actually compile or run anything other than the basic `validate` phase (the default goal of the project):

```shell
mvn
```

You should see logs starting with:

```
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO]
[INFO] module-example                                                     [pom]
[...]
```

What modules appear after that? In which order? Why?

## Step 2: Partial Build

Try to build only module A:

```shell
mvn --projects a package # short version: mvn -pl a package
```

Does it fail? Why (or why not)?
If so, how can it be fixed?

## Step 3: Building downstream

Let's try to build module B and all the modules that depend on it:

```shell
mvn --projects b --also-make-dependents package # short version: mvn -pl b -amd package
```

What other modules are built? In which order? And why?
What if one of these modules depended on module D: would the above command still work?

## Step 4: Parallel builds!

Module builds can be run in parallel with the `--threads` flag.
The value can be any number of threads, or a symbolic one like `1C`, which means "1 thread per CPU core" (`1.5C` is also possible e.g.).

```shell
mvn --threads 4 package
```

Do all modules run in parallel? Why (or why not)?

## Step 5: Test Jars?

### Definition

What is a test-jar?

Look into `b/src/test/java/TestUtilB.java` class inside module B.
We can see `a/src/test/java/TestUtilA.java` imports `TestUtilB` and calls one of its methods. 
For the test compilation and execution to work, we added a "test-jar" typed dependency from A to B.
We can see that in `a/pom.xml`.

Test JARs includes all the bytecode resulting from the compilation of the sources present in `src/test/java` 
(or more generally: `${build.testSourceDirectory}`). They are usually produced by the [Maven JAR plugin](https://maven.apache.org/plugins/maven-jar-plugin/) and its [`test-jar`](https://maven.apache.org/plugins/maven-jar-plugin/test-jar-mojo.html).
Modules A and B are no exceptions, as we can see in the `<plugins>` section of their respective `pom.xml`.

### Action!

Let's now introduce a test in module C, which will leverage A's `TestUtilA` class.

First, let's declare the extra dependencies in `c/pom.xml` (in the `<dependencies>` section):

```xml
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.7.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>${project.groupId}</groupId>
        <artifactId>a</artifactId>
        <version>${project.version}</version>
        <type>test-jar</type>
        <scope>test</scope>
    </dependency>
```

Then, let's add a new test class in module C (`c/src/test/java/TestC.java`), which calls a method of `TestUtilA`.

```java
// JUnit is the de facto standard test execution framework in Java
// Here, we are using JUnit 5 (aka JUnit Jupiter)
// The Maven Surefire plugin (https://maven.apache.org/surefire/maven-surefire-plugin/) is the default plugin bound to the `test` phase.
// Its recent versions support JUnit 5 out of the box!
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestC
{

    // runs before each test
    @BeforeEach
    void setup()
    {
        TestUtilA.ensureA();
    }

    // specific test checking something very important ;)
    @Test
    void shouldBeFalse()
    {
        assertFalse( false );
    }
}
```

Let's try to run the test, with the following simple invocation:

```shell
mvn test
```

It failed, why? How can we fix it?

Hint:
```xml
<dependency>
    <groupId>${project.groupId}</groupId>
    <artifactId>b</artifactId>
    <version>${project.version}</version>
    <type>test-jar</type>
    <scope>test</scope>
</dependency>
```

Can we structure this use-case in a better way?

## Bonus: Maven 4 improvements

Let's try to build module C from the folder of module A:

```shell
mvn --file ../c/pom.xml package
```

Unfortunately, Maven 3 does not understand that these folders are part
of a multimodule project.
The only way for Maven 3 to build from module A folder is to run:

```shell
mvn --file ../pom.xml --also-make --projects c package
```

Maven 4 to the rescue! (Mac OS users: [here is an easy way](https://github.com/mthmulders/homebrew-maven-snapshot) to install Maven 4)

```shell
mvn4 --file ../c/pom.xml package
```

You can even run a build from outside the project file tree:

```shell
cd /tmp
mvn4 --file ~/workspace/maven-training-exercises/exercise_8/c/pom.xml package
```

How does it work?
Maven will resolve the project folders by moving up to the first `.mvn` folder it encounters.

Note: if you run the same command with module A, Maven 4 will fail.
Test JAR resolution is still not supported, unfortunately.