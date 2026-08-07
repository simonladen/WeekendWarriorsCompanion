package com.weekendwarriorscompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = WarriorOrange,
    secondary = WarriorLightBlue,
    tertiary = WarriorBlue,
    background = WarriorDarkBlue,
    surface = WarriorDarkBlue,
    onPrimary = Color.White,
    onSecondary = WarriorDarkBlue,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = WarriorOrange,
    secondary = WarriorBlue,
    tertiary = WarriorLightBlue,
    background = WarriorLightBlue,
    surface = WarriorWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = WarriorDarkBlue,
    onBackground = WarriorDarkBlue,
    onSurface = WarriorDarkBlue
)

@Composable
fun WeekendWarriorsCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
