package info.ginpei.hellokotlin

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

object NoteUiMisc {
    private val tag = "G#NoteUiMisc"

    fun askDelete(activity: AppCompatActivity, onOk: () -> Unit) {
        val builder = AlertDialog.Builder(activity)

        builder.setTitle("Delete")
        builder.setMessage("Are you sure to delete this note?")

        builder.setPositiveButton(android.R.string.ok) { _, _ -> onOk() }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.show()
    }

    fun toastForCreated(context: Context) {
        Toast.makeText(context, "A new note is added.", Toast.LENGTH_SHORT).show()
    }

    fun toastForUpdated(context: Context) {
        Toast.makeText(context, "The note is updated.", Toast.LENGTH_SHORT).show()
    }

    fun toastForBlankTitle(context: Context) {
        Toast.makeText(context, "Title is required.", Toast.LENGTH_SHORT).show()
    }
}