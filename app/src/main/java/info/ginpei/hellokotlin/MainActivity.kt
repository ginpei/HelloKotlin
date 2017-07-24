package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
            R.id.createNote -> createNote()
            R.id.signoutMenuItem -> signOut()
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
            showNote(note)
        }

        val db = Note.ofUser(User.currentId!!)
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
        noteListAdapter.sort { n1, n2 -> if (n1.updatedAt > n2.updatedAt) -1 else 1 }
        emptyTextView.visibility = if (notes.size > 0) View.GONE else View.VISIBLE
    }

    private fun createNote() {
        val intent = Intent(applicationContext, CreateNoteActivity::class.java)
        startActivity(intent)
    }

    private fun showNote(note: Note) {
        val intent = Intent(applicationContext, ShowNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    private fun editNote(note: Note) {
        val intent = Intent(applicationContext, EditNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    private fun deleteNote(note: Note) {
        UiMisc.Note.askDelete(this) {
            note.delete()
            UiMisc.Note.toastForDeleted(applicationContext)
        }
    }

    private fun signOut() {
        val message = "When you sign out from an anonymous account, all notes are gone.\n\nAre you sure to sign out?"
        UiMisc.ask(this, "Sign out", message) {
            User.current?.delete()  // TODO check FirebaseAuthRecentLoginRequiredException
            User.auth.signOut()

            UiMisc.toast(applicationContext, "OK, see you soon!")

            val intent = Intent(applicationContext, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
