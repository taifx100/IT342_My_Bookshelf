package com.example.my_bookshelf

import android.util.Patterns
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_bookshelf.ui.theme.*

@Composable
fun SignUpScreen(
    repository: AuthRepository,
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
            .verticalScroll(rememberScrollState())
    ) {
        // Top latte area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(LatteLight, Latte)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MochaDark),
                    contentAlignment = Alignment.Center
                ) {
                    SignUpBookLogo()
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "My Bookshelf",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Coffee
                )

                Spacer(Modifier.height(14.dp))

                SignUpShelfIllustration(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-16).dp)
                .shadow(
                    elevation = 7.dp,
                    shape = RoundedCornerShape(
                        topStart = 36.dp,
                        topEnd = 36.dp
                    )
                )
                .background(
                    PureWhite,
                    RoundedCornerShape(
                        topStart = 36.dp,
                        topEnd = 36.dp
                    )
                )
                .padding(
                    start = 28.dp,
                    end = 28.dp,
                    top = 28.dp,
                    bottom = 32.dp
                )
        ) {
            Text(
                text = "Create account",
                fontFamily = FrauncesFont,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Coffee
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "It only takes a minute.",
                fontFamily = DMSansFont,
                fontSize = 14.sp,
                color = TextMuted
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Email",
                fontFamily = DMSansFont,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Coffee
            )

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("you@example.com")
                },
                leadingIcon = {
                    Text("✉", color = TextMuted)
                },
                singleLine = true,
                enabled = !loading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                shape = RoundedCornerShape(16.dp),
                colors = signUpFieldColors()
            )

            Spacer(Modifier.height(14.dp))

            Text(
                text = "Password",
                fontFamily = DMSansFont,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Coffee
            )

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("At least 6 characters")
                },
                leadingIcon = {
                    Text("●", color = TextMuted)
                },
                trailingIcon = {
                    TextButton(
                        onClick = { showPassword = !showPassword }
                    ) {
                        Text(
                            if (showPassword) "Hide" else "Show",
                            color = MochaDark,
                            fontSize = 12.sp
                        )
                    }
                },
                visualTransformation =
                    if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                enabled = !loading,
                shape = RoundedCornerShape(16.dp),
                colors = signUpFieldColors()
            )

            Spacer(Modifier.height(14.dp))

            Text(
                text = "Confirm password",
                fontFamily = DMSansFont,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Coffee
            )

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Repeat your password")
                },
                leadingIcon = {
                    Text("●", color = TextMuted)
                },
                trailingIcon = {
                    TextButton(
                        onClick = { showConfirm = !showConfirm }
                    ) {
                        Text(
                            if (showConfirm) "Hide" else "Show",
                            color = MochaDark,
                            fontSize = 12.sp
                        )
                    }
                },
                visualTransformation =
                    if (showConfirm) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                enabled = !loading,
                shape = RoundedCornerShape(16.dp),
                colors = signUpFieldColors()
            )

            if (errorMessage.isNotBlank()) {
                Spacer(Modifier.height(12.dp))

                Text(
                    text = errorMessage,
                    color = ErrorRed,
                    fontFamily = DMSansFont,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(MochaLight, MochaDark)
                        )
                    )
                    .clickable(enabled = !loading) {
                        errorMessage = ""

                        when {
                            !Patterns.EMAIL_ADDRESS
                                .matcher(email.trim())
                                .matches() -> {
                                errorMessage =
                                    "Please enter a valid email."
                            }

                            password.length < 6 -> {
                                errorMessage =
                                    "Password must be at least 6 characters."
                            }

                            password != confirmPassword -> {
                                errorMessage =
                                    "Passwords do not match."
                            }

                            else -> {
                                loading = true

                                val defaultName =
                                    email.trim().substringBefore("@")

                                repository.signUp(
                                    name = defaultName,
                                    email = email.trim(),
                                    password = password
                                ) { success, message ->
                                    loading = false

                                    if (success) {
                                        onSignUpSuccess()
                                    } else {
                                        errorMessage = message
                                    }
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = PureWhite,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Sign up",
                        fontFamily = DMSansFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = PureWhite
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    fontFamily = DMSansFont,
                    fontSize = 13.sp,
                    color = TextMuted
                )

                Text(
                    text = "Log in",
                    fontFamily = DMSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = LinkBrown,
                    modifier = Modifier.clickable(
                        enabled = !loading
                    ) {
                        onLoginClick()
                    }
                )
            }
        }
    }
}

@Composable
private fun signUpFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MochaDark,
        unfocusedBorderColor = FieldBorder,
        focusedContainerColor = PureWhite,
        unfocusedContainerColor = PureWhite,
        focusedTextColor = Coffee,
        unfocusedTextColor = Coffee
    )

@Composable
private fun SignUpBookLogo() {
    Canvas(modifier = Modifier.size(34.dp)) {
        val w = size.width
        val h = size.height

        val left = Path().apply {
            moveTo(w * 0.5f, h * 0.22f)
            quadraticTo(
                w * 0.28f, h * 0.1f,
                w * 0.1f, h * 0.24f
            )
            lineTo(w * 0.1f, h * 0.76f)
            quadraticTo(
                w * 0.3f, h * 0.65f,
                w * 0.5f, h * 0.84f
            )
            close()
        }

        val right = Path().apply {
            moveTo(w * 0.5f, h * 0.22f)
            quadraticTo(
                w * 0.72f, h * 0.1f,
                w * 0.9f, h * 0.24f
            )
            lineTo(w * 0.9f, h * 0.76f)
            quadraticTo(
                w * 0.7f, h * 0.65f,
                w * 0.5f, h * 0.84f
            )
            close()
        }

        drawPath(
            path = left,
            color = Paper,
            style = Stroke(width = 2.3.dp.toPx())
        )

        drawPath(
            path = right,
            color = Paper,
            style = Stroke(width = 2.3.dp.toPx())
        )
    }
}

@Composable
private fun SignUpShelfIllustration(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Background sun
        drawCircle(
            color = Color(0x99FBF3E6),
            radius = h * 0.32f,
            center = Offset(w * 0.76f, h * 0.36f)
        )

        // Shelf
        drawRoundRect(
            color = ShelfTop,
            topLeft = Offset(w * 0.14f, h * 0.77f),
            size = Size(w * 0.72f, h * 0.09f)
        )

        drawRect(
            color = ShelfBottom,
            topLeft = Offset(w * 0.17f, h * 0.86f),
            size = Size(w * 0.66f, h * 0.035f)
        )

        // Standing books
        val bookColors = listOf(
            TreeGreen,
            CoverSand,
            CoverWine,
            Coffee,
            MochaDark
        )

        bookColors.forEachIndexed { index, color ->
            val bookHeight = h * (0.42f + (index % 3) * 0.07f)
            val x = w * (0.23f + index * 0.075f)

            drawRoundRect(
                color = color,
                topLeft = Offset(
                    x,
                    h * 0.77f - bookHeight
                ),
                size = Size(w * 0.058f, bookHeight)
            )
        }

        // Cup
        drawRoundRect(
            color = MochaDark,
            topLeft = Offset(w * 0.68f, h * 0.53f),
            size = Size(w * 0.09f, h * 0.23f)
        )

        drawCircle(
            color = Paper,
            radius = h * 0.07f,
            center = Offset(w * 0.79f, h * 0.64f),
            style = Stroke(width = 3.dp.toPx())
        )

        // Steam
        drawLine(
            color = Paper,
            start = Offset(w * 0.71f, h * 0.48f),
            end = Offset(w * 0.72f, h * 0.35f),
            strokeWidth = 2.dp.toPx()
        )

        drawLine(
            color = Paper,
            start = Offset(w * 0.75f, h * 0.48f),
            end = Offset(w * 0.76f, h * 0.33f),
            strokeWidth = 2.dp.toPx()
        )
    }
}

