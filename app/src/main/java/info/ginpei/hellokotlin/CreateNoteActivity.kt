package info.ginpei.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {
    private val tag = "G#CreateNoteActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.header_create_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return super.onOptionsItemSelected(item)
        }

        when (item.getItemId()) {
            R.id.createNoteMenuItem -> createNote()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun createNote() {
        val title = titleEditText.text.toString()
        val description = descriptionTextEdit.text.toString()
        val note = Note(title, description)
        when (note.save(User.currentId)) {
            Note.SaveResult.OK -> {
                UiMisc.Note.toastForCreated(applicationContext)
                finish()
            }
            Note.SaveResult.BLANK_TITLE -> UiMisc.Note.toastForBlankTitle(applicationContext)
        }
    }
}
