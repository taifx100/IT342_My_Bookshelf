<div align="center">

# 📖 My Bookshelf

### A little space for every story worth keeping.

Your personal digital bookshelf, where every book has a place.

![Kotlin](https://img.shields.io/badge/Kotlin-8B6F47?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-A68A64?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-C4A484?style=for-the-badge&logo=firebase&logoColor=white)
![Android](https://img.shields.io/badge/Android-6B705C?style=for-the-badge&logo=android&logoColor=white)

</div>

---

## ☕ About the Project

**My Bookshelf** is a native Android application that allows users to create, organize, and manage their own personal book collections.

Built with Kotlin, Jetpack Compose, and Firebase, the application combines a cozy, book-inspired design with secure authentication and real-time cloud storage.

## ✦ Features

- **Personal Bookshelf** — An independent book collection for every user.
- **User Authentication** — Secure registration and login.
- **Book Management** — Add, view, edit, and delete books.
- **Custom Book Covers** — Five beautiful cover color options.
- **Book Details** — Title, author, publication year, category, and description.
- **Default Collection** — Three books to begin your personal library.
- **Cloud Synchronization** — Real-time updates and persistent data storage.
- **Input Validation** — Clear error messages and deletion confirmation.

## ✦ Technologies

| Technology | Purpose |
| :--- | :--- |
| Kotlin | Android application development |
| Jetpack Compose | User interface and design |
| Firebase Authentication | User registration and login |
| Cloud Firestore | Cloud storage and CRUD operations |
| Android Studio | Development environment |

## ✦ Database Structure

Each user has a private bookshelf stored in Cloud Firestore, with real-time synchronization and secure access.

    users
    └── userId
        ├── booksInitialized
        ├── booksInitializedAt
        └── books
            └── bookId
                ├── id
                ├── title
                ├── author
                ├── year
                ├── category
                ├── description
                ├── coverColor
                ├── coverUrl
                └── createdAt

Firebase security rules ensure that each user can only access and manage their own collection.

## ✦ Academic Information

| | |
| :--- | :--- |
| **Project** | My Bookshelf |
| **Course** | Mobile Cloud Computing |
| **Course Code** | IT342 |
| **Major** | Information Technology |
| **Year** | 2026 |
| **Project Type** | Group Project |

---

<div align="center">

### Every book belongs somewhere🤎

*Designed for readers. Built with Kotlin. Powered by Firebase.*

</div>
