package info.ginpei.hellokotlin

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val tag = "DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val task = intent.getSerializableExtra("task")
        if (task is Task) {
            titleTextView.text = task.title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.header_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return super.onOptionsItemSelected(item)
        }

        when (item.getItemId()) {
            R.id.editMenuItem -> edit()
            R.id.deleteMenuItem -> delete()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun edit() {
        Log.d(tag, "edit")
    }

    fun delete() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete")
        builder.setMessage("Are you sure to delete this task?")

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            Toast.makeText(applicationContext, "The task has been done.", Toast.LENGTH_SHORT).show()
            finish()
        }

        builder.setNegativeButton(android.R.string.cancel, null)

        builder.show()
    }
}
