package com.example.my_bookshelf

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(
    repository: AuthRepository,
    onLoginSuccess: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "My Bookshelf",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(8.dp))
        Text("Your personal space for books!")
        Spacer(Modifier.height(32.dp))

        Text(
            if (isSignUp) "Create Account" else "Welcome Back",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(20.dp))

        if (isSignUp) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                message = ""

                when {
                    isSignUp && name.isBlank() ->
                        message = "Please enter your name."

                    email.isBlank() || password.isBlank() ->
                        message = "Please fill in all fields."

                    !Patterns.EMAIL_ADDRESS
                        .matcher(email.trim()).matches() ->
                        message = "Please enter a valid email."

                    isSignUp && password.length < 6 ->
                        message = "Password must be at least 6 characters."

                    else -> {
                        loading = true

                        val callback: (Boolean, String) -> Unit =
                            { success, result ->
                                loading = false
                                message = result
                                if (success) onLoginSuccess()
                            }

                        if (isSignUp) {
                            repository.signUp(
                                name.trim(),
                                email.trim(),
                                password,
                                callback
                            )
                        } else {
                            repository.signIn(
                                email.trim(),
                                password,
                                callback
                            )
                        }
                    }
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (loading) "Please wait..."
                else if (isSignUp) "Sign Up"
                else "Sign In"
            )
        }

        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = {
                isSignUp = !isSignUp
                password = ""
                message = ""
            },
            enabled = !loading
        ) {
            Text(
                if (isSignUp)
                    "Already have an account? Sign In"
                else
                    "Don't have an account? Sign Up"
            )
        }

        if (message.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(
                message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
