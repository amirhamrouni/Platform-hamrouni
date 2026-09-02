package nl.leersprong.app.feature.buddy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.leersprong.app.feature.home.HomeViewModel
import nl.leersprong.app.ui.navigation.LearnerBottomBar
import nl.leersprong.app.ui.navigation.LearnerTab

@Composable
fun BuddyRoute(
    onStartLesson: () -> Unit,
    onTab: (LearnerTab) -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val math = state.tasks.firstOrNull { it.id == "math" }
    val review = state.tasks.firstOrNull { it.id == "review" }

    Scaffold(bottomBar = { LearnerBottomBar(selected = LearnerTab.Buddy, onSelect = onTab) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF4F7FC)),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.size(72.dp).clip(CircleShape).background(Color(0xFFB8ED6F)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.SmartToy, contentDescription = "Leermaatje", tint = Color(0xFF062A70), modifier = Modifier.size(42.dp))
                    }
                    Column {
                        Text("AI LEERMAATJE", color = Color(0xFF607089), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                        Text("Ik kijk mee met je leerbewijs", color = Color(0xFF062A70), fontWeight = FontWeight.Black, fontSize = 25.sp)
                    }
                }
            }
            item {
                Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Wat ik nu weet", fontWeight = FontWeight.Black, fontSize = 18.sp)
                        Text(
                            when {
                                (review?.subtitle ?: "").contains("klaar") -> "Je hebt een herhaling klaarstaan. Die krijgt nu voorrang omdat herhalen op het juiste moment helpt om kennis terug te halen."
                                (math?.progress ?: 0) > 0 -> "Je laatste rekenbewijs staat op ${math?.progress ?: 0}%. We bouwen verder vanaf wat je al hebt laten zien."
                                else -> "Ik heb nog weinig leerbewijs. Doe één korte rekenles; daarna kan ik je volgende stap beter kiezen."
                            },
                            color = Color(0xFF53657D),
                            lineHeight = 21.sp,
                        )
                    }
                }
            }
            item {
                Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3C7))) {
                    Row(modifier = Modifier.padding(18.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Rounded.Lightbulb, contentDescription = null, tint = Color(0xFF8A6A00))
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text("Slimme hulp zonder toneel", fontWeight = FontWeight.Black, color = Color(0xFF5E4A00))
                            Text("Deze native versie gebruikt nu je echte lokale voortgang voor coaching. Vrije AI-chat komt pas wanneer de beveiligde backend is aangesloten.", color = Color(0xFF6D5A18), lineHeight = 20.sp)
                        }
                    }
                }
            }
            item {
                Button(onClick = onStartLesson, modifier = Modifier.fillMaxWidth()) {
                    Text("Doe een slimme oefening", fontWeight = FontWeight.Black)
                }
            }
        }
    }
}
