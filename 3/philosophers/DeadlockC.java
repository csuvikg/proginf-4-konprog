import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockC {
    public static void main(String[] args) {
        Fork[] forks = {new Fork(), new Fork(), new Fork(), new Fork(), new Fork()};
        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher("phil " + i, forks[i], forks[(i + 1) % forks.length]);
            new Thread(philosophers[i]).start();
        }
    }

    static class Philosopher implements Runnable {
        private final String name;
        private final Fork left;
        private final Fork right;

        Philosopher(String name, Fork left, Fork right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(name + " is thinking...");
                    Thread.sleep(200);
                    if (left.tryLock()) {
                        try {
                            Thread.sleep(1000);
                            if (right.tryLock()) {
                                try {
                                    System.out.println(name + " is eating...");
                                    Thread.sleep(300);
                                } finally {
                                    right.unlock();
                                }
                            }
                        } finally {
                            left.unlock();
                        }
                        System.out.println(name + "\t" + new Date());
                    }
                }
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class Fork extends ReentrantLock {

    }
}
