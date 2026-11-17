package com.example.my_bookshelf

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUser() = auth.currentUser

    fun signUp(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val user = result.user

                if (user == null) {
                    onResult(false, "Could not create account.")
                    return@addOnSuccessListener
                }

                val profile = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()

                user.updateProfile(profile)
                    .addOnSuccessListener {
                        onResult(true, "Account created successfully!")
                    }
                    .addOnFailureListener {
                        onResult(
                            true,
                            "Account created, but name was not saved."
                        )
                    }
            }
            .addOnFailureListener {
                onResult(
                    false,
                    it.localizedMessage ?: "Sign up failed."
                )
            }
    }

    fun signIn(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onResult(true, "Welcome back!")
            }
            .addOnFailureListener {
                onResult(
                    false,
                    it.localizedMessage ?: "Sign in failed."
                )
            }
    }

    fun signOut() {
        auth.signOut()
    }
}
