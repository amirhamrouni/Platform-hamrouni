package nl.leersprong.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.leersprong.app.feature.home.HomeRoute
import nl.leersprong.app.feature.lesson.LessonRoute
import nl.leersprong.app.ui.theme.LeerSprongTheme

private enum class AppDestination { Home, Lesson }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeerSprongTheme {
                var destination by remember { mutableStateOf(AppDestination.Home) }
                when (destination) {
                    AppDestination.Home -> HomeRoute(onContinue = { destination = AppDestination.Lesson })
                    AppDestination.Lesson -> LessonRoute(onBack = { destination = AppDestination.Home })
                }
            }
        }
    }
}
