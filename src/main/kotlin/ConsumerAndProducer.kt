// 生产者消费者模式

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.locks.ReentrantLock

val queue = ArrayBlockingQueue<Int>(10) // 阻塞队列，容量为10
val lock = ReentrantLock() // 锁

class Producer : Thread() {
    override fun run() {
        for (i in 1..20) {
            try {
                queue.put(i) // 将数据放入队列
                println("生产者生产: $i")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

class Consumer : Thread() {
    override fun run() {
        for (i in 1..20) {
            try {
                val item = queue.take() // 从队列中取出数据
                println("消费者消费: $item")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

fun main() {
    val producer = Producer()
    val consumer = Consumer()

    producer.start()
    consumer.start()

    producer.join()
    consumer.join()
}