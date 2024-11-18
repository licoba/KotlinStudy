import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

    ReentrantLock lock = new ReentrantLock(true);
    Condition conditionProducer = lock.newCondition();  // 生产者
    Condition conditionConsumer = lock.newCondition();  // 消费者
    LinkedList<Integer> queue = new LinkedList<>();
    final int MAX = 100;

    Thread producer = new Thread(() -> {
        for (int i = 1; i <= MAX; i++) {
            lock.lock();
            try {
                while (queue.size() >= 10) {
                    conditionProducer.await();
                }
                System.out.println("生产者生产了 " + i);
                queue.offerLast(i);
                conditionConsumer.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    });

    Thread consumer = new Thread(() -> {
        for (int i = 1; i <= MAX; i++) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    conditionConsumer.await();
                }
                int value = queue.removeFirst();
                System.out.println("消费者消费了 " + value);
                conditionProducer.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    });

    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.consumer.start();
        producerConsumer.producer.start();
    }
}
