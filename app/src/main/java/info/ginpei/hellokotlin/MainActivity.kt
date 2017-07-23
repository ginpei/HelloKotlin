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

    var notes = ArrayList<Note>()
    private lateinit var noteListAdapter: ArrayAdapter<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareNoteList()
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume() num of notes=${notes.size}")
        updateScreen()
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
        inflater.inflate(R.menu.context_main_notes, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val info = item?.getMenuInfo() as AdapterView.AdapterContextMenuInfo
        val position = info.id.toInt()
        val note = notes[position]
        when (item.getItemId()) {
            R.id.editMenuItem -> {
                editNote(note)
                return true
            }

            R.id.deleteMenuItem -> {
                deleteNote(note)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun prepareNoteList() {
        registerForContextMenu(noteListView)

        noteListAdapter = object : ArrayAdapter<Note>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                notes
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                val t = notes.get(position)

                (v.findViewById(android.R.id.text1) as TextView).text = t.title
                (v.findViewById(android.R.id.text2) as TextView).text = t.description

                return v
            }
        }
        noteListView.adapter = noteListAdapter

        noteListView.setOnItemClickListener { adapterView, view, i, l ->
            val note = notes[i]
            startDetailActivity(note)
        }

        val db = FirebaseDatabase.getInstance().getReference("note")
        db.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(tag, "onCancelled")
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                Log.d(tag, "onChildMoved")
            }

            override fun onChildChanged(data: DataSnapshot?, p1: String?) {
                Log.d(tag, "onChildChanged() ${data} / ${p1}")

                val note = notes.find { it.id == data?.key }
                if (note != null) {
                    note.setData(data)
                    updateScreen()
                }
            }

            override fun onChildAdded(data: DataSnapshot?, p1: String?) {
                Log.d(tag, "onChildAdded() ${data}")
                if (data == null) return

                val note = Note(data)
                notes.add(note)
                Log.d(tag, "onChildAdded() num of notes=${notes.size}")
                updateScreen()
            }

            override fun onChildRemoved(data: DataSnapshot?) {
                Log.d(tag, "onChildRemoved() ${data}")

                val note = notes.find { it.id == data?.key }
                notes.remove(note)
                Log.d(tag, "onChildRemoved() num of notes=${notes.size} / note exists? ${note != null} / IDs ${notes.map { it.id }}")
                updateScreen()
            }

        })
    }

    private fun updateScreen() {
        noteListAdapter.notifyDataSetChanged()
        emptyTextView.visibility = if (notes.size > 0) View.GONE else View.VISIBLE
    }

    private fun startCreateNoteActivity() {
        val intent = Intent(applicationContext, CreateNoteActivity::class.java)
        startActivity(intent)
    }

    private fun startDetailActivity(note: Note) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    private fun editNote(note: Note) {
        val intent = Intent(applicationContext, EditNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    private fun deleteNote(note: Note) {
        NoteUiMisc.askDelete(this) {
            note.delete()
            Toast.makeText(applicationContext, "The note has been done.", Toast.LENGTH_SHORT).show()
        }
    }
}
