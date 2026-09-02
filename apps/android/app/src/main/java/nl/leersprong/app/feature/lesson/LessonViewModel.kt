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

enum class LessonInteractionType { MultipleChoice, FillBlank, Ordering, ListenChoose }

data class LessonOption(val id: String, val label: String)

data class LessonStep(
    val id: String,
    val title: String,
    val prompt: String,
    val interaction: LessonInteractionType,
    val conceptTag: String,
    val options: List<LessonOption> = emptyList(),
    val correctOptionId: String? = null,
    val acceptedAnswers: List<String> = emptyList(),
    val correctOrder: List<String> = emptyList(),
    val speakText: String? = null,
    val hint: String,
    val explanation: String,
)

data class LessonUiState(
    val lessonTitle: String,
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
    val answeredActivities: Int = 0,
    val pendingRelearningConcept: String? = null,
    val relearningActive: Boolean = false,
    val completed: Boolean = false,
    val masteryPercent: Int = 0,
    val nextReviewAtEpochMs: Long? = null,
)

object LessonLaunchStore {
    var selectedLessonId: String = LessonCatalog.G4_MULTIPLICATION
        private set

    fun select(lessonId: String) {
        selectedLessonId = AllLessons.get(lessonId).id
    }
}

class LessonViewModel(application: Application) : AndroidViewModel(application) {
    private val learningRepository = OfflineLearningRepository(application)
    private var definition = AllLessons.get(LessonLaunchStore.selectedLessonId)
    private var remedialStep: LessonStep? = null

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    private val steps: List<LessonStep> get() = definition.steps
    private val remedialSteps: Map<String, LessonStep> get() = definition.remedialSteps

    private fun initialState() = LessonUiState(
        lessonTitle = definition.title,
        order = definition.steps.first().options.map { it.id },
    )

    private fun syncSelectedLesson() {
        val selected = AllLessons.get(LessonLaunchStore.selectedLessonId)
        if (selected.id == definition.id) return
        definition = selected
        remedialStep = null
        _uiState.value = initialState()
    }

    fun currentStep(): LessonStep {
        syncSelectedLesson()
        return remedialStep ?: steps[_uiState.value.currentIndex.coerceIn(0, steps.lastIndex)]
    }

    fun stepCount(): Int {
        syncSelectedLesson()
        return steps.size
    }

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
        syncSelectedLesson()
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
                answeredActivities = it.answeredActivities + 1,
                pendingRelearningConcept = if (!correct && !it.relearningActive) step.conceptTag else it.pendingRelearningConcept,
            )
        }

        viewModelScope.launch {
            learningRepository.recordAttempt(
                lessonId = definition.id,
                skillId = definition.skillId,
                activityId = step.id,
                correct = correct,
                hintsUsed = if (state.showHint) 1 else 0,
                earnedXp = xpDelta,
            )
        }
    }

    fun continueLesson() {
        syncSelectedLesson()
        val state = _uiState.value
        if (!state.checked) return

        if (state.relearningActive) {
            remedialStep = null
            moveToNextCoreOrComplete(state.currentIndex + 1)
            return
        }

        val remedial = state.pendingRelearningConcept?.let(remedialSteps::get)
        if (remedial != null) {
            remedialStep = remedial
            _uiState.update {
                it.copy(
                    selectedOptionId = null,
                    textAnswer = "",
                    order = remedial.options.map(LessonOption::id),
                    checked = false,
                    isCorrect = null,
                    showHint = false,
                    pendingRelearningConcept = null,
                    relearningActive = true,
                )
            }
            return
        }

        moveToNextCoreOrComplete(state.currentIndex + 1)
    }

    private fun moveToNextCoreOrComplete(nextIndex: Int) {
        if (nextIndex > steps.lastIndex) {
            completeLesson()
            return
        }
        val next = steps[nextIndex]
        _uiState.update {
            it.copy(
                currentIndex = nextIndex,
                selectedOptionId = null,
                textAnswer = "",
                order = next.options.map(LessonOption::id),
                checked = false,
                isCorrect = null,
                showHint = false,
                pendingRelearningConcept = null,
                relearningActive = false,
            )
        }
    }

    private fun completeLesson() {
        val state = _uiState.value
        val evidenceCount = state.answeredActivities.coerceAtLeast(1)
        val mastery = (state.correctAnswers.toFloat() / evidenceCount * 100).roundToInt().coerceIn(0, 100)
        val reviewDelayMs = when {
            mastery < 50 -> 6 * 60 * 60 * 1000L
            mastery < 75 -> 24 * 60 * 60 * 1000L
            mastery < 90 -> 3 * 24 * 60 * 60 * 1000L
            else -> 7 * 24 * 60 * 60 * 1000L
        }
        val nextReview = System.currentTimeMillis() + reviewDelayMs
        _uiState.update {
            it.copy(
                completed = true,
                masteryPercent = mastery,
                nextReviewAtEpochMs = nextReview,
                relearningActive = false,
            )
        }
        viewModelScope.launch {
            learningRepository.updateReview(
                skillId = definition.skillId,
                masteryPercent = mastery,
                evidenceCount = evidenceCount,
                nextReviewAtEpochMs = nextReview,
            )
        }
    }

    fun restart() {
        syncSelectedLesson()
        remedialStep = null
        _uiState.value = initialState()
    }
}
