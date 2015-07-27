<!---
Copyright 2015 Karl Bennett

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
A library that can be used to simplify creating builders and fluent API's.

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>com.github.shiver-me-timbers</groupId>
        <artifactId>smt-building</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```
### Usage

This library provides an API for creating
[`Builder`](https://github.com/shiver-me-timbers/smt-building/blob/master/src/main/java/shiver/me/timbers/building/Builder.java)s
by populating them with code
[`Block`](https://github.com/shiver-me-timbers/smt-building/blob/master/src/main/java/shiver/me/timbers/building/Block.java)s.
All `Block`s are evaluated when the `build()` method is called. They will be evaluated in the order dictated by the
`Builder` implementation.

```java
final Block<Integer> increment = new Block<Integer>() {
    public Integer build(Integer number) {
        return number + 1;
    }
};
final Builder<Integer> builder = new CollectionBuilder<>(new ArrayList<Block<Integer>>(), 0);
builder.add(increment).add(increment).add(increment);
final Integer result = builder.build();
System.out.println(result); // 3
```

Three builder implementations have been provided, each one evaluates it's code `Block`s in a different order.

#### [QueueBuilder](https://github.com/shiver-me-timbers/smt-building/blob/master/src/main/java/shiver/me/timbers/building/QueueBuilder.java)

This builder will evaluate all it's code `Block`s in FIFO order.

#### [StackBuilder](https://github.com/shiver-me-timbers/smt-building/blob/master/src/main/java/shiver/me/timbers/building/StackBuilder.java)

This builder will evaluate all it's code `Block`s in LIFO order.

#### [CollectionBuilder](https://github.com/shiver-me-timbers/smt-building/blob/master/src/main/java/shiver/me/timbers/building/CollectionBuilder.java)

This builder will evaluate it's code `Block`s in the order specified by it's supplied `Collection` implementation. This
also allows the `Block`s to be sorted and/or unique.

```java
// All Blocks will be ordered by their input.
abstract class OrderedBlock implements Block<Integer>, Comparable<Ordered> {
    private final Integer number;
    Ordered(Integer number) {
        this.number = number;
    }
    public int compareTo(Ordered that) {
        return number.compareTo(that.number);
    }
}
class Addition extends OrderedBlock {
    private final Integer number;
    Addition(Integer number) {
        super(number);
        this.number = number;
    }
    public Integer build(Integer number) {
        return this.number + number;
    }
}
class Multiplication extends OrderedBlock {
    private final Integer number;
    Multiplication(Integer number) {
        super(number);
        this.number = number;
    }
    public Integer build(Integer number) {
        return this.number * number;
    }
}

// Using TreeSet so that the Blocks will be unique and sorted.
final Builder<Integer> builder = new CollectionBuilder<>(new TreeSet<Block<Integer>>(), 0);
builder.add(new Addition(3)).add(new Addition(3)).add(new Multiplication(2));
final Integer result = builder.build();
System.out.println(result); // 3
```

#### Iteration

The `Builder` implements [`Iterable`](http://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html) so can be used
within a `for` loop.

```java
for (Integer result : builder) {
    System.out.println(result);
}
```

If used in this way each result produced by the iterator will be the result of each individual `Block` as it is called.
The `Block`s will be iterated over in the order dictated by the `Builder` or internal collection implementation.

#### Specified Iteration

It is also possible to state how many iterations should be run over the `Builder`. Any number of iterations can be
requested, be it less than or greater than the number of `Block`s in the `Builder`.

```java
final Builder<Integer> builder = new CollectionBuilder<>(new ArrayList<Block<Integer>>(), 0);
builder.add(new Addition(3)).add(new Addition(3)).add(new Multiplication(2));

for (Integer result : builder.iterable(2)) {
    System.out.println(result);
}
// 3
// 6
```

As would be expected if you set the number of iterations to be less than the number of `Block`s then only that number of
will be iterated over. Though, if you set a number iterations that is greater than the number of `Block`s then when the
iterator has run to the end it will start back at the beginning taking the final result and feeding that back into the
top of the `Block`s. This will be repeated until the request number of iterations has been completed.

```java
for (Integer result : builder.iterable(5)) {
    System.out.println(result);
}
// 3
// 6
// 12
// 15
// 18
```