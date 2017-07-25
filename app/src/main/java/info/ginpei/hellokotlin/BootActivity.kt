package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class BootActivity : AppCompatActivity() {
    private val tag = "G#BootActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot)

        if (User.loggedIn) {
            start()
        } else {
            signIn()
        }
    }

    private fun signIn() {
        Log.d(tag, "signIn()")
        val intent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun start() {
        Log.d(tag, "start()")
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
