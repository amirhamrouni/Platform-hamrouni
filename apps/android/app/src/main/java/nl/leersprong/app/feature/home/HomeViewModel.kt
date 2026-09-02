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
import nl.leersprong.app.feature.lesson.PlatformLessons
import nl.leersprong.app.profile.LearnerProfileRepository
import java.util.Calendar

data class HomeTask(val id: String, val title: String, val subtitle: String, val progress: Int)
data class HomeUiState(
    val learnerName: String = "Leerling", val group: Int = 4, val homeLanguage: String = "Nederlands", val supportLanguageEnabled: Boolean = false,
    val streakDays: Int = 0, val xp: Int = 0, val badges: Int = 0, val currentLesson: String = "Startles", val pathStep: Int = 1, val pathTotal: Int = 1,
    val nextReviewAtEpochMs: Long? = null, val coachMessage: String = "Begin met één korte oefening. Daarna bouwen we verder op je bewijs.", val tasks: List<HomeTask> = emptyList(),
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = OfflineLearningRepository(application)
    private val profileRepository = LearnerProfileRepository(application)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init { viewModelScope.launch {
        combine(repository.observeReviews(), repository.observeTotalXp(), repository.observePracticeTimestamps(), profileRepository.profile) { reviews, xp, practiceTimestamps, profile ->
            val now = System.currentTimeMillis()
            val groupLessons = PlatformLessons.forGroup(profile.group)
            val firstLesson = groupLessons.firstOrNull()
            val groupSkillIds = groupLessons.mapTo(mutableSetOf()) { it.skillId }
            val groupReviews = reviews.filter { it.skillId in groupSkillIds }
            val primaryReview = firstLesson?.let { lesson -> groupReviews.firstOrNull { it.skillId == lesson.skillId } }
            val dueCount = groupReviews.count { it.nextReviewAtEpochMs <= now }
            val primaryMastery = primaryReview?.masteryPercent ?: 0
            val lessonTasks = groupLessons.map { lesson ->
                val review = groupReviews.firstOrNull { it.skillId == lesson.skillId }
                HomeTask(lesson.id, lesson.subject, lesson.title, review?.masteryPercent ?: 0)
            }
            HomeUiState(
                learnerName = profile.name.ifBlank { "Leerling" }, group = profile.group, homeLanguage = profile.homeLanguage, supportLanguageEnabled = profile.supportLanguageEnabled,
                streakDays = calculatePracticeStreak(practiceTimestamps, now), xp = xp, badges = reviews.count { it.masteryPercent >= 75 && it.evidenceCount >= 4 },
                currentLesson = firstLesson?.title ?: "Startles Groep ${profile.group}", pathStep = if (primaryMastery > 0) 2 else 1, pathTotal = groupLessons.size.coerceAtLeast(1),
                nextReviewAtEpochMs = primaryReview?.nextReviewAtEpochMs,
                coachMessage = when {
                    dueCount > 0 -> "Je hebt $dueCount slimme herhaling${if (dueCount == 1) "" else "en"} klaarstaan voor Groep ${profile.group}. We pakken eerst terug wat dreigt weg te zakken."
                    primaryMastery > 0 -> "Je eerste leerpad staat op $primaryMastery%. We bouwen vandaag verder met ${firstLesson?.title ?: "je volgende oefening"}."
                    profile.supportLanguageEnabled -> "We leren in het Nederlands. Als iets moeilijk is, kan ik extra taalsteun geven in ${profile.homeLanguage}."
                    else -> "Je Groep ${profile.group}-pad staat klaar. Begin met ${firstLesson?.title ?: "een korte startles"}; daarna groeit je route mee met je antwoorden."
                },
                tasks = lessonTasks + HomeTask("review", "Slim herhalen", if (dueCount > 0) "$dueCount herhaling${if (dueCount == 1) "" else "en"} klaar" else "Je bent bij met je geplande herhalingen", if (groupReviews.isEmpty()) 0 else if (dueCount == 0) 100 else 25),
            )
        }.collect { _uiState.value = it }
    } }
}

private fun calculatePracticeStreak(timestamps: List<Long>, now: Long): Int {
    if (timestamps.isEmpty()) return 0
    val practicedDays = timestamps.mapTo(mutableSetOf()) { startOfLocalDay(it) }
    val cursor = Calendar.getInstance().apply { timeInMillis = startOfLocalDay(now) }
    if (cursor.timeInMillis !in practicedDays) { cursor.add(Calendar.DAY_OF_YEAR, -1); if (cursor.timeInMillis !in practicedDays) return 0 }
    var streak = 0
    while (cursor.timeInMillis in practicedDays) { streak += 1; cursor.add(Calendar.DAY_OF_YEAR, -1) }
    return streak
}
private fun startOfLocalDay(timestamp: Long): Long = Calendar.getInstance().run {
    timeInMillis = timestamp; set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0); timeInMillis
}
