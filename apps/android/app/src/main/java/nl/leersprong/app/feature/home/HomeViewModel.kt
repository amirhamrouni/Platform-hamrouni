package nl.leersprong.app.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeTask(
    val id: String,
    val title: String,
    val subtitle: String,
    val progress: Int,
)

data class HomeUiState(
    val learnerName: String = "Emma",
    val group: Int = 4,
    val streakDays: Int = 7,
    val xp: Int = 350,
    val badges: Int = 12,
    val currentLesson: String = "Optellen tot 20",
    val pathStep: Int = 2,
    val pathTotal: Int = 4,
    val coachMessage: String = "Goedemorgen! Vandaag bouwen we rustig verder op wat je gisteren al goed kon.",
    val tasks: List<HomeTask> = listOf(
        HomeTask("math", "Rekenen", "Optellen tot 20", 60),
        HomeTask("language", "Taal", "Werkwoorden", 40),
        HomeTask("review", "Slim herhalen", "2 korte herhalingen", 25),
    ),
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
}
