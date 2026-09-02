package nl.leersprong.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.leersprong.app.feature.buddy.BuddyRoute
import nl.leersprong.app.feature.home.LearnerHomeRoute
import nl.leersprong.app.feature.learn.LearnWorldScreen
import nl.leersprong.app.feature.lesson.LessonRoute
import nl.leersprong.app.feature.progress.ProgressRoute
import nl.leersprong.app.ui.navigation.LearnerTab
import nl.leersprong.app.ui.theme.LeerSprongTheme

private enum class AppDestination { Home, Learn, Buddy, Progress, Lesson }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeerSprongTheme {
                var destination by remember { mutableStateOf(AppDestination.Home) }

                fun selectTab(tab: LearnerTab) {
                    destination = when (tab) {
                        LearnerTab.Home -> AppDestination.Home
                        LearnerTab.Learn -> AppDestination.Learn
                        LearnerTab.Buddy -> AppDestination.Buddy
                        LearnerTab.Progress -> AppDestination.Progress
                    }
                }

                when (destination) {
                    AppDestination.Home -> LearnerHomeRoute(
                        onContinue = { destination = AppDestination.Lesson },
                        onTab = ::selectTab,
                    )
                    AppDestination.Learn -> LearnWorldScreen(
                        onStartMath = { destination = AppDestination.Lesson },
                        onTab = ::selectTab,
                    )
                    AppDestination.Buddy -> BuddyRoute(
                        onStartLesson = { destination = AppDestination.Lesson },
                        onTab = ::selectTab,
                    )
                    AppDestination.Progress -> ProgressRoute(onTab = ::selectTab)
                    AppDestination.Lesson -> LessonRoute(onBack = { destination = AppDestination.Home })
                }
            }
        }
    }
}
