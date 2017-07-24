package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class BootActivity : AppCompatActivity() {
    private val tag = "G#BootActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot)

        User.auth.addAuthStateListener({ firebaseAuth ->
            if (User.loggedIn) {
                start()
            } else {
                signIn()
            }
        })
    }

    private fun signIn() {
        val intent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun start() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}
