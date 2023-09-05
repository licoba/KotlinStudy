import kotlinx.coroutines.delay
import java.lang.Thread.sleep

import java.util.concurrent.LinkedBlockingQueue

/**
 * LinkedBlockingQueue 是 Java 中的一个阻塞队列实现，它基于链表数据结构。它的内部实现包含了一个链表和两个锁，分别用于实现生产者和消费者的线程安全访问。
 *
 * 具体来说，LinkedBlockingQueue 内部维护了一个链表，用于存储元素。每个节点都包含一个元素以及指向下一个节点的引用。链表的头部和尾部分别由两个节点表示。
 *
 * LinkedBlockingQueue 还包含两个锁：一个是用于生产者的锁（putLock），一个是用于消费者的锁（takeLock）。这两个锁可以实现生产者和消费者之间的互斥访问。
 *
 * 当生产者调用 put() 方法将元素放入队列时，它会先获取 putLock 锁，确保只有一个生产者线程能够执行插入操作。如果队列已满，生产者线程会被阻塞，直到有空间可用。当插入操作完成后，生产者会释放 putLock 锁，允许其他线程进行插入或取出操作。
 *
 * 当消费者调用 take() 方法从队列中取出元素时，它会先获取 takeLock 锁，确保只有一个消费者线程能够执行取出操作。如果队列为空，消费者线程会被阻塞，直到有元素可用。当取出操作完成后，消费者会释放 takeLock 锁，允许其他线程进行插入或取出操作。
 *
 *
 */
val queue = LinkedBlockingQueue<Data>(5)

class Producer(private val i: Int) {
    fun produce() {
        sleep(1000)
        val data = Data(i)
        try {
            println("生产者开始生产: $data，剩余 ${queue.size}")
            queue.put(data) // 将数据放入队列
            println("生产者生产完毕: $data，剩余 ${queue.size}")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}

class Consumer() {
    fun consume() {
        try {
            val data = queue.take() // 从队列中获取数据
//            sleep(1000)
            println("消费者消费了: $data，剩余 ${queue.size}")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}

fun main() {
    repeat(20) { Thread { Consumer().consume() }.start() }
    repeat(20) { Thread { Producer(it).produce() }.start() }
}
