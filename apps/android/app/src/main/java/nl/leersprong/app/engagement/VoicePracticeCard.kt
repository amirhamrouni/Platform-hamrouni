package nl.leersprong.app.engagement

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun VoicePracticeCard(
    learnerGroup: Int,
    onMissionComplete: () -> Unit,
) {
    val context = LocalContext.current
    val phrase = if (learnerGroup <= 4) "Goedemorgen, hoe gaat het?" else "Could you help me with this question?"
    val locale = if (learnerGroup <= 4) Locale("nl", "NL") else Locale.ENGLISH
    var heardText by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("Luister eerst en spreek daarna de zin na.") }
    var ttsReady by remember { mutableStateOf(false) }
    val tts = remember { TextToSpeech(context) { status -> ttsReady = status == TextToSpeech.SUCCESS } }

    DisposableEffect(tts) {
        onDispose { tts.shutdown() }
    }

    val speechLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val recognized = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull().orEmpty()
            heardText = recognized
            val normalizedExpected = phrase.lowercase(locale).replace(Regex("[^a-zà-ÿ ]"), "").trim()
            val normalizedHeard = recognized.lowercase(locale).replace(Regex("[^a-zà-ÿ ]"), "").trim()
            val expectedWords = normalizedExpected.split(" ").filter { it.isNotBlank() }.toSet()
            val heardWords = normalizedHeard.split(" ").filter { it.isNotBlank() }.toSet()
            val overlap = if (expectedWords.isEmpty()) 0f else expectedWords.intersect(heardWords).size.toFloat() / expectedWords.size
            feedback = when {
                normalizedHeard == normalizedExpected -> "Perfect uitgesproken! 🎤⭐"
                overlap >= 0.7f -> "Heel goed. Bijna helemaal hetzelfde!"
                recognized.isBlank() -> "Ik hoorde niets. Probeer nog eens rustig."
                else -> "Goed geprobeerd. Luister nog een keer en probeer opnieuw."
            }
            if (overlap >= 0.7f || normalizedHeard == normalizedExpected) onMissionComplete()
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF5FF)),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Luister & Spreek", fontWeight = FontWeight.Black)
            Text("\"$phrase\"", fontWeight = FontWeight.Bold)
            Text(feedback, color = Color(0xFF53657E))
            if (heardText.isNotBlank()) Text("Ik hoorde: $heardText", fontWeight = FontWeight.SemiBold)
            OutlinedButton(
                enabled = ttsReady,
                onClick = {
                    tts.language = locale
                    tts.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, "leersprong-voice")
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(Icons.Rounded.Headphones, contentDescription = null)
                Text("  Luister")
            }
            Button(
                onClick = {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.toLanguageTag())
                        putExtra(RecognizerIntent.EXTRA_PROMPT, "Spreek de zin na")
                    }
                    speechLauncher.launch(intent)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(Icons.Rounded.Mic, contentDescription = null)
                Text("  Spreek na")
            }
        }
    }
}
