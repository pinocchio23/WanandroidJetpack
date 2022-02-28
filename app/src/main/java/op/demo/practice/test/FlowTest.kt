package op.demo.practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * Author: pk
 * Time: 2021/8/16  10:04 上午
 * Description:
 */

fun simple(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(1000)
        yield(i)
    }
}

//fun main() {
//    runBlocking {
//        launch {
//            for ( k in 4..6){
//                delay(100)
//                println("not block $k")
//            }
//        }
//        simple2().collect {
//            println(it)
//        }
//        val sum = (1..5).asFlow()
//            .map { it * it }
//            .reduce{a,b -> a - b}
//        println(sum)
//
//    }
//}

fun main() = runBlocking {

    val sum = (1..5).asFlow()
        .map { it * it }
        .fold(0) { a, b -> a + b }

    println(sum)
}

fun simple2(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}


