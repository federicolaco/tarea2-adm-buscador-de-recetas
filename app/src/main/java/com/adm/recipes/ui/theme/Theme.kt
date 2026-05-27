package com.adm.recipes.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val OrangePrimary = Color(0xFFFF6B35)
private val OrangeDark = Color(0xFFE85A24)

private val LightColors = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    secondary = Color(0xFF2EC4B6),
    onSecondary = Color.White,
    background = Color(0xFFFFFBF8),
    surface = Color.White,
)

private val DarkColors = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    secondary = Color(0xFF2EC4B6),
    onSecondary = Color.Black,
    background = Color(0xFF1A1412),
    surface = Color(0xFF2A2220),
)

@Composable
fun RecipeFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
