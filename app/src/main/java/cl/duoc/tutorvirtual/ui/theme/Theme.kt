package cl.duoc.tutorvirtual.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = VerdeOscuro,
    onPrimary = Color.White,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = VerdeMuyOscuro,

    secondary = GrisVerde,
    onSecondary = Color.White,

    tertiary = Ocre,
    onTertiary = Color.White,

    background = Fondo,
    onBackground = TextoPrincipal,
    surface = Fondo,
    onSurface = TextoPrincipal
)

private val DarkColorScheme = darkColorScheme(
    primary = VerdeNeon,
    onPrimary = VerdeMuyOscuro,

    secondary = VerdeNeon,
    onSecondary = VerdeMuyOscuro,

    tertiary = VerdeNeon,
    onTertiary = VerdeMuyOscuro,

    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = FondoOscuro,
    onSurface = TextoClaro
)

@Composable
fun TutorVirtualESUCARTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}