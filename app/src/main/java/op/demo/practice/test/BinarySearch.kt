package op.demo.practice.test

/**
 * Author: pk
 * Time: 2022/1/27  3:34 下午
 * Description:
 */

private fun binarySearch(array: IntArray, target: Int): Int {
    val arrSize = array.size
    if (arrSize == 1) return if (array[0] == target) 0 else -1
    var pos = if (array.size / 2 == 0) {
        array.size / 2 - 1
    } else {
        (array.size - 1) / 2
    }
    when {
        target == array[pos] -> {
            return pos
        }
        target > array[pos] -> {
            val rightArr =
                array.sliceArray(IntRange(if (array.size / 2 == 0) pos else pos + 1, arrSize - 1))
            val innerPos = binarySearch(rightArr, target)
            return if (innerPos == -1) -1 else innerPos + pos + 1
        }
        target < array[pos] -> {
            val leftArr = array.sliceArray(IntRange(0, if (array.size / 2 == 0) pos else pos - 1))
            return binarySearch(leftArr, target)
        }
        else -> {
            return -1
        }
    }
}

//采用边界法
private fun binarySearch2(array: IntArray, target: Int): Int {
    var left = 0
    var right = array.size - 1
    while (left <= right) {
        val mid = left + (right - left) / 2
        when {
            array[mid] == target -> {
                return mid
            }
            target > array[mid] -> {
                left = mid + 1
            }
            else -> {
                right = mid - 1
            }
        }
    }
    return -1
}

fun main() {
    val arr = intArrayOf(1, 4, 5, 23, 26, 55, 56, 90, 99)
//
    println(binarySearch2(arr, 26))
//    println(7 / 2)
}