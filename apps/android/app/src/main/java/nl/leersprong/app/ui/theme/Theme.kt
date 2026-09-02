package nl.leersprong.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LeerSprongColors = lightColorScheme(
    primary = Color(0xFF0B56C8),
    onPrimary = Color.White,
    secondary = Color(0xFF21B86B),
    onSecondary = Color.White,
    tertiary = Color(0xFFFFC62E),
    background = Color(0xFFF5F8FC),
    surface = Color.White,
    onSurface = Color(0xFF10213D),
    surfaceVariant = Color(0xFFEAF0F7),
    outlineVariant = Color(0xFFDCE5EF),
)

@Composable
fun LeerSprongTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LeerSprongColors,
        content = content,
    )
}
