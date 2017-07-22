package info.ginpei.hellokotlin

import java.io.Serializable

class Task(var title: String): Serializable {
    companion object {
        fun dummyArray(): Array<Task> = arrayOf(
                Task("Buy milk"),
                Task("Buy chocolate"),
                Task("Buy house"),
                Task("Buy whole life")
        )
    }
}
