package nl.leersprong.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import nl.leersprong.app.feature.home.HomeRoute
import nl.leersprong.app.ui.theme.LeerSprongTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeerSprongTheme {
                HomeRoute()
            }
        }
    }
}
