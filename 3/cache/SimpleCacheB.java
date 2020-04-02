import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimpleCacheB {
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
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public long getSquare(int i) {
            lock.readLock().lock();
            if (cache.containsKey(i)) {
                lock.readLock().unlock();
                return cache.get(i);
            }
            lock.readLock().unlock();
            lock.writeLock().lock();
            long result = 0L;
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < i; k++) {
                    ++result;
                }
            }
            cache.put(i, result);
            lock.writeLock().unlock();
            return result;
        }
    }
}
