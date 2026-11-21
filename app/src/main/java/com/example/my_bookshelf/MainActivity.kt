package com.example.my_bookshelf

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.my_bookshelf.ui.theme.My_BookshelfTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AuthRepository()

        setContent {

            var currentScreen by remember {
                mutableStateOf(
                    if (repository.getCurrentUser() != null) {
                        "home"
                    } else {
                        "login"
                    }
                )
            }

            // Temporary navigation until Shaden adds BookDetailsScreen
            BackHandler(
                enabled = currentScreen == "signup"
            ) {
                currentScreen = "login"
            }

            My_BookshelfTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (currentScreen) {

                        // LOGIN
                        "login" -> {
                            LoginScreen(
                                repository = repository,
                                onLoginSuccess = {
                                    currentScreen = "home"
                                },
                                onCreateAccountClick = {
                                    currentScreen = "signup"
                                }
                            )
                        }

                        // SIGN UP
                        "signup" -> {
                            SignUpScreen(
                                repository = repository,
                                onSignUpSuccess = {
                                    currentScreen = "home"
                                },
                                onLoginClick = {
                                    currentScreen = "login"
                                }
                            )
                        }

                        // HOME
                        "home" -> {

                            val user = repository.getCurrentUser()

                            val userName = user?.displayName
                                ?.takeIf { it.isNotBlank() }
                                ?: user?.email?.substringBefore("@")
                                ?: "Reader"

                            HomeScreen(
                                userName = userName,

                                onBookClick = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Book Details page is coming soon!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },

                                onAddBookClick = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Add Book page is coming soon!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },

                                onSignOut = {
                                    repository.signOut()
                                    currentScreen = "login"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
