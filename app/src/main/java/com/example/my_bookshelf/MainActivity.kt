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

            // Save the selected book
            var selectedBook by remember {
                mutableStateOf<Book?>(null)
            }

            val books = remember {
                mutableStateListOf<Book>().apply {
                    addAll(initialBooks)
                }
            }

            // Back from Add Book to Home
            BackHandler(enabled = currentScreen == "addBook") {
                currentScreen = "home"
            }

            // Back from Sign Up to Login
            BackHandler(enabled = currentScreen == "signup") {
                currentScreen = "login"
            }

            // Back from Details to Home
            BackHandler(enabled = currentScreen == "details") {
                currentScreen = "home"
            }

            // Back from Edit to Details
            BackHandler(enabled = currentScreen == "edit") {
                currentScreen = "details"
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
                                books = books,

                                onBookClick = { book ->
                                    selectedBook = book
                                    currentScreen = "details"
                                },

                                onAddBookClick = {
                                    currentScreen = "addBook"
                                },

                                onSignOut = {
                                    repository.signOut()
                                    currentScreen = "login"
                                }
                            )
                        }

                        // ADD NEW BOOK
                        "addBook" -> {
                            AddNewBookScreen(
                                onSave = { book ->
                                    books.add(book)
                                    currentScreen = "home"
                                },
                                onCancel = {
                                    currentScreen = "home"
                                }
                            )
                        }

                        // BOOK DETAILS
                        "details" -> {
                            selectedBook?.let { book ->

                                BookDetailsScreen(
                                    book = book,

                                    onBack = {
                                        currentScreen = "home"
                                    },

                                    onEdit = {
                                        currentScreen = "edit"
                                    },

                                    onDelete = {
                                        books.removeAll { it.id == book.id }
                                        selectedBook = null
                                        currentScreen = "home"
                                    }
                                )
                            }
                        }

                        // EDIT BOOK
                        "edit" -> {
                            selectedBook?.let { book ->

                                EditBookScreen(
                                    book = book,

                                    onSave = { updatedBook ->

                                        val index = books.indexOfFirst {
                                            it.id == updatedBook.id
                                        }

                                        if (index != -1) {
                                            books[index] = updatedBook
                                        }

                                        selectedBook = updatedBook
                                        currentScreen = "details"
                                    },

                                    onCancel = {
                                        currentScreen = "details"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
