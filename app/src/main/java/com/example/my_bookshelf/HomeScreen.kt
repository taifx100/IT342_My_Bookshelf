package com.example.my_bookshelf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_bookshelf.ui.theme.*
import java.util.Calendar

@Composable
fun HomeScreen(
    userName: String,
    onBookClick: (Book) -> Unit,
    onAddBookClick: () -> Unit,
    onSignOut: () -> Unit
) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = when (hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        else -> "Good evening"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
    ) {
        Canvas(
            modifier = Modifier
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
                .padding(horizontal = 22.dp)
        ) {
            Spacer(Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$greeting, $userName",
                    fontFamily = DMSansFont,
                    fontSize = 14.sp,
                    color = TextSoft,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                TextButton(onClick = onSignOut) {
                    Text(
                        text = "Sign out",
                        fontFamily = DMSansFont,
                        color = LinkBrown,
                        fontSize = 12.sp
                    )
                }
            }

            Text(
                text = "My Bookshelf",
                fontFamily = FrauncesFont,
                fontWeight = FontWeight.Bold,
                fontSize = 31.sp,
                color = Coffee
            )

            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "On your shelf",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    color = Coffee
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(ChipBackground)
                        .padding(horizontal = 14.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "${initialBooks.size} books",
                        fontFamily = DMSansFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = LinkBrown
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                contentPadding = PaddingValues(bottom = 85.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(initialBooks) { book ->
                    ShelfBookItem(
                        book = book,
                        onClick = { onBookClick(book) }
                    )
                }

                item {
                    AddBookTile(
                        onClick = onAddBookClick
                    )
                }
            }
        }

        MiniBooksDecoration(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(52.dp)
        )
    }
}

@Composable
private fun ShelfBookItem(
    book: Book,
    onClick: () -> Unit
) {
    val coverColors = when (book.coverColor) {
        "sand" -> listOf(CoverSandLight, CoverSand)
        "navy" -> listOf(CoverNavyLight, CoverNavy)
        else -> listOf(CoverSageLight, CoverSage)
    }

    val titleColor =
        if (book.coverColor == "sand") Coffee else PureWhite

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 6.dp,
                        bottomEnd = 6.dp,
                        bottomStart = 14.dp
                    )
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 6.dp,
                        bottomEnd = 6.dp,
                        bottomStart = 14.dp
                    )
                )
                .background(
                    Brush.verticalGradient(coverColors)
                )
        ) {
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
                    .padding(
                        start = 12.dp,
                        end = 18.dp,
                        bottom = 14.dp
                    )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(7.dp)
                    .background(SpineShade)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .shadow(2.dp, RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(5.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(ShelfTop, ShelfBottom)
                    )
                )
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = book.title,
            fontFamily = AmiriFont,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            lineHeight = 22.sp,
            color = Coffee,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${book.author} · ${book.year}",
            fontFamily = CairoFont,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            color = TextMuted,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CoverDecoration(
    coverType: String,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        when (coverType) {
            "sand" -> {
                drawCircle(
                    color = TreeGreen,
                    radius = h * 0.25f,
                    center = Offset(w * 0.5f, h * 0.4f)
                )

                drawRect(
                    color = TreeTrunk,
                    topLeft = Offset(w * 0.47f, h * 0.53f),
                    size = Size(w * 0.06f, h * 0.32f)
                )

                listOf(
                    Offset(w * 0.4f, h * 0.35f),
                    Offset(w * 0.59f, h * 0.42f),
                    Offset(w * 0.48f, h * 0.54f)
                ).forEach {
                    drawCircle(
                        color = OrangeFruit,
                        radius = h * 0.038f,
                        center = it
                    )
                }
            }

            "navy" -> {
                listOf(
                    Offset(w * 0.24f, h * 0.25f),
                    Offset(w * 0.65f, h * 0.2f),
                    Offset(w * 0.45f, h * 0.46f),
                    Offset(w * 0.78f, h * 0.55f),
                    Offset(w * 0.3f, h * 0.68f)
                ).forEachIndexed { index, point ->
                    drawCircle(
                        color = if (index % 2 == 0)
                            StarCream else StarGold,
                        radius = if (index % 2 == 0)
                            3.dp.toPx() else 2.dp.toPx(),
                        center = point
                    )
                }
            }

            else -> {
                drawCircle(
                    color = SunCream,
                    radius = h * 0.17f,
                    center = Offset(w * 0.68f, h * 0.35f)
                )

                drawOval(
                    color = HillsGreen,
                    topLeft = Offset(w * 0.05f, h * 0.65f),
                    size = Size(w * 0.9f, h * 0.65f)
                )
            }
        }
    }
}

@Composable
private fun AddBookTile(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(TileBackground)
                .border(
                    width = 2.dp,
                    color = TileBorder,
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.linearGradient(
                                listOf(MochaLight, MochaDark)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        fontSize = 32.sp,
                        color = PureWhite
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Add a book",
                    fontFamily = DMSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = LinkBrown
                )
            }
        }
    }
}

@Composable
private fun MiniBooksDecoration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val colors = listOf(
            TreeGreen, CoverSand, CoverNavy,
            CoverWine, MochaDark, CoverSage,
            Latte, Coffee, CoverNavyLight, MochaLight
        )

        val bookWidth = size.width / colors.size

        colors.forEachIndexed { index, color ->
            val heightFactor = 0.55f + (index % 4) * 0.12f
            val bookHeight = size.height * heightFactor

            drawRect(
                color = color,
                topLeft = Offset(
                    index * bookWidth,
                    size.height - bookHeight
                ),
                size = Size(
                    bookWidth * 0.93f,
                    bookHeight
                )
            )

            drawRect(
                color = PureWhite.copy(alpha = 0.3f),
                topLeft = Offset(
                    index * bookWidth + bookWidth * 0.15f,
                    size.height - bookHeight + 5.dp.toPx()
                ),
                size = Size(
                    bookWidth * 0.08f,
                    bookHeight * 0.5f
                )
            )
        }
    }
}
