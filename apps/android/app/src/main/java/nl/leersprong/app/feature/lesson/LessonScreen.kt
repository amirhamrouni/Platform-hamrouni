package nl.leersprong.app.feature.lesson

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.DateFormat
import java.util.Date
import java.util.Locale

private val LessonBlue = Color(0xFF062A70)
private val LessonGreen = Color(0xFF20B866)
private val LessonYellow = Color(0xFFFFC62E)

@Composable
fun LessonRoute(
    onBack: () -> Unit,
    viewModel: LessonViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LessonScreen(
        state = state,
        step = viewModel.currentStep(),
        totalSteps = viewModel.stepCount(),
        onBack = onBack,
        onOption = viewModel::selectOption,
        onMove = viewModel::moveOrderItem,
        onHint = viewModel::toggleHint,
        onCheck = viewModel::checkAnswer,
        onContinue = viewModel::continueLesson,
        onRestart = viewModel::restart,
    )
}

@Composable
private fun LessonScreen(
    state: LessonUiState,
    step: LessonStep,
    totalSteps: Int,
    onBack: () -> Unit,
    onOption: (String) -> Unit,
    onMove: (String, Int) -> Unit,
    onHint: () -> Unit,
    onCheck: () -> Unit,
    onContinue: () -> Unit,
    onRestart: () -> Unit,
) {
    if (state.completed) {
        LessonComplete(state = state, onBack = onBack, onRestart = onRestart)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FC))
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Terug") }
            LinearProgressIndicator(
                progress = { (state.currentIndex + 1f) / totalSteps },
                modifier = Modifier.weight(1f).height(9.dp).clip(CircleShape),
                color = LessonGreen,
                trackColor = Color(0xFFDCE5F1),
            )
            Text("  ${state.currentIndex + 1}/$totalSteps", fontWeight = FontWeight.ExtraBold)
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFB8ED6F)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Rounded.SmartToy, contentDescription = null, tint = LessonBlue)
            }
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                Text(
                    if (state.checked && state.isCorrect == true) "Uitstekend! Je strategie klopt." else if (state.checked) "Bijna. Kijk naar de uitleg en probeer de denkwijze te volgen." else "${step.title}. Neem je tijd — ik help als je vastloopt.",
                    modifier = Modifier.padding(14.dp),
                    color = Color(0xFF263A58),
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(state.lessonTitle, color = Color(0xFF718096), fontWeight = FontWeight.Bold)
                Text(step.prompt, fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.Black, color = LessonBlue)

                if (step.interaction == LessonInteractionType.ListenChoose && step.speakText != null) {
                    DutchSpeechButton(text = step.speakText)
                }

                when (step.interaction) {
                    LessonInteractionType.MultipleChoice,
                    LessonInteractionType.ListenChoose -> step.options.forEach { option ->
                        val selected = state.selectedOptionId == option.id
                        val correctSelected = state.checked && option.id == step.correctOptionId
                        val wrongSelected = state.checked && selected && !correctSelected
                        val background = when {
                            correctSelected -> Color(0xFFE7F8ED)
                            wrongSelected -> Color(0xFFFFECEC)
                            selected -> Color(0xFFEAF2FF)
                            else -> Color.White
                        }
                        val border = when {
                            correctSelected -> LessonGreen
                            wrongSelected -> Color(0xFFE24D4D)
                            selected -> Color(0xFF3B82F6)
                            else -> Color(0xFFD7DFEA)
                        }
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable(enabled = !state.checked) { onOption(option.id) },
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = background),
                            border = androidx.compose.foundation.BorderStroke(2.dp, border),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 17.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(option.label, modifier = Modifier.weight(1f), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                                if (correctSelected) Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = LessonGreen)
                            }
                        }
                    }

                    LessonInteractionType.Ordering -> state.order.forEachIndexed { index, id ->
                        val option = step.options.first { it.id == id }
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFD)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD7DFEA)),
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text("${index + 1}", modifier = Modifier.size(28.dp), fontWeight = FontWeight.Black, color = LessonBlue)
                                Text(option.label, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                                IconButton(enabled = index > 0 && !state.checked, onClick = { onMove(id, -1) }) {
                                    Icon(Icons.Rounded.ArrowUpward, contentDescription = "Omhoog")
                                }
                                IconButton(enabled = index < state.order.lastIndex && !state.checked, onClick = { onMove(id, 1) }) {
                                    Icon(Icons.Rounded.ArrowDownward, contentDescription = "Omlaag")
                                }
                            }
                        }
                    }
                }

                OutlinedButton(onClick = onHint, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Rounded.Lightbulb, contentDescription = null)
                    Text(if (state.showHint) "  Verberg hint" else "  Geef een hint")
                }

                if (state.showHint) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7D8)),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Text(step.hint, modifier = Modifier.padding(14.dp), color = Color(0xFF604A00), lineHeight = 21.sp)
                    }
                }

                if (state.checked) {
                    Card(
                        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                        colors = CardDefaults.cardColors(containerColor = if (state.isCorrect == true) Color(0xFFE8F8EE) else Color(0xFFFFF0E2)),
                        shape = RoundedCornerShape(18.dp),
                    ) {
                        Column(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(if (state.isCorrect == true) "Goed gedaan! 🎉" else "We leren van deze fout", fontWeight = FontWeight.Black)
                            Text(step.explanation, lineHeight = 21.sp)
                            Text("+${if (state.isCorrect == true) 20 else 5} XP", color = LessonGreen, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = if (state.checked) onContinue else onCheck,
            enabled = state.checked || step.interaction == LessonInteractionType.Ordering || state.selectedOptionId != null,
            modifier = Modifier.fillMaxWidth().height(58.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (state.checked) LessonGreen else LessonYellow, contentColor = if (state.checked) Color.White else Color(0xFF2F2700)),
        ) {
            Text(if (state.checked) "Volgende" else "Controleer", fontSize = 17.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun DutchSpeechButton(text: String) {
    val context = LocalContext.current
    var ready by remember { mutableStateOf(false) }
    val speaker = remember {
        TextToSpeech(context) { status ->
            ready = status == TextToSpeech.SUCCESS
        }
    }
    DisposableEffect(speaker) {
        onDispose {
            speaker.stop()
            speaker.shutdown()
        }
    }
    Button(
        onClick = {
            if (ready) {
                speaker.language = Locale("nl", "NL")
                speaker.setSpeechRate(0.9f)
                speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null, "leersprong-listen")
            }
        },
        enabled = ready,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEAF2FF), contentColor = LessonBlue),
    ) {
        Icon(Icons.Rounded.VolumeUp, contentDescription = null)
        Text("  Luister opnieuw", fontWeight = FontWeight.Black)
    }
}

@Composable
private fun LessonComplete(state: LessonUiState, onBack: () -> Unit, onRestart: () -> Unit) {
    val reviewLabel = state.nextReviewAtEpochMs?.let {
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("nl", "NL")).format(Date(it))
    }
    Column(
        modifier = Modifier.fillMaxSize().background(LessonBlue).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.size(110.dp).clip(CircleShape).background(LessonYellow),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Rounded.Star, contentDescription = null, tint = LessonBlue, modifier = Modifier.size(64.dp))
        }
        Spacer(Modifier.height(24.dp))
        Text("Les afgerond!", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
        Text("Je verdiende ${state.earnedXp} XP", color = Color(0xFFDCEAFF), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("${state.masteryPercent}% beheersing · ${state.correctAnswers} goed", color = Color(0xFFB8ED6F), fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 8.dp))
        Text("${state.mistakes} fout${if (state.mistakes == 1) "" else "en"} gebruikt om slimmer te oefenen", color = Color(0xFFBFD5FF), textAlign = TextAlign.Center, modifier = Modifier.padding(vertical = 12.dp))
        if (reviewLabel != null) {
            Text("Slimme herhaling: $reviewLabel", color = Color.White, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().padding(top = 18.dp), colors = ButtonDefaults.buttonColors(containerColor = LessonYellow, contentColor = Color(0xFF2F2700))) {
            Text("Terug naar mijn pad", fontWeight = FontWeight.Black)
        }
        OutlinedButton(onClick = onRestart, modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
            Icon(Icons.Rounded.Refresh, contentDescription = null)
            Text("  Nog een keer")
        }
    }
}
