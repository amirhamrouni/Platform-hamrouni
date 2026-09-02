package nl.leersprong.app.engagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun RetentionPanel(learnerGroup: Int) {
    val context = LocalContext.current
    val repository = remember { EngagementProgressRepository(context.applicationContext) }
    val progress by repository.progress.collectAsState(initial = EngagementProgress())
    val scope = rememberCoroutineScope()

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Column(modifier = Modifier.padding(horizontal = 18.dp)) {
            Text("Dagmissies", fontWeight = FontWeight.Black, color = Color(0xFF13294B))
            Text("${progress.completedMissionCount}/3 vandaag · ${progress.totalStars} sterren verzameld", color = Color(0xFF68788F))
            LinearProgressIndicator(
                progress = { progress.completedMissionCount / 3f },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            )
        }

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PersistentMissionCard(Modifier.weight(1f), "🧩", "Memory", progress.missionDone(0))
            PersistentMissionCard(Modifier.weight(1f), "🎤", "Stem", progress.missionDone(1))
            PersistentMissionCard(Modifier.weight(1f), "⚡", "Quiz", progress.missionDone(2))
        }

        VoicePracticeCard(
            learnerGroup = learnerGroup,
            onMissionComplete = { scope.launch { repository.completeMission(1) } },
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5D6)),
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Mijn collectie", fontWeight = FontWeight.Black)
                Text("Beloningen worden ontgrendeld door echte dagelijkse activiteit.", color = Color(0xFF6B5B31))
                RewardRow("🌱 Eerste Ster", progress.totalStars >= 1)
                RewardRow("🎯 Missiemeester", progress.completedDays >= 1)
                RewardRow("⭐ Sterrenjager", progress.totalStars >= 10)
                RewardRow("🏆 Weekheld", progress.completedDays >= 7)
            }
        }
    }
}

@Composable
private fun PersistentMissionCard(modifier: Modifier, emoji: String, title: String, completed: Boolean) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = if (completed) Color(0xFFDDF7E8) else Color.White),
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(emoji)
            Text(title, fontWeight = FontWeight.Black)
            Text(if (completed) "Klaar ✓" else "Nog te doen", color = if (completed) Color(0xFF0C8053) else Color(0xFF7A8799))
        }
    }
}

@Composable
private fun RewardRow(title: String, unlocked: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(if (unlocked) title else "🔒 ${title.substringAfter(' ')}", fontWeight = FontWeight.SemiBold)
        Text(if (unlocked) "Ontgrendeld" else "Nog niet", color = if (unlocked) Color(0xFF0C8053) else Color(0xFF8A8F98))
    }
}
