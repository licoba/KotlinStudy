import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumerBlockingQueue {

    // 用阻塞队列实现生产者和消费者
    LinkedBlockingDeque<Integer> blockingQueue = new LinkedBlockingDeque<>();
    final int MAX = 100;
    Thread producer = new Thread(() -> {
        for (int i = 1; i <= MAX; i++) {
            try {
                blockingQueue.put(i);
                System.out.println("生产者生产了 "+i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    Thread consumer = new Thread(() -> {
        for (int i = 1; i <= MAX; i++) {
            int value = 0;
            try {
                value = blockingQueue.take();
                System.out.println("消费者消费了 "+value);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    public static void main(String[] args) {
        ProducerConsumerBlockingQueue producerConsumer = new ProducerConsumerBlockingQueue();
        producerConsumer.consumer.start();
        producerConsumer.producer.start();
    }
}
