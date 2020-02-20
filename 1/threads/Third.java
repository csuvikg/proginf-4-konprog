package threads;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Third {
    public static void main(String[] args) throws IOException {
        // 4
        String[] words = {"hello", "world", "other"};

        try (PrintWriter pw = new PrintWriter("out.txt")) {
            List<Thread> threads = new ArrayList<>();
            for (String word : words) {
                Thread t = new Thread(() -> {
                    for (int i = 1; i <= 10000; i++) {
                        pw.println(word + " " + i);
                    }
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
        }

        System.out.println("Done");
    }
}
