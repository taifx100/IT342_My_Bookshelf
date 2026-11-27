<div align="center">

# 📚 My Bookshelf

### A little space for every story worth keeping.

*Your personal digital bookshelf, where every book has a place.*

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)

![Platform](https://img.shields.io/badge/Platform-Android-8B6F47?style=flat-square)
![Database](https://img.shields.io/badge/Database-Cloud_Firestore-C4A484?style=flat-square)
![Course](https://img.shields.io/badge/Course-IT342-6B705C?style=flat-square)

</div>

---

## ☕ About the Project

**My Bookshelf** is a native Android application designed to help readers create, organize, and manage their personal digital libraries.

Built with **Kotlin**, **Jetpack Compose**, and **Firebase**, the application brings together a warm, book-inspired design, secure authentication, and real-time cloud storage.

Every reader has their own bookshelf, their own collection, and their own little world of stories.

## ✨ Key Features

| Feature | Description |
| :--- | :--- |
| 🔐 **User Authentication** | Secure account registration and login. |
| 📚 **Personal Bookshelf** | An independent book collection for every user. |
| ➕ **Add Books** | Expand your library with new books. |
| 📖 **View Book Details** | Explore complete information about each book. |
| ✏️ **Edit Books** | Update existing book information. |
| 🗑️ **Delete Books** | Remove books with a confirmation dialog. |
| 🎨 **Custom Book Covers** | Personalize books with five beautiful cover themes. |
| 🎁 **Default Collection** | Start your library with three preloaded books. |
| ☁️ **Cloud Storage** | Keep your book collection saved across sessions. |
| ⚡ **Real-Time Updates** | Instantly synchronize changes with Firestore. |
| 🛡️ **Input Validation** | Clear validation and user-friendly error messages. |

## 🎨 Design & Aesthetics

Inspired by cozy libraries, warm coffee tones, and the beauty of classic book covers.

The application offers five unique book cover themes:

<div align="center">

![Sand](https://img.shields.io/badge/Sand-D8B98C?style=for-the-badge)
![Sage](https://img.shields.io/badge/Sage-87986A?style=for-the-badge)
![Navy](https://img.shields.io/badge/Navy-34495E?style=for-the-badge)
![Wine](https://img.shields.io/badge/Wine-722F37?style=for-the-badge)
![Mocha](https://img.shields.io/badge/Mocha-795548?style=for-the-badge)

</div>

Each theme features its own color palette and decorative illustration, giving every book a unique personality.

## 🛠️ Technologies Used

| Technology | Purpose |
| :--- | :--- |
| **Kotlin** | Native Android application development |
| **Jetpack Compose** | Modern and interactive user interfaces |
| **Firebase Authentication** | User registration and authentication |
| **Cloud Firestore** | Cloud database and real-time CRUD operations |
| **Android Studio** | Application development and testing |
| **Git & GitHub** | Version control and team collaboration |

## ☁️ Firebase Integration

My Bookshelf uses Firebase to provide secure authentication, persistent cloud storage, and real-time book management.

Each authenticated user has a private bookshelf associated with their unique user ID.

### CRUD Operations

| Operation | Functionality |
| :--- | :--- |
| 🟢 **Create** | Add new books to the cloud database. |
| 🔵 **Read** | Retrieve and display the user's book collection. |
| 🟠 **Update** | Modify existing book information. |
| 🔴 **Delete** | Remove books from the user's collection. |

### Database Structure

    Cloud Firestore
    └── users
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

Firebase security rules ensure that each user can only access and manage their own book collection.

## 🎓 Academic Information

| | |
| :--- | :--- |
| 📖 **Project Name** | My Bookshelf |
| ☁️ **Course** | Mobile Cloud Computing |
| 🏷️ **Course Code** | IT342 |
| 💻 **Major** | Information Technology |
| 👥 **Project Type** | Group Project |
| 📅 **Year** | 2026 |

---

<div align="center">

### 🤎 Every book belongs somewhere 🤎

**Designed for readers · Built with Kotlin · Powered by Firebase**

</div>
