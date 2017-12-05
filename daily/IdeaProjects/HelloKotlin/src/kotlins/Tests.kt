package kotlins

import javafx.scene.control.Separator


//创建set集合
val set = hashSetOf(1, 2, 3, 4)
//创建list
val list = arrayListOf<Int>(1, 5, 6)
//创建map
val map = hashMapOf<Int, String>(1 to "one", 5 to "five", 6 to "six")

@JvmOverloads
fun <T> joinToString(
        collection: Collection<T>,
        separator: String=",",
        prefix: String="",
        postfix: String=""
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) {
            result.append(separator)
        }
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}




/**
 * 函数的定义和调用
 */
fun main(args: Array<String>): Unit {
    println(set.javaClass)
    println(list.javaClass)
    println(map.javaClass)
//    class java.util.HashSet
//    class java.util.ArrayList
//    class java.util.HashMap

    val list = listOf<Int>(1, 2, 3)
    println(joinToString(list, "--"))
    //命名参数
    println(joinToString(collection = list,separator = "+",prefix = "[",postfix = "]"))
}