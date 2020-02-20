package threads;

public class Fourth {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("threads");

        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());

        Thread t1 = new Thread(group, runnable, "t1");
        Thread t2 = new Thread(group, runnable, "t2");
        Thread t3 = new Thread(group, runnable, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
