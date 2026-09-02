package nl.leersprong.app.feature.lesson

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.leersprong.app.data.OfflineLearningRepository
import kotlin.math.roundToInt

private const val LESSON_ID = "g4-math-multiplication-foundations-v1"
private const val SKILL_ID = "g4-math-multiplication-foundations"

enum class LessonInteractionType { MultipleChoice, FillBlank, Ordering, ListenChoose }

data class LessonOption(val id: String, val label: String)

data class LessonStep(
    val id: String,
    val title: String,
    val prompt: String,
    val interaction: LessonInteractionType,
    val options: List<LessonOption> = emptyList(),
    val correctOptionId: String? = null,
    val acceptedAnswers: List<String> = emptyList(),
    val correctOrder: List<String> = emptyList(),
    val speakText: String? = null,
    val hint: String,
    val explanation: String,
)

data class LessonUiState(
    val lessonTitle: String = "Tafels begrijpen",
    val currentIndex: Int = 0,
    val selectedOptionId: String? = null,
    val textAnswer: String = "",
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
    private val learningRepository = OfflineLearningRepository(application)

    private val steps = listOf(
        LessonStep(
            id = "g4-mul-01",
            title = "Gelijke groepjes",
            prompt = "Er staan 3 mandjes met in elk mandje 4 appels. Welke som past hierbij?",
            interaction = LessonInteractionType.MultipleChoice,
            options = listOf(LessonOption("add", "3 + 4"), LessonOption("mul", "3 × 4"), LessonOption("sub", "4 − 3")),
            correctOptionId = "mul",
            hint = "Kijk naar het aantal gelijke groepjes en hoeveel er in elk groepje zitten.",
            explanation = "3 gelijke groepjes van 4 schrijf je als 3 × 4.",
        ),
        LessonStep(
            id = "g4-mul-02",
            title = "Herhaald optellen",
            prompt = "Vul in: 5 groepjes van 2 is samen ___.",
            interaction = LessonInteractionType.FillBlank,
            acceptedAnswers = listOf("10"),
            hint = "Tel 2 vijf keer bij elkaar op.",
            explanation = "5 × 2 = 10. Je kunt ook 2 + 2 + 2 + 2 + 2 rekenen.",
        ),
        LessonStep(
            id = "g4-mul-03",
            title = "Luister en kies",
            prompt = "Luister naar de keersom en kies het juiste antwoord.",
            interaction = LessonInteractionType.ListenChoose,
            options = listOf(LessonOption("20", "20"), LessonOption("24", "24"), LessonOption("28", "28")),
            correctOptionId = "24",
            speakText = "Wat is vier keer zes?",
            hint = "Vier gelijke groepjes van zes.",
            explanation = "4 groepjes van 6 zijn samen 24.",
        ),
        LessonStep(
            id = "g4-mul-04",
            title = "Zet de stappen goed",
            prompt = "Zet de stappen in de goede volgorde om 3 × 5 uit te rekenen.",
            interaction = LessonInteractionType.Ordering,
            options = listOf(
                LessonOption("sum", "Tel alles bij elkaar"),
                LessonOption("groups", "Maak 3 gelijke groepjes"),
                LessonOption("fill", "Doe 5 in elk groepje"),
            ),
            correctOrder = listOf("groups", "fill", "sum"),
            hint = "Begin met hoeveel groepjes je nodig hebt.",
            explanation = "Een keersom beschrijft eerst hoeveel groepjes je hebt en daarna hoeveel er in elk groepje zitten.",
        ),
        LessonStep(
            id = "g4-mul-05",
            title = "Wisselregel",
            prompt = "Welke twee sommen hebben dezelfde uitkomst?",
            interaction = LessonInteractionType.MultipleChoice,
            options = listOf(
                LessonOption("swap", "3 × 4 en 4 × 3"),
                LessonOption("plus1", "3 × 4 en 3 + 4"),
                LessonOption("plus2", "4 × 3 en 4 + 3"),
            ),
            correctOptionId = "swap",
            hint = "Bij vermenigvuldigen kun je de factoren omwisselen.",
            explanation = "3 × 4 en 4 × 3 zijn allebei 12. De groepjes zijn anders verdeeld, maar het totaal is gelijk.",
        ),
        LessonStep(
            id = "g4-mul-06",
            title = "Gebruik wat je weet",
            prompt = "Je weet dat 5 × 6 = 30. Wat is dan 6 × 5?",
            interaction = LessonInteractionType.FillBlank,
            acceptedAnswers = listOf("30"),
            hint = "Denk aan de wisselregel.",
            explanation = "Bij vermenigvuldigen mag je de factoren omwisselen: 5 × 6 = 6 × 5.",
        ),
        LessonStep(
            id = "g4-mul-07",
            title = "Verhaalsom",
            prompt = "Een klas heeft 7 tafels. Aan elke tafel zitten 4 kinderen. Hoeveel kinderen zijn dat?",
            interaction = LessonInteractionType.MultipleChoice,
            options = listOf(LessonOption("11", "11"), LessonOption("24", "24"), LessonOption("28", "28")),
            correctOptionId = "28",
            hint = "Je hebt 7 gelijke groepjes van 4.",
            explanation = "7 gelijke groepjes van 4: 7 × 4 = 28.",
        ),
        LessonStep(
            id = "g4-mul-08",
            title = "Slimme strategie",
            prompt = "Een doos heeft 8 rijen met 6 stickers. Hoeveel stickers zijn er?",
            interaction = LessonInteractionType.FillBlank,
            acceptedAnswers = listOf("48"),
            hint = "Je kunt bijvoorbeeld 4 × 6 uitrekenen en daarna verdubbelen.",
            explanation = "8 × 6 = 48. Je kunt bijvoorbeeld 4 × 6 verdubbelen.",
        ),
    )

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    private fun initialState() = LessonUiState(order = steps.first().options.map { it.id })

    fun currentStep(): LessonStep = steps[_uiState.value.currentIndex.coerceIn(0, steps.lastIndex)]
    fun stepCount(): Int = steps.size

    fun selectOption(id: String) {
        if (_uiState.value.checked) return
        _uiState.update { it.copy(selectedOptionId = id) }
    }

    fun updateTextAnswer(value: String) {
        if (_uiState.value.checked) return
        _uiState.update { it.copy(textAnswer = value) }
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
            LessonInteractionType.FillBlank -> step.acceptedAnswers.any { it.equals(state.textAnswer.trim(), ignoreCase = true) }
            LessonInteractionType.Ordering -> state.order == step.correctOrder
        }
        val xpDelta = if (correct) 20 else 5
        _uiState.update {
            it.copy(
                checked = true,
                isCorrect = correct,
                earnedXp = it.earnedXp + xpDelta,
                mistakes = it.mistakes + if (correct) 0 else 1,
                correctAnswers = it.correctAnswers + if (correct) 1 else 0,
            )
        }
        viewModelScope.launch {
            learningRepository.recordAttempt(
                lessonId = LESSON_ID,
                skillId = SKILL_ID,
                activityId = step.id,
                correct = correct,
                hintsUsed = if (state.showHint) 1 else 0,
                earnedXp = xpDelta,
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
                textAnswer = "",
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
        _uiState.update { it.copy(completed = true, masteryPercent = mastery, nextReviewAtEpochMs = nextReview) }
        viewModelScope.launch {
            learningRepository.updateReview(
                skillId = SKILL_ID,
                masteryPercent = mastery,
                evidenceCount = steps.size,
                nextReviewAtEpochMs = nextReview,
            )
        }
    }

    fun restart() {
        _uiState.value = initialState()
    }
}
