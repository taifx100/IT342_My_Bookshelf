package com.example.my_bookshelf

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.example.my_bookshelf.ui.theme.*

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
    val coverColors = coverGradientColors(book.coverColor)

    // titleColor changes based on the book cover color,
    // to be in contrast with it
    val titleColor = coverTitleColor(book.coverColor)

    val coverShape = RoundedCornerShape(
        topStart = 14.dp,
        topEnd = 6.dp,
        bottomEnd = 6.dp,
        bottomStart = 14.dp
    )
    val sheetShape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SheetCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // At the top: colored area (360dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .background(
                        // background changes with the cover color
                        Brush.verticalGradient(
                            detailsBackgroundColors(book.coverColor)
                        )
                    )
            ) {
                // two soft white circles
                Box(
                    modifier = Modifier
                        .offset(x = (-50).dp, y = 110.dp)
                        .size(200.dp)
                        .background(PureWhite.copy(alpha = 0.12f), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .offset(x = 270.dp, y = 10.dp)
                        .size(160.dp)
                        .background(PureWhite.copy(alpha = 0.12f), CircleShape)
                )

                // Back button (white circle 46dp)
                Box(
                    modifier = Modifier
                        .offset(x = 24.dp, y = 48.dp)
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

                // Book cover box (140 x 200)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 66.dp)
                        .width(140.dp)
                        .height(200.dp)
                        .shadow(elevation = 14.dp, shape = coverShape)
                        .clip(coverShape)
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
                        // when no image: colored cover with drawing + title
                        CoverDecoration(
                            coverType = book.coverColor,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(110.dp)
                        )

                        Text(
                            text = book.title,
                            fontFamily = AmiriFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            lineHeight = 25.sp,
                            color = titleColor,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 12.dp, end = 18.dp, bottom = 14.dp)
                        )
                    }

                    // thin dark strip on the right side of the cover, like a book spine
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .width(7.dp)
                            .background(SpineShade)
                    )
                }
            }

            // "About this book" card: white sheet that starts at 300dp and overlaps the colored area
            Column(
                modifier = Modifier
                    .offset(y = (-60).dp)
                    .fillMaxWidth()
                    .shadow(12.dp, sheetShape)
                    .clip(sheetShape)
                    .background(SheetCream)
                    .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Book title
                Text(
                    text = book.title,
                    fontFamily = AmiriFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    color = Coffee,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(4.dp))

                // Author name
                Text(
                    text = book.author,
                    fontFamily = CairoFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(10.dp))

                DetailsChip(
                    text = book.year.toString(),
                    backgroundColor = ChipBackground,
                    textColor = LinkBrown
                )

                Spacer(Modifier.height(20.dp))

                // Category chips: one chip for each category
                Text(
                    text = "Category",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Coffee
                )

                Spacer(Modifier.height(8.dp))

                // DetailsChip is reused for year and category (split by "،" or ",")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    book.category
                        .split('،', ',')
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .forEach { categoryName ->
                            DetailsChip(
                                text = categoryName,
                                backgroundColor = TreeGreen,
                                textColor = PureWhite
                            )
                        }
                }

                Spacer(Modifier.height(20.dp))

                // "About this book" section
                Text(
                    text = "About this book",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Coffee
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = book.description,
                    fontFamily = CairoFont,
                    fontSize = 14.sp,
                    lineHeight = 26.sp,
                    color = TextBody,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Edit button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .shadow(6.dp, RoundedCornerShape(18.dp))
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                Brush.linearGradient(listOf(MochaLight, MochaDark))
                            )
                            .clickable(onClick = onEdit),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✎  Edit",
                            fontFamily = DMSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = PureWhite
                        )
                    }

                    // Delete button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(DeleteTint)
                            .border(2.dp, ErrorRed, RoundedCornerShape(18.dp))
                            .clickable { showDeleteDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🗑  Delete",
                            fontFamily = DMSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = ErrorRed
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.width(330.dp),
            shape = RoundedCornerShape(32.dp),
            containerColor = SheetCream,
            icon = {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .background(DeleteIconBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🗑", fontSize = 30.sp, color = ErrorRed)
                }
            },
            title = {
                Text(
                    text = "Delete this book?",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Coffee,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = buildAnnotatedString {
                        append("Are you sure that you want to delete ")
                        withStyle(
                            SpanStyle(
                                fontFamily = AmiriFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = Coffee
                            )
                        ) {
                            append(book.title)
                        }
                        append("? This cannot be undone.")
                    },
                    fontFamily = DMSansFont,
                    fontSize = 14.5.sp,
                    color = TextSoft,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier.width(282.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Cancel
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(2.dp, TileBorder, RoundedCornerShape(16.dp))
                            .clickable { showDeleteDialog = false },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancel",
                            fontFamily = DMSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Coffee
                        )
                    }

                    // Delete
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .shadow(
                                6.dp,
                                RoundedCornerShape(16.dp),
                                ambientColor = ErrorRed,
                                spotColor = ErrorRed
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .background(ErrorRed)
                            .clickable {
                                showDeleteDialog = false
                                onDelete()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Delete",
                            fontFamily = DMSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PureWhite
                        )
                    }
                }
            }
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
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = textColor
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