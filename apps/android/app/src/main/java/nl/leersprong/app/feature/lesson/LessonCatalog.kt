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

    val lessons: List<LessonDefinition> = listOf(
        multiplication(),
        addSub100(),
        dutchVowels(),
    )

    fun get(id: String): LessonDefinition = lessons.firstOrNull { it.id == id } ?: lessons.first()

    private fun multiplication() = LessonDefinition(
        id = G4_MULTIPLICATION,
        skillId = "g4-math-multiplication-foundations",
        title = "Tafels begrijpen",
        subject = "Rekenen & Wiskunde",
        group = 4,
        estimatedMinutes = 12,
        steps = listOf(
            LessonStep(
                id = "g4-mul-01", title = "Gelijke groepjes",
                prompt = "Er staan 3 mandjes met in elk mandje 4 appels. Welke som past hierbij?",
                interaction = LessonInteractionType.MultipleChoice, conceptTag = "equal-groups",
                options = listOf(LessonOption("add", "3 + 4"), LessonOption("mul", "3 × 4"), LessonOption("sub", "4 − 3")),
                correctOptionId = "mul",
                hint = "Kijk naar het aantal gelijke groepjes en hoeveel er in elk groepje zitten.",
                explanation = "3 gelijke groepjes van 4 schrijf je als 3 × 4.",
            ),
            LessonStep(
                id = "g4-mul-02", title = "Herhaald optellen",
                prompt = "Vul in: 5 groepjes van 2 is samen ___.",
                interaction = LessonInteractionType.FillBlank, conceptTag = "repeated-addition",
                acceptedAnswers = listOf("10"),
                hint = "Tel 2 vijf keer bij elkaar op.",
                explanation = "5 × 2 = 10. Je kunt ook 2 + 2 + 2 + 2 + 2 rekenen.",
            ),
            LessonStep(
                id = "g4-mul-03", title = "Luister en kies",
                prompt = "Luister naar de keersom en kies het juiste antwoord.",
                interaction = LessonInteractionType.ListenChoose, conceptTag = "repeated-addition",
                options = listOf(LessonOption("20", "20"), LessonOption("24", "24"), LessonOption("28", "28")),
                correctOptionId = "24", speakText = "Wat is vier keer zes?",
                hint = "Vier gelijke groepjes van zes.", explanation = "4 groepjes van 6 zijn samen 24.",
            ),
            LessonStep(
                id = "g4-mul-04", title = "Zet de stappen goed",
                prompt = "Zet de stappen in de goede volgorde om 3 × 5 uit te rekenen.",
                interaction = LessonInteractionType.Ordering, conceptTag = "equal-groups",
                options = listOf(
                    LessonOption("sum", "Tel alles bij elkaar"),
                    LessonOption("groups", "Maak 3 gelijke groepjes"),
                    LessonOption("fill", "Doe 5 in elk groepje"),
                ),
                correctOrder = listOf("groups", "fill", "sum"),
                hint = "Begin met hoeveel groepjes je nodig hebt.",
                explanation = "Een keersom beschrijft eerst hoeveel groepjes je hebt en daarna hoeveel er in elk groepje zitten.",
            ),
            LessonStep(
                id = "g4-mul-05", title = "Wisselregel",
                prompt = "Welke twee sommen hebben dezelfde uitkomst?",
                interaction = LessonInteractionType.MultipleChoice, conceptTag = "commutative",
                options = listOf(
                    LessonOption("swap", "3 × 4 en 4 × 3"),
                    LessonOption("plus1", "3 × 4 en 3 + 4"),
                    LessonOption("plus2", "4 × 3 en 4 + 3"),
                ),
                correctOptionId = "swap", hint = "Bij vermenigvuldigen kun je de factoren omwisselen.",
                explanation = "3 × 4 en 4 × 3 zijn allebei 12.",
            ),
            LessonStep(
                id = "g4-mul-06", title = "Gebruik wat je weet",
                prompt = "Je weet dat 5 × 6 = 30. Wat is dan 6 × 5?",
                interaction = LessonInteractionType.FillBlank, conceptTag = "commutative",
                acceptedAnswers = listOf("30"), hint = "Denk aan de wisselregel.",
                explanation = "Bij vermenigvuldigen mag je de factoren omwisselen: 5 × 6 = 6 × 5.",
            ),
            LessonStep(
                id = "g4-mul-07", title = "Verhaalsom",
                prompt = "Een klas heeft 7 tafels. Aan elke tafel zitten 4 kinderen. Hoeveel kinderen zijn dat?",
                interaction = LessonInteractionType.MultipleChoice, conceptTag = "word-problem",
                options = listOf(LessonOption("11", "11"), LessonOption("24", "24"), LessonOption("28", "28")),
                correctOptionId = "28", hint = "Je hebt 7 gelijke groepjes van 4.",
                explanation = "7 gelijke groepjes van 4: 7 × 4 = 28.",
            ),
            LessonStep(
                id = "g4-mul-08", title = "Slimme strategie",
                prompt = "Een doos heeft 8 rijen met 6 stickers. Hoeveel stickers zijn er?",
                interaction = LessonInteractionType.FillBlank, conceptTag = "word-problem",
                acceptedAnswers = listOf("48"), hint = "Reken 4 × 6 en verdubbel.",
                explanation = "8 × 6 = 48. Je kunt 4 × 6 verdubbelen.",
            ),
        ),
        remedialSteps = mapOf(
            "equal-groups" to LessonStep(
                id = "g4-mul-remedial-groups", title = "Even terug naar groepjes",
                prompt = "2 bakjes hebben elk 3 knikkers. Welke keersom hoort daarbij?",
                interaction = LessonInteractionType.MultipleChoice, conceptTag = "equal-groups",
                options = listOf(LessonOption("2x3", "2 × 3"), LessonOption("2p3", "2 + 3"), LessonOption("3m2", "3 − 2")),
                correctOptionId = "2x3", hint = "Twee gelijke groepjes, met drie in elk groepje.",
                explanation = "2 groepjes van 3 schrijf je als 2 × 3.",
            ),
            "repeated-addition" to LessonStep(
                id = "g4-mul-remedial-repeat", title = "Bouw de keersom op",
                prompt = "3 groepjes van 2 is 2 + 2 + 2. Hoeveel is dat samen?",
                interaction = LessonInteractionType.FillBlank, conceptTag = "repeated-addition",
                acceptedAnswers = listOf("6"), hint = "Tel drie keer 2.",
                explanation = "2 + 2 + 2 = 6, dus 3 × 2 = 6.",
            ),
            "commutative" to LessonStep(
                id = "g4-mul-remedial-swap", title = "Draai de som om",
                prompt = "Als 2 × 5 = 10, wat is dan 5 × 2?",
                interaction = LessonInteractionType.FillBlank, conceptTag = "commutative",
                acceptedAnswers = listOf("10"), hint = "De factoren mogen van plaats wisselen.",
                explanation = "2 × 5 en 5 × 2 hebben dezelfde uitkomst: 10.",
            ),
            "word-problem" to LessonStep(
                id = "g4-mul-remedial-story", title = "Lees het verhaal in groepjes",
                prompt = "Er zijn 4 zakjes met 3 koekjes per zakje. Hoeveel koekjes zijn er?",
                interaction = LessonInteractionType.MultipleChoice, conceptTag = "word-problem",
                options = listOf(LessonOption("7", "7"), LessonOption("12", "12"), LessonOption("16", "16")),
                correctOptionId = "12", hint = "Vier gelijke groepjes van drie.",
                explanation = "4 × 3 = 12 koekjes.",
            ),
        ),
    )

    private fun addSub100() = LessonDefinition(
        id = G4_ADD_SUB_100,
        skillId = "g4-math-add-sub-100",
        title = "Optellen & aftrekken tot 100",
        subject = "Rekenen & Wiskunde",
        group = 4,
        estimatedMinutes = 10,
        steps = listOf(
            LessonStep("g4-as-01", "Tientallen", "Wat is 34 + 20?", LessonInteractionType.MultipleChoice, "tens", listOf(LessonOption("44","44"), LessonOption("54","54"), LessonOption("64","64")), "54", hint = "Tel twee tientallen bij 34.", explanation = "34 + 20 = 54."),
            LessonStep("g4-as-02", "Over het tiental", "Vul in: 48 + 7 = ___.", LessonInteractionType.FillBlank, "bridge-ten", acceptedAnswers = listOf("55"), hint = "Maak eerst 50: 48 + 2, daarna nog 5.", explanation = "48 + 7 = 55."),
            LessonStep("g4-as-03", "Luister en reken", "Luister naar de som en kies.", LessonInteractionType.ListenChoose, "subtraction", listOf(LessonOption("41","41"), LessonOption("44","44"), LessonOption("47","47")), "44", speakText = "Zesenvijftig min twaalf.", hint = "56 min 10 is 46; haal daarna nog 2 weg.", explanation = "56 − 12 = 44."),
            LessonStep("g4-as-04", "Strategie op volgorde", "Zet de stappen voor 67 + 8 goed.", LessonInteractionType.Ordering, "bridge-ten", options = listOf(LessonOption("split","Splits 8 in 3 en 5"), LessonOption("make70","67 + 3 = 70"), LessonOption("finish","70 + 5 = 75")), correctOrder = listOf("split","make70","finish"), hint = "Maak eerst het volgende tiental.", explanation = "Via 70 reken je 67 + 8 slim uit."),
            LessonStep("g4-as-05", "Aftrekken", "Wat is 83 − 30?", LessonInteractionType.MultipleChoice, "tens", listOf(LessonOption("43","43"), LessonOption("53","53"), LessonOption("63","63")), "53", hint = "Haal drie tientallen weg.", explanation = "83 − 30 = 53."),
            LessonStep("g4-as-06", "Verhaalsom", "Noor heeft 62 stickers en geeft er 18 weg. Hoeveel blijven er?", LessonInteractionType.FillBlank, "word-problem", acceptedAnswers = listOf("44"), hint = "62 − 10 = 52; 52 − 8 = 44.", explanation = "62 − 18 = 44."),
        ),
        remedialSteps = mapOf(
            "tens" to LessonStep("g4-as-r1", "Tientallen apart", "Wat is 40 + 30?", LessonInteractionType.FillBlank, "tens", acceptedAnswers = listOf("70"), hint = "4 tientallen + 3 tientallen.", explanation = "40 + 30 = 70."),
            "bridge-ten" to LessonStep("g4-as-r2", "Maak eerst een tiental", "19 + 4: vul eerst aan tot 20. Wat is het antwoord?", LessonInteractionType.FillBlank, "bridge-ten", acceptedAnswers = listOf("23"), hint = "19 + 1 = 20, daarna nog 3.", explanation = "19 + 4 = 23."),
            "subtraction" to LessonStep("g4-as-r3", "Stap voor stap eraf", "Wat is 45 − 10?", LessonInteractionType.FillBlank, "subtraction", acceptedAnswers = listOf("35"), hint = "Haal één tiental weg.", explanation = "45 − 10 = 35."),
            "word-problem" to LessonStep("g4-as-r4", "Vertaal het verhaal", "Je hebt 30 knikkers en geeft er 6 weg. Hoeveel blijven er?", LessonInteractionType.MultipleChoice, "word-problem", listOf(LessonOption("24","24"), LessonOption("36","36"), LessonOption("26","26")), "24", hint = "Weggeven betekent aftrekken.", explanation = "30 − 6 = 24."),
        ),
    )

    private fun dutchVowels() = LessonDefinition(
        id = G4_DUTCH_VOWELS,
        skillId = "g4-dutch-short-long-vowels",
        title = "Korte en lange klanken",
        subject = "Nederlands",
        group = 4,
        estimatedMinutes = 10,
        steps = listOf(
            LessonStep("g4-nl-01", "Hoor de klank", "Welk woord heeft een korte klank?", LessonInteractionType.MultipleChoice, "short-vowel", listOf(LessonOption("maan","maan"), LessonOption("man","man"), LessonOption("meer","meer")), "man", hint = "Zeg de woorden langzaam hardop.", explanation = "In man hoor je een korte a."),
            LessonStep("g4-nl-02", "Lange klank", "Welk woord heeft een lange klank?", LessonInteractionType.MultipleChoice, "long-vowel", listOf(LessonOption("vis","vis"), LessonOption("boom","boom"), LessonOption("bus","bus")), "boom", hint = "De oo klinkt lang.", explanation = "Boom heeft een lange oo-klank."),
            LessonStep("g4-nl-03", "Luister en kies", "Luister en kies het woord dat je hoort.", LessonInteractionType.ListenChoose, "listen-spell", listOf(LessonOption("tak","tak"), LessonOption("taak","taak"), LessonOption("tik","tik")), "taak", speakText = "taak", hint = "Luister naar de lange aa.", explanation = "Je hoort taak met een lange aa."),
            LessonStep("g4-nl-04", "Vul het woord aan", "Vul in: b__m. Het woord is boom.", LessonInteractionType.FillBlank, "long-vowel", acceptedAnswers = listOf("oo"), hint = "Je hoort een lange oo.", explanation = "Boom schrijf je met oo."),
            LessonStep("g4-nl-05", "Vergelijk", "Welke twee woorden verschillen alleen in korte/lange klinker?", LessonInteractionType.MultipleChoice, "contrast", listOf(LessonOption("man / maan","man / maan"), LessonOption("vis / boom","vis / boom"), LessonOption("kat / fiets","kat / fiets")), "man / maan", hint = "Zoek bijna dezelfde woorden.", explanation = "Man en maan verschillen in de lengte van de a-klank."),
            LessonStep("g4-nl-06", "Zet de regel goed", "Zet de denkstappen op volgorde.", LessonInteractionType.Ordering, "strategy", options = listOf(LessonOption("listen","Luister naar de klinker"), LessonOption("decide","Bepaal kort of lang"), LessonOption("write","Kies de juiste spelling")), correctOrder = listOf("listen","decide","write"), hint = "Je moet eerst horen voordat je schrijft.", explanation = "Luisteren → klank bepalen → spelling kiezen."),
        ),
        remedialSteps = mapOf(
            "short-vowel" to LessonStep("g4-nl-r1", "Korte klank nog eens", "Welke klinkt kort: kat of kaat?", LessonInteractionType.MultipleChoice, "short-vowel", listOf(LessonOption("kat","kat"), LessonOption("kaat","kaat")), "kat", hint = "Zeg beide woorden hardop.", explanation = "Kat heeft een korte a."),
            "long-vowel" to LessonStep("g4-nl-r2", "Lange klank nog eens", "Vul in: m__n. Het woord is maan.", LessonInteractionType.FillBlank, "long-vowel", acceptedAnswers = listOf("aa"), hint = "Maan heeft een lange aa.", explanation = "Maan schrijf je met aa."),
            "listen-spell" to LessonStep("g4-nl-r3", "Luister nog eens", "Luister en kies.", LessonInteractionType.ListenChoose, "listen-spell", listOf(LessonOption("bot","bot"), LessonOption("boot","boot")), "boot", speakText = "boot", hint = "Luister naar de lange oo.", explanation = "Je hoort boot."),
            "contrast" to LessonStep("g4-nl-r4", "Zie het verschil", "Welk paar laat kort/lang zien?", LessonInteractionType.MultipleChoice, "contrast", listOf(LessonOption("ram / raam","ram / raam"), LessonOption("zon / vis","zon / vis")), "ram / raam", hint = "De woorden moeten bijna hetzelfde zijn.", explanation = "Ram en raam laten korte en lange a zien."),
            "strategy" to LessonStep("g4-nl-r5", "Spelstrategie", "Wat doe je eerst bij een lastig woord?", LessonInteractionType.MultipleChoice, "strategy", listOf(LessonOption("luisteren","Goed luisteren"), LessonOption("gokken","Gokken")), "luisteren", hint = "Begin bij de klank.", explanation = "Goed luisteren is de eerste stap."),
        ),
    )
}
