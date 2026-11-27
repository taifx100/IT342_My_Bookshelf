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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
    onSignOut: () -> Unit,
    books: List<Book> = initialBooks
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
                        text = "${books.size} books",
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
                contentPadding = PaddingValues(bottom = 90.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(books) { book ->
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
                .height(64.dp)
        )
    }
}

@Composable
private fun ShelfBookItem(
    book: Book,
    onClick: () -> Unit
) {
    // Colors come from the shared cover helpers (bottom of this file)
    val coverColors = coverGradientColors(book.coverColor)

    val titleColor = coverTitleColor(book.coverColor)

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

// Public (was private) so Add / Edit / Details draw the same cover drawings
@Composable
fun CoverDecoration(
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

            "wine" -> {
                // Rose: six petals, a center and a stem
                val c = Offset(w * 0.5f, h * 0.4f)
                val dist = h * 0.15f

                for (i in 0 until 6) {
                    val angle = Math.toRadians(i * 60.0 - 90.0)
                    drawCircle(
                        color = PetalPink.copy(alpha = 0.9f),
                        radius = h * 0.12f,
                        center = Offset(
                            c.x + (dist * Math.cos(angle)).toFloat(),
                            c.y + (dist * Math.sin(angle)).toFloat()
                        )
                    )
                }

                drawCircle(color = CoverWineLight, radius = h * 0.11f, center = c)
                drawCircle(color = PetalPink, radius = h * 0.05f, center = c)

                drawLine(
                    color = HillsGreen,
                    start = Offset(w * 0.5f, h * 0.6f),
                    end = Offset(w * 0.5f, h * 0.82f),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            "mocha" -> {
                // Coffee cup with steam
                drawRoundRect(
                    color = SunCream,
                    topLeft = Offset(w * 0.34f, h * 0.42f),
                    size = Size(w * 0.3f, h * 0.34f),
                    cornerRadius = CornerRadius(h * 0.05f)
                )

                drawCircle(
                    color = SunCream,
                    radius = h * 0.085f,
                    center = Offset(w * 0.66f, h * 0.56f),
                    style = Stroke(width = 3.dp.toPx())
                )

                drawRoundRect(
                    color = StarGold,
                    topLeft = Offset(w * 0.28f, h * 0.78f),
                    size = Size(w * 0.42f, h * 0.05f),
                    cornerRadius = CornerRadius(h * 0.03f)
                )

                listOf(0.42f, 0.49f, 0.56f).forEach { x ->
                    drawLine(
                        color = SunCream.copy(alpha = 0.7f),
                        start = Offset(w * x, h * 0.2f),
                        end = Offset(w * x, h * 0.34f),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }

            else -> {
                // Sage: sun and green hills
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
            CoverSageLight, CoverSand, CoverWineLight, SpineBrown,
            SpineBlue, SpineGold, TreeGreen, MochaDark,
            SpineAmber, CoverWine, CoverSage
        )
        val bands = listOf(LatteLight, Coffee, StarGold)
        val widths = listOf(28, 30, 26, 32, 24, 30, 26, 34, 26, 28, 24)
        val heights = listOf(46, 58, 50, 62, 44, 56, 48, 60, 46, 54, 42)
        val gap = 2.dp.toPx()

        val totalWidth = widths.sumOf { it.dp.toPx().toDouble() }.toFloat() +
                gap * (colors.size - 1)
        var x = (size.width - totalWidth) / 2f

        colors.forEachIndexed { index, color ->
            val w = widths[index].dp.toPx()
            val h = heights[index].dp.toPx()
            val top = size.height - h

            drawRect(
                color = color,
                topLeft = Offset(x, top),
                size = Size(w, h)
            )

            // decorative band near the top of each spine
            drawRect(
                color = bands[index % bands.size],
                topLeft = Offset(x, top + 8.dp.toPx()),
                size = Size(w, 3.dp.toPx())
            )

            x += w + gap
        }
    }
}

// ───────────── Shared cover helpers (used by Home, Details, Add, Edit) ─────────────

// All cover color names, in the order shown in the Add picker
val coverColorOptions = listOf("sand", "sage", "navy", "wine", "mocha")

// Cover gradient (top, bottom)
fun coverGradientColors(coverColor: String): List<Color> = when (coverColor) {
    "sand" -> listOf(CoverSandLight, CoverSand)
    "navy" -> listOf(CoverNavyLight, CoverNavy)
    "wine" -> listOf(CoverWineLight, CoverWine)
    "mocha" -> listOf(CoverMochaLight, CoverMocha)
    else -> listOf(CoverSageLight, CoverSage)
}

// Title color that contrasts with the cover
fun coverTitleColor(coverColor: String): Color =
    if (coverColor == "sand") Coffee else PureWhite

// Dot color in the color picker
fun coverDotColor(coverColor: String): Color = when (coverColor) {
    "sand" -> CoverSand
    "navy" -> CoverNavy
    "wine" -> CoverWine
    "mocha" -> CoverMocha
    else -> TreeGreen
}

// Readable name under the picker
fun coverDisplayName(coverColor: String): String = when (coverColor) {
    "sand" -> "Golden / Sand"
    "navy" -> "Navy Blue"
    "wine" -> "Burgundy / Wine"
    "mocha" -> "Mocha Brown"
    else -> "Sage Green"
}

// Light background gradient behind the cover in Book Details
fun detailsBackgroundColors(coverColor: String): List<Color> = when (coverColor) {
    "sand" -> listOf(DetailsGoldTop, DetailsGoldBottom)
    "navy" -> listOf(DetailsNavyTop, DetailsNavyBottom)
    "wine" -> listOf(DetailsWineTop, DetailsWineBottom)
    "mocha" -> listOf(DetailsMochaTop, DetailsMochaBottom)
    else -> listOf(DetailsSageTop, DetailsSageBottom)
}