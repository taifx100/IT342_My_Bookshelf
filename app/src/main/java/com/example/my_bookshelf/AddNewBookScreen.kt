package com.example.my_bookshelf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
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
    var coverColor by remember { mutableStateOf("sand") }
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
                .imePadding()
                .padding(horizontal = 22.dp)
                .padding(top = 48.dp, bottom = 36.dp)
        ) {
            // Header: back circle + title
            BookFormHeader(title = "Add a book", onBack = onCancel)

            Spacer(Modifier.height(22.dp))

            // Cover preview + color dots
            CoverPickerRow(
                title = title,
                coverColor = coverColor,
                onColorSelected = { coverColor = it }
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
                verticalAlignment = Alignment.Top
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

            // Category comes BEFORE the description
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

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = error,
                    color = ErrorRed,
                    fontFamily = DMSansFont,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(22.dp))

            BookFormButton(
                text = "Save book",
                onClick = {

                    // Remove invisible formatting characters from the year
                    val cleanYear = year
                        .filterNot { it.category == CharCategory.FORMAT }
                        .trim()

                    val parsedYear = cleanYear.toIntOrNull()

                    // Validate each required field separately
                    if (title.isBlank()) {

                        error = "Please enter the book title."

                    } else if (author.isBlank()) {

                        error = "Please enter the author name."

                    } else if (parsedYear == null || parsedYear <= 0) {

                        error = "Please enter a valid publication year."

                    } else {

                        // Clear the previous validation error
                        error = ""

                        onSave(
                            Book(
                                id = UUID.randomUUID().toString(),
                                title = title.trim(),
                                author = author.trim(),
                                year = parsedYear,
                                category = category.trim(),
                                description = description.trim(),
                                coverColor = coverColor,
                                createdAt = System.currentTimeMillis()
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

// ───────────── Shared by Add and Edit (same package) ─────────────

/** White circle back button (46dp) + screen title. */
@Composable
fun BookFormHeader(title: String, onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(PureWhite)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center
        ) {
            Canvas(Modifier.size(18.dp)) {
                val stroke = 2.5.dp.toPx()
                drawLine(
                    Coffee,
                    Offset(size.width * 0.65f, size.height * 0.1f),
                    Offset(size.width * 0.25f, size.height * 0.5f),
                    stroke, StrokeCap.Round
                )
                drawLine(
                    Coffee,
                    Offset(size.width * 0.25f, size.height * 0.5f),
                    Offset(size.width * 0.65f, size.height * 0.9f),
                    stroke, StrokeCap.Round
                )
            }
        }

        Spacer(Modifier.width(14.dp))

        Text(
            text = title,
            fontFamily = FrauncesFont,
            fontWeight = FontWeight.Bold,
            fontSize = 27.sp,
            color = Coffee
        )
    }
}

/** Small cover preview (74x104) + label + color dots (dots only if onColorSelected is given). */
@Composable
fun CoverPickerRow(
    title: String,
    coverColor: String,
    onColorSelected: ((String) -> Unit)? = null
) {
    val coverColors = coverGradientColors(coverColor)
    val titleColor = coverTitleColor(coverColor)
    val coverShape = RoundedCornerShape(
        topStart = 14.dp, topEnd = 6.dp,
        bottomEnd = 6.dp, bottomStart = 14.dp
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(width = 74.dp, height = 104.dp)
                .shadow(8.dp, coverShape)
                .clip(coverShape)
                .background(Brush.verticalGradient(coverColors))
        ) {
            CoverDecoration(
                coverType = coverColor,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(57.dp)
            )

            Text(
                text = title.ifBlank { "Title" },
                fontFamily = AmiriFont,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                lineHeight = 13.sp,
                color = titleColor,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 6.dp, end = 10.dp, bottom = 7.dp)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(5.dp)
                    .background(SpineShade)
            )
        }

        Spacer(Modifier.width(16.dp))

        // weight(1f): the column never goes outside the screen width
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Book cover",
                fontFamily = DMSansFont,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Coffee
            )

            Text(
                text = if (onColorSelected != null) "Pick a color for your book"
                else "Your book cover",
                fontFamily = DMSansFont,
                fontSize = 13.sp,
                color = TextMuted
            )

            Spacer(Modifier.height(10.dp))

            // Color dots (only when the screen allows choosing a color)
            if (onColorSelected != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    coverColorOptions.forEach { name ->
                        val selected = name == coverColor

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .then(
                                    if (selected) Modifier
                                        .border(2.dp, Coffee, CircleShape)
                                        .padding(3.dp)
                                    else Modifier
                                )
                                .clip(CircleShape)
                                .background(coverDotColor(name))
                                .clickable { onColorSelected(name) }
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = coverDisplayName(coverColor),
                    fontFamily = DMSansFont,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }
    }
}

/** Brown gradient button (54dp, 18dp corners). */
@Composable
fun BookFormButton(text: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(18.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .shadow(6.dp, shape)
            .clip(shape)
            .background(
                Brush.linearGradient(
                    listOf(MochaLight, MochaDark)
                )
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = DMSansFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = PureWhite
        )
    }
}

/** Light cancel button: same width, height and corners as the save button. */
@Composable
fun BookFormCancelButton(onClick: () -> Unit) {
    val shape = RoundedCornerShape(18.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(shape)
            .background(TileBackground)
            .border(2.dp, MochaLight, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Cancel",
            fontFamily = DMSansFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = LinkBrown
        )
    }
}

@Composable
fun AddBookField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    numeric: Boolean = false,
    multiline: Boolean = false
) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)

    Column(modifier) {
        Text(
            text = label,
            fontFamily = DMSansFont,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Coffee
        )

        Spacer(Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (multiline) 92.dp else 50.dp)
                .clip(shape)
                .background(PureWhite)
                .border(
                    width = if (focused) 2.dp else 1.5.dp,
                    color = if (focused) MochaDark else FieldBorder,
                    shape = shape
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = if (multiline) 12.dp else 0.dp
                ),
            contentAlignment = if (multiline)
                Alignment.TopStart else Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = hint,
                    fontFamily = DMSansFont,
                    fontSize = 15.sp,
                    color = TextMuted
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onChange,
                singleLine = !multiline,
                textStyle = TextStyle(
                    fontFamily = CairoFont,
                    fontSize = 15.sp,
                    color = Coffee,
                    textDirection = TextDirection.Content
                ),
                cursorBrush = SolidColor(MochaDark),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (numeric)
                        KeyboardType.Number else KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focused = it.isFocused }
            )
        }
    }
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