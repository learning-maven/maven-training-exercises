# Exercise 5

The setup is a bit more involved than usual. There are 2 libraries:

- `greeter` and
- `super-greeter`

... available in two, incompatible versions: 1.0 and 2.0.

Usually, such versions live in the same project at different Git revisions.
To make things simpler, each project version lives in its own directory.

There is another folder named `my-application`.
It contains the main application that needs to compile and run.

## Step 0: "cache" (install) these libraries

```shell
mvn --file greeter-v1 install
mvn --file greeter-v2 install
mvn --file super-greeter-v1 install
mvn --file super-greeter-v2 install
```

`--file` saves you from running `cd` to move to each folder. Its value can be a path to a `pom.xml` (or Yaml...) file or the parent folder thereof.

## Step 1: compile & run

```shell
mvn --file my-application/pom.xml compile
mvn --file my-application/pom.xml exec:java -Dexec.mainClass=com.acme.greet.MyApplication
```

Do any of these above steps fail? If so, why (or why not)?

## Step 2: climbing the tree

Run the following:

```shell
mvn --file my-application/pom.xml dependency:tree -Dverbose
```

Does it help understanding the issue?
Can we and if so, how do we solve the problem?