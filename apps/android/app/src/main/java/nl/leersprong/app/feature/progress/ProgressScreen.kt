package nl.leersprong.app.feature.progress

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.leersprong.app.feature.home.HomeViewModel
import nl.leersprong.app.reminder.ReviewReminderScheduler
import nl.leersprong.app.ui.navigation.LearnerBottomBar
import nl.leersprong.app.ui.navigation.LearnerTab
import java.text.DateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProgressRoute(
    onTab: (LearnerTab) -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val math = state.tasks.firstOrNull { it.id == "math" }
    val review = state.tasks.firstOrNull { it.id == "review" }
    val context = LocalContext.current
    var reminderScheduled by remember { mutableStateOf(false) }

    fun scheduleReminder() {
        val reviewAt = state.nextReviewAtEpochMs ?: return
        ReviewReminderScheduler.schedule(context, reviewAt)
        reminderScheduled = true
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) scheduleReminder()
    }

    val reviewLabel = state.nextReviewAtEpochMs?.let {
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("nl", "NL")).format(Date(it))
    }

    Scaffold(bottomBar = { LearnerBottomBar(selected = LearnerTab.Progress, onSelect = onTab) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF4F7FC)),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text("VOORTGANG", color = Color(0xFF607089), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    Text("Dit heb je echt opgebouwd", color = Color(0xFF062A70), fontWeight = FontWeight.Black, fontSize = 30.sp)
                    Text("Alle cijfers hieronder komen uit lokaal opgeslagen oefenbewijzen.", color = Color(0xFF65758B))
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SummaryCard(Modifier.weight(1f), "XP", state.xp.toString())
                    SummaryCard(Modifier.weight(1f), "Badges", state.badges.toString())
                }
            }
            item {
                EvidenceCard(
                    title = "Rekenen · Tafels begrijpen",
                    subtitle = math?.subtitle ?: "Nog geen bewijs",
                    progress = math?.progress ?: 0,
                )
            }
            item {
                EvidenceCard(
                    title = "Slim herhalen",
                    subtitle = review?.subtitle ?: "Nog geen review gepland",
                    progress = review?.progress ?: 0,
                )
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF)),
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Rounded.NotificationsActive, contentDescription = null, tint = Color(0xFF0A58CA))
                            Text("Slimme herinnering", fontWeight = FontWeight.Black, fontSize = 17.sp, color = Color(0xFF062A70))
                        }
                        Text(
                            when {
                                reminderScheduled -> "Herinnering staat aan voor $reviewLabel."
                                reviewLabel != null -> "Je volgende slimme herhaling staat gepland voor $reviewLabel. Je kiest zelf of je een melding wilt."
                                else -> "Na je eerste afgeronde les kan je hier een herinnering instellen."
                            },
                            color = Color(0xFF53657D),
                        )
                        Button(
                            enabled = state.nextReviewAtEpochMs != null && !reminderScheduled,
                            onClick = {
                                val needsPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                                    ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                                if (needsPermission) {
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    scheduleReminder()
                                }
                            },
                        ) {
                            Text(
                                when {
                                    reminderScheduled -> "Herinnering ingesteld"
                                    state.nextReviewAtEpochMs == null -> "Voltooi eerst een les"
                                    else -> "Herinner mij"
                                },
                                fontWeight = FontWeight.Black,
                            )
                        }
                    }
                }
            }
            item {
                Text("Jouw leerbewijs", fontWeight = FontWeight.Black, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp))
            }
            items(state.tasks, key = { "evidence-${it.id}" }) { task ->
                Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(task.title, fontWeight = FontWeight.Black)
                        Text(task.subtitle, color = Color(0xFF69788D), fontSize = 13.sp)
                        Text("${task.progress}%", color = Color(0xFF168A4B), fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(modifier: Modifier, label: String, value: String) {
    Card(modifier = modifier, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(label, color = Color(0xFF69788D), fontSize = 12.sp)
            Text(value, color = Color(0xFF062A70), fontWeight = FontWeight.Black, fontSize = 30.sp)
        }
    }
}

@Composable
private fun EvidenceCard(title: String, subtitle: String, progress: Int) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, fontWeight = FontWeight.Black, fontSize = 17.sp)
            Text(subtitle, color = Color(0xFF69788D))
            LinearProgressIndicator(
                progress = { progress.coerceIn(0, 100) / 100f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = Color(0xFF22B66D),
                trackColor = Color(0xFFE4EAF1),
            )
        }
    }
}
