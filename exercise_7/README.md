# Exercise 7

## Step 1: bind!

Configure [maven-enforcer-plugin](http://maven.apache.org/enforcer/maven-enforcer-plugin/) in order to bind its `enforce` to `compile` phase (from the `default` lifecycle).

## Step 2: reject!

Configure the plugin with the [JDK rule](http://maven.apache.org/enforcer/enforcer-rules/requireJavaVersion.html): the build should fail if the JDK version is not `1.8`.

Thanks to step 1, you just need to run: `mvn compile` to do so (or any phase including `compile`).

Hint: beware of version range [syntax](http://maven.apache.org/enforcer/enforcer-rules/versionRanges.html).
