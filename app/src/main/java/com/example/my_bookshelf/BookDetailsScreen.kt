package com.example.my_bookshelf

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_bookshelf.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.text.style.TextAlign
@Composable
fun BookDetailsScreen(
    book: Book,
    onBack: () -> Unit
) {
    //val covercolors changes based on a book cover color
    val coverColors = when (book.coverColor) {
        "sand" -> listOf(CoverSandLight, CoverSand)
        "navy" -> listOf(CoverNavyLight, CoverNavy)
        else -> listOf(CoverSageLight, CoverSage)
    }
    //val titleColor changes based on book cover color,
    //to be in contrast with it
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
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //At the top
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "< Back",
                        fontFamily = DMSansFont,
                        fontSize = 15.sp,
                        color = LinkBrown
                    )
                }

                Text(
                    text = "Book details",
                    fontFamily = FrauncesFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Coffee,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(Modifier.height(24.dp))
            //book cover box
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(265.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.verticalGradient(coverColors))
            ) {
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
                // Extra: thin dark strip on the right side of the cover,
                // like a book spine
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .width(8.dp)
                        .background(SpineShade)
                )
            }
        }
    }
}
//Preview (private fun so it won't mess up the other screens' preview)
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