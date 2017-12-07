package kotlins

import strings.Button
import strings.View


//创建set集合
val set = hashSetOf(1, 2, 3, 4)
//创建list
val list = arrayListOf<Int>(1, 5, 6)
//创建map
val map = hashMapOf<Int, String>(1 to "one", 5 to "five", 6 to "six")

//@JvmOverloads
//fun <T> joinToString(
//        collection: Collection<T>,
//        separator: String=",",
//        prefix: String="",
//        postfix: String=""
//): String {
//    val result = StringBuilder(prefix)
//
//    for ((index, element) in collection.withIndex()) {
//        if (index > 0) {
//            result.append(separator)
//        }
//        result.append(element)
//    }
//    result.append(postfix)
//    return result.toString()
//}


/**
 * 函数的定义和调用
 */
fun main(args: Array<String>): Unit {
    println(list.javaClass)
    println(map.javaClass)
//    class java.util.HashSet
//    class java.util.ArrayList
//    class java.util.HashMap

    var list = listOf<String>("1", "2", "3")
//    println(joinToString(list, "--"))
    //命名参数
//    println(joinToString(collection = list, separator = "+", prefix = "[", postfix = "]"))
    println("Sunxin".lastChar())
    println(list.joinToString(" "))
    println(list.joinToString(collection = list, prefix = "<", postfix = ">"))

    val view: View = Button()
    view.click()
    view.showOff()

    println("Lottie".lastChar)//e

    val sb = StringBuilder("Kotlin?")
    sb.lastChar = '!'
    println(sb)//Kotlin!

    val list2 = listOf("args", *args)
    println("---" + list2)

    val map = mapOf(1 to "one",2 to "two", 3 to "three")

    val (number,name) = 1 to "one"

    println("123.345-6.A".split("\\.|-".toRegex()))//[123, 345, 6, A]
    println("123.345-6.A".split(".","-"))//指定多个分隔符[123, 345, 6, A]
    println("123.345-6.A".split("."))//[123, 345-6, A]

}