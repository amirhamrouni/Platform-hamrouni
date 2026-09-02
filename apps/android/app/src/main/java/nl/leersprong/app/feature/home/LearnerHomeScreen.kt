package nl.leersprong.app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material.icons.rounded.SportsEsports
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.leersprong.app.ui.navigation.LearnerBottomBar
import nl.leersprong.app.ui.navigation.LearnerTab

private val DeepBlue = Color(0xFF062A70)
private val SuccessGreen = Color(0xFF22B66D)
private val WarmYellow = Color(0xFFFFC62E)

@Composable
fun LearnerHomeRoute(
    onContinue: () -> Unit,
    onPlay: () -> Unit,
    onTab: (LearnerTab) -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(bottomBar = { LearnerBottomBar(selected = LearnerTab.Home, onSelect = onTab) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF4F7FC)),
            contentPadding = PaddingValues(bottom = 20.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(DeepBlue, Color(0xFF0A3B92), Color(0xFF0B6D93)))).padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text("LeerSprong NL", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    Column {
                        Text("Hoi ${state.learnerName}! 👋", color = Color.White, fontWeight = FontWeight.Black, fontSize = 30.sp)
                        Text("Groep ${state.group} · klaar voor je volgende slimme stap?", color = Color(0xFFDCEAFF))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(72.dp).clip(CircleShape).background(Color(0xFFB8ED6F)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Rounded.SmartToy, contentDescription = "Leermaatje", tint = DeepBlue, modifier = Modifier.size(42.dp))
                        }
                        Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                            Text(state.coachMessage, modifier = Modifier.padding(14.dp), color = Color(0xFF243B5A), fontWeight = FontWeight.SemiBold, lineHeight = 20.sp)
                        }
                    }
                    Button(onClick = onContinue, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                        Icon(Icons.Rounded.PlayArrow, contentDescription = null)
                        Text("  Ga verder", fontWeight = FontWeight.Black)
                    }
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard(Modifier.weight(1f), "XP", state.xp.toString(), Icons.Rounded.Star)
                    MetricCard(Modifier.weight(1f), "Streak", state.streakDays.toString(), Icons.Rounded.LocalFireDepartment)
                    MetricCard(Modifier.weight(1f), "Badges", state.badges.toString(), Icons.Rounded.EmojiEvents)
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 2.dp).clickable(onClick = onPlay),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF132F74)),
                ) {
                    Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        Box(Modifier.size(58.dp).background(WarmYellow, RoundedCornerShape(18.dp)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Rounded.SportsEsports, contentDescription = null, tint = DeepBlue, modifier = Modifier.size(34.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Speelplein", color = Color.White, fontWeight = FontWeight.Black, fontSize = 21.sp)
                            Text("Memory, Quiz Arena, Dagmissies & challenges", color = Color(0xFFD8E5FF), fontSize = 13.sp)
                        }
                        Text("→", color = Color.White, fontWeight = FontWeight.Black, fontSize = 24.sp)
                    }
                }
            }
            item {
                Text("Vandaag voor jou", modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp), fontWeight = FontWeight.Black, fontSize = 21.sp)
            }
            items(state.tasks, key = { it.id }) { task ->
                Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(task.title, fontWeight = FontWeight.Black)
                                Text(task.subtitle, color = Color(0xFF6B7B91), fontSize = 13.sp)
                            }
                            Text("${task.progress}%", color = SuccessGreen, fontWeight = FontWeight.Black)
                        }
                        LinearProgressIndicator(
                            progress = { task.progress / 100f },
                            modifier = Modifier.fillMaxWidth().height(7.dp).clip(CircleShape),
                            color = SuccessGreen,
                            trackColor = Color(0xFFE5EAF1),
                        )
                    }
                }
            }
            item {
                Card(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3C7))) {
                    Text("🌱 Je voortgang groeit alleen uit echte oefenbewijzen. Geen ingevulde demo-scores.", modifier = Modifier.padding(16.dp), color = Color(0xFF5E4A00), fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun MetricCard(modifier: Modifier, label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = WarmYellow)
            Text(value, fontSize = 23.sp, fontWeight = FontWeight.Black)
            Text(label, fontSize = 12.sp, color = Color(0xFF6B7B91))
        }
    }
}
