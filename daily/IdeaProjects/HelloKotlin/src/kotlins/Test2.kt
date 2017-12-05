package kotlins

import java.io.BufferedReader
import java.io.StringReader
import java.util.*

//接口
interface Expr

//定义一个类实现这个接口
class Num(val value: Int) : Expr

//参数类型也是接口类型
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int {
    if (e is Num) {
        //在判断过类型之后，不用强制转换，直接使用，这就是只能转换
        return e.value
    }

    if (e is Sum) {
        return eval(e.left) + eval(e.right)
    }

    throw IllegalArgumentException("Unknown Expression")
}


fun evalUseWhen(e: Expr): Int =
        when (e) {
            is Num -> {

                e.value//智能转换
            }
            is Sum -> {

                evalUseWhen(e.right) + evalUseWhen(e.left)//智能转换
            }
            else -> {
                throw IllegalArgumentException("Unknow Expression")
            }
        }


//循环 .. 区间   数列
fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz"
    i % 5 == 0 -> "Buzz"
    i % 3 == 0 -> "Fizz"
    else ->
        "$i"
}

//判断一个字符是否在一个字符区间
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'

//判断不是一个数字
fun isNotDigit(c: Char) = c !in '0'..'9'

//in 检查作为when的分支
fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z',in 'A'..'Z' -> "It's a letter"
    else -> "I don't know"
}


//try...catch
fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    }catch (e:NumberFormatException){
        null
    }

    println(number)

}


//迭代map

val binaryReps = TreeMap<Char, String>()


fun main(args: Array<String>): Unit {
//    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
//    println(evalUseWhen(Sum(Num(1), Num(2))))
//    for (i in 1..100){
//        println(fizzBuzz(i))
//    }

    //100 -》 1 递减 -2
//    for (i in 100 downTo 1 step 2){
//        println(fizzBuzz(i))
//    }

    for (c in 'A'..'F') {
        val binaryString = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binaryString;
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }
//    A = 1000001
//    B = 1000010
//    C = 1000011
//    D = 1000100
//    E = 1000101
//    F = 1000110


    //跟踪当前项的下标
    val list = arrayListOf("10,", "101", "10001")
    for ((index, ele) in list.withIndex()) {
        println("$index:$ele")
    }

//    0:10,
//    1:101
//    2:10001

    println(isLetter('W'))//true
    println(isNotDigit('X'))//true

    //try..catch
    val reader = BufferedReader(StringReader("It's not a number"))
    readNumber(reader)

}

