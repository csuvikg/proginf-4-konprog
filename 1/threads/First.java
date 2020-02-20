package threads;

public class First {
    public static void main(String[] args) {
        // 1
        new Thread(() -> {
            for (int i = 1; i <= 10000; i++) {
                System.out.println("hello " + i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i <= 10000; i++) {
                System.out.println("world " + i);
            }
        }).start();

        //2
        new Thread(() -> {
            for (int i = 1; i <= 10000; i++) {
                System.out.println("other " + i);
            }
        }).start();
    }
}
