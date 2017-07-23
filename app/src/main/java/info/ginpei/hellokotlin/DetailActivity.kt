package info.ginpei.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val tag = "G#DetailActivity"

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val task = intent.getSerializableExtra("task") as? Task
        if (task != null) {
            this.task = task
            titleTextView.text = task.title
            descriptionTextView.text = task.description
        } else {
            Toast.makeText(applicationContext, "Task was not found.", Toast.LENGTH_SHORT).show()
            finish()
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
        TaskUiMisc.askDelete(this) {
            task.delete()
            Toast.makeText(applicationContext, "The task has been done.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
