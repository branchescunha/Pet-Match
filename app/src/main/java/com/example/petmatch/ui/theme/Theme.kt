package com.example.petmatch.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AmareloPrincipal,
    secondary = AmareloSuave,
    tertiary = AmareloEscuro,
    background = FundoClaro,
    surface = Branco,
    onPrimary = Color.Black,
    onSecondary = TextoPrimario,
    onTertiary = Color.White,
    onBackground = TextoPrimario,
    onSurface = TextoPrimario
)

@Composable
fun PetMatchTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(),
        content = content
    )
}