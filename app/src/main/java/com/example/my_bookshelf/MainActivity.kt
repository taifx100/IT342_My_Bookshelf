package com.example.my_bookshelf

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AuthRepository()

        setContent {
            var isLoggedIn by remember {
                mutableStateOf(repository.getCurrentUser() != null)
            }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn) {
                        val user = repository.getCurrentUser()

                        val userName = user?.displayName
                            ?.takeIf { it.isNotBlank() }
                            ?: user?.email?.substringBefore("@")
                            ?: "Reader"

                        HomeScreen(
                            userName = userName,
                            onMyBooksClick = {
                                Toast.makeText(
                                    this@MainActivity,
                                    "My Books screen is coming soon!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onSignOut = {
                                repository.signOut()
                                isLoggedIn = false
                            }
                        )
                    } else {
                        AuthScreen(
                            repository = repository,
                            onLoginSuccess = {
                                isLoggedIn = true
                            }
                        )
                    }
                }
            }
        }
    }
}
