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

    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val note = intent.getSerializableExtra("note") as? Note
        if (note != null) {
            this.note = note
            titleTextView.text = note.title
            descriptionTextView.text = note.description
        } else {
            Toast.makeText(applicationContext, "Note was not found.", Toast.LENGTH_SHORT).show()
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
        NoteUiMisc.askDelete(this) {
            note.delete()
            Toast.makeText(applicationContext, "The note has been done.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
