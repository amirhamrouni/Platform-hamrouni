package nl.leersprong.app.engagement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Biotech
import androidx.compose.material.icons.rounded.HistoryEdu
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class ExploreQuestion(
    val category: String,
    val prompt: String,
    val answers: List<String>,
    val correctIndex: Int,
    val explanation: String,
)

@Composable
fun ExploreWorldScreen(learnerGroup: Int, onBack: () -> Unit) {
    val questions = remember(learnerGroup) { exploreQuestions(learnerGroup) }
    var index by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var feedback by remember { mutableStateOf<String?>(null) }
    val current = questions[index]

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF4F8FC)),
        contentPadding = PaddingValues(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth().background(Color(0xFF0A4A67)).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                OutlinedButton(onClick = onBack) { Text("← Speelplein", color = Color.White) }
                Text("Ontdek de Wereld", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                Text("Aardrijkskunde, natuur, techniek en geschiedenis in korte speelrondes.", color = Color(0xFFD9F5FF))
                LinearProgressIndicator(
                    progress = { (index + 1) / questions.size.toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFD24A),
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ExploreCategoryCard(Modifier.weight(1f), "Wereld", "🌍", Icons.Rounded.Public)
                ExploreCategoryCard(Modifier.weight(1f), "Natuur", "🔬", Icons.Rounded.Biotech)
                ExploreCategoryCard(Modifier.weight(1f), "Historie", "🏛️", Icons.Rounded.HistoryEdu)
                ExploreCategoryCard(Modifier.weight(1f), "Techniek", "🚀", Icons.Rounded.RocketLaunch)
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(22.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(current.category, color = Color(0xFF0A6A78), fontWeight = FontWeight.Black)
                        Text("$score ⭐", fontWeight = FontWeight.Black)
                    }
                    Text(current.prompt, fontSize = 21.sp, fontWeight = FontWeight.Black, color = Color(0xFF152A45))
                    current.answers.forEachIndexed { answerIndex, answer ->
                        Button(
                            onClick = {
                                if (feedback == null) {
                                    val correct = answerIndex == current.correctIndex
                                    if (correct) score += 1
                                    feedback = if (correct) "Goed! ${current.explanation}" else "Bijna. ${current.explanation}"
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) { Text(answer) }
                    }
                    feedback?.let { message ->
                        Text(message, fontWeight = FontWeight.SemiBold, color = Color(0xFF43546C))
                        OutlinedButton(
                            onClick = {
                                feedback = null
                                if (index == questions.lastIndex) {
                                    index = 0
                                    score = 0
                                } else {
                                    index += 1
                                }
                            },
                        ) { Text(if (index == questions.lastIndex) "Nieuwe ronde" else "Volgende ontdekking") }
                    }
                }
            }
        }

        item {
            GeographyMatchCard(learnerGroup)
        }

        item {
            FactLabCard(learnerGroup)
        }
    }
}

@Composable
private fun GeographyMatchCard(group: Int) {
    val pairs = if (group <= 4) {
        listOf("Nederland" to "Amsterdam", "België" to "Brussel", "Frankrijk" to "Parijs")
    } else {
        listOf("Duitsland" to "Berlijn", "Spanje" to "Madrid", "Italië" to "Rome")
    }
    var selectedCountry by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf("Kies eerst een land en daarna de juiste hoofdstad.") }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE9F7EF)),
        shape = RoundedCornerShape(22.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Land ↔ hoofdstad", fontWeight = FontWeight.Black, fontSize = 19.sp)
            Text(result, color = Color(0xFF4E6858))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pairs.forEach { (country, _) ->
                    Box(
                        modifier = Modifier.weight(1f).background(
                            if (selectedCountry == country) Color(0xFFBDECCF) else Color.White,
                            RoundedCornerShape(14.dp),
                        ).clickable { selectedCountry = country }.padding(10.dp),
                        contentAlignment = Alignment.Center,
                    ) { Text(country, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pairs.reversed().forEach { (_, capital) ->
                    Box(
                        modifier = Modifier.weight(1f).background(Color.White, RoundedCornerShape(14.dp))
                            .clickable {
                                val expected = pairs.firstOrNull { it.first == selectedCountry }?.second
                                result = if (expected == capital) "Goed gekoppeld! ⭐" else if (selectedCountry == null) "Kies eerst een land." else "Nog niet. Probeer opnieuw."
                            }.padding(10.dp),
                        contentAlignment = Alignment.Center,
                    ) { Text(capital, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                }
            }
        }
    }
}

@Composable
private fun FactLabCard(group: Int) {
    val fact = if (group <= 4) {
        "Planten gebruiken licht om voedsel te maken."
    } else {
        "Water kan voorkomen als vaste stof, vloeistof en gas."
    }
    var answer by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2DB)),
        shape = RoundedCornerShape(22.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text("Feitenlab", fontWeight = FontWeight.Black, fontSize = 19.sp)
            Text(fact, fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { answer = "Juist! ✅" }, modifier = Modifier.weight(1f)) { Text("Waar") }
                Button(onClick = { answer = "Deze uitspraak is waar. Probeer nog eens." }, modifier = Modifier.weight(1f)) { Text("Niet waar") }
            }
            answer?.let { Text(it, fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
private fun ExploreCategoryCard(modifier: Modifier, title: String, emoji: String, icon: ImageVector) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(9.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji)
            Icon(icon, contentDescription = null, tint = Color(0xFF0A6073), modifier = Modifier.size(22.dp))
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

private fun exploreQuestions(group: Int): List<ExploreQuestion> = if (group <= 4) {
    listOf(
        ExploreQuestion("🌍 Wereld", "Welke zee ligt aan de Nederlandse kust?", listOf("Noordzee", "Middellandse Zee", "Zwarte Zee"), 0, "Nederland grenst in het westen en noorden aan de Noordzee."),
        ExploreQuestion("🔬 Natuur", "Wat heeft een plant nodig om te groeien?", listOf("Licht en water", "Alleen steen", "Alleen wind"), 0, "Planten hebben onder andere licht en water nodig."),
        ExploreQuestion("🏛️ Historie", "Wat gebruikten mensen vroeger om te schrijven vóór computers?", listOf("Papier en pen", "Satellieten", "QR-codes"), 0, "Papier en schrijfgerei werden lang vóór computers gebruikt."),
        ExploreQuestion("🚀 Techniek", "Welk voorwerp helpt je om iets kleins groter te zien?", listOf("Vergrootglas", "Paraplu", "Kompas"), 0, "Een vergrootglas maakt kleine details groter zichtbaar."),
    )
} else {
    listOf(
        ExploreQuestion("🌍 Wereld", "Welke rivier stroomt door Nederland naar de Noordzee?", listOf("Rijn", "Nijl", "Amazone"), 0, "De Rijn splitst zich in Nederland en bereikt via de delta de Noordzee."),
        ExploreQuestion("🔬 Natuur", "Hoe heet de overgang van vloeibaar water naar waterdamp?", listOf("Verdamping", "Bevriezing", "Smelten"), 0, "Bij verdamping verandert vloeibaar water in gas."),
        ExploreQuestion("🏛️ Historie", "Welke bron helpt het best om het verleden direct te onderzoeken?", listOf("Een voorwerp uit die tijd", "Een verzonnen verhaal", "Een moderne reclame"), 0, "Een authentieke bron uit de periode geeft direct historisch bewijs."),
        ExploreQuestion("🚀 Techniek", "Waarom heeft een brug vaak driehoeken in de constructie?", listOf("Voor stevigheid", "Alleen voor kleur", "Om water vast te houden"), 0, "Driehoeken vervormen moeilijk en geven constructies stevigheid."),
    )
}
