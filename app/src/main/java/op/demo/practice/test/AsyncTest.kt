package op.demo.practice

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * Author: pk
 * Time: 8/4/21  2:18 PM
 * Description:
 */

//fun main2() {
//    GlobalScope.launch {
//        val time = measureTimeMillis {
//            val one = async { doSomethingOne() }
//            val two = async { doSomethingTwo() }
//            println("The answer is ${one.await() + two.await()}")
//        }
//        println("Completed in $time ms")
//    }
//    println("Completed")
//    println(Thread.currentThread().name)
//    runBlocking {
//        delay(4000L)
//    }
//}

fun main(){
    runBlocking {
        launch(Dispatchers.Default) {
            val time = measureTimeMillis {
                val one = async { doSomethingOne() }
                val two = async { doSomethingTwo() }
                println("The answer is ${one.await() + two.await()}")
                println("Completed in s")
            }
            println("Completed in $time ms")
        }

        launch(Dispatchers.IO) { println("Completed") }
        delay(1000L)

    }
    println("in s")
}

suspend fun doSomethingOne(): Int {
    delay(1000L)
    println(Thread.currentThread().name)
    return 12
}

suspend fun doSomethingTwo(): Int {
    delay(3000L)
    println(Thread.currentThread().name)
    return 23
}

