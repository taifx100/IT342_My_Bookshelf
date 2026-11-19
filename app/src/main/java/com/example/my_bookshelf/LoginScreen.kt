package com.example.my_bookshelf

import android.util.Patterns
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_bookshelf.ui.theme.*

@Composable
fun LoginScreen(
    repository: AuthRepository,
    onLoginSuccess: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
    ) {
        val screenWidth = maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Latte gradient header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(LatteLight, Latte)
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // App logo
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(MochaDark),
                        contentAlignment = Alignment.Center
                    ) {
                        BookLogo()
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "My Bookshelf",
                        fontFamily = FrauncesFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        color = Coffee
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Every story you love, in one place.",
                        fontFamily = DMSansFont,
                        fontSize = 15.sp,
                        color = TextSoft,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    LoginShelfIllustration(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                    )
                }
            }

            // White login card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-26).dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(
                            topStart = 36.dp,
                            topEnd = 36.dp
                        )
                    )
                    .background(
                        color = PureWhite,
                        shape = RoundedCornerShape(
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
                    text = "Welcome back",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Coffee
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Log in to open your shelf.",
                    fontFamily = DMSansFont,
                    fontSize = 14.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Email",
                    fontFamily = DMSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Coffee
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    },
                    placeholder = {
                        Text(
                            "you@example.com",
                            color = TextMuted,
                            fontFamily = DMSansFont
                        )
                    },
                    leadingIcon = {
                        Text(
                            text = "✉",
                            color = TextMuted,
                            fontSize = 20.sp
                        )
                    },
                    singleLine = true,
                    enabled = !loading,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MochaDark,
                        unfocusedBorderColor = FieldBorder,
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedTextColor = Coffee,
                        unfocusedTextColor = Coffee
                    ),
                    textStyle = TextStyle(
                        fontFamily = DMSansFont,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Password",
                    fontFamily = DMSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Coffee
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    placeholder = {
                        Text(
                            "Enter your password",
                            color = TextMuted,
                            fontFamily = DMSansFont
                        )
                    },
                    leadingIcon = {
                        Text(
                            text = "●",
                            color = TextMuted,
                            fontSize = 18.sp
                        )
                    },
                    trailingIcon = {
                        TextButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Text(
                                text = if (passwordVisible) "Hide" else "Show",
                                fontFamily = DMSansFont,
                                fontSize = 12.sp,
                                color = MochaDark
                            )
                        }
                    },
                    visualTransformation =
                        if (passwordVisible) {
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MochaDark,
                        unfocusedBorderColor = FieldBorder,
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedTextColor = Coffee,
                        unfocusedTextColor = Coffee
                    ),
                    textStyle = TextStyle(
                        fontFamily = DMSansFont,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                )

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = errorMessage,
                        color = ErrorRed,
                        fontFamily = DMSansFont,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                            focusManager.clearFocus()
                            errorMessage = ""

                            when {
                                email.isBlank() ||
                                        password.isBlank() -> {
                                    errorMessage =
                                        "Please fill in all fields."
                                }

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

                                else -> {
                                    loading = true

                                    repository.signIn(
                                        email = email.trim(),
                                        password = password
                                    ) { success, message ->
                                        loading = false

                                        if (success) {
                                            onLoginSuccess()
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
                            text = "Log in",
                            fontFamily = DMSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = PureWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "New here? ",
                        fontFamily = DMSansFont,
                        fontSize = 14.sp,
                        color = TextMuted
                    )

                    Text(
                        text = "Create an account",
                        fontFamily = DMSansFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = LinkBrown,
                        modifier = Modifier.clickable(
                            enabled = !loading
                        ) {
                            onCreateAccountClick()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BookLogo() {
    Canvas(modifier = Modifier.size(38.dp)) {
        val w = size.width
        val h = size.height

        val leftPage = Path().apply {
            moveTo(w * 0.50f, h * 0.25f)
            quadraticTo(
                w * 0.27f, h * 0.12f,
                w * 0.10f, h * 0.25f
            )
            lineTo(w * 0.10f, h * 0.76f)
            quadraticTo(
                w * 0.30f, h * 0.66f,
                w * 0.50f, h * 0.83f
            )
            close()
        }

        val rightPage = Path().apply {
            moveTo(w * 0.50f, h * 0.25f)
            quadraticTo(
                w * 0.73f, h * 0.12f,
                w * 0.90f, h * 0.25f
            )
            lineTo(w * 0.90f, h * 0.76f)
            quadraticTo(
                w * 0.70f, h * 0.66f,
                w * 0.50f, h * 0.83f
            )
            close()
        }

        drawPath(
            leftPage,
            color = Paper,
            style = Stroke(width = 2.5.dp.toPx())
        )

        drawPath(
            rightPage,
            color = Paper,
            style = Stroke(width = 2.5.dp.toPx())
        )
    }
}

@Composable
private fun LoginShelfIllustration(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Soft sun
        drawCircle(
            color = Color(0x99FBF3E6),
            radius = h * 0.32f,
            center = Offset(w * 0.76f, h * 0.40f)
        )

        // Shelf
        drawRoundRect(
            color = ShelfTop,
            topLeft = Offset(w * 0.12f, h * 0.78f),
            size = Size(w * 0.76f, h * 0.09f)
        )

        drawRect(
            color = ShelfBottom,
            topLeft = Offset(w * 0.15f, h * 0.87f),
            size = Size(w * 0.70f, h * 0.035f)
        )

        // Books
        val bookColors = listOf(
            TreeGreen,
            CoverSand,
            CoverWine,
            Coffee,
            MochaDark
        )

        val bookHeights = listOf(
            0.51f, 0.62f, 0.47f, 0.57f, 0.43f
        )

        bookColors.forEachIndexed { index, color ->
            val x = w * (0.23f + index * 0.085f)
            val bookHeight = h * bookHeights[index]

            drawRoundRect(
                color = color,
                topLeft = Offset(
                    x,
                    h * 0.78f - bookHeight
                ),
                size = Size(w * 0.067f, bookHeight)
            )

            drawRect(
                color = Color.White.copy(alpha = 0.35f),
                topLeft = Offset(
                    x + w * 0.015f,
                    h * 0.78f - bookHeight + h * 0.08f
                ),
                size = Size(w * 0.006f, h * 0.18f)
            )
        }

        // Plant pot
        val potX = w * 0.73f

        val pot = Path().apply {
            moveTo(potX - w * 0.05f, h * 0.59f)
            lineTo(potX + w * 0.05f, h * 0.59f)
            lineTo(potX + w * 0.035f, h * 0.78f)
            lineTo(potX - w * 0.035f, h * 0.78f)
            close()
        }

        drawPath(pot, color = MochaDark)

        drawLine(
            color = TreeGreen,
            start = Offset(potX, h * 0.60f),
            end = Offset(potX, h * 0.25f),
            strokeWidth = 3.dp.toPx()
        )

        drawCircle(
            color = TreeGreen,
            radius = h * 0.075f,
            center = Offset(
                potX - w * 0.035f,
                h * 0.34f
            )
        )

        drawCircle(
            color = HillsGreen,
            radius = h * 0.07f,
            center = Offset(
                potX + w * 0.04f,
                h * 0.43f
            )
        )

        drawCircle(
            color = TreeGreen,
            radius = h * 0.065f,
            center = Offset(
                potX + w * 0.015f,
                h * 0.24f
            )
        )
    }
}

