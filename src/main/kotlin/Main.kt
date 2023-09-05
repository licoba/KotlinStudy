import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun doRequest(index: Int): Int {
    val delayTime = (1..5).random() * 1000L
    println("任务$index 耗时：$delayTime 毫秒")
    delay(delayTime) // 模拟请求时间不定
    return (1..10).random()
}

suspend fun main() {
    val deferred = mutableListOf<Deferred<Int>>()
    val startTime = System.currentTimeMillis()

    coroutineScope {
        repeat(100) { index ->
            val d = async { doRequest(index) }
            deferred.add(d)
        }
    }

    val results = deferred.awaitAll()
    val totalTime = System.currentTimeMillis() - startTime

    println("总耗时：$totalTime 毫秒")
    println("结果：$results")
    println("结果总和：${results.sum()}")
}
