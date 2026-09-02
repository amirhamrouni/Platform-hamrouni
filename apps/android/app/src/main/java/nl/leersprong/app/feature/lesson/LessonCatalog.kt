package nl.leersprong.app.feature.lesson

data class LessonDefinition(
    val id: String,
    val skillId: String,
    val title: String,
    val subject: String,
    val group: Int,
    val estimatedMinutes: Int,
    val steps: List<LessonStep>,
    val remedialSteps: Map<String, LessonStep>,
)

object LessonCatalog {
    const val G4_MULTIPLICATION = "g4-math-multiplication-foundations-v1"
    const val G4_ADD_SUB_100 = "g4-math-add-sub-100-v1"
    const val G4_DUTCH_VOWELS = "g4-dutch-short-long-vowels-v1"

    val lessons: List<LessonDefinition> = GroupStarterCatalog.lessons + listOf(
        multiplication(), addSub100(), dutchVowels(),
    )

    fun get(id: String): LessonDefinition = lessons.firstOrNull { it.id == id } ?: lessons.first()

    private fun multiplication() = LessonDefinition(
        id = G4_MULTIPLICATION, skillId = "g4-math-multiplication-foundations", title = "Tafels begrijpen", subject = "Rekenen & Wiskunde", group = 4, estimatedMinutes = 12,
        steps = listOf(
            LessonStep("g4-mul-01","Gelijke groepjes","Er staan 3 mandjes met in elk mandje 4 appels. Welke som past hierbij?",LessonInteractionType.MultipleChoice,"equal-groups",listOf(LessonOption("add","3 + 4"),LessonOption("mul","3 × 4"),LessonOption("sub","4 − 3")),"mul",hint="Kijk naar het aantal gelijke groepjes.",explanation="3 gelijke groepjes van 4 schrijf je als 3 × 4."),
            LessonStep("g4-mul-02","Herhaald optellen","Vul in: 5 groepjes van 2 is samen ___.",LessonInteractionType.FillBlank,"repeated-addition",acceptedAnswers=listOf("10"),hint="Tel 2 vijf keer.",explanation="5 × 2 = 10."),
            LessonStep("g4-mul-03","Luister en kies","Luister naar de keersom en kies het juiste antwoord.",LessonInteractionType.ListenChoose,"repeated-addition",listOf(LessonOption("20","20"),LessonOption("24","24"),LessonOption("28","28")),"24",speakText="Wat is vier keer zes?",hint="Vier groepjes van zes.",explanation="4 × 6 = 24."),
            LessonStep("g4-mul-04","Zet de stappen goed","Zet de stappen in de goede volgorde om 3 × 5 uit te rekenen.",LessonInteractionType.Ordering,"equal-groups",options=listOf(LessonOption("sum","Tel alles bij elkaar"),LessonOption("groups","Maak 3 gelijke groepjes"),LessonOption("fill","Doe 5 in elk groepje")),correctOrder=listOf("groups","fill","sum"),hint="Begin met hoeveel groepjes.",explanation="Eerst groepjes, dan vullen, dan tellen."),
            LessonStep("g4-mul-05","Wisselregel","Welke twee sommen hebben dezelfde uitkomst?",LessonInteractionType.MultipleChoice,"commutative",listOf(LessonOption("swap","3 × 4 en 4 × 3"),LessonOption("plus1","3 × 4 en 3 + 4")),"swap",hint="Factoren mogen omwisselen.",explanation="3 × 4 en 4 × 3 zijn allebei 12."),
            LessonStep("g4-mul-06","Verhaalsom","Een klas heeft 7 tafels. Aan elke tafel zitten 4 kinderen. Hoeveel kinderen zijn dat?",LessonInteractionType.FillBlank,"word-problem",acceptedAnswers=listOf("28"),hint="7 groepjes van 4.",explanation="7 × 4 = 28."),
        ),
        remedialSteps = mapOf("equal-groups" to LessonStep("g4-mul-r1","Even terug naar groepjes","2 bakjes hebben elk 3 knikkers. Welke keersom hoort daarbij?",LessonInteractionType.MultipleChoice,"equal-groups",listOf(LessonOption("2x3","2 × 3"),LessonOption("2p3","2 + 3")),"2x3",hint="Twee gelijke groepjes.",explanation="2 groepjes van 3 is 2 × 3."))
    )

    private fun addSub100() = LessonDefinition(
        G4_ADD_SUB_100,"g4-math-add-sub-100","Optellen & aftrekken tot 100","Rekenen & Wiskunde",4,10,
        listOf(
            LessonStep("g4-as-01","Tientallen","Wat is 34 + 20?",LessonInteractionType.MultipleChoice,"tens",listOf(LessonOption("44","44"),LessonOption("54","54"),LessonOption("64","64")),"54",hint="Tel twee tientallen bij 34.",explanation="34 + 20 = 54."),
            LessonStep("g4-as-02","Over het tiental","Vul in: 48 + 7 = ___.",LessonInteractionType.FillBlank,"bridge-ten",acceptedAnswers=listOf("55"),hint="Maak eerst 50.",explanation="48 + 7 = 55."),
            LessonStep("g4-as-03","Luister en reken","Luister naar de som en kies.",LessonInteractionType.ListenChoose,"subtraction",listOf(LessonOption("41","41"),LessonOption("44","44"),LessonOption("47","47")),"44",speakText="Zesenvijftig min twaalf.",hint="Eerst min tien, dan min twee.",explanation="56 − 12 = 44."),
            LessonStep("g4-as-04","Verhaalsom","Noor heeft 62 stickers en geeft er 18 weg. Hoeveel blijven er?",LessonInteractionType.FillBlank,"word-problem",acceptedAnswers=listOf("44"),hint="62 − 10, daarna − 8.",explanation="62 − 18 = 44."),
        ), emptyMap()
    )

    private fun dutchVowels() = LessonDefinition(
        G4_DUTCH_VOWELS,"g4-dutch-short-long-vowels","Korte en lange klanken","Nederlands",4,10,
        listOf(
            LessonStep("g4-nl-01","Hoor de klank","Welk woord heeft een korte klank?",LessonInteractionType.MultipleChoice,"short-vowel",listOf(LessonOption("maan","maan"),LessonOption("man","man"),LessonOption("meer","meer")),"man",hint="Zeg de woorden langzaam.",explanation="In man hoor je een korte a."),
            LessonStep("g4-nl-02","Lange klank","Welk woord heeft een lange klank?",LessonInteractionType.MultipleChoice,"long-vowel",listOf(LessonOption("vis","vis"),LessonOption("boom","boom"),LessonOption("bus","bus")),"boom",hint="De oo klinkt lang.",explanation="Boom heeft een lange oo-klank."),
            LessonStep("g4-nl-03","Luister en kies","Luister en kies het woord dat je hoort.",LessonInteractionType.ListenChoose,"listen-spell",listOf(LessonOption("tak","tak"),LessonOption("taak","taak")),"taak",speakText="taak",hint="Luister naar de lange aa.",explanation="Je hoort taak."),
            LessonStep("g4-nl-04","Vul het woord aan","Vul in: b__m. Het woord is boom.",LessonInteractionType.FillBlank,"long-vowel",acceptedAnswers=listOf("oo"),hint="Je hoort een lange oo.",explanation="Boom schrijf je met oo."),
        ), emptyMap()
    )
}
