package strings

/*扩展函数不可重写*/

open class View {
    open fun click() = println("i am a view")
}

class Button : View() {
    override fun click() {
        println("Button Clicked!")
    }
}

