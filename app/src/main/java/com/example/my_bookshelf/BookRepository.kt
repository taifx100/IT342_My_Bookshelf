package com.example.my_bookshelf

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Transaction

// Talks to Firestore for ONE user: users/{userId}/books/{bookId}
class BookRepository(private val userId: String) {

    private val db = FirebaseFirestore.getInstance()

    // users/{userId}
    private val userDoc = db.collection("users").document(userId)

    // users/{userId}/books
    private val booksRef: CollectionReference = userDoc.collection("books")

    // Converts a Book to a map (all fields)
    private fun Book.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "title" to title,
        "author" to author,
        "year" to year,
        "category" to category,
        "description" to description,
        "coverColor" to coverColor,
        "coverUrl" to coverUrl,
        "createdAt" to createdAt
    )

    // Adds the 3 initial books ONE time for this user.
    // Uses a transaction, so the books and the flag are saved together or not at all.
    // The flag "booksInitialized" stays true even if the user deletes all the books.
    fun initializeBooksIfNeeded(
        onResult: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        db.runTransaction(Transaction.Function<Boolean> { transaction ->

            val userSnapshot = transaction.get(userDoc)

            if (userSnapshot.getBoolean("booksInitialized") == true) {
                // Already initialized before, do nothing
                false
            } else {
                val now = System.currentTimeMillis()

                // Fixed ids (book_1, book_2, book_3), so they can never be duplicated
                initialBooks.forEachIndexed { index, book ->
                    transaction.set(
                        booksRef.document(book.id),
                        book.copy(createdAt = now + index).toMap()
                    )
                }

                transaction.set(
                    userDoc,
                    mapOf(
                        "booksInitialized" to true,
                        "booksInitializedAt" to now
                    ),
                    SetOptions.merge()
                )

                true
            }
        })
            .addOnSuccessListener {
                onResult(true, "Library is ready.")
            }
            .addOnFailureListener {
                onResult(
                    false,
                    it.localizedMessage ?: "Could not prepare your library."
                )
            }
    }

    // Real-time listener for the books of this user.
    // Call remove() on the returned registration to stop it.
    fun listenToBooks(
        onBooks: (List<Book>) -> Unit,
        onError: (String) -> Unit
    ): ListenerRegistration {
        return booksRef.addSnapshotListener { snapshot, error ->

            if (error != null) {
                onError(error.localizedMessage ?: "Could not load your books.")
                return@addSnapshotListener
            }

            if (snapshot == null) return@addSnapshotListener

            val books = snapshot.documents
                .mapNotNull { doc ->
                    // The document id is always the book id
                    doc.toObject(Book::class.java)?.copy(id = doc.id)
                }
                .sortedBy { it.createdAt }

            onBooks(books)
        }
    }

    // Create: saves a new book using its own id
    fun addBook(
        book: Book,
        onResult: (Boolean, String) -> Unit
    ) {
        if (book.id.isBlank()) {
            onResult(false, "Could not save the book.")
            return
        }

        val bookToSave = if (book.createdAt == 0L) {
            book.copy(createdAt = System.currentTimeMillis())
        } else {
            book
        }

        booksRef.document(bookToSave.id)
            .set(bookToSave.toMap())
            .addOnSuccessListener {
                onResult(true, "Book saved.")
            }
            .addOnFailureListener {
                onResult(
                    false,
                    it.localizedMessage ?: "Could not save the book."
                )
            }
    }

    // Update: changes the same document (id and createdAt are not changed).
    // If the document does not exist anymore, it fails instead of creating a new book.
    fun updateBook(
        book: Book,
        onResult: (Boolean, String) -> Unit
    ) {
        if (book.id.isBlank()) {
            onResult(false, "Could not update the book.")
            return
        }

        val changes = mapOf(
            "title" to book.title,
            "author" to book.author,
            "year" to book.year,
            "category" to book.category,
            "description" to book.description,
            "coverColor" to book.coverColor,
            "coverUrl" to book.coverUrl
        )

        booksRef.document(book.id)
            .update(changes)
            .addOnSuccessListener {
                onResult(true, "Book updated.")
            }
            .addOnFailureListener {
                onResult(
                    false,
                    it.localizedMessage ?: "Could not update the book."
                )
            }
    }

    // Delete: removes the book document
    fun deleteBook(
        bookId: String,
        onResult: (Boolean, String) -> Unit
    ) {
        if (bookId.isBlank()) {
            onResult(false, "Could not delete the book.")
            return
        }

        booksRef.document(bookId)
            .delete()
            .addOnSuccessListener {
                onResult(true, "Book deleted.")
            }
            .addOnFailureListener {
                onResult(
                    false,
                    it.localizedMessage ?: "Could not delete the book."
                )
            }
    }
}
