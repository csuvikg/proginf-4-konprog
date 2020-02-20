package threads;

public class Second {
    public static void main(String[] args) {
        // 3
        String[] words = {"hello", "world", "other"};

        for (String word : words) {
            new Thread(() -> {
                for (int i = 1; i <= 10000; i++) {
                    System.out.println(word + " " + i);
                }
            }).start();
        }
    }
}
