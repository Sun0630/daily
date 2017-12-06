@file:JvmName("StringFunctions")

package kotlins

import strings.Button
import strings.View

//  接收者类型                  接收者对象
fun String.lastChar(): Char = this.get(this.length - 1)

fun View.showOff() = println("i am a view")

fun Button.showOff() = println("i am a button")

/*不可变的扩展属性*/
val String.lastChar: Char
    get() = get(length - 1)

/*可变的扩展属性*/
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value) {
        this.setCharAt(length - 1, value)
    }


fun <T> Collection<T>.joinToString(  //为Collection<T> 声明一个扩展函数
        collection: Collection<T>,
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in this.withIndex()) {
        if (index > 0) {
            result.append(separator)
        }
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}