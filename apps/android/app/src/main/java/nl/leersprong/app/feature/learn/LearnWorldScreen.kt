package nl.leersprong.app.feature.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.NaturePeople
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.leersprong.app.feature.lesson.AllLessons
import nl.leersprong.app.feature.lesson.LessonDefinition
import nl.leersprong.app.ui.navigation.LearnerBottomBar
import nl.leersprong.app.ui.navigation.LearnerTab

private data class SubjectCardModel(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
)

private val subjects = listOf(
    SubjectCardModel("Rekenen & Wiskunde", "Getallen, optellen, tafels, breuken, procenten, verhoudingen en data", Icons.Rounded.Calculate),
    SubjectCardModel("Nederlands", "Lezen, spelling, woordenschat, schrijven en spreken", Icons.Rounded.AutoStories),
    SubjectCardModel("Wereldoriëntatie", "Natuur, geschiedenis en aardrijkskunde", Icons.Rounded.Public),
    SubjectCardModel("Engels", "Luisteren, spreken en woordenschat", Icons.Rounded.Language),
    SubjectCardModel("Burgerschap", "Samenleven, keuzes en maatschappij", Icons.Rounded.NaturePeople),
    SubjectCardModel("Digitale geletterdheid", "Media, informatie en computational thinking", Icons.Rounded.Memory),
    SubjectCardModel("Kunst & Cultuur", "Maken, ontdekken en reflecteren", Icons.Rounded.Palette),
    SubjectCardModel("NT2 / Thuistaalhulp", "Extra taalsteun terwijl Nederlands centraal blijft", Icons.Rounded.RecordVoiceOver),
)

@Composable
fun LearnWorldScreen(
    learnerGroup: Int,
    onStartLesson: (String) -> Unit,
    onTab: (LearnerTab) -> Unit,
) {
    val groupLessons = AllLessons.forGroup(learnerGroup)

    Scaffold(
        bottomBar = { LearnerBottomBar(selected = LearnerTab.Learn, onSelect = onTab) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF4F7FC)),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 4.dp)) {
                    Text("LEERWERELD · GROEP $learnerGroup", color = Color(0xFF5B6B82), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    Text("Wat wil je vandaag ontdekken?", fontWeight = FontWeight.Black, fontSize = 30.sp, color = Color(0xFF062A70))
                    Text("Je ziet alleen lessen die echt in de native app werken. Nieuwe onderdelen komen erbij zonder lege knoppen.", color = Color(0xFF64748B), lineHeight = 21.sp)
                }
            }

            item {
                Text("Voor jouw groep", fontWeight = FontWeight.Black, fontSize = 20.sp, color = Color(0xFF172B4D), modifier = Modifier.padding(top = 8.dp))
            }

            items(groupLessons, key = { it.id }) { lesson ->
                LessonCard(lesson = lesson, onStart = { onStartLesson(lesson.id) })
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF062A70)),
                    shape = RoundedCornerShape(22.dp),
                ) {
                    Row(modifier = Modifier.padding(18.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Rounded.Map, contentDescription = null, tint = Color(0xFFB8ED6F))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Slimme route", color = Color.White, fontWeight = FontWeight.Black)
                            Text("Fouten kunnen een korte herstapoefening activeren. Je beheersing en volgende review worden lokaal uit echte antwoorden opgebouwd.", color = Color(0xFFDCEAFF), lineHeight = 20.sp)
                        }
                    }
                }
            }

            item {
                Text("Alle leergebieden", fontWeight = FontWeight.Black, fontSize = 20.sp, color = Color(0xFF172B4D), modifier = Modifier.padding(top = 10.dp))
            }

            items(subjects, key = { it.title }) { subject ->
                val count = groupLessons.count { it.subject == subject.title }
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.background(Color(0xFFEAF2FF), RoundedCornerShape(16.dp)).padding(12.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(subject.icon, contentDescription = null, tint = Color(0xFF0A58CA))
                        }
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(subject.title, fontWeight = FontWeight.Black, fontSize = 17.sp)
                            Text(subject.subtitle, color = Color(0xFF6B7B91), fontSize = 13.sp, lineHeight = 18.sp)
                            Text(
                                if (count > 0) "$count native les${if (count == 1) "" else "sen"} beschikbaar" else "Volgende contentronde",
                                color = if (count > 0) Color(0xFF168A4B) else Color(0xFF8A6A00),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonCard(
    lesson: LessonDefinition,
    onStart: () -> Unit,
) {
    val accent = if (lesson.subject == "Nederlands") Color(0xFF7C3AED) else Color(0xFF0A58CA)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier.background(accent.copy(alpha = 0.10f), RoundedCornerShape(14.dp)).padding(10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        if (lesson.subject == "Nederlands") Icons.Rounded.AutoStories else Icons.Rounded.Calculate,
                        contentDescription = null,
                        tint = accent,
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(lesson.subject, color = accent, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    Text(lesson.title, fontWeight = FontWeight.Black, fontSize = 19.sp, color = Color(0xFF172B4D))
                }
                Text("± ${lesson.estimatedMinutes} min", color = Color(0xFF6B7B91), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text("${lesson.steps.size} kernactiviteiten · hints · feedback · slimme herstap · review", color = Color(0xFF64748B), fontSize = 13.sp)
            Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Rounded.PlayArrow, contentDescription = null)
                Text("  Start les", fontWeight = FontWeight.Black)
            }
        }
    }
}
