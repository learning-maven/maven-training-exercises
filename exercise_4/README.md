# Exercise 4

## Step 1: trying to compile

Run:
```shell
    $> mvn compile
```

What happens and why?

## Step 2: change the scope

What [scope(s)](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Dependency_Scope) would work here and why (or why not)?

By the way, to run the application, you need to compile it and execute the main method.
One way to do that is to rely on the [Exec Maven Plugin](https://www.mojohaus.org/exec-maven-plugin/):

```shell
    $> mvn compile exec:java -Dexec.mainClass=com.acme.fbiville.App
```