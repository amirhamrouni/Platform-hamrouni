package nl.leersprong.app.engagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Lightbulb
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class StoryChoice(
    val label: String,
    val nextId: String,
    val insight: String? = null,
)

private data class StoryNode(
    val id: String,
    val title: String,
    val text: String,
    val choices: List<StoryChoice>,
    val word: String? = null,
    val wordMeaning: String? = null,
)

@Composable
fun StoryWorldScreen(learnerGroup: Int, onBack: () -> Unit) {
    val nodes = remember(learnerGroup) { storyNodes(learnerGroup).associateBy { it.id } }
    var currentId by remember { mutableStateOf("start") }
    var visited by remember { mutableIntStateOf(1) }
    var lastInsight by remember { mutableStateOf<String?>(null) }
    val current = requireNotNull(nodes[currentId])
    val isEnding = current.choices.isEmpty()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0E8))) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF70421C)).padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            OutlinedButton(onClick = onBack) { Text("← Speelplein", color = Color.White) }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Rounded.AutoStories, contentDescription = null, tint = Color(0xFFFFD76A))
                Column {
                    Text("Verhalenwereld", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                    Text("Jouw keuzes veranderen het verhaal.", color = Color(0xFFFFE8CF))
                }
            }
            LinearProgressIndicator(
                progress = { (visited.coerceAtMost(6)) / 6f },
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFFD76A),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(current.title, fontSize = 23.sp, fontWeight = FontWeight.Black, color = Color(0xFF3D2A1B))
                    Text(current.text, fontSize = 17.sp, lineHeight = 25.sp, color = Color(0xFF4D443D))

                    if (current.word != null && current.wordMeaning != null) {
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CE)), shape = RoundedCornerShape(16.dp)) {
                            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Rounded.Lightbulb, contentDescription = null, tint = Color(0xFF9A6500))
                                Column {
                                    Text("Nieuw woord: ${current.word}", fontWeight = FontWeight.Black)
                                    Text(current.wordMeaning, color = Color(0xFF67542E))
                                }
                            }
                        }
                    }
                }
            }

            lastInsight?.let {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F4EA)), shape = RoundedCornerShape(16.dp)) {
                    Text(it, modifier = Modifier.padding(12.dp), color = Color(0xFF205C3A), fontWeight = FontWeight.SemiBold)
                }
            }

            if (isEnding) {
                Text("🌟 Einde van deze route", fontWeight = FontWeight.Black, fontSize = 19.sp)
                Button(
                    onClick = {
                        currentId = "start"
                        visited = 1
                        lastInsight = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) { Text("Speel opnieuw en kies anders") }
            } else {
                Text("Wat kies jij?", fontWeight = FontWeight.Black, fontSize = 18.sp)
                current.choices.forEach { choice ->
                    Button(
                        onClick = {
                            currentId = choice.nextId
                            visited += 1
                            lastInsight = choice.insight
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) { Text(choice.label) }
                }
            }
        }
    }
}

private fun storyNodes(group: Int): List<StoryNode> = if (group <= 4) lowerStory() else upperStory()

private fun lowerStory(): List<StoryNode> = listOf(
    StoryNode(
        id = "start",
        title = "De kleine bij en de grote tuin",
        text = "Mila ziet in de schooltuin een bij die steeds rond dezelfde lege hoek vliegt. Er groeien bijna geen bloemen. Mila wil helpen, maar ze weet nog niet wat de bij nodig heeft.",
        choices = listOf(
            StoryChoice("Kijk eerst goed wat de bij doet", "observe", "Goed onderzocht: eerst waarnemen geeft informatie voordat je iets verandert."),
            StoryChoice("Plant meteen een grote boom", "tree", "Een boom kan nuttig zijn, maar past niet automatisch bij de behoefte die je ziet."),
        ),
        word = "waarnemen",
        wordMeaning = "Goed kijken, luisteren of voelen om informatie te verzamelen.",
    ),
    StoryNode(
        id = "observe",
        title = "Stuifmeel aan de pootjes",
        text = "Mila ziet geel poeder aan de pootjes van de bij. De bij vliegt daarna naar een paarse bloem verderop. Mila denkt dat bloemen belangrijk zijn.",
        choices = listOf(
            StoryChoice("Kies verschillende bloemen", "flowers", "Sterke keuze: verschillende bloemen kunnen op verschillende momenten voedsel bieden."),
            StoryChoice("Leg alleen stenen neer", "stones", "Stenen geven geen nectar of stuifmeel. De bij heeft bloeiende planten nodig."),
        ),
        word = "stuifmeel",
        wordMeaning = "Fijn poeder van bloemen dat bijen kunnen meenemen.",
    ),
    StoryNode(
        id = "tree",
        title = "Past de oplossing?",
        text = "De meester vraagt: ‘Waarom juist een boom?’ Mila merkt dat ze nog geen bewijs heeft dat dit de beste oplossing is.",
        choices = listOf(
            StoryChoice("Teruggaan en eerst observeren", "observe", "Slim: je mag een idee aanpassen als nieuwe informatie dat nodig maakt."),
            StoryChoice("Bij het plan blijven zonder te kijken", "stones", "Een plan zonder controle kan het echte probleem missen."),
        ),
    ),
    StoryNode(
        id = "flowers",
        title = "Een tuin vol bezoek",
        text = "De klas plant bloemen die in verschillende maanden bloeien. Na een tijdje komen bijen, vlinders en andere insecten langs. Mila schrijft op welke dieren ze ziet.",
        choices = listOf(
            StoryChoice("Maak een klein dierenlogboek", "good_end", "Je combineert natuurzorg met meten en terugkijken."),
            StoryChoice("Stop met kijken zodra de bloemen staan", "quiet_end", "De tuin helpt al, maar door te blijven observeren leer je meer."),
        ),
    ),
    StoryNode(
        id = "stones",
        title = "Nog geen lunch voor de bij",
        text = "De hoek ziet er netjes uit, maar de bij vindt er geen nectar. Mila begrijpt dat mooi niet altijd hetzelfde is als nuttig voor dieren.",
        choices = listOf(StoryChoice("Probeer opnieuw met bloemen", "flowers", "Fouten geven informatie. Je gebruikt die informatie voor een betere volgende stap.")),
        word = "nectar",
        wordMeaning = "Zoete vloeistof in bloemen waar veel insecten voedsel uit halen.",
    ),
    StoryNode("good_end", "Onderzoeker van de tuin", "Mila telt elke week meer verschillende bezoekers. De klas gebruikt haar logboek om te besluiten welke planten ze volgend jaar opnieuw willen.", emptyList()),
    StoryNode("quiet_end", "Een rustige tuin", "De bloemen blijven groeien en de bij komt terug. Mila weet nu dat een volgende stap kan zijn om de veranderingen ook bij te houden.", emptyList()),
)

private fun upperStory(): List<StoryNode> = listOf(
    StoryNode(
        id = "start",
        title = "Missie Waterstad",
        text = "Na dagen van zware regen staat een deel van de wijk blank. Noor zit in het jeugdteam dat ideeën mag geven voor een nieuwe, klimaatbestendige buurt. Er is geld voor één eerste proefproject.",
        choices = listOf(
            StoryChoice("Bekijk waar het water zich verzamelt", "measure", "Je begint met gegevens in plaats van aannames."),
            StoryChoice("Kies meteen de duurste pomp", "pump", "Een pomp kan helpen, maar zonder analyse weet je niet of dit de beste eerste stap is."),
        ),
        word = "klimaatbestendig",
        wordMeaning = "Zo ontworpen dat een plek beter tegen gevolgen van extreem weer kan.",
    ),
    StoryNode(
        id = "measure",
        title = "De kaart vertelt iets",
        text = "Op de hoogtekaart ziet Noor dat veel water naar een betegeld plein stroomt. Bijna al het regenwater loopt daar snel over het oppervlak.",
        choices = listOf(
            StoryChoice("Maak ruimte voor planten en wateropvang", "sponge", "Groen en opvang kunnen water tijdelijk vasthouden en langzaam afvoeren."),
            StoryChoice("Leg nog meer tegels", "tiles", "Meer gesloten oppervlak laat vaak minder water in de bodem zakken."),
        ),
        word = "oppervlaktewater",
        wordMeaning = "Water dat zichtbaar op of boven het aardoppervlak aanwezig is.",
    ),
    StoryNode(
        id = "pump",
        title = "Een oplossing met een vraagteken",
        text = "De ingenieur vraagt hoeveel water de pomp moet verwerken en waar het daarna heen kan. Het team heeft die gegevens nog niet.",
        choices = listOf(
            StoryChoice("Eerst meten en de kaart onderzoeken", "measure", "Goede techniek begint vaak met het probleem precies begrijpen."),
            StoryChoice("Toch bestellen zonder gegevens", "tiles", "Een oplossing kan duur zijn en toch het verkeerde probleem aanpakken."),
        ),
    ),
    StoryNode(
        id = "sponge",
        title = "Het sponsplein",
        text = "Het team ontwerpt een lager groen deel dat bij veel regen tijdelijk water opvangt. Op droge dagen is het gewoon een speel- en natuurplek.",
        choices = listOf(
            StoryChoice("Test het ontwerp op kleine schaal", "test", "Een proefmodel maakt sterke en zwakke punten zichtbaar vóór de grote bouw."),
            StoryChoice("Bouw direct zonder test", "risk_end", "Zonder test ontdek je fouten pas als aanpassen veel moeilijker is."),
        ),
        word = "prototype",
        wordMeaning = "Een eerste proefversie waarmee je een idee kunt testen.",
    ),
    StoryNode(
        id = "tiles",
        title = "Het water zoekt een weg",
        text = "Bij de volgende bui stroomt het water opnieuw snel over het plein. Het team ziet dat de gekozen oplossing weinig ruimte maakte om water vast te houden.",
        choices = listOf(StoryChoice("Gebruik de fout en ontwerp opnieuw", "sponge", "Itereren betekent leren van een test en het ontwerp verbeteren.")),
        word = "itereren",
        wordMeaning = "Een ontwerp herhaald testen en verbeteren.",
    ),
    StoryNode(
        id = "test",
        title = "Een emmer regen",
        text = "Met bakken, zand en planten maakt het team een schaalmodel. Ze gieten dezelfde hoeveelheid water over twee ontwerpen en meten hoeveel water wegstroomt.",
        choices = listOf(
            StoryChoice("Kies het ontwerp dat aantoonbaar beter werkt", "good_end", "Je gebruikt meetresultaten als bewijs voor een ontwerpbeslissing."),
            StoryChoice("Negeer de meting en kies alleen de mooiste", "risk_end", "Vorm is belangrijk, maar bij een technisch probleem moet de functie ook bewezen zijn."),
        ),
    ),
    StoryNode("good_end", "Een wijk die leert van regen", "Het proefplein vangt bij de volgende bui veel water tijdelijk op. Het team blijft meten en gebruikt de resultaten voor andere plekken in de wijk.", emptyList()),
    StoryNode("risk_end", "Een tweede kans voor het ontwerp", "Het eerste resultaat werkt minder goed dan gehoopt. Noor overtuigt het team om de gegevens te bekijken en een nieuwe versie te testen. Een mislukte proef is nog steeds informatie.", emptyList()),
)
