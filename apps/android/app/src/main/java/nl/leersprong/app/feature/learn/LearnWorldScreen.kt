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
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import nl.leersprong.app.ui.navigation.LearnerBottomBar
import nl.leersprong.app.ui.navigation.LearnerTab

private data class SubjectCardModel(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val available: Boolean,
)

private val subjects = listOf(
    SubjectCardModel("Rekenen & Wiskunde", "Getallen, optellen, tafels, meten en meer", Icons.Rounded.Calculate, true),
    SubjectCardModel("Nederlands", "Lezen, spelling, woordenschat en taal", Icons.Rounded.AutoStories, false),
    SubjectCardModel("Wereldoriëntatie", "Natuur, geschiedenis en aardrijkskunde", Icons.Rounded.Public, false),
    SubjectCardModel("Engels", "Luisteren, spreken en woordenschat", Icons.Rounded.Language, false),
    SubjectCardModel("Burgerschap", "Samenleven, keuzes en maatschappij", Icons.Rounded.NaturePeople, false),
    SubjectCardModel("Digitale geletterdheid", "Media, informatie en computational thinking", Icons.Rounded.Memory, false),
    SubjectCardModel("Kunst & Cultuur", "Maken, ontdekken en reflecteren", Icons.Rounded.Palette, false),
    SubjectCardModel("NT2 / Thuistaalhulp", "Extra taalsteun terwijl Nederlands centraal blijft", Icons.Rounded.RecordVoiceOver, false),
)

@Composable
fun LearnWorldScreen(
    onStartMath: () -> Unit,
    onTab: (LearnerTab) -> Unit,
) {
    Scaffold(
        bottomBar = { LearnerBottomBar(selected = LearnerTab.Learn, onSelect = onTab) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF4F7FC)),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 8.dp)) {
                    Text("LEERWERELD", color = Color(0xFF5B6B82), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    Text("Wat wil je vandaag ontdekken?", fontWeight = FontWeight.Black, fontSize = 30.sp, color = Color(0xFF062A70))
                    Text("Je pad wordt steeds persoonlijker op basis van wat je laat zien.", color = Color(0xFF64748B), lineHeight = 21.sp)
                }
            }
            items(subjects, key = { it.title }) { subject ->
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
                                if (subject.available) "Beschikbaar · adaptieve les" else "Binnenkort in de native app",
                                color = if (subject.available) Color(0xFF168A4B) else Color(0xFF8A6A00),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            )
                        }
                        if (subject.available) {
                            Button(onClick = onStartMath) { Text("Start") }
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF062A70)),
                    shape = RoundedCornerShape(22.dp),
                ) {
                    Row(modifier = Modifier.padding(18.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Rounded.Map, contentDescription = null, tint = Color(0xFFB8ED6F))
                        Column {
                            Text("Slimme route", color = Color.White, fontWeight = FontWeight.Black)
                            Text("Moeilijke onderdelen komen sneller terug; sterke onderdelen krijgen meer ruimte.", color = Color(0xFFDCEAFF), lineHeight = 20.sp)
                        }
                    }
                }
            }
        }
    }
}
