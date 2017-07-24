package info.ginpei.hellokotlin

import com.google.firebase.auth.FirebaseAuth

class User {
    companion object {
        val auth
            get() = FirebaseAuth.getInstance()

        val currentId
            get() = auth.currentUser?.uid
    }
}
