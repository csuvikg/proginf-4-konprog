import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class SimpleCacheD {
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

    // WIP: there is an issue
    static class MyCache {
        private final Map<Integer, Long> cache = new HashMap<>();
        private final StampedLock lock = new StampedLock();

        public long getSquare(int i) {
            long stamp = lock.tryOptimisticRead();
            if (stamp == 0L) {
                stamp = lock.readLock();
            }
            if (cache.containsKey(i)) {
                if (lock.validate(stamp)) {
                    lock.unlock(stamp);
                    return cache.get(i);
                } else {
                    lock.unlock(stamp);
                    stamp = lock.readLock();
                    long result = cache.get(i);
                    lock.unlockRead(stamp);
                    return result;
                }
            }
            if (lock.validate(stamp)) {
                stamp = lock.tryConvertToWriteLock(stamp);
            } else {
                stamp = lock.writeLock();
            }
            long result = 0L;
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < i; k++) {
                    ++result;
                }
            }
            cache.put(i, result);
            lock.unlockWrite(stamp);
            return result;
        }
    }
}
