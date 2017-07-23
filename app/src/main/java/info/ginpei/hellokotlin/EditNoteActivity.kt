package info.ginpei.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.content_edit_note.*

class EditNoteActivity : AppCompatActivity() {
    private val tag = "G#EditNoteActivity"

    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val note = intent.getSerializableExtra("note") as? Note
        if (note != null) {
            this.note = note
            titleEditText.setText(note.title)
            descriptionEditText.setText(note.description)
        } else {
            Toast.makeText(applicationContext, "Note was not found.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.header_edit_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return super.onOptionsItemSelected(item)
        }

        when (item.getItemId()) {
            R.id.updateNoteMenuItem -> updateNote()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun updateNote() {
        note.title = titleEditText.text.toString()
        note.description = descriptionEditText.text.toString()
        when (note.save()) {
            Note.SaveResult.OK -> {
                Toast.makeText(applicationContext, "The note is updated.", Toast.LENGTH_SHORT).show()
                finish()
            }
            Note.SaveResult.BLANK_TITLE -> NoteUiMisc.toastForBlankTitle(applicationContext)
        }
    }
}
