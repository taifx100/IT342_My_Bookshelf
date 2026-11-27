# 📚 My Bookshelf

### Your personal space for books, stories, and discoveries.

My Bookshelf is a native Android application designed to help readers build and manage their own personal digital library. Built with Kotlin, Jetpack Compose, and Firebase, the app combines a warm, elegant design with real-time cloud data management.

## ✨ Features

- Secure user registration and login.
- A private, personalized bookshelf for each user.
- Three default books to start your reading journey.
- Add, view, edit, and delete books (CRUD operations).
- Customizable book covers with five color themes.
- Detailed book information, including title, author, publication year, category, and description.
- Real-time data synchronization with Cloud Firestore.
- Input validation and deletion confirmation.
- A warm and elegant user interface inspired by the cozy atmosphere of bookshelves.

## 🛠️ Technologies Used

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)

- **Kotlin:** Application development.
- **Jetpack Compose:** Modern Android user interface.
- **Firebase Authentication:** Secure user authentication.
- **Cloud Firestore:** Cloud storage and real-time CRUD operations.
- **Android Studio:** Development environment.

## ☁️ Database Structure

Each authenticated user has an independent bookshelf stored in Cloud Firestore.

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

Firebase security rules ensure that users can only access and manage their own books.

## 🎓 Academic Project

Developed as a group project for:

- **Course:** Mobile Cloud Computing
- **Course Code:** IT342
- **Major:** Information Technology
- **Year:** 2026

---

🤎 A little space for every story worth keeping.
