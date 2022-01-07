package com.acme.performance;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class Main {
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    private static class Fibonacci extends RecursiveTask<Long> {
        private final int n;
        private final byte depth;
        public Fibonacci(int n) {
            this(n, 0);
        }
        private Fibonacci(int n, int depth) {
            this.depth = (byte) depth;
            this.n = n;
        }
        public Long compute() {
            if (depth > 10) {
                return fibonacci(n);
            }
            Fibonacci f1 = new Fibonacci(n - 1, depth + 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2, depth + 1);
            return f2.compute() + f1.join();
        }
    }

    public static Long fibonacciParallel(int n) {
        return new Fibonacci(n, 0).compute();
    }

    Map<String, List<Long>> results = new HashMap<>();
    void execute(IntFunction<Long> code, int num, String name) {
        long start = System.currentTimeMillis();
        long r = code.apply(num);
        long time = System.currentTimeMillis() - start;
        results.computeIfAbsent(name, k -> new ArrayList<>()).add(time);
        System.out.println(r + " in " + time + " ms");
    }
    void run(int num, int repeats) {
        for (int i = 0; i < repeats; i++) {
            execute(Main::fibonacci, num, "one");
            execute(n -> new Fibonacci(n).compute(), num, "multi");
        }

        results.forEach((k, v) ->
                System.out.println(k + " " + v.stream().mapToLong(l -> l).average().getAsDouble()));
    }
    public static void main(String... args) {
        new Main().run(Integer.parseInt(args[0]),  Integer.parseInt(args[1]));
    }
}