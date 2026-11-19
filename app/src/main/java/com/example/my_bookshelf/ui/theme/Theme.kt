package com.example.my_bookshelf.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BookshelfColorScheme = lightColorScheme(
    primary = MochaDark,
    onPrimary = PureWhite,

    primaryContainer = LatteLight,
    onPrimaryContainer = Coffee,

    secondary = MochaLight,
    onSecondary = PureWhite,

    secondaryContainer = ChipBackground,
    onSecondaryContainer = Coffee,

    background = Paper,
    onBackground = Coffee,

    surface = PureWhite,
    onSurface = Coffee,

    surfaceVariant = TileBackground,
    onSurfaceVariant = TextMuted,

    outline = FieldBorder,
    error = ErrorRed,
    onError = PureWhite
)

@Composable
fun My_BookshelfTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BookshelfColorScheme,
        typography = Typography,
        content = content
    )
}
