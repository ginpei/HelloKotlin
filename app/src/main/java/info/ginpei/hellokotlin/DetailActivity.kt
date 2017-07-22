package info.ginpei.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val task = intent.getSerializableExtra("task")
        if (task is Task) {
            titleTextView.text = task.title
        }
    }
}
