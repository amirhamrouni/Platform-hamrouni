package nl.leersprong.app.feature.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import nl.leersprong.app.data.OfflineLearningRepository

data class HomeTask(
    val id: String,
    val title: String,
    val subtitle: String,
    val progress: Int,
)

data class HomeUiState(
    val learnerName: String = "Leerling",
    val group: Int = 4,
    val streakDays: Int? = null,
    val xp: Int = 0,
    val badges: Int = 0,
    val currentLesson: String = "Optellen tot 20",
    val pathStep: Int = 1,
    val pathTotal: Int = 4,
    val coachMessage: String = "Begin met één korte oefening. Daarna pas ik je volgende stap aan.",
    val tasks: List<HomeTask> = listOf(
        HomeTask("math", "Rekenen", "Optellen tot 20", 0),
        HomeTask("review", "Slim herhalen", "Nog geen herhaling gepland", 0),
    ),
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = OfflineLearningRepository(application)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(repository.observeReviews(), repository.observeTotalXp()) { reviews, xp ->
                val now = System.currentTimeMillis()
                val math = reviews.firstOrNull { it.skillId == "math-addition-foundations" }
                val dueCount = reviews.count { it.nextReviewAtEpochMs <= now }
                val mastery = math?.masteryPercent ?: 0
                HomeUiState(
                    xp = xp,
                    badges = reviews.count { it.masteryPercent >= 75 && it.evidenceCount >= 4 },
                    pathStep = if (mastery > 0) 2 else 1,
                    coachMessage = when {
                        dueCount > 0 -> "Je hebt $dueCount slimme herhaling${if (dueCount == 1) "" else "en"} klaarstaan. We beginnen met wat nu het meeste helpt."
                        mastery > 0 -> "Mooi, je laatste rekenstand is $mastery%. Vandaag bouwen we daarop verder."
                        else -> "Begin met één korte oefening. Daarna pas ik je volgende stap aan."
                    },
                    tasks = listOf(
                        HomeTask("math", "Rekenen", "Optellen tot 20", mastery),
                        HomeTask(
                            "review",
                            "Slim herhalen",
                            if (dueCount > 0) "$dueCount herhaling${if (dueCount == 1) "" else "en"} klaar" else "Je bent bij met je herhalingen",
                            if (reviews.isEmpty()) 0 else if (dueCount == 0) 100 else 25,
                        ),
                    ),
                )
            }.collect { _uiState.value = it }
        }
    }
}
