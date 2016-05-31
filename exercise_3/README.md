# Exercise 3

## Step 1: let it fail!

```shell
  $> mvn install
  $> mvn fr.jcgay.maven.plugins:buildplan-maven-plugin:1.2:list
```

> What does it display? Why?

## Step 2: a little change can do a lot

First: change `<packaging>pom</packaging>` to `<packaging>jar</packaging>`.
Then:

```shell
 $> mvn fr.jcgay.maven.plugins:buildplan-maven-plugin:1.2:list
```

> Compare this output to the previous? Why has it changed?
