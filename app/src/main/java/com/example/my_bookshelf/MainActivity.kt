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
import androidx.compose.ui.platform.LocalContext
import com.example.my_bookshelf.ui.theme.My_BookshelfTheme
import com.google.firebase.firestore.ListenerRegistration

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AuthRepository()

        setContent {

            val context = LocalContext.current

            // Shows a short error message
            fun showMessage(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }

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

            // The books of the current user, they come from Firestore
            var books by remember {
                mutableStateOf<List<Book>>(emptyList())
            }

            // true while a save / update / delete is running (blocks repeated clicks)
            var isBusy by remember {
                mutableStateOf(false)
            }

            // Only the signed-in user has an id (login and signup screens have none)
            val userId =
                if (currentScreen == "login" || currentScreen == "signup") {
                    null
                } else {
                    repository.getCurrentUser()?.uid
                }

            // One repository per user
            val bookRepository = remember(userId) {
                userId?.let { BookRepository(it) }
            }

            // Starts the real-time listener for this user and stops it
            // when the user signs out or a different user signs in.
            DisposableEffect(userId) {

                // Never show books (or a selected book) of a previous session
                books = emptyList()
                selectedBook = null

                var registration: ListenerRegistration? = null

                if (bookRepository != null) {

                    // Adds the 3 initial books only once for this user
                    bookRepository.initializeBooksIfNeeded { success, message ->
                        if (!success) showMessage(message)
                    }

                    registration = bookRepository.listenToBooks(
                        onBooks = { list ->
                            books = list

                            // Keep the open book up to date
                            selectedBook = selectedBook?.let { current ->
                                list.firstOrNull { it.id == current.id } ?: current
                            }
                        },
                        onError = { message ->
                            showMessage(message)
                        }
                    )
                }

                onDispose {
                    registration?.remove()
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

                                    // Clear the session data
                                    books = emptyList()
                                    selectedBook = null
                                    isBusy = false

                                    currentScreen = "login"
                                }
                            )
                        }

                        // ADD NEW BOOK
                        "addBook" -> {
                            AddNewBookScreen(
                                onSave = { book ->
                                    if (!isBusy && bookRepository != null) {
                                        isBusy = true

                                        // Go to Home only after Firestore confirms the save
                                        bookRepository.addBook(book) { success, message ->
                                            isBusy = false

                                            if (success) {
                                                if (currentScreen == "addBook") {
                                                    currentScreen = "home"
                                                }
                                            } else {
                                                showMessage(message)
                                            }
                                        }
                                    }
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
                                        if (!isBusy && bookRepository != null) {
                                            isBusy = true

                                            // Go to Home only after Firestore confirms the delete
                                            bookRepository.deleteBook(book.id) { success, message ->
                                                isBusy = false

                                                if (success) {
                                                    selectedBook = null
                                                    currentScreen = "home"
                                                } else {
                                                    showMessage(message)
                                                }
                                            }
                                        }
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
                                        if (!isBusy && bookRepository != null) {
                                            isBusy = true

                                            // Go back to Details only after Firestore confirms the update
                                            bookRepository.updateBook(updatedBook) { success, message ->
                                                isBusy = false

                                                if (success) {
                                                    selectedBook = updatedBook

                                                    if (currentScreen == "edit") {
                                                        currentScreen = "details"
                                                    }
                                                } else {
                                                    showMessage(message)
                                                }
                                            }
                                        }
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