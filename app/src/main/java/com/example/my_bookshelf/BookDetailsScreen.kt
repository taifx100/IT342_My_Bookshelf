
package com.example.my_bookshelf

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.my_bookshelf.ui.theme.*
import androidx.compose.ui.graphics.Color

@Composable
fun BookDetailsScreen(
    book: Book,
    onBack: () -> Unit,
    onEdit: () -> Unit = {},    // empty for now
    onDelete: () -> Unit = {}   // empty for now
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    // true if the cover image link failed to load,
    // then we show the colored cover instead
    var imageFailed by remember(book.coverUrl) { mutableStateOf(false) }
    val showImage = book.coverUrl.isNotBlank() && !imageFailed

    // coverColors changes based on the book cover color
    val coverColors = when (book.coverColor) {
        "sand" -> listOf(CoverSandLight, CoverSand)
        "navy" -> listOf(CoverNavyLight, CoverNavy)
        else -> listOf(CoverSageLight, CoverSage)
    }

    // titleColor changes based on the book cover color,
    // to be in contrast with it
    val titleColor =
        if (book.coverColor == "sand") Coffee
        else PureWhite

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                //.padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // At the top
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "<",
                        fontFamily = DMSansFont,
                        fontSize = 20.sp,
                        color = LinkBrown
                    )
                }

//                Text(
//                    text = "Book details",
//                    fontFamily = FrauncesFont,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    color = Coffee,
//                    modifier = Modifier.align(Alignment.Center)
//                )
            }

            Spacer(Modifier.height(24.dp))

            // Book cover box
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(200.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.verticalGradient(coverColors))
            ) {
                if (showImage) {
                    // real cover image from the link
                    AsyncImage(
                        model = book.coverUrl,
                        contentDescription = book.title,
                        contentScale = ContentScale.Crop,
                        onError = { imageFailed = true },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    //when no image: colored cover with the title
                    Text(
                        text = book.title,
                        fontFamily = AmiriFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        lineHeight = 32.sp,
                        color = titleColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(18.dp)
                    )
                }

                // thin dark strip on the right side of the cover, like a book spine
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .width(8.dp)
                        .background(SpineShade)
                )
            }

            Spacer(Modifier.height(25.dp))


            // "About this book" card
            Column(
                modifier = Modifier
                    .fillMaxWidth() // to fit all different device screens
                    .shadow(3.dp, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(60.dp)
            ) {
                // Book title
                Text(
                    text = book.title,
                    fontFamily = AmiriFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    lineHeight = 36.sp,
                    color = Coffee,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(6.dp))

                // Author name
                Text(
                    text = book.author,
                    fontFamily = CairoFont,
                    fontSize = 15.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )
                DetailsChip(text = book.year.toString(), backgroundColor = LatteLight, WarmBrown)

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Category",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    color = Coffee
                )
                // Two chips, one function
                // DetailsChip is reused for year and category
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DetailsChip(text = book.category, backgroundColor = TreeGreen, textColor = PureWhite )
                }
                Text(
                    text = "About this book",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    color = Coffee
                )

                Spacer(Modifier.height(14.dp))

                Text(
                    text = book.description,
                    fontFamily = CairoFont,
                    fontSize = 15.sp,
                    lineHeight = 28.sp,
                    color = TextSoft,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                )
                {    // Edit button
                    Button(
                        onClick = onEdit,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MochaDark,
                            contentColor = PureWhite
                        )
                    ) {
                        Text(
                            text = "✎ Edit",
                            fontFamily = DMSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    // Delete button
                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        border = BorderStroke(2.dp, ErrorRed),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
                    ) {
                        Text(
                            text = "🗑️ Delete",
                            fontFamily = DMSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
            }

            }

        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text("Delete this book?", fontFamily = FrauncesFont, color = Coffee)
            },
            text = {
                Text(
                    "Are you sure you want to remove this book from your shelf?",
                    fontFamily = DMSansFont,
                    color = TextSoft
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) { Text("Delete", color = ErrorRed) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = LinkBrown)
                }
            },
            containerColor = PureWhite,
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Composable
private fun DetailsChip(text: String, backgroundColor: Color, textColor: Color) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontFamily = CairoFont,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = textColor,

        )
    }
}
// Preview (private so it won't mess up the other screens' previews)
@Preview(showBackground = true)
@Composable
private fun BookDetailsScreenPreview() {
    My_BookshelfTheme {
        BookDetailsScreen(
            book = initialBooks[0],
            onBack = {}
        )
    }
}