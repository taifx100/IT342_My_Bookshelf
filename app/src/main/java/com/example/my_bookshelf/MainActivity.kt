package com.example.my_bookshelf

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
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

            var selectedBook by remember {
                mutableStateOf<Book?>(null)
            }

            // Handle Android back button
            BackHandler(
                enabled = currentScreen == "details" ||
                        currentScreen == "signup"
            ) {
                when (currentScreen) {
                    "details" -> {
                        selectedBook = null
                        currentScreen = "home"
                    }

                    "signup" -> {
                        currentScreen = "login"
                    }
                }
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
                                    selectedBook = null
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
                                    selectedBook = null
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

                                onBookClick = { book ->
                                    selectedBook = book
                                    currentScreen = "details"
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
                                    selectedBook = null
                                    currentScreen = "login"
                                }
                            )
                        }

                        // BOOK DETAILS
                        "details" -> {

                            val book = selectedBook

                            if (book != null) {

                                BookDetailsScreen(
                                    book = book,

                                    onBack = {
                                        selectedBook = null
                                        currentScreen = "home"
                                    },

                                    onEdit = {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Edit Book page is coming soon!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },

                                    onDelete = {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Delete will be available after Firestore integration.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )

                            } else {
                                LaunchedEffect(Unit) {
                                    currentScreen = "home"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
