package info.ginpei.hellokotlin

class Task(var title: String) {
    companion object {
        fun dummyArray(): Array<Task> = arrayOf(
                Task("Buy milk"),
                Task("Buy chocolate"),
                Task("Buy house"),
                Task("Buy whole life")
        )
    }
}
