package info.ginpei.hellokotlin

import java.io.Serializable

class Task(var title: String, var description: String = ""): Serializable {
    companion object {
        fun dummyArray(): Array<Task> = arrayOf(
                Task("Buy milk"),
                Task("Buy chocolate", "as many as you can if cheap"),
                Task("Buy house"),
                Task("Buy whole life", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        )
    }
}
