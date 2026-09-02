package nl.leersprong.app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.leersprong.app.ui.theme.LeerSprongTheme

private val DeepBlue = Color(0xFF062A70)
private val HeroBlue = Color(0xFF0A3B92)
private val SuccessGreen = Color(0xFF22B66D)
private val WarmYellow = Color(0xFFFFC62E)

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = viewModel(),
    onContinue: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(state = state, onContinue = onContinue)
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onContinue: () -> Unit,
) {
    Scaffold(
        bottomBar = { LearnerBottomBar() },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item {
                HeroSection(state = state, onContinue = onContinue)
            }
            item {
                LearningPathCard(state = state)
            }
            item {
                StatsRow(state = state)
            }
            item {
                SectionHeader(title = "Vandaag voor jou", action = "${state.tasks.size} taken")
            }
            items(state.tasks, key = { it.id }) { task ->
                TodayTaskCard(task = task)
            }
            item { Spacer(Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun HeroSection(state: HomeUiState, onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(DeepBlue, HeroBlue, Color(0xFF0B6D93)),
                ),
            )
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF1C83E8)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("LS", color = Color.White, fontWeight = FontWeight.Black)
                }
                Text(
                    "  LeerSprong NL",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Notifications, contentDescription = "Meldingen", tint = Color.White)
                }
            }

            Column {
                Text("Hoi ${state.learnerName}! 👋", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                Text("Klaar om verder te leren?", color = Color(0xFFDCEAFF), fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB8ED6F)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Rounded.SmartToy, contentDescription = "Leermaatje", tint = DeepBlue, modifier = Modifier.size(48.dp))
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text(
                        state.coachMessage,
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF213A5B),
                        lineHeight = 21.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WarmYellow, contentColor = Color(0xFF2B2500)),
            ) {
                Icon(Icons.Rounded.PlayArrow, contentDescription = null)
                Text("  Ga verder", fontSize = 18.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun LearningPathCard(state: HomeUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Jouw pad", fontSize = 20.sp, fontWeight = FontWeight.Black)
                    Text("${state.currentLesson} · Groep ${state.group}", color = Color(0xFF6B7B91))
                }
                Text("Bekijk alles", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PathStep(icon = Icons.Rounded.Home, label = "Start", done = true)
                PathConnector(done = true)
                PathStep(icon = Icons.Rounded.AutoStories, label = "Les ${state.pathStep}", done = false, active = true)
                PathConnector(done = false)
                PathStep(icon = Icons.Rounded.EmojiEvents, label = "Les 3", done = false)
                PathConnector(done = false)
                PathStep(icon = Icons.Rounded.School, label = "Les 4", done = false)
            }
        }
    }
}

@Composable
private fun PathStep(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    done: Boolean,
    active: Boolean = false,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(
                    when {
                        done -> SuccessGreen
                        active -> Color(0xFF2788F6)
                        else -> Color(0xFFE8EDF4)
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = if (done || active) Color.White else Color(0xFF8491A5))
        }
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun PathConnector(done: Boolean) {
    Box(
        modifier = Modifier
            .height(4.dp)
            .size(width = 26.dp, height = 4.dp)
            .clip(CircleShape)
            .background(if (done) SuccessGreen else Color(0xFFDDE4ED)),
    )
}

@Composable
private fun StatsRow(state: HomeUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        StatCard(Modifier.weight(1f), Icons.Rounded.LocalFireDepartment, "Streak", "${state.streakDays}", "dagen", Color(0xFFFF7138))
        StatCard(Modifier.weight(1f), Icons.Rounded.Star, "XP", "${state.xp}", "punten", WarmYellow)
        StatCard(Modifier.weight(1f), Icons.Rounded.EmojiEvents, "Badges", "${state.badges}", "verdiend", Color(0xFF8359E8))
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    subtitle: String,
    color: Color,
) {
    Card(modifier = modifier, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = color)
            Text(title, fontSize = 12.sp, color = Color(0xFF68768B))
            Text(value, fontSize = 23.sp, fontWeight = FontWeight.Black)
            Text(subtitle, fontSize = 11.sp, color = Color(0xFF68768B))
        }
    }
}

@Composable
private fun SectionHeader(title: String, action: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, modifier = Modifier.weight(1f), fontSize = 20.sp, fontWeight = FontWeight.Black)
        Text(action, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun TodayTaskCard(task: HomeTask) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE9F5FF)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    if (task.id == "math") Icons.Rounded.EmojiEvents else if (task.id == "language") Icons.Rounded.AutoStories else Icons.Rounded.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(task.title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Text(task.subtitle, color = Color(0xFF68768B), fontSize = 13.sp)
                LinearProgressIndicator(
                    progress = { task.progress / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp)
                        .clip(CircleShape),
                    color = SuccessGreen,
                    trackColor = Color(0xFFE4EAF1),
                )
            }
            Text("${task.progress}%", fontWeight = FontWeight.Black, color = SuccessGreen)
        }
    }
}

@Composable
private fun LearnerBottomBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Rounded.Home, null) }, label = { Text("Home") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.AutoStories, null) }, label = { Text("Leren") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.SmartToy, null) }, label = { Text("Maatje") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.Leaderboard, null) }, label = { Text("Voortgang") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.MoreHoriz, null) }, label = { Text("Meer") })
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun HomePreview() {
    LeerSprongTheme {
        HomeScreen(state = HomeUiState(), onContinue = {})
    }
}
