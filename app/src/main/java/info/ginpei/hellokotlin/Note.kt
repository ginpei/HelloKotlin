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

    var userId = ""
    var createdAt = 0L
    var updatedAt = 0L

    val ref
        get() = storage.child(id)

    constructor(data: DataSnapshot) : this("") {
        setData(data)
    }

    fun setData(data: DataSnapshot?) {
        if (data == null) return

        id = data.key
        this.userId = data.child("userId").value as? String ?: ""
        this.title = data.child("title").value as? String ?: ""
        this.description = data.child("description").value as? String ?: ""
        this.createdAt = data.child("createdAt").value as? Long ?: 0
        this.updatedAt = data.child("updatedAt").value as? Long ?: 0
    }

    fun save(userId: String? = null): SaveResult {
        if (title.isBlank()) {
            Log.d(tag, "save() title is empty")
            return SaveResult.BLANK_TITLE
        }

        val isNew = id.isEmpty()

        if (isNew && userId == null) {
            Log.d(tag, "save() user ID is required")
            return SaveResult.NOT_SIGNED_IN
        }

        val now = Calendar.getInstance().time.time  // Date -> Long
        updatedAt = now
        if (isNew) {
            createdAt = now
        }

        val ref = if (isNew) {
            Log.d(tag, "save() creating a new record")
            storage.push()
        } else {
            Log.d(tag, "save() fetching own record")
            storage.child(id)
        }

        if (isNew) {
            id = ref.key
            ref.child("userId").setValue(userId)
        }

        ref.child("title").setValue(title)
        ref.child("description").setValue(description)
        ref.child("createdAt").setValue(createdAt)
        ref.child("updatedAt").setValue(updatedAt)

        Log.d(tag, "save() : $this")

        return SaveResult.OK
    }

    fun delete() {
        storage.child(id).removeValue()
    }

    companion object {
        private val storage
            get() = FirebaseDatabase.getInstance().getReference("note")

        fun ofUser(key: String) = storage.orderByChild("userId").equalTo(key)
    }

    enum class SaveResult {
        OK,
        BLANK_TITLE,
        NOT_SIGNED_IN,
    }
}
