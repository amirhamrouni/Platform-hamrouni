package nl.leersprong.app.feature.lesson

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

enum class LessonInteractionType { MultipleChoice, Ordering, ListenChoose }

data class LessonOption(val id: String, val label: String)

data class LessonStep(
    val id: String,
    val title: String,
    val prompt: String,
    val interaction: LessonInteractionType,
    val options: List<LessonOption>,
    val correctOptionId: String? = null,
    val correctOrder: List<String> = emptyList(),
    val speakText: String? = null,
    val hint: String,
    val explanation: String,
)

data class LessonUiState(
    val lessonTitle: String = "Optellen tot 20",
    val currentIndex: Int = 0,
    val selectedOptionId: String? = null,
    val order: List<String> = emptyList(),
    val checked: Boolean = false,
    val isCorrect: Boolean? = null,
    val showHint: Boolean = false,
    val earnedXp: Int = 0,
    val mistakes: Int = 0,
    val correctAnswers: Int = 0,
    val completed: Boolean = false,
    val masteryPercent: Int = 0,
    val nextReviewAtEpochMs: Long? = null,
)

class LessonViewModel(application: Application) : AndroidViewModel(application) {
    private val progressStore = application.getSharedPreferences("leersprong_lesson_progress", Context.MODE_PRIVATE)

    private val steps = listOf(
        LessonStep(
            id = "sum-8-7",
            title = "Slim optellen",
            prompt = "Wat is 8 + 7?",
            interaction = LessonInteractionType.MultipleChoice,
            options = listOf(LessonOption("13", "13"), LessonOption("15", "15"), LessonOption("16", "16")),
            correctOptionId = "15",
            hint = "Maak eerst 10: 8 + 2 = 10. Hoeveel blijft er dan van 7 over?",
            explanation = "Goed gezien: 8 + 7 = 15. Splits 7 in 2 en 5: 8 + 2 + 5 = 15.",
        ),
        LessonStep(
            id = "listen-ten-plus-four",
            title = "Luister en kies",
            prompt = "Luister naar de som en kies het juiste antwoord.",
            interaction = LessonInteractionType.ListenChoose,
            options = listOf(LessonOption("12", "12"), LessonOption("14", "14"), LessonOption("16", "16")),
            correctOptionId = "14",
            speakText = "Tien plus vier. Wat is het antwoord?",
            hint = "Je hoort: tien plus vier.",
            explanation = "Tien plus vier is veertien.",
        ),
        LessonStep(
            id = "order-strategy",
            title = "Zet de stappen goed",
            prompt = "Zet de rekenstappen voor 9 + 6 in de juiste volgorde.",
            interaction = LessonInteractionType.Ordering,
            options = listOf(
                LessonOption("a", "9 + 1 = 10"),
                LessonOption("b", "Splits 6 in 1 en 5"),
                LessonOption("c", "10 + 5 = 15"),
            ),
            correctOrder = listOf("b", "a", "c"),
            hint = "Denk eerst: welk deel van 6 heb je nodig om 9 naar 10 te brengen?",
            explanation = "Precies. Eerst splits je 6, daarna maak je 10 en tenslotte tel je de rest erbij.",
        ),
        LessonStep(
            id = "sum-7-6",
            title = "Zelf proberen",
            prompt = "Wat is 7 + 6?",
            interaction = LessonInteractionType.MultipleChoice,
            options = listOf(LessonOption("12", "12"), LessonOption("13", "13"), LessonOption("14", "14")),
            correctOptionId = "13",
            hint = "7 heeft nog 3 nodig om 10 te worden.",
            explanation = "Juist: 7 + 3 + 3 = 13.",
        ),
    )

    private val _uiState = MutableStateFlow(LessonUiState(order = steps.first().options.map { it.id }))
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun currentStep(): LessonStep = steps[_uiState.value.currentIndex.coerceIn(0, steps.lastIndex)]
    fun stepCount(): Int = steps.size

    fun selectOption(id: String) {
        if (_uiState.value.checked) return
        _uiState.update { it.copy(selectedOptionId = id) }
    }

    fun moveOrderItem(id: String, delta: Int) {
        if (_uiState.value.checked) return
        _uiState.update { state ->
            val mutable = state.order.toMutableList()
            val index = mutable.indexOf(id)
            val target = index + delta
            if (index >= 0 && target in mutable.indices) {
                val item = mutable.removeAt(index)
                mutable.add(target, item)
            }
            state.copy(order = mutable)
        }
    }

    fun toggleHint() = _uiState.update { it.copy(showHint = !it.showHint) }

    fun checkAnswer() {
        val step = currentStep()
        val state = _uiState.value
        if (state.checked) return
        val correct = when (step.interaction) {
            LessonInteractionType.MultipleChoice,
            LessonInteractionType.ListenChoose -> state.selectedOptionId == step.correctOptionId
            LessonInteractionType.Ordering -> state.order == step.correctOrder
        }
        _uiState.update {
            it.copy(
                checked = true,
                isCorrect = correct,
                earnedXp = it.earnedXp + if (correct) 20 else 5,
                mistakes = it.mistakes + if (correct) 0 else 1,
                correctAnswers = it.correctAnswers + if (correct) 1 else 0,
            )
        }
    }

    fun continueLesson() {
        val state = _uiState.value
        if (!state.checked) return
        if (state.currentIndex >= steps.lastIndex) {
            completeLesson()
            return
        }
        val nextIndex = state.currentIndex + 1
        val next = steps[nextIndex]
        _uiState.update {
            it.copy(
                currentIndex = nextIndex,
                selectedOptionId = null,
                order = next.options.map { option -> option.id },
                checked = false,
                isCorrect = null,
                showHint = false,
            )
        }
    }

    private fun completeLesson() {
        val state = _uiState.value
        val mastery = (state.correctAnswers.toFloat() / steps.size * 100).roundToInt().coerceIn(0, 100)
        val reviewDelayMs = when {
            mastery < 50 -> 6 * 60 * 60 * 1000L
            mastery < 75 -> 24 * 60 * 60 * 1000L
            mastery < 90 -> 3 * 24 * 60 * 60 * 1000L
            else -> 7 * 24 * 60 * 60 * 1000L
        }
        val nextReview = System.currentTimeMillis() + reviewDelayMs
        progressStore.edit()
            .putInt("math_addition_mastery", mastery)
            .putLong("math_addition_next_review", nextReview)
            .putInt("math_addition_last_xp", state.earnedXp)
            .apply()
        _uiState.update { it.copy(completed = true, masteryPercent = mastery, nextReviewAtEpochMs = nextReview) }
    }

    fun restart() {
        val first = steps.first()
        _uiState.value = LessonUiState(order = first.options.map { it.id })
    }
}
