package op.demo.practice

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Author: pk
 * Time: 8/4/21  5:02 PM
 * Description:
 */

fun main() {
    runBlocking {
        val job = GlobalScope.launch {
            delay(2000L)
            println("222")
        }

        println("111")
        job.join()
    }
}