# Java 8 vs Java 17 performance. Modern does not mean faster.
Why migrate to Java 17? Not because of its performance.

Sample code for my [post](https://marian-caikovski.medium.com/java-17-language-compared-to-java-8-how-modern-java-is-better-than-java-8-65a4e39c448e) comparing performance of Java 8 and Java 17.

I am using Java 1.8.0_302 and 17.0.1.

## Fibonacci

Comes from this [article](https://marian-caikovski.medium.com/not-stagnation-but-regression-of-java-where-modern-java-17-loses-to-java-8-e45b31fec5ac).

### Usage

* Compile with `mvn clean verify` using Java 8 or Java 17
* Run `java -cp target/startJetty.jar com.acme.performance.Main 40 100` to replay the example in the article
* Run `java -jar target/benchmarks.jar` to run the JMH equivalent

### Results

My current results are

#### Main

|      | Java 8 | Java 17 |
|one   | 328.17 | 343.36  |
|multi |  60.18 |  66.10  |

### JMH

```
Java 17
Benchmark           Mode  Cnt    Score   Error  Units
MyBenchmark.fibPar  avgt   20   82.433 ± 2.607  ms/op
MyBenchmark.fibSeq  avgt   20  356.393 ± 0.601  ms/op

Java 8
Benchmark           Mode  Cnt    Score   Error  Units
MyBenchmark.fibPar  avgt   20   71.610 ± 3.832  ms/op
MyBenchmark.fibSeq  avgt   20  326.094 ± 0.536  ms/op
```