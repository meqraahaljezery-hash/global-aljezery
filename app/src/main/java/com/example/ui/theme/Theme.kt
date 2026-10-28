package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Night Color Scheme (أزرق داكن وليلي مع أزرق سماوي وذهب وأبيض)
private val DarkColorScheme = darkColorScheme(
    primary = ImperialGold,
    onPrimary = NightBg,
    primaryContainer = NightCardBg,
    onPrimaryContainer = ImperialGoldLight,
    secondary = LightSkyBlue,
    onSecondary = NightBg,
    tertiary = EmeraldGreen,
    background = NightBg,
    onBackground = NightTextPrimary,
    surface = NightCardBg,
    onSurface = NightTextPrimary,
    surfaceVariant = NightCardBorder,
    onSurfaceVariant = NightTextSecondary,
    outline = ImperialGold.copy(alpha = 0.4f)
)

// Day Color Scheme (أزرق فاتح وأبيض وذهبي نقي ونهاري)
private val LightColorScheme = lightColorScheme(
    primary = LightBluePrimary,
    onPrimary = PureWhite,
    primaryContainer = LightSkyBlueSoft,
    onPrimaryContainer = LightBluePrimary,
    secondary = ImperialGoldDark,
    onSecondary = PureWhite,
    tertiary = EmeraldGreen,
    background = DayBg,
    onBackground = DayTextPrimary,
    surface = DayCardBg,
    onSurface = DayTextPrimary,
    surfaceVariant = LightSkyBlueSubtle,
    onSurfaceVariant = DayTextSecondary,
    outline = DayCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
