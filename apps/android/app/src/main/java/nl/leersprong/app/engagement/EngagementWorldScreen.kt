package nl.leersprong.app.engagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EngagementWorldScreen(
    learnerGroup: Int,
    onStartLesson: (String) -> Unit,
    onBack: () -> Unit,
) {
    var section by remember { mutableStateOf("games") }

    when (section) {
        "games" -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFF051F53)).padding(horizontal = 8.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Button(onClick = { section = "games" }, shape = RoundedCornerShape(14.dp)) { Text("🎮") }
                    OutlinedButton(onClick = { section = "missions" }, shape = RoundedCornerShape(14.dp)) { Text("⭐", color = Color.White) }
                    OutlinedButton(onClick = { section = "discover" }, shape = RoundedCornerShape(14.dp)) { Text("🌍", color = Color.White) }
                    OutlinedButton(onClick = { section = "art" }, shape = RoundedCornerShape(14.dp)) { Text("🎨", color = Color.White) }
                    OutlinedButton(onClick = { section = "story" }, shape = RoundedCornerShape(14.dp)) { Text("📖", color = Color.White) }
                }
                EngagementHubScreen(
                    learnerGroup = learnerGroup,
                    onStartLesson = onStartLesson,
                    onBack = onBack,
                )
            }
        }
        "discover" -> ExploreWorldScreen(
            learnerGroup = learnerGroup,
            onBack = { section = "games" },
        )
        "art" -> ArtLabScreen(
            learnerGroup = learnerGroup,
            onBack = { section = "games" },
        )
        "story" -> StoryWorldScreen(
            learnerGroup = learnerGroup,
            onBack = { section = "games" },
        )
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Color(0xFFF5F8FD)),
                contentPadding = PaddingValues(bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().background(Color(0xFF062A70)).padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            OutlinedButton(onClick = onBack) { Text("← Home", color = Color.White) }
                            OutlinedButton(onClick = { section = "games" }) { Text("🎮", color = Color.White) }
                            OutlinedButton(onClick = { section = "discover" }) { Text("🌍", color = Color.White) }
                            OutlinedButton(onClick = { section = "art" }) { Text("🎨", color = Color.White) }
                            OutlinedButton(onClick = { section = "story" }) { Text("📖", color = Color.White) }
                        }
                        Text("Missies & Stem", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                        Text("Kom elke dag terug voor sterren, stemtraining en nieuwe beloningen.", color = Color(0xFFDCEAFF))
                    }
                }
                item { RetentionPanel(learnerGroup) }
            }
        }
    }
}
