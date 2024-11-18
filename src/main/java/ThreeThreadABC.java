import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThreadABC {
    ReentrantLock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    int curValue = 1;

    Thread thread1 = new Thread(() -> {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                while (curValue != 1) {
                    condition1.await();
                }
                System.out.println("A");
                curValue = 2;
                condition2.signal();
            }
        } catch (Exception ignored) {
        } finally {
            lock.unlock();
        }
    });

    Thread thread2 = new Thread(() -> {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                while (curValue != 2) {
                    condition2.await();
                }
                System.out.println("B");
                curValue = 3;
                condition3.signal();
            }
        } catch (Exception ignored) {
        } finally {
            lock.unlock();
        }
    });


    Thread thread3 = new Thread(() -> {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                while (curValue != 3) {
                    condition3.await();
                }
                System.out.println("C");
                curValue = 1;
                condition1.signal();
            }
        } catch (Exception ignored) {
        } finally {
            lock.unlock();
        }
    });


    public static void main(String[] args) {
        ThreeThreadABC threeThreadABC = new ThreeThreadABC();
        threeThreadABC.thread1.start();
        threeThreadABC.thread2.start();
        threeThreadABC.thread3.start();
    }

}
