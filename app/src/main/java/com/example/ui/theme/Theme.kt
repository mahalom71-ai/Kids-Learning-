package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val KidsLightColorScheme = lightColorScheme(
    primary = KidsPurple,
    onPrimary = Color.White,
    primaryContainer = KidsPurpleBg,
    onPrimaryContainer = KidsPurple,
    secondary = KidsCoral,
    onSecondary = Color.White,
    secondaryContainer = KidsCoralBg,
    onSecondaryContainer = KidsCoral,
    tertiary = KidsMint,
    onTertiary = Color.White,
    tertiaryContainer = KidsMintBg,
    onTertiaryContainer = KidsMint,
    background = KidsLightBg,
    onBackground = KidsDark,
    surface = KidsCardBg,
    onSurface = KidsDark,
    surfaceVariant = Color(0xFFF0F2FB),
    onSurfaceVariant = KidsGray
)

private val KidsDarkColorScheme = darkColorScheme(
    primary = KidsPurpleLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2E2A68),
    onPrimaryContainer = Color.White,
    secondary = KidsCoralLight,
    onSecondary = Color.White,
    tertiary = KidsMint,
    background = Color(0xFF14142B),
    onBackground = Color(0xFFF7F8FD),
    surface = Color(0xFF1F1F3D),
    onSurface = Color.White
)

val KidsShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun KidsBornomalaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) KidsDarkColorScheme else KidsLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = KidsShapes,
        content = content
    )
}
