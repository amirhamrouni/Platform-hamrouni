package nl.leersprong.app.feature.lesson

object AllLessons {
    val lessons: List<LessonDefinition> = LessonCatalog.lessons + CreativeNt2Lessons.lessons + listOf(
        group1(), group2(), group3(), group5(), group6(), group7(), group8(),
    )

    fun get(id: String): LessonDefinition = lessons.firstOrNull { it.id == id } ?: LessonCatalog.get(id)
    fun forGroup(group: Int): List<LessonDefinition> = lessons.filter { it.group == group }

    private fun group1() = LessonDefinition(
        id = "g1-math-counting-10-v1", skillId = "g1-math-counting-10", title = "Tellen tot 10",
        subject = "Rekenen & Wiskunde", group = 1, estimatedMinutes = 7,
        steps = listOf(
            LessonStep("g1-01", "Tel mee", "Hoeveel stippen zie je? ● ● ●", LessonInteractionType.MultipleChoice, "count", listOf(LessonOption("2","2"), LessonOption("3","3"), LessonOption("4","4")), "3", hint = "Wijs elke stip één keer aan.", explanation = "Er zijn 3 stippen."),
            LessonStep("g1-02", "Wat komt daarna?", "Welk getal komt na 5?", LessonInteractionType.MultipleChoice, "number-order", listOf(LessonOption("4","4"), LessonOption("6","6"), LessonOption("7","7")), "6", hint = "Tel verder: 4, 5, ...", explanation = "Na 5 komt 6."),
            LessonStep("g1-03", "Luister", "Luister en kies het getal.", LessonInteractionType.ListenChoose, "number-sound", listOf(LessonOption("7","7"), LessonOption("8","8"), LessonOption("9","9")), "8", speakText = "acht", hint = "Luister nog een keer.", explanation = "Je hoorde acht."),
            LessonStep("g1-04", "Meer of minder", "Welke groep heeft meer?", LessonInteractionType.MultipleChoice, "compare", listOf(LessonOption("a","● ●"), LessonOption("b","● ● ● ●")), "b", hint = "Tel beide groepjes.", explanation = "Vier is meer dan twee."),
        ),
        remedialSteps = mapOf(
            "count" to LessonStep("g1-r1", "Samen tellen", "Tel: ● ●", LessonInteractionType.MultipleChoice, "count", listOf(LessonOption("1","1"), LessonOption("2","2")), "2", hint = "Eén, twee.", explanation = "Er zijn 2 stippen."),
            "number-order" to LessonStep("g1-r2", "Getallenrij", "Wat komt na 2?", LessonInteractionType.MultipleChoice, "number-order", listOf(LessonOption("3","3"), LessonOption("4","4")), "3", hint = "1, 2, ...", explanation = "Na 2 komt 3."),
        ),
    )

    private fun group2() = LessonDefinition(
        id = "g2-math-addition-10-v1", skillId = "g2-math-addition-10", title = "Optellen tot 10",
        subject = "Rekenen & Wiskunde", group = 2, estimatedMinutes = 8,
        steps = listOf(
            LessonStep("g2-01", "Samenvoegen", "Wat is 3 + 2?", LessonInteractionType.MultipleChoice, "addition", listOf(LessonOption("4","4"), LessonOption("5","5"), LessonOption("6","6")), "5", hint = "Begin bij 3 en tel twee verder.", explanation = "3 + 2 = 5."),
            LessonStep("g2-02", "Vul in", "4 + 3 = ___.", LessonInteractionType.FillBlank, "addition", acceptedAnswers = listOf("7"), hint = "Tel drie verder vanaf 4.", explanation = "4 + 3 = 7."),
            LessonStep("g2-03", "Luister en kies", "Luister naar de som.", LessonInteractionType.ListenChoose, "addition", listOf(LessonOption("6","6"), LessonOption("7","7"), LessonOption("8","8")), "7", speakText = "vijf plus twee", hint = "Vijf, zes, zeven.", explanation = "5 + 2 = 7."),
            LessonStep("g2-04", "Verhaaltje", "Er liggen 6 appels. Er komen 2 bij. Hoeveel zijn er?", LessonInteractionType.MultipleChoice, "word-problem", listOf(LessonOption("4","4"), LessonOption("8","8"), LessonOption("9","9")), "8", hint = "Er komen appels bij, dus je telt op.", explanation = "6 + 2 = 8."),
        ),
        remedialSteps = mapOf(
            "addition" to LessonStep("g2-r1", "Tel verder", "2 + 1 = ?", LessonInteractionType.MultipleChoice, "addition", listOf(LessonOption("2","2"), LessonOption("3","3")), "3", hint = "Eén stap verder dan 2.", explanation = "2 + 1 = 3."),
            "word-problem" to LessonStep("g2-r2", "Wat gebeurt er?", "Je hebt 2 blokken en krijgt er 2 bij. Hoeveel?", LessonInteractionType.MultipleChoice, "word-problem", listOf(LessonOption("4","4"), LessonOption("0","0")), "4", hint = "Krijgen betekent erbij.", explanation = "2 + 2 = 4."),
        ),
    )

    private fun group3() = LessonDefinition(
        id = "g3-math-addition-20-v1", skillId = "g3-math-addition-20", title = "Slim rekenen tot 20",
        subject = "Rekenen & Wiskunde", group = 3, estimatedMinutes = 9,
        steps = listOf(
            LessonStep("g3-01", "Maak 10", "Wat is 8 + 5?", LessonInteractionType.MultipleChoice, "bridge-ten", listOf(LessonOption("12","12"), LessonOption("13","13"), LessonOption("14","14")), "13", hint = "8 + 2 = 10, daarna nog 3.", explanation = "8 + 5 = 13."),
            LessonStep("g3-02", "Aftrekken", "15 − 7 = ___.", LessonInteractionType.FillBlank, "subtraction", acceptedAnswers = listOf("8"), hint = "15 − 5 = 10, nog 2 eraf.", explanation = "15 − 7 = 8."),
            LessonStep("g3-03", "Luister", "Luister en kies.", LessonInteractionType.ListenChoose, "bridge-ten", listOf(LessonOption("15","15"), LessonOption("16","16"), LessonOption("17","17")), "16", speakText = "negen plus zeven", hint = "Maak eerst 10.", explanation = "9 + 7 = 16."),
            LessonStep("g3-04", "Stappen", "Zet 9 + 6 in slimme volgorde.", LessonInteractionType.Ordering, "strategy", options = listOf(LessonOption("split","Splits 6 in 1 en 5"), LessonOption("ten","9 + 1 = 10"), LessonOption("finish","10 + 5 = 15")), correctOrder = listOf("split","ten","finish"), hint = "Maak eerst 10.", explanation = "Via 10 krijg je 15."),
        ),
        remedialSteps = mapOf(
            "bridge-ten" to LessonStep("g3-r1", "Eerst naar 10", "9 + 2 = ?", LessonInteractionType.FillBlank, "bridge-ten", acceptedAnswers = listOf("11"), hint = "9 + 1 = 10, nog 1.", explanation = "9 + 2 = 11."),
            "subtraction" to LessonStep("g3-r2", "Eraf halen", "12 − 2 = ?", LessonInteractionType.FillBlank, "subtraction", acceptedAnswers = listOf("10"), hint = "Twee terug vanaf 12.", explanation = "12 − 2 = 10."),
        ),
    )

    private fun group5() = LessonDefinition(
        id = "g5-math-fractions-v1", skillId = "g5-math-fractions", title = "Breuken begrijpen",
        subject = "Rekenen & Wiskunde", group = 5, estimatedMinutes = 10,
        steps = listOf(
            LessonStep("g5-01", "Een deel van een geheel", "Welke breuk betekent één van vier gelijke delen?", LessonInteractionType.MultipleChoice, "fraction-meaning", listOf(LessonOption("1/2","1/2"), LessonOption("1/4","1/4"), LessonOption("4/1","4/1")), "1/4", hint = "De noemer vertelt hoeveel gelijke delen er zijn.", explanation = "Eén van vier delen is 1/4."),
            LessonStep("g5-02", "Gelijke breuken", "1/2 is hetzelfde als __/4.", LessonInteractionType.FillBlank, "equivalent", acceptedAnswers = listOf("2"), hint = "Verdubbel teller én noemer.", explanation = "1/2 = 2/4."),
            LessonStep("g5-03", "Vergelijk", "Welke is groter?", LessonInteractionType.MultipleChoice, "compare", listOf(LessonOption("1/3","1/3"), LessonOption("1/2","1/2")), "1/2", hint = "Bij dezelfde teller is een kleiner aantal delen groter.", explanation = "Een halve is groter dan een derde."),
            LessonStep("g5-04", "Verhaal", "Een pizza is in 8 stukken. Je eet 3 stukken. Welke breuk heb je gegeten?", LessonInteractionType.FillBlank, "word-problem", acceptedAnswers = listOf("3/8"), hint = "3 van de 8 stukken.", explanation = "Je eet 3/8 van de pizza."),
        ),
        remedialSteps = mapOf(
            "fraction-meaning" to LessonStep("g5-r1", "Tel de delen", "Eén van twee gelijke delen is?", LessonInteractionType.MultipleChoice, "fraction-meaning", listOf(LessonOption("1/2","1/2"), LessonOption("2/1","2/1")), "1/2", hint = "Eén van twee.", explanation = "Dat is 1/2."),
            "equivalent" to LessonStep("g5-r2", "Zelfde hoeveelheid", "1/2 = ?/2", LessonInteractionType.FillBlank, "equivalent", acceptedAnswers = listOf("1"), hint = "De breuk blijft hetzelfde.", explanation = "1/2 = 1/2."),
        ),
    )

    private fun group6() = LessonDefinition(
        id = "g6-math-decimals-percent-v1", skillId = "g6-math-decimals-percent", title = "Kommagetallen & procenten",
        subject = "Rekenen & Wiskunde", group = 6, estimatedMinutes = 11,
        steps = listOf(
            LessonStep("g6-01", "Kommagetal", "Welke is groter?", LessonInteractionType.MultipleChoice, "decimals", listOf(LessonOption("0,5","0,5"), LessonOption("0,35","0,35")), "0,5", hint = "0,5 is vijf tienden.", explanation = "0,5 > 0,35."),
            LessonStep("g6-02", "Procent", "50% van 20 = ___.", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("10"), hint = "50% is de helft.", explanation = "De helft van 20 is 10."),
            LessonStep("g6-03", "Koppelen", "Welke hoort bij 25%?", LessonInteractionType.MultipleChoice, "percent-fraction", listOf(LessonOption("1/2","1/2"), LessonOption("1/4","1/4"), LessonOption("3/4","3/4")), "1/4", hint = "100% verdeeld in vier gelijke delen.", explanation = "25% = 1/4."),
            LessonStep("g6-04", "Geld", "€12,50 + €2,75 = ___.", LessonInteractionType.FillBlank, "decimals", acceptedAnswers = listOf("15,25", "15.25"), hint = "Tel eerst euro's en centen.", explanation = "Samen is dat €15,25."),
        ),
        remedialSteps = mapOf(
            "decimals" to LessonStep("g6-r1", "Tienden", "0,5 = hoeveel tienden?", LessonInteractionType.FillBlank, "decimals", acceptedAnswers = listOf("5"), hint = "Kijk naar de eerste plek na de komma.", explanation = "0,5 is vijf tienden."),
            "percent" to LessonStep("g6-r2", "De helft", "50% van 8 = ?", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("4"), hint = "Neem de helft.", explanation = "De helft van 8 is 4."),
        ),
    )

    private fun group7() = LessonDefinition(
        id = "g7-math-ratio-v1", skillId = "g7-math-ratio", title = "Verhoudingen oplossen",
        subject = "Rekenen & Wiskunde", group = 7, estimatedMinutes = 12,
        steps = listOf(
            LessonStep("g7-01", "Verhouding", "Voor 2 glazen limonade gebruik je 1 lepel siroop. Hoeveel lepels voor 6 glazen?", LessonInteractionType.MultipleChoice, "ratio", listOf(LessonOption("2","2"), LessonOption("3","3"), LessonOption("6","6")), "3", hint = "6 is drie keer 2.", explanation = "Dus je hebt drie keer 1 lepel nodig: 3."),
            LessonStep("g7-02", "Schaal", "Op een kaart is 1 cm = 5 km. 4 cm is __ km.", LessonInteractionType.FillBlank, "scale", acceptedAnswers = listOf("20"), hint = "4 × 5.", explanation = "4 cm staat voor 20 km."),
            LessonStep("g7-03", "Procent", "20% van 50 = ___.", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("10"), hint = "10% van 50 is 5.", explanation = "20% is twee keer 10%, dus 10."),
            LessonStep("g7-04", "Route", "Zet de aanpak voor 3 : 5 = 12 : ? in volgorde.", LessonInteractionType.Ordering, "ratio-strategy", options = listOf(LessonOption("factor","Van 3 naar 12 is ×4"), LessonOption("apply","Doe 5 × 4"), LessonOption("answer","Antwoord 20")), correctOrder = listOf("factor","apply","answer"), hint = "Gebruik dezelfde factor aan beide kanten.", explanation = "3×4=12, dus 5×4=20."),
        ),
        remedialSteps = mapOf(
            "ratio" to LessonStep("g7-r1", "Zelfde factor", "1 zak kost €2. Wat kosten 3 zakken?", LessonInteractionType.FillBlank, "ratio", acceptedAnswers = listOf("6", "€6"), hint = "3 × €2.", explanation = "Drie zakken kosten €6."),
            "scale" to LessonStep("g7-r2", "Kaartschaal", "1 cm = 2 km. 2 cm = ? km", LessonInteractionType.FillBlank, "scale", acceptedAnswers = listOf("4"), hint = "2 × 2.", explanation = "Dat is 4 km."),
        ),
    )

    private fun group8() = LessonDefinition(
        id = "g8-math-data-v1", skillId = "g8-math-data", title = "Data & procenten gebruiken",
        subject = "Rekenen & Wiskunde", group = 8, estimatedMinutes = 12,
        steps = listOf(
            LessonStep("g8-01", "Gemiddelde", "Wat is het gemiddelde van 6, 8 en 10?", LessonInteractionType.MultipleChoice, "average", listOf(LessonOption("7","7"), LessonOption("8","8"), LessonOption("9","9")), "8", hint = "Tel op en deel door drie.", explanation = "(6+8+10)/3 = 8."),
            LessonStep("g8-02", "Korting", "Een jas van €80 krijgt 25% korting. De korting is €___.", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("20"), hint = "25% is een kwart.", explanation = "Een kwart van €80 is €20."),
            LessonStep("g8-03", "Kans", "Een zak heeft 3 rode en 1 blauwe knikker. Welke kleur trek je waarschijnlijker?", LessonInteractionType.MultipleChoice, "probability", listOf(LessonOption("red","rood"), LessonOption("blue","blauw")), "red", hint = "Welke kleur zit er vaker in?", explanation = "Er zijn meer rode knikkers, dus rood is waarschijnlijker."),
            LessonStep("g8-04", "Data-aanpak", "Zet in volgorde hoe je een gemiddelde berekent.", LessonInteractionType.Ordering, "average", options = listOf(LessonOption("sum","Tel alle waarden op"), LessonOption("count","Tel hoeveel waarden er zijn"), LessonOption("divide","Deel de som door het aantal")), correctOrder = listOf("sum","count","divide"), hint = "Eerst heb je de totale som nodig.", explanation = "Som ÷ aantal = gemiddelde."),
        ),
        remedialSteps = mapOf(
            "average" to LessonStep("g8-r1", "Klein gemiddelde", "Gemiddelde van 4 en 6 = ?", LessonInteractionType.FillBlank, "average", acceptedAnswers = listOf("5"), hint = "(4+6)/2.", explanation = "10/2 = 5."),
            "percent" to LessonStep("g8-r2", "Tien procent", "10% van 30 = ?", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("3"), hint = "Deel door 10.", explanation = "10% van 30 is 3."),
        ),
    )
}
