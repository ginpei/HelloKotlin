package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {
    private val tag = "G#SigninActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

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
                UiMisc.toast(applicationContext, "Failed to create an anonymous account.")
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
