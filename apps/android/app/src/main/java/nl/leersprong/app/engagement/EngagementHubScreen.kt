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
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Extension
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.SportsEsports
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.leersprong.app.feature.lesson.LessonLibrary

private val Navy = Color(0xFF062A70)
private val Mint = Color(0xFFD8F7E8)
private val Yellow = Color(0xFFFFD24A)
private val Surface = Color(0xFFF5F8FD)

private data class MemoryCard(val id: Int, val symbol: String, val pair: Int, val open: Boolean = false, val solved: Boolean = false)

private data class QuickQuestion(val prompt: String, val answers: List<String>, val correctIndex: Int)

@Composable
fun EngagementHubScreen(
    learnerGroup: Int,
    onStartLesson: (String) -> Unit,
    onBack: () -> Unit,
) {
    val groupLessons = remember(learnerGroup) { LessonLibrary.forGroup(learnerGroup) }
    val rekenen = groupLessons.firstOrNull { it.subject.contains("Rekenen", ignoreCase = true) }
    val nederlands = groupLessons.firstOrNull { it.subject.contains("Nederlands", ignoreCase = true) }
    val english = groupLessons.firstOrNull { it.subject.contains("Engels", ignoreCase = true) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Surface),
        contentPadding = PaddingValues(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth().background(
                    Brush.verticalGradient(listOf(Navy, Color(0xFF0A489B), Color(0xFF0C7A87))),
                ).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(onClick = onBack) { Text("← Terug", color = Color.White) }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier.size(58.dp).background(Yellow, RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Icons.Rounded.SportsEsports, contentDescription = null, tint = Navy, modifier = Modifier.size(34.dp))
                    }
                    Column {
                        Text("Speelplein", color = Color.White, fontWeight = FontWeight.Black, fontSize = 30.sp)
                        Text("Korte spellen, missies en slimme uitdagingen voor groep $learnerGroup", color = Color(0xFFDDEBFF))
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = {}, label = { Text("⚡ 5-min spellen") })
                    AssistChip(onClick = {}, label = { Text("🏆 echte XP") })
                }
            }
        }

        item { SectionTitle("Vandaag te doen", "Drie korte missies zodat leren niet als een lange les voelt.") }
        item {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MissionCard(Modifier.weight(1f), "1", "Speel", "Memory", "2 min")
                MissionCard(Modifier.weight(1f), "2", "Oefen", "Taal", "5 min")
                MissionCard(Modifier.weight(1f), "3", "Versla", "Quiz", "3 min")
            }
        }

        item { SectionTitle("Memory & Match", "Train aandacht en geheugen met een echt speelbaar kaartspel.") }
        item { MemoryMatchGame() }

        item { SectionTitle("Quiz Arena", "Snelle vragen met directe score — opnieuw spelen kan meteen.") }
        item { QuickQuizGame(learnerGroup) }

        item { SectionTitle("Kies je uitdaging", "Spring rechtstreeks naar een vak zonder door menu's te zoeken.") }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                rekenen?.let {
                    GameLane("RekenChallenge", "Slim rekenen met korte rondes", Icons.Rounded.Psychology) { onStartLesson(it.id) }
                }
                nederlands?.let {
                    GameLane("WoordChallenge", "Spelling, patronen en woordgevoel", Icons.Rounded.Extension) { onStartLesson(it.id) }
                }
                english?.let {
                    GameLane("Luister & kies", "Engels luisteren en direct reageren", Icons.Rounded.Headphones) { onStartLesson(it.id) }
                }
            }
        }

        item { SectionTitle("Trofeeënkast", "Beloningen worden zichtbaar door oefenen, niet door nepdata.") }
        item {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TrophyCard(Modifier.weight(1f), "🌱", "Starter", "Eerste missie")
                TrophyCard(Modifier.weight(1f), "🔥", "Volhouder", "3 dagen actief")
                TrophyCard(Modifier.weight(1f), "🧠", "Slimmerik", "Review afgerond")
            }
        }
    }
}

@Composable
private fun MemoryMatchGame() {
    val seed = listOf("🐝", "🌈", "🚀", "🍎")
    val cards = remember {
        mutableStateListOf<MemoryCard>().apply {
            addAll(
                seed.flatMapIndexed { index, symbol ->
                    listOf(MemoryCard(index * 2, symbol, index), MemoryCard(index * 2 + 1, symbol, index))
                }.let { listOf(it[3], it[0], it[6], it[5], it[2], it[7], it[1], it[4]) },
            )
        }
    }
    var firstIndex by remember { mutableIntStateOf(-1) }
    var moves by remember { mutableIntStateOf(0) }

    fun flip(index: Int) {
        if (cards[index].solved || cards[index].open) return
        cards[index] = cards[index].copy(open = true)
        if (firstIndex == -1) {
            firstIndex = index
        } else {
            val previous = firstIndex
            moves += 1
            if (cards[previous].pair == cards[index].pair) {
                cards[previous] = cards[previous].copy(solved = true)
                cards[index] = cards[index].copy(solved = true)
            } else {
                cards[previous] = cards[previous].copy(open = false)
                cards[index] = cards[index].copy(open = false)
            }
            firstIndex = -1
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(22.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Zet $moves", fontWeight = FontWeight.Bold)
                Text("${cards.count { it.solved }}/8 gevonden", color = Color(0xFF138A5A), fontWeight = FontWeight.Bold)
            }
            cards.chunked(4).forEach { rowCards ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    rowCards.forEach { card ->
                        val index = cards.indexOfFirst { it.id == card.id }
                        Box(
                            modifier = Modifier.weight(1f).size(62.dp)
                                .background(if (card.solved) Mint else Color(0xFFEAF0FA), RoundedCornerShape(14.dp))
                                .clickable { flip(index) },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(if (card.open || card.solved) card.symbol else "?", fontSize = 27.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
            if (cards.all { it.solved }) {
                Text("🎉 Alles gevonden in $moves zetten!", color = Color(0xFF0D7B50), fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun QuickQuizGame(group: Int) {
    val questions = remember(group) {
        if (group <= 4) listOf(
            QuickQuestion("Wat is 6 + 7?", listOf("11", "12", "13"), 2),
            QuickQuestion("Welk woord rijmt op maan?", listOf("baan", "boek", "vis"), 0),
            QuickQuestion("Welke hoort bij de lente?", listOf("Sneeuwpop", "Bloesem", "Herfstblad"), 1),
        ) else listOf(
            QuickQuestion("Wat is 25% van 80?", listOf("10", "20", "25"), 1),
            QuickQuestion("Wat is een hoofdgedachte?", listOf("De kern van de tekst", "Een moeilijk woord", "De titel"), 0),
            QuickQuestion("Choose the polite request.", listOf("Give me that.", "Could you help me?", "You do it."), 1),
        )
    }
    var index by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var feedback by remember { mutableStateOf<String?>(null) }
    val current = questions[index]

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7D8)),
        shape = RoundedCornerShape(22.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Ronde ${index + 1}/${questions.size}", fontWeight = FontWeight.Black)
                Text("Score $score", fontWeight = FontWeight.Black, color = Navy)
            }
            Text(current.prompt, fontWeight = FontWeight.Black, fontSize = 20.sp)
            current.answers.forEachIndexed { answerIndex, answer ->
                Button(
                    onClick = {
                        if (feedback == null) {
                            val correct = answerIndex == current.correctIndex
                            if (correct) score += 1
                            feedback = if (correct) "Goed! +1 ⭐" else "Nog niet — juiste antwoord: ${current.answers[current.correctIndex]}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) { Text(answer) }
            }
            feedback?.let { message ->
                Text(message, fontWeight = FontWeight.Bold)
                OutlinedButton(
                    onClick = {
                        feedback = null
                        index = if (index == questions.lastIndex) 0 else index + 1
                        if (index == questions.lastIndex) score = 0
                    },
                ) { Text(if (index == questions.lastIndex) "Opnieuw" else "Volgende") }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 2.dp)) {
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Black, color = Color(0xFF13294B))
        Text(subtitle, color = Color(0xFF68788F), fontSize = 13.sp)
    }
}

@Composable
private fun MissionCard(modifier: Modifier, number: String, verb: String, title: String, duration: String) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(number, color = Navy, fontWeight = FontWeight.Black, fontSize = 12.sp)
            Text(verb, color = Color(0xFF6A7890), fontSize = 12.sp)
            Text(title, fontWeight = FontWeight.Black)
            Text(duration, color = Color(0xFF0C8A5E), fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun GameLane(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp),
    ) {
        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(48.dp).background(Mint, RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = Navy)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Black)
                Text(subtitle, color = Color(0xFF6B7B91), fontSize = 13.sp)
            }
            Icon(Icons.Rounded.AutoAwesome, contentDescription = null, tint = Color(0xFFFFB300))
        }
    }
}

@Composable
private fun TrophyCard(modifier: Modifier, emoji: String, title: String, subtitle: String) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 28.sp)
            Text(title, fontWeight = FontWeight.Black, fontSize = 13.sp)
            Text(subtitle, color = Color(0xFF7A8799), fontSize = 10.sp)
        }
    }
}
