package info.ginpei.hellokotlin

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

object TaskUiMisc {
    private val tag = "G#TaskUiMisc"

    fun askDelete(activity: AppCompatActivity, onOk: () -> Unit) {
        val builder = AlertDialog.Builder(activity)

        builder.setTitle("Delete")
        builder.setMessage("Are you sure to delete this task?")

        builder.setPositiveButton(android.R.string.ok) { _, _ -> onOk() }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.show()
    }
}