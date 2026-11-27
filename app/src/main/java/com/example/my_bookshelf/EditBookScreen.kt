package com.example.my_bookshelf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.my_bookshelf.ui.theme.*

@Composable
fun EditBookScreen(
    book: Book,
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember(book.id) { mutableStateOf(book.title) }
    var author by remember(book.id) { mutableStateOf(book.author) }
    var year by remember(book.id) { mutableStateOf(book.year.toString()) }
    var category by remember(book.id) { mutableStateOf(book.category) }
    var description by remember(book.id) { mutableStateOf(book.description) }

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
                .imePadding()
                .padding(horizontal = 22.dp)
                .padding(top = 48.dp, bottom = 36.dp)
        ) {
            BookFormHeader(title = "Edit book", onBack = onCancel)

            Spacer(Modifier.height(22.dp))

            // Preview of the current cover (color is not editable here)
            CoverPickerRow(
                title = title,
                coverColor = book.coverColor
            )

            Spacer(Modifier.height(18.dp))

            AddBookField(
                label = "Book title",
                value = title,
                onChange = { title = it },
                hint = "e.g., Harry Potter"
            )

            Spacer(Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = androidx.compose.ui.Alignment.Top
            ) {
                AddBookField(
                    label = "Author",
                    value = author,
                    onChange = { author = it },
                    hint = "e.g., J.K. Rowling",
                    modifier = Modifier.weight(1f)
                )

                AddBookField(
                    label = "Year",
                    value = year,
                    onChange = { year = it },
                    hint = "e.g., 1997",
                    numeric = true,
                    modifier = Modifier.width(112.dp)
                )
            }

            Spacer(Modifier.height(14.dp))

            AddBookField(
                label = "Category",
                value = category,
                onChange = { category = it },
                hint = "e.g., Fiction, Fantasy"
            )

            Spacer(Modifier.height(14.dp))

            AddBookField(
                label = "Short description",
                value = description,
                onChange = { description = it },
                hint = "Write a few lines about the book…",
                multiline = true
            )

            Spacer(Modifier.height(22.dp))

            BookFormButton(
                text = "Save changes",
                onClick = {
                    val updatedYear = year.toIntOrNull()

                    if (
                        title.isNotBlank() &&
                        author.isNotBlank() &&
                        updatedYear != null
                    ) {
                        onSave(
                            book.copy(
                                title = title,
                                author = author,
                                year = updatedYear,
                                category = category,
                                description = description
                            )
                        )
                    }
                }
            )

            Spacer(Modifier.height(12.dp))

            // Cancel button (uses the original onCancel)
            BookFormCancelButton(onClick = onCancel)
        }
    }
}