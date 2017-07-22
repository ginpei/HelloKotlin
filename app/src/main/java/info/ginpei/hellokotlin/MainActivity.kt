package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "G#MainActivity"

    var tasks: Array<Task> = Task.dummyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareTaskList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.header_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return super.onOptionsItemSelected(item)
        }

        when (item.getItemId()) {
            R.id.createNote -> startCreateNoteActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun prepareTaskList() {
        taskListView.adapter = object : ArrayAdapter<Task>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                tasks
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                val t = tasks.get(position)

                (v.findViewById(android.R.id.text1) as TextView).text = t.title
                (v.findViewById(android.R.id.text2) as TextView).text = t.description

                return v
            }
        }

        taskListView.setOnItemClickListener { adapterView, view, i, l ->
            val task = tasks[i]
            startDetailActivity(task)
        }
    }

    private fun startCreateNoteActivity() {
        val intent = Intent(applicationContext, CreateNoteActivity::class.java)
        startActivity(intent)
    }

    private fun startDetailActivity(task: Task) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }
}
