package op.demo.practice

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Author: pk
 * Time: 8/4/21  4:27 PM
 * Description:
 */
fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch(Dispatchers.Default) {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}


