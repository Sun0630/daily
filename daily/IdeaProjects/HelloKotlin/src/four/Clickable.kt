package four

/**
 * 定义一个接口
 */
interface Clickable {
    fun click()
    //接口的方法可以有一个默认实现
    fun showOff() = println("i am Clickable!")
}


interface Focusable {
    fun setFocus(b: Boolean) {
        println("I ${if (b) "got" else "lost"} focus.")
    }

    fun showOff() = println("i am Focusable")
}


class Button : Clickable, Focusable {
    override fun showOff() {
        //表明想要调用哪个父类的方法
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }


    override fun click() {
        println("i was clicked")
    }

}


/**
 * 这个类是open的，其他类可以继承
 */
open class RichButton:Clickable{
    //open
    override fun click() {}

    //默认是final 的，不能在子类中重写
    fun disable(){}

    //可以重写
    open fun animate(){}

}

/**
 * 声明一个抽象类,不能创建实例
 */
abstract class Animated{
    //必须被子类重写
    abstract fun animate()
    //不是默认open的，但是可以标注为open
    open fun stopAnimation(){}
    fun animateTewic(){}
}


fun main(args: Array<String>): Unit {
    val button = Button()
    button.showOff()
    button.setFocus(true)
    button.click()
}