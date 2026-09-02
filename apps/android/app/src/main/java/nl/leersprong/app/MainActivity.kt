package nl.leersprong.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.leersprong.app.feature.buddy.BuddyRoute
import nl.leersprong.app.feature.home.LearnerHomeRoute
import nl.leersprong.app.feature.learn.LearnWorldScreen
import nl.leersprong.app.feature.lesson.LessonCatalog
import nl.leersprong.app.feature.lesson.LessonLaunchStore
import nl.leersprong.app.feature.lesson.LessonRoute
import nl.leersprong.app.feature.progress.ProgressRoute
import nl.leersprong.app.profile.ProfileScreen
import nl.leersprong.app.profile.ProfileViewModel
import nl.leersprong.app.ui.navigation.LearnerTab
import nl.leersprong.app.ui.theme.LeerSprongTheme

private enum class AppDestination { Home, Learn, Buddy, Progress, Profile, Lesson }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeerSprongTheme {
                val profileViewModel: ProfileViewModel = viewModel()
                val profile by profileViewModel.profile.collectAsStateWithLifecycle()
                var destination by remember { mutableStateOf(AppDestination.Home) }

                val currentProfile = profile
                if (currentProfile == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Je leerprofiel wordt geladen…")
                    }
                    return@LeerSprongTheme
                }

                if (!currentProfile.completed) {
                    ProfileScreen(
                        profile = currentProfile,
                        firstRun = true,
                        onSave = profileViewModel::save,
                        onDone = { destination = AppDestination.Home },
                    )
                    return@LeerSprongTheme
                }

                fun launchLesson(lessonId: String) {
                    LessonLaunchStore.select(lessonId)
                    destination = AppDestination.Lesson
                }

                fun selectTab(tab: LearnerTab) {
                    destination = when (tab) {
                        LearnerTab.Home -> AppDestination.Home
                        LearnerTab.Learn -> AppDestination.Learn
                        LearnerTab.Buddy -> AppDestination.Buddy
                        LearnerTab.Progress -> AppDestination.Progress
                        LearnerTab.Profile -> AppDestination.Profile
                    }
                }

                when (destination) {
                    AppDestination.Home -> LearnerHomeRoute(
                        onContinue = { launchLesson(LessonCatalog.G4_MULTIPLICATION) },
                        onTab = ::selectTab,
                    )
                    AppDestination.Learn -> LearnWorldScreen(
                        learnerGroup = currentProfile.group,
                        onStartLesson = ::launchLesson,
                        onTab = ::selectTab,
                    )
                    AppDestination.Buddy -> BuddyRoute(
                        onStartLesson = { launchLesson(LessonCatalog.G4_MULTIPLICATION) },
                        onTab = ::selectTab,
                    )
                    AppDestination.Progress -> ProgressRoute(onTab = ::selectTab)
                    AppDestination.Profile -> ProfileScreen(
                        profile = currentProfile,
                        firstRun = false,
                        onSave = profileViewModel::save,
                        onDone = { destination = AppDestination.Home },
                    )
                    AppDestination.Lesson -> LessonRoute(onBack = { destination = AppDestination.Learn })
                }
            }
        }
    }
}
