package info.ginpei.hellokotlin

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

class Task(var title: String, var description: String = "") : Serializable {
    private val tag = "G#Task"

    companion object {
        fun dummyArray(): Array<Task> = arrayOf(
                Task("Buy milk"),
                Task("Buy chocolate", "as many as you can if cheap"),
                Task("Buy house"),
                Task("Buy whole life", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        )
    }

    private var id = ""

    fun save(): Boolean {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("task").push()
        id = ref.key
        ref.child("title").setValue(title)
        ref.child("description").setValue(description)

        Log.d(tag, "save() : $id / title / $description")

        return true
    }
}
