package info.ginpei.hellokotlin

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable
import java.util.*

data class Note(var title: String, var description: String = "") : Serializable {
    private val tag = "G#Note"

    var id = ""
        private set

    var createdAt = 0L
    var updatedAt = 0L

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
        this.createdAt = data.child("createdAt").value as? Long ?: 0
        this.updatedAt = data.child("updatedAt").value as? Long ?: 0
    }


    fun save(): SaveResult {
        if (title.isBlank()) {
            Log.d(tag, "save() title is empty")
            return SaveResult.BLANK_TITLE
        }

        val isNew = id.isEmpty()
        val ref = if (isNew) {
            Log.d(tag, "save() creating a new record")
            storage.push()
        } else {
            Log.d(tag, "save() fetching own record")
            storage.child(id)
        }

        id = ref.key
        ref.child("title").setValue(title)
        ref.child("description").setValue(description)

        val now = Calendar.getInstance().time.time  // Date -> Long
        ref.child("updatedAt").setValue(now)
        if (isNew) {
            ref.child("createdAt").setValue(now)
        }

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
