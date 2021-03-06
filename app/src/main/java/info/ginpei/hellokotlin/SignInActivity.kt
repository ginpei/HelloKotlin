package info.ginpei.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private val tag = "G#SignInActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        createAnonymousAccountButton.setOnClickListener {
            createAnonymousAccount()
        }
    }

    private fun createAnonymousAccount() {
        startLoading()

        User.auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(tag, "createAnonymousAccount() success")
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
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
