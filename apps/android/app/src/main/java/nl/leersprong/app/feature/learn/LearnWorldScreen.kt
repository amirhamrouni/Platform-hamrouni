package nl.leersprong.app.feature.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.leersprong.app.feature.lesson.LessonDefinition
import nl.leersprong.app.feature.lesson.LessonLibrary
import nl.leersprong.app.ui.navigation.LearnerBottomBar
import nl.leersprong.app.ui.navigation.LearnerTab

private data class SubjectCardModel(val title: String, val subtitle: String, val icon: ImageVector)
private val subjects = listOf(
    SubjectCardModel("Rekenen & Wiskunde", "Getallen, optellen, tafels, breuken, procenten, verhoudingen en data", Icons.Rounded.Calculate),
    SubjectCardModel("Nederlands", "Lezen, spelling, woordenschat, schrijven en spreken", Icons.Rounded.AutoStories),
    SubjectCardModel("Wereldoriëntatie", "Natuur, geschiedenis en aardrijkskunde", Icons.Rounded.Public),
    SubjectCardModel("Engels", "Luisteren, spreken, lezen en eenvoudige schrijfvaardigheid", Icons.Rounded.Language),
    SubjectCardModel("Burgerschap", "Samenleven, keuzes en maatschappij", Icons.Rounded.NaturePeople),
    SubjectCardModel("Digitale geletterdheid", "Media, informatie en veilig online", Icons.Rounded.Memory),
    SubjectCardModel("Kunst & Cultuur", "Maken, ontdekken en reflecteren", Icons.Rounded.Palette),
    SubjectCardModel("NT2 / Thuistaalhulp", "Extra taalsteun terwijl Nederlands centraal blijft", Icons.Rounded.RecordVoiceOver),
)

@Composable
fun LearnWorldScreen(learnerGroup: Int, onStartLesson: (String) -> Unit, onTab: (LearnerTab) -> Unit) {
    val groupLessons = LessonLibrary.forGroup(learnerGroup)
    val schoolYearPath = SchoolYearLearningPath.forGroup(learnerGroup, groupLessons)
    Scaffold(bottomBar = { LearnerBottomBar(selected = LearnerTab.Learn, onSelect = onTab) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF4F7FC)),
            contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 4.dp)) {
                    Text("LEERWERELD · GROEP $learnerGroup", color = Color(0xFF5B6B82), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    Text("Jouw schooljaar", fontWeight = FontWeight.Black, fontSize = 30.sp, color = Color(0xFF062A70))
                    Text("Lessen staan in een duidelijke leerlijn door het schooljaar. Je niveau en slimme herhaling blijven zich aanpassen aan je echte antwoorden.", color = Color(0xFF64748B), lineHeight = 21.sp)
                }
            }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF)), shape = RoundedCornerShape(22.dp)) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.CalendarMonth, null, tint = Color(0xFF0A58CA))
                        Column {
                            Text("Schooljaar 2026–2027", fontWeight = FontWeight.Black, color = Color(0xFF172B4D))
                            Text("5 leerblokken · SLO-richting · adaptief per leerling", color = Color(0xFF5B6B82), fontSize = 13.sp)
                        }
                    }
                }
            }

            SchoolYearBlock.entries.forEach { block ->
                val blockLessons = schoolYearPath.filter { it.block == block }
                if (blockLessons.isNotEmpty()) {
                    item(key = "header-${block.name}") {
                        Column(Modifier.padding(top = 8.dp, bottom = 2.dp)) {
                            Text(block.label, fontWeight = FontWeight.Black, fontSize = 20.sp, color = Color(0xFF172B4D))
                            Text(block.period, color = Color(0xFF64748B), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    items(blockLessons, key = { it.lesson.id }) { scheduled ->
                        LessonCard(scheduled.lesson, scheduled.sequence) { onStartLesson(scheduled.lesson.id) }
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF062A70)), shape = RoundedCornerShape(22.dp)) {
                    Row(modifier = Modifier.padding(18.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Rounded.Map, null, tint = Color(0xFFB8ED6F))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Slimme route", color = Color.White, fontWeight = FontWeight.Black)
                            Text("De jaarlijn geeft volgorde. Fouten kunnen een herstap activeren en FSRS bepaalt wanneer beheerde stof terugkomt.", color = Color(0xFFDCEAFF), lineHeight = 20.sp)
                        }
                    }
                }
            }
            item { Text("Alle leergebieden", fontWeight = FontWeight.Black, fontSize = 20.sp, color = Color(0xFF172B4D), modifier = Modifier.padding(top = 10.dp)) }
            items(subjects, key = { it.title }) { subject ->
                val count = groupLessons.count { it.subject == subject.title }
                Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.background(Color(0xFFEAF2FF), RoundedCornerShape(16.dp)).padding(12.dp), contentAlignment = Alignment.Center) { Icon(subject.icon, null, tint = Color(0xFF0A58CA)) }
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(subject.title, fontWeight = FontWeight.Black, fontSize = 17.sp)
                            Text(subject.subtitle, color = Color(0xFF6B7B91), fontSize = 13.sp, lineHeight = 18.sp)
                            Text(if (count > 0) "$count native les${if (count == 1) "" else "sen"} beschikbaar" else "Inhoud wordt uitgebreid", color = if (count > 0) Color(0xFF168A4B) else Color(0xFF8A6A00), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonCard(lesson: LessonDefinition, sequence: Int, onStart: () -> Unit) {
    val accent = when (lesson.subject) {
        "Nederlands" -> Color(0xFF7C3AED); "Engels" -> Color(0xFF0F8A83); "Wereldoriëntatie" -> Color(0xFF2E7D32)
        "Burgerschap" -> Color(0xFFD05A2B); "Digitale geletterdheid" -> Color(0xFF4557C4); "Kunst & Cultuur" -> Color(0xFFE88A16)
        "NT2 / Thuistaalhulp" -> Color(0xFF8D4AC7); else -> Color(0xFF0A58CA)
    }
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(Modifier.background(accent.copy(alpha = .10f), RoundedCornerShape(14.dp)).padding(10.dp), contentAlignment = Alignment.Center) { Icon(subjectIcon(lesson.subject), null, tint = accent) }
                Column(Modifier.weight(1f)) {
                    Text("LES $sequence · ${lesson.subject}", color = accent, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    Text(lesson.title, fontWeight = FontWeight.Black, fontSize = 19.sp, color = Color(0xFF172B4D))
                }
                Text("± ${lesson.estimatedMinutes} min", color = Color(0xFF6B7B91), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text("${lesson.steps.size} kernactiviteiten · hints · feedback · slimme review", color = Color(0xFF64748B), fontSize = 13.sp)
            Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Rounded.PlayArrow, null); Text("  Start les", fontWeight = FontWeight.Black) }
        }
    }
}

private fun subjectIcon(subject: String): ImageVector = when (subject) {
    "Nederlands" -> Icons.Rounded.AutoStories; "Engels" -> Icons.Rounded.Language; "Wereldoriëntatie" -> Icons.Rounded.Public
    "Burgerschap" -> Icons.Rounded.NaturePeople; "Digitale geletterdheid" -> Icons.Rounded.Memory; "Kunst & Cultuur" -> Icons.Rounded.Palette
    "NT2 / Thuistaalhulp" -> Icons.Rounded.RecordVoiceOver; else -> Icons.Rounded.Calculate
}
