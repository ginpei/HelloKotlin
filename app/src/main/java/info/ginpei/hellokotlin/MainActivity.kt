package info.ginpei.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    var tasks: Array<Task> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloButton.setOnClickListener { v -> helloButton_onClick(v) }
    }

    fun helloButton_onClick(view: View) {
        val task = Task("title")
        tasks = tasks.plus(task)

        Log.d(tag, "Hello! " + tasks.size)
    }


}
