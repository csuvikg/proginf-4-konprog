import java.util.HashMap;
import java.util.Map;

public class SimpleCacheA {
    public static void main(String[] args) {
        MyCache cache = new MyCache();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + j + ": " + cache.getSquare(j));
                }
            }).start();
        }
    }

    static class MyCache {
        private final Map<Integer, Long> cache = new HashMap<>();

        public synchronized long getSquare(int i) {
            if (cache.containsKey(i)) {
                return cache.get(i);
            }
            long result = 0L;
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < i; k++) {
                    ++result;
                }
            }
            cache.put(i, result);
            return result;
        }
    }
}
