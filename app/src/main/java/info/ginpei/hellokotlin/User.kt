package info.ginpei.hellokotlin

import com.google.firebase.auth.FirebaseAuth

class User {
    companion object {
        val auth
            get() = FirebaseAuth.getInstance()

        val current
            get() = auth.currentUser

        val loggedIn
            get() = current != null

        val currentId
            get() = current?.uid
    }
}
