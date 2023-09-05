import java.lang.Thread.sleep
import java.util.*

// 生产者消费者模型
val buffer = LinkedList<Data>()
const val MAX = 5 //buffer最大size
val lock = Object()  // 唯一的一把锁

fun produce(i: Int, data: Data) {
    sleep(2000) // 模拟生产所需要的时间
    val id = Thread.currentThread().name
    synchronized(lock) {
        println("生产者{$id}获取到了锁")
        while (buffer.size >= MAX) {
            println("生产者{$id}等待消费，先释放")
            lock.wait()
        }

        buffer.push(data)
        println("生产者{$id}生产: $data ,目前大小 ${buffer.size}")
        // notify方法只唤醒其中一个线程，选择哪个线程取决于操作系统对多线程管理的实现。
        // notifyAll会唤醒所有等待中线程，哪一个线程将会第一个处理取决于操作系统的实现，但是都有机会处理。
        // 此处使用notify有可能唤醒的是另一个生产线程从而造成死锁，所以必须使用notifyAll
        lock.notifyAll()
        println("生产者{$id}释放了锁，并notifyAll")
    }
}

fun consume(i: Int) {
    synchronized(lock) { // 使用 synchronized 关键字，确保消费者已经获取到了锁
        val id = Thread.currentThread().name

        println("消费者{$id}获取到了锁")
        if (buffer.isEmpty()) {//  如果buffer是空的，则一直在锁上wait
            // 这里必须要用while，因为如果不用while，每个线程只会在开始的时候判断一次buffer是否为空，后面就直接开始消费了
            // 也就是说，当生产者产生一个商品时，notifyAll，所有在等待的消费者线程，会同时开始消费buffer，但是这时候buffer里面只有一个商品
            println("消费者{$id}等待生产，先释放")
            lock.wait() // 暂停消费，消费者线程释放锁，并且进入等待状态
        }
        println("消费者{$id}开始消费")
        val data = buffer.removeFirst()
        println("消费者{$id}消费完成: $data ,目前大小 ${buffer.size}")
        lock.notifyAll()
        println("消费者{$id}释放了锁，并notifyAll")
    }
    sleep(2000) // mock consume
}


fun main() {
    // 同时启动多个生产、消费线程
    repeat(10) {  // 创建10个生产者线程
        Thread { produce(it, Data(it)) }.start()
    }
    repeat(10) {  // 创建10个消费者线程
        Thread { consume(it) }.start()
    }
}

