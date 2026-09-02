package nl.leersprong.app.diagnostic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.leersprong.app.data.OfflineLearningRepository
import nl.leersprong.app.feature.lesson.LessonLibrary
import nl.leersprong.app.profile.LearnerProfileRepository

data class DiagnosticUiState(
    val questions: List<DiagnosticQuestion> = emptyList(),
    val index: Int = 0,
    val answers: Map<String, Int> = emptyMap(),
    val result: DiagnosticResult? = null,
    val saving: Boolean = false,
)

class DiagnosticViewModel(application: Application) : AndroidViewModel(application) {
    private val learningRepository = OfflineLearningRepository(application)
    private val profileRepository = LearnerProfileRepository(application)
    private val _state = MutableStateFlow(DiagnosticUiState())
    val state: StateFlow<DiagnosticUiState> = _state.asStateFlow()

    fun start(group: Int) {
        if (_state.value.questions.isNotEmpty()) return
        _state.value = DiagnosticUiState(questions = DiagnosticEngine.questions(group))
    }

    fun answer(optionIndex: Int) {
        val current = _state.value
        val question = current.questions.getOrNull(current.index) ?: return
        val answers = current.answers + (question.id to optionIndex)
        val nextIndex = current.index + 1
        _state.value = if (nextIndex >= current.questions.size) {
            current.copy(
                answers = answers,
                result = DiagnosticEngine.score(current.questions, answers),
            )
        } else {
            current.copy(index = nextIndex, answers = answers)
        }
    }

    fun save(group: Int, onDone: () -> Unit) {
        val result = _state.value.result ?: return
        if (_state.value.saving) return
        _state.value = _state.value.copy(saving = true)
        viewModelScope.launch {
            val lessons = LessonLibrary.forGroup(group)
            val now = System.currentTimeMillis()
            lessons.forEach { lesson ->
                val mastery = when (lesson.subject) {
                    "Nederlands", "NT2 / Thuistaalhulp" -> result.dutchPercent
                    "Rekenen", "Rekenen & Wiskunde" -> result.mathPercent
                    else -> ((result.dutchPercent + result.mathPercent) / 2).coerceIn(0, 100)
                }
                learningRepository.updateReview(
                    skillId = lesson.skillId,
                    masteryPercent = mastery,
                    evidenceCount = 3,
                    nextReviewAtEpochMs = now,
                )
            }
            profileRepository.markDiagnosticCompleted()
            onDone()
        }
    }
}
