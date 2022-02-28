package op.demo.practice.test

/**
 * Author: pk
 * Time: 2022/1/14  9:24 下午
 * Description:
 */


class Account {
    var balance = 100.0

    infix fun add(amount: Double) : Unit {
        this.balance = balance + amount
    }

    infix fun plus(amount: Double){

    }
}

fun main() {
//    val s: String? = null
//    if (s?.get(0) == '2') {
//        println("is empty")
//    }
//    if (s.isNullOrEmpty()) {
//        println("is null or empty")
//    }

//    val increment = { i: Int -> i + 1 }
//    val bicrement = { i: Int -> i + 2 }
//    val double = { i: Int -> i * 2 }
//    val one = { 1 }
//
//    val equilibrum = one then double then (increment + bicrement)
//    println(equilibrum())

    val  account = Account()
    account add 100.00
    account plus 100.00
    print(account.balance)

}

private infix fun <T, R> (() -> T).then(another: (T) -> R): () -> R = { another(this()) }
operator fun <T, R1, R2> ((T) -> R1).plus(another: (T) -> R2) = { x: T -> this(x) to another(x) }