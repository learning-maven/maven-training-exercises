# Exercise 4

## Step 1: execute

Run:

```shell
$> mvn compile exec:java
```

<details>
  <summary>Does it run? What are we missing?</summary>

We need to provide the main class to the exec plugin. It could be done via couple of ways;

1. From command line
    ```shell
   $> mvn compile exec:java -D exec.mainClass=com.acme.fbiville.App -D exec.args="-g Merhaba Ali"
    ```

2. By setting corresponding properties
   ```xml
   <properties>
       <exec.mainClass>com.acme.fbiville.App</exec.mainClass>
       <exec.args>-g Merhaba Ali</exec.args>
   </properties>
   ```

3. By configuring exec plugin
   ```xml
   <build>
       <plugins>
           <plugin>
               <groupId>org.codehaus.mojo</groupId>
               <artifactId>exec-maven-plugin</artifactId>
               <version>3.0.0</version>
               <configuration>
                   <mainClass>com.acme.fbiville.App</mainClass>
                   <arguments>
                       <argument>--greet-with=Merhaba</argument>
                       <argument>Ali</argument>
                   </arguments>
               </configuration>
           </plugin>
       </plugins>
   </build>
   ```

</details>

## Step 2: package

Run:

```shell
$> mvn package
$> java -jar target/packaging-1.0-SNAPSHOT.jar
```

<details>
    <summary>Does it run? How do we fix it?</summary>

We need to define the main class in the manifest too!

```xml

<plugin>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.2.0</version>
    <configuration>
        <archive>
            <manifest>
                <mainClass>com.acme.fbiville.App</mainClass>
            </manifest>
        </archive>
    </configuration>
</plugin>
```

</details>

Let's try it again after fixing the problem:

```shell
$> mvn package
$> java -jar target/packaging-1.0-SNAPSHOT.jar
```

<details>
    <summary>Still lacks something, right?</summary>

It requires dependencies on the class path.

1. We can have jar plugin to add class-path entries into the manifest by;
   ```xml
    <plugins>
        <plugin>
            <artifactId>maven-dependency-plugin</artifactId>
            <version>3.2.0</version>
            <executions>
                <execution>
                    <id>copy-dependencies</id>
                    <phase>prepare-package</phase>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                        <outputDirectory>${project.build.directory}/lib</outputDirectory>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        <plugin>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.2.0</version>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>com.acme.fbiville.App</mainClass>
                        <classpathPrefix>lib</classpathPrefix>
                        <addClasspath>true</addClasspath>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
   ```

With this maven will copy all dependencies into `lib` directory and add class path entries into the manifest.

```shell
$> mvn package
```

and finally;

```shell
$> java -jar target/packaging-1.0-SNAPSHOT.jar -g Hello "executable jar"
```

</details>

## Step 3: build a hermetic jar

Still sounds like a bit overwhelming right? Let's use the assembly plugin and build an executable that self-contains everything needed.

```xml

<plugin>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.3.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
            <configuration>
                <archive><!-- skip this if no executable JAR needed -->
                    <manifest>
                        <mainClass>com.acme.fbiville.App</mainClass>
                    </manifest>
                </archive>
                <descriptorRefs><!-- predefined descriptor to include dependencies -->
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs>
            </configuration>
        </execution>
    </executions>
</plugin>
```

and run our program;

```shell
$> java -jar target/packaging-1.0-SNAPSHOT-jar-with-dependencies.jar -g Welcome "single executable jar"
```

## Step 4: let's shade

Let's assume this is a library that could be used by third parties and we want to stick with our version of picocli.

```xml

<plugin>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.4</version>
    <executions>
        <execution>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <relocations>
                    <relocation>
                        <pattern>picocli</pattern>
                        <shadedPattern>com.acme.fbiville.shaded.picocli</shadedPattern>
                    </relocation>
                </relocations>
                <shadedArtifactAttached>true</shadedArtifactAttached>
                <transformers>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>com.acme.fbiville.App</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

let's try it out;

```shell
$> mvn clean package
$> javar -jar target/packaging-1.0-SNAPSHOT-shaded.jar -g Hey "my shaded jar"
```

You can inspect the shaded jar and verify that picocli package is indeed shaded.