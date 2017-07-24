package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class BootActivity : AppCompatActivity() {
    private val tag = "G#BootActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot)

        FirebaseAuth.getInstance().addAuthStateListener({ firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                signIn()
            } else {
                start()
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
