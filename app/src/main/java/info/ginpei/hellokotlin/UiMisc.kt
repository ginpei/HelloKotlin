package info.ginpei.hellokotlin

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

object UiMisc {
    private val tag = "G#UiMisc"

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun ask(activity: AppCompatActivity, title: String, message: String, onOk: () -> Unit) {
        val builder = AlertDialog.Builder(activity)

        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.ok) { _, _ -> onOk() }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.show()
    }

    object Note {
        fun askDelete(activity: AppCompatActivity, onOk: () -> Unit) {
            ask(activity, "Delete", "Are you sure to delete this note?", onOk)
        }

        fun toastForCreated(context: Context) {
            toast(context, "A new note has been created.")
        }

        fun toastForUpdated(context: Context) {
            toast(context, "The note has been updated.")
        }

        fun toastForDeleted(context: Context) {
            toast(context, "The note has been deleted.")
        }

        fun toastForBlankTitle(context: Context) {
            toast(context, "Title is required.")
        }
    }
}