package com.example.my_bookshelf

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    userName: String,
    onMyBooksClick: () -> Unit,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "My Bookshelf",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(24.dp))

        Text(
            "Hello, $userName!",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(12.dp))
        Text("Welcome to your personal library.")
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onMyBooksClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("My Books")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Out")
        }
    }
}
