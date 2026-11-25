package com.example.my_bookshelf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_bookshelf.ui.theme.*
import java.util.UUID

@Composable
fun AddNewBookScreen(
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
    ) {
        Canvas(
            Modifier
                .size(300.dp)
                .offset(x = (-70).dp, y = (-100).dp)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        LatteLight.copy(alpha = 0.6f),
                        Latte.copy(alpha = 0.15f),
                        Color.Transparent
                    )
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 40.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            TextButton(onClick = onCancel) {
                Text(
                    "< Back",
                    fontFamily = DMSansFont,
                    color = LinkBrown
                )
            }

            Text(
                text = "Add New Book",
                fontFamily = FrauncesFont,
                fontWeight = FontWeight.Bold,
                fontSize = 31.sp,
                color = Coffee
            )

            Text(
                text = "Add a book to your shelf",
                fontFamily = DMSansFont,
                fontSize = 14.sp,
                color = TextSoft
            )

            AddBookField(
                title,
                { title = it },
                "Book Title"
            )

            AddBookField(
                author,
                { author = it },
                "Author"
            )

            AddBookField(
                year,
                { year = it },
                "Publication Year",
                numeric = true
            )

            AddBookField(
                category,
                { category = it },
                "Category"
            )

            AddBookField(
                description,
                { description = it },
                "Description",
                multiline = true
            )

            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = ErrorRed,
                    fontFamily = DMSansFont
                )
            }

            Button(
                onClick = {
                    val parsedYear = year.trim().toIntOrNull()

                    if (
                        title.isBlank() ||
                        author.isBlank() ||
                        parsedYear == null ||
                        parsedYear <= 0
                    ) {
                        error = "Enter a title, author and valid publication year."
                    } else {
                        onSave(
                            Book(
                                id = UUID.randomUUID().toString(),
                                title = title.trim(),
                                author = author.trim(),
                                year = parsedYear,
                                category = category.trim(),
                                description = description.trim(),
                                coverColor = "sand",
                                createdAt = System.currentTimeMillis()
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MochaDark,
                    contentColor = PureWhite
                )
            ) {
                Text(
                    text = "Save Book",
                    fontFamily = DMSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(
                    text = "Cancel",
                    fontFamily = DMSansFont,
                    color = LinkBrown
                )
            }
        }
    }
}

@Composable
private fun AddBookField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    numeric: Boolean = false,
    multiline: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = {
            Text(
                label,
                fontFamily = DMSansFont
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        singleLine = !multiline,
        minLines = if (multiline) 4 else 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (numeric)
                KeyboardType.Number
            else
                KeyboardType.Text
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = PureWhite,
            unfocusedContainerColor = PureWhite,
            focusedBorderColor = MochaDark,
            unfocusedBorderColor = FieldBorder
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun AddNewBookPreview() {
    My_BookshelfTheme {
        AddNewBookScreen(
            onSave = {},
            onCancel = {}
        )
    }
}