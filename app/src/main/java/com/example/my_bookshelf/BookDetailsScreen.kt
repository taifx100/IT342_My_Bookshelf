package com.example.my_bookshelf

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun BookDetailsScreen(
    book: Book,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp)
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