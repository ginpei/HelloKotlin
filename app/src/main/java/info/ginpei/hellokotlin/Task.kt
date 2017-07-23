package info.ginpei.hellokotlin

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

data class Task(var title: String, var description: String = "") : Serializable {
    private val tag = "G#Task"

    var id = ""
        private set

    private val storage = FirebaseDatabase.getInstance().getReference("task")

    constructor(data: DataSnapshot) : this("") {
        setData(data)
    }

    fun setData(data: DataSnapshot?) {
        if (data == null) return

        id = data.key
        this.title = data.child("title").value as? String ?: ""
        this.description = data.child("description").value as? String ?: ""
    }


    fun save(): Boolean {
        val ref = storage.push()
        id = ref.key
        ref.child("title").setValue(title)
        ref.child("description").setValue(description)

        Log.d(tag, "save() : $id / title / $description")

        return true
    }
}
