package com.example.my_bookshelf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_bookshelf.ui.theme.*

@Composable
fun BookDetailsScreen(
    book: Book,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val coverColors = when (book.coverColor) {
        "sand" -> listOf(CoverSandLight, CoverSand)
        "navy" -> listOf(CoverNavyLight, CoverNavy)
        "sage" -> listOf(CoverSageLight, CoverSage)
        else -> listOf(CoverMochaLight, CoverMocha)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
    ) {
        Canvas(
            modifier = Modifier
                .size(320.dp)
                .offset(x = (-90).dp, y = (-110).dp)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        LatteLight.copy(alpha = 0.8f),
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
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text(
                        text = "‹  Back",
                        fontFamily = DMSansFont,
                        fontSize = 15.sp,
                        color = LinkBrown
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = "Book details",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Coffee
                )

                Spacer(Modifier.weight(1f))

                Spacer(Modifier.width(55.dp))
            }

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(265.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(coverColors)
                    )
            ) {
                DetailsCoverDecoration(
                    coverColor = book.coverColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                        .align(Alignment.TopCenter)
                )

                Text(
                    text = book.title,
                    fontFamily = AmiriFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    lineHeight = 32.sp,
                    color = if (book.coverColor == "sand")
                        Coffee else PureWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(18.dp)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .width(8.dp)
                        .background(SpineShade)
                )
            }

            Spacer(Modifier.height(25.dp))

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

            Text(
                text = book.author,
                fontFamily = CairoFont,
                fontSize = 15.sp,
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DetailsChip(text = book.year.toString())

                DetailsChip(text = book.category)
            }

            Spacer(Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(22.dp))
                    .clip(RoundedCornerShape(22.dp))
                    .background(PureWhite)
                    .padding(22.dp)
            ) {
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
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onEdit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MochaDark,
                    contentColor = PureWhite
                )
            ) {
                Text(
                    text = "Edit book",
                    fontFamily = DMSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, ErrorRed
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ErrorRed
                )
            ) {
                Text(
                    text = "Delete book",
                    fontFamily = DMSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete this book?",
                    fontFamily = FrauncesFont,
                    color = Coffee
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to remove this book from your shelf?",
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
                ) {
                    Text("Delete", color = ErrorRed)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancel", color = LinkBrown)
                }
            },
            containerColor = PureWhite,
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Composable
private fun DetailsChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(ChipBackground)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = text,
            fontFamily = CairoFont,
            fontSize = 12.sp,
            color = LinkBrown
        )
    }
}

@Composable
private fun DetailsCoverDecoration(
    coverColor: String,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        when (coverColor) {
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
                ).forEach { point ->
                    drawCircle(
                        color = OrangeFruit,
                        radius = h * 0.038f,
                        center = point
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
