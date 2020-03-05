package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class MultiThreads {
    final static long TOP_BOUND = 10_000_000_000L;
    final static long STEP = 1_000_000_000L;
    static long sum1 = 0;
    static long sum2 = 0;

    public static void main(String[] args) {
        // single threaded solution
        long start = System.nanoTime();
        sum1 = LongStream.rangeClosed(1, TOP_BOUND).sum();
        System.out.println("1 thread:\t\t\t" + (System.nanoTime() - start) / 1000000 + "ms");

        // threaded solution
        List<Thread> threads = new ArrayList<>();

        start = System.nanoTime();
        for (int i = 0; i < TOP_BOUND/STEP; i++) {
            int finalI = i;
            Thread t = new Thread(() -> {
                long localSum = LongStream.rangeClosed(finalI * STEP + 1, (finalI + 1) * STEP).sum();
                sum2 += localSum;
            });
            t.start();
            threads.add(t);
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Multithread:\t\t" + (System.nanoTime() - start) / 1000000 + "ms");

        start = System.nanoTime();
        long sum3 = LongStream.rangeClosed(1, TOP_BOUND).parallel().sum();
        System.out.println("Parallel stream:\t" + (System.nanoTime() - start) / 1000000 + "ms");
    }
}
