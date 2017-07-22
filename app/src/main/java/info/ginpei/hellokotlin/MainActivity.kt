package info.ginpei.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    var tasks: Array<Task> = Task.dummyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareTaskList()
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
                (v.findViewById(android.R.id.text2) as TextView).text = "When could you do ${t.title}?"

                return v
            }
        }

        taskListView.setOnItemClickListener { adapterView, view, i, l ->
            val task = tasks[i]
            startDetailActivity(task)
        }
    }

    private fun startDetailActivity(task: Task) {
        Log.d(tag, "Task ${task.title}")
    }
}
