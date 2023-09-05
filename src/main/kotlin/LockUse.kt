import kotlinx.coroutines.delay
import kotlin.concurrent.thread

class LockUse {
}

fun main() {
    val lock = Object() // 唯一的一把锁

    for (i in 1..100) {
        thread {
            synchronized(lock) {
                println("Thread $i obtained the lock")
                // 在这里执行需要同步的代码块
                // ...
                println("Thread $i released the lock")
            }
        }
    }
}