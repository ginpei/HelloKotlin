package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val tag = "G#LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        createAnonymousAccountButton.setOnClickListener {
            createAnonymousAccount()
        }
    }

    private fun createAnonymousAccount() {
        startLoading()

        val auth = FirebaseAuth.getInstance()
        auth.signInAnonymously().addOnCompleteListener {
            if (auth.currentUser != null) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Failed to create an anonymous account.", Toast.LENGTH_SHORT).show()
                stopLoading()
            }
        }
    }

    private fun startLoading() {
        createAnonymousAccountButton.isEnabled = false
        loadingProgressBar.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        createAnonymousAccountButton.isEnabled = true
        loadingProgressBar.visibility = View.GONE
    }
}
