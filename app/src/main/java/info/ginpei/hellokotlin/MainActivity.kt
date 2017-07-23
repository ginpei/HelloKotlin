package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "G#MainActivity"

    var tasks = ArrayList<Task>()
    private lateinit var taskListAdapter: ArrayAdapter<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareTaskList()
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume() num of tasks=${tasks.size}")
        taskListAdapter.notifyDataSetChanged()
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

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_main_tasks, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val info = item?.getMenuInfo() as AdapterView.AdapterContextMenuInfo
        val position = info.id.toInt()
        val task = tasks[position]
        when (item.getItemId()) {
            R.id.editMenuItem -> {
                Log.d(tag, "edit ${task.title}")  // TODO move to editor page
                return true
            }

            R.id.deleteMenuItem -> {
                deleteTask(task)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun prepareTaskList() {
        registerForContextMenu(taskListView)

        taskListAdapter = object : ArrayAdapter<Task>(
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
        taskListView.adapter = taskListAdapter

        taskListView.setOnItemClickListener { adapterView, view, i, l ->
            val task = tasks[i]
            startDetailActivity(task)
        }

        val db = FirebaseDatabase.getInstance().getReference("task")
        db.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(tag, "onCancelled")
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                Log.d(tag, "onChildMoved")
            }

            override fun onChildChanged(data: DataSnapshot?, p1: String?) {
                Log.d(tag, "onChildChanged() ${data} / ${p1}")

                val task = tasks.find { it.id === data?.key }
                if (task != null) {
                    task.setData(data)
                    taskListAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildAdded(data: DataSnapshot?, p1: String?) {
                Log.d(tag, "onChildAdded() ${data}")
                if (data == null) return

                val task = Task(data)
                tasks.add(task)
                Log.d(tag, "onChildAdded() num of tasks=${tasks.size}")
                taskListAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(data: DataSnapshot?) {
                Log.d(tag, "onChildRemoved() ${data}")

                val task = tasks.find { it.id == data?.key }
                tasks.remove(task)
                Log.d(tag, "onChildRemoved() num of tasks=${tasks.size} / task exists? ${task != null} / IDs ${tasks.map { it.id }}")
                taskListAdapter.notifyDataSetChanged()
            }

        })
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

    private fun deleteTask(task: Task) {
        TaskUiMisc.askDelete(this) {
            task.delete()
            Toast.makeText(applicationContext, "The task has been done.", Toast.LENGTH_SHORT).show()
        }
    }
}
