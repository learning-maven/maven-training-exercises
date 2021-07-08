# Exercise 6

## Step 1: build

```shell
  $> mvn install -f bom/
```

## Step 2: climb the tree


```shell
 $> mvn dependency:tree -DoutputFile=tree.txt
```

Open `tree.txt` and compare to the dependencies
declared in `bom/pom.xml`. Are all declared 
dependencies in the tree? Why?
