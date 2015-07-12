<!---
Copyright (C) 2015  Karl Bennett

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

This library provides an API for creating `Builder`s by populating them with code `Block`s. All `Block`s are evaluated
when the `build()` method is called. They will be evaluated in the order dictated by the `Builder` implementation.

```java
final Block<Integer> increment = new Block<Integer>() {
    public Integer build(Integer number) {
        return number + 1;
    }
};
final Builder<Integer> builder = new CollectionBuilder<>(new ArrayList<Block<Integer>>(), 0);
builder.addBlock(increment).addBlock(increment).addBlock(increment);
final Integer result = builder.build();
System.out.println(result); // 3
```

Three builder implementation have been provided, each one evaluates the it's code `Block`s in a different order.

#### QueueBuilder

This builder will evaluate all it's code `Block`s a FIFO order.

#### StackBuilder

This builder will evaluate all it's code `Block`s a LIFO order.

#### CollectionBuilder

This builder will evaluate it's code `Block`s in the order specified by it's supplied `Collection` implementation. This
also allows the `Block`s to be sorted and/or unique.

```java
// All Blocks will be ordered by their input.
abstract class Ordered implements Block<Integer>, Comparable<Ordered> {
    private final Integer number;
    Ordered(Integer number) {
        this.number = number;
    }
    public int compareTo(Ordered that) {
        return number.compareTo(that.number);
    }
}
class Addition extends Ordered {
    private final Integer number;
    Addition(Integer number) {
        super(number);
        this.number = number;
    }
    public Integer build(Integer number) {
        return this.number + number;
    }
}
class Multiplication extends Ordered {
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
builder.addBlock(new Addition(3)).addBlock(new Addition(3)).addBlock(new Multiplication(2));
final Integer result = builder.build();
System.out.println(result); // 3
```