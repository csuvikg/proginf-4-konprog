package threads;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Threads {
    public static void main(String[] args) throws IOException {
        // 4
        String[] words = {"hello", "world", "other"};

        ThreadGroup group = new ThreadGroup("threads");

        Thread d = new Thread(() -> {
            while (true) {
                System.out.println("Alive: " + group.activeCount());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        d.setDaemon(true);
        d.start();

        try (PrintWriter pw = new PrintWriter("out.txt")) {
            List<Thread> threads = new ArrayList<>();

            for (int i = 0; i < words.length; i++) {
                int finalI = i;
                Thread t = new Thread(group, () -> {
                    for (int j = 1; j <= 10000; j++) {
                        pw.println(words[finalI] + " " + j);
                        try {
                            sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, "thread" + finalI);

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
        }

        System.out.println("Done");
    }
}
