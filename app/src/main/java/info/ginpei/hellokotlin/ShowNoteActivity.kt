package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_show_note.*

class ShowNoteActivity : AppCompatActivity() {
    private val tag = "G#ShowNoteActivity"

    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_note)

        val note = intent.getSerializableExtra("note") as? Note
        if (note != null) {
            setNote(note)
        } else {
            Toast.makeText(applicationContext, "Note was not found.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.header_show_note, menu)
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

    private fun setNote(note: Note) {
        this.note = note
        udpateData()

        note.ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(tag, "onCancelled()")
            }

            override fun onDataChange(p0: DataSnapshot?) {
                Log.d(tag, "onDataChange()")
                udpateData(p0)
            }

        })
    }

    fun udpateData(data: DataSnapshot? = null) {
        Log.d(tag, "udpateData() $data / $note")
        note.setData(data)

        titleTextView.text = note.title
        descriptionTextView.text = note.description
    }

    fun edit() {
        val intent = Intent(applicationContext, EditNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    fun delete() {
        NoteUiMisc.askDelete(this) {
            note.delete()
            NoteUiMisc.toastForDeleted(applicationContext)
            finish()
        }
    }
}
