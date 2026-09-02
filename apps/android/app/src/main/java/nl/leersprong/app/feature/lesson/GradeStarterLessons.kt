package nl.leersprong.app.feature.lesson

object AllLessons {
    val lessons: List<LessonDefinition> = LessonCatalog.lessons + listOf(
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
            "percent" to LessonStep("g6-r1", "Procent als deel", "50% van 10 = ?", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("5"), hint = "Neem de helft.", explanation = "50% van 10 is 5."),
            "decimals" to LessonStep("g6-r2", "Tienden", "0,7 of 0,4: welke is groter?", LessonInteractionType.MultipleChoice, "decimals", listOf(LessonOption("0,7","0,7"), LessonOption("0,4","0,4")), "0,7", hint = "Zeven tienden is meer dan vier tienden.", explanation = "0,7 is groter."),
        ),
    )

    private fun group7() = LessonDefinition(
        id = "g7-math-ratios-v1", skillId = "g7-math-ratios", title = "Verhoudingen slim gebruiken",
        subject = "Rekenen & Wiskunde", group = 7, estimatedMinutes = 11,
        steps = listOf(
            LessonStep("g7-01", "Verhouding", "2 rode kralen op 3 blauwe. Hoe schrijf je rood : blauw?", LessonInteractionType.MultipleChoice, "ratio", listOf(LessonOption("2:3","2:3"), LessonOption("3:2","3:2"), LessonOption("5:1","5:1")), "2:3", hint = "Schrijf in dezelfde volgorde als de vraag.", explanation = "Rood : blauw = 2 : 3."),
            LessonStep("g7-02", "Opschalen", "3 flessen kosten €6. Wat kosten 6 flessen?", LessonInteractionType.FillBlank, "scale", acceptedAnswers = listOf("12", "€12"), hint = "6 is twee keer 3.", explanation = "Dus de prijs wordt ook twee keer: €12."),
            LessonStep("g7-03", "Schaal", "Op een kaart is 1 cm = 5 km. Hoeveel km is 4 cm?", LessonInteractionType.FillBlank, "scale", acceptedAnswers = listOf("20"), hint = "4 × 5.", explanation = "4 cm staat voor 20 km."),
            LessonStep("g7-04", "Proportioneel", "Welke tabel klopt bij 2 broodjes = €5?", LessonInteractionType.MultipleChoice, "proportion", listOf(LessonOption("a","4 broodjes = €10"), LessonOption("b","4 broodjes = €7")), "a", hint = "Verdubbel je het aantal, dan verdubbelt de prijs.", explanation = "4 broodjes kosten €10."),
        ),
        remedialSteps = mapOf(
            "ratio" to LessonStep("g7-r1", "Lees de volgorde", "1 kat en 2 honden. Kat : hond = ?", LessonInteractionType.MultipleChoice, "ratio", listOf(LessonOption("1:2","1:2"), LessonOption("2:1","2:1")), "1:2", hint = "Kat staat eerst.", explanation = "Kat : hond = 1 : 2."),
            "scale" to LessonStep("g7-r2", "Eenvoudig opschalen", "2 × 4 = ?", LessonInteractionType.FillBlank, "scale", acceptedAnswers = listOf("8"), hint = "Twee groepjes van vier.", explanation = "2 × 4 = 8."),
        ),
    )

    private fun group8() = LessonDefinition(
        id = "g8-math-percent-data-v1", skillId = "g8-math-percent-data", title = "Procenten & data",
        subject = "Rekenen & Wiskunde", group = 8, estimatedMinutes = 12,
        steps = listOf(
            LessonStep("g8-01", "Korting", "Een jas kost €80 en heeft 25% korting. Hoeveel euro korting?", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("20", "€20"), hint = "25% is een kwart.", explanation = "Een kwart van €80 is €20."),
            LessonStep("g8-02", "Nieuwe prijs", "De jas van €80 krijgt €20 korting. Nieuwe prijs?", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("60", "€60"), hint = "80 − 20.", explanation = "De nieuwe prijs is €60."),
            LessonStep("g8-03", "Gemiddelde", "Wat is het gemiddelde van 6, 8 en 10?", LessonInteractionType.MultipleChoice, "average", listOf(LessonOption("8","8"), LessonOption("9","9"), LessonOption("24","24")), "8", hint = "Tel op en deel door 3.", explanation = "6 + 8 + 10 = 24; 24 / 3 = 8."),
            LessonStep("g8-04", "Data lezen", "Een klas heeft scores 7, 7, 8, 9. Welke score komt het vaakst voor?", LessonInteractionType.MultipleChoice, "mode", listOf(LessonOption("7","7"), LessonOption("8","8"), LessonOption("9","9")), "7", hint = "Tel hoe vaak elke score voorkomt.", explanation = "7 komt twee keer voor en is de modus."),
            LessonStep("g8-05", "Procent naar breuk", "75% is gelijk aan?", LessonInteractionType.MultipleChoice, "percent-fraction", listOf(LessonOption("1/4","1/4"), LessonOption("1/2","1/2"), LessonOption("3/4","3/4")), "3/4", hint = "75 van 100 kun je vereenvoudigen.", explanation = "75% = 3/4."),
        ),
        remedialSteps = mapOf(
            "percent" to LessonStep("g8-r1", "Terug naar 10%", "10% van 50 = ?", LessonInteractionType.FillBlank, "percent", acceptedAnswers = listOf("5"), hint = "Deel door 10.", explanation = "10% van 50 is 5."),
            "average" to LessonStep("g8-r2", "Gemiddelde stap voor stap", "Gemiddelde van 4 en 6?", LessonInteractionType.FillBlank, "average", acceptedAnswers = listOf("5"), hint = "4 + 6 = 10; deel door 2.", explanation = "Het gemiddelde is 5."),
        ),
    )
}
