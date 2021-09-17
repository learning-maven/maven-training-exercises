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

Finally, module builds can be run in parallel with the `--threads` flag.
The value can be any number of threads, or a symbolic one like `1C`, which means "1 thread per CPU core" (`1.5C` is also possible e.g.).

```shell
mvn --threads 4 package
```

Do all modules run in parallel? Why (or why not)?

## Step 5: Test Jars?

What is a test-jar? Look into `TestUtilB` class inside module b, which is used by `TestUtilA` in module a. For that to happen (to depend on test classes),
we added a test-jar typed dependency from a to b. Let's add a new Test class on c, and use `TestUtilA` from module a.

```xml

<dependencies>
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
</dependencies>
```

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestC
{
    
    @BeforeEach
    void setup()
    {
        TestUtilA.ensureA();
    }

    @Test
    void shouldBeFalse()
    {
        assertFalse( false );
    }
}
```

Let's try to run the test

```shell
mvn verify
```

It failed, why? How can we fix it?

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