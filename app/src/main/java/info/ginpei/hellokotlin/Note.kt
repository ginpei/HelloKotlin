package info.ginpei.hellokotlin

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

data class Note(var title: String, var description: String = "") : Serializable {
    private val tag = "G#Note"

    var id = ""
        private set

    private val storage
        get() = FirebaseDatabase.getInstance().getReference("note")

    val ref
        get() = storage.child(id)

    constructor(data: DataSnapshot) : this("") {
        setData(data)
    }

    fun setData(data: DataSnapshot?) {
        if (data == null) return

        id = data.key
        this.title = data.child("title").value as? String ?: ""
        this.description = data.child("description").value as? String ?: ""
    }


    fun save(): SaveResult {
        if (title.isBlank()) {
            Log.d(tag, "save() title is empty")
            return SaveResult.BLANK_TITLE
        }

        val ref = if (id.isEmpty()) {
            Log.d(tag, "save() creating a new record")
            storage.push()
        } else {
            Log.d(tag, "save() fetching own record")
            storage.child(id)
        }
        id = ref.key
        ref.child("title").setValue(title)
        ref.child("description").setValue(description)

        Log.d(tag, "save() : $id / $title / $description")

        return SaveResult.OK
    }

    fun delete() {
        storage.child(id).removeValue()
    }

    enum class SaveResult {
        OK,
        BLANK_TITLE,
    }
}
