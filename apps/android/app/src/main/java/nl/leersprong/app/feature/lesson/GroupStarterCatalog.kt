package nl.leersprong.app.feature.lesson

/** Native starter curriculum beyond the deeper Groep 4 tracks. */
object GroupStarterCatalog {
    val lessons = listOf(
        lesson(1, "g1-math-counting-10-v1", "g1-math-counting-10", "Tellen tot 10", "Rekenen & Wiskunde",
            mc("g1-1", "Tellen", "Hoeveel stippen zie je: ● ● ●?", "3", listOf("2", "3", "4"), "Tel elke stip één keer."),
            listen("g1-2", "Luister en kies", "Luister naar het getal.", "vijf", "5", listOf("4", "5", "6"), "Het woord vijf hoort bij 5."),
            mc("g1-3", "Meer of minder", "Welke groep heeft meer?", "5", listOf("2", "3", "5"), "Meer betekent: het grootste aantal."),
            fill("g1-4", "Tel verder", "1, 2, 3, 4, __", "5", "Na vier komt vijf."),
            mc("g1-5", "Kies het getal", "Welk getal hoort bij zeven blokken?", "7", listOf("6", "7", "8"), "Zeven blokken horen bij het cijfer 7."),
        ),
        lesson(2, "g2-math-add-10-v1", "g2-math-add-10", "Optellen tot 10", "Rekenen & Wiskunde",
            mc("g2-1", "Samenvoegen", "Je hebt 3 appels en krijgt er 2 bij. Hoeveel heb je?", "5", listOf("4", "5", "6"), "3 + 2 = 5."),
            fill("g2-2", "Maak vijf", "4 + 1 = __", "5", "Eén erbij maakt vijf."),
            listen("g2-3", "Luister en reken", "Luister en kies het antwoord.", "twee plus drie", "5", listOf("4", "5", "6"), "2 + 3 = 5."),
            mc("g2-4", "Verhaalsom", "Er zitten 5 vogels. Er komen 2 bij. Hoeveel vogels zijn er?", "7", listOf("6", "7", "8"), "5 + 2 = 7."),
            fill("g2-5", "Bijna tien", "8 + 2 = __", "10", "Twee erbij maakt tien."),
        ),
        lesson(3, "g3-math-add-sub-20-v1", "g3-math-add-sub-20", "Optellen & aftrekken tot 20", "Rekenen & Wiskunde",
            mc("g3-1", "Over de tien", "Wat is 9 + 4?", "13", listOf("12", "13", "14"), "Maak eerst 10: 9 + 1, daarna nog 3."),
            fill("g3-2", "Aftrekken", "15 − 6 = __", "9", "15 − 5 = 10 en nog 1 eraf is 9."),
            listen("g3-3", "Luister en reken", "Luister naar de som.", "zeven plus acht", "15", listOf("14", "15", "16"), "7 + 8 = 15."),
            mc("g3-4", "Verhaal", "Mila heeft 18 kralen en geeft er 5 weg. Hoeveel blijven er?", "13", listOf("12", "13", "14"), "18 − 5 = 13."),
            fill("g3-5", "Verdubbelen", "Dubbel 7 is __", "14", "7 + 7 = 14."),
        ),
        lesson(5, "g5-math-fractions-v1", "g5-math-fractions", "Breuken begrijpen", "Rekenen & Wiskunde",
            mc("g5-1", "Een helft", "Welke breuk betekent één van twee gelijke delen?", "1/2", listOf("1/2", "1/3", "2/3"), "Een helft schrijf je als 1/2."),
            mc("g5-2", "Vergelijken", "Welke breuk is groter?", "3/4", listOf("1/4", "2/4", "3/4"), "Bij gelijke noemers is de grootste teller de grootste breuk."),
            fill("g5-3", "Gelijke delen", "Twee kwart is gelijk aan __ helft.", "1", "2/4 is hetzelfde als 1/2."),
            listen("g5-4", "Luister", "Luister en kies de breuk.", "drie vierde", "3/4", listOf("1/4", "2/4", "3/4"), "Drie vierde schrijf je als 3/4."),
            mc("g5-5", "Pizza", "Een pizza is in 8 gelijke stukken. Je eet 3. Welke breuk heb je gegeten?", "3/8", listOf("3/8", "5/8", "3/5"), "3 van de 8 gelijke stukken is 3/8."),
        ),
        lesson(6, "g6-math-decimals-percent-v1", "g6-math-decimals-percent", "Kommagetallen & procenten", "Rekenen & Wiskunde",
            mc("g6-1", "Kommagetal", "Welke is gelijk aan een halve?", "0,5", listOf("0,2", "0,5", "1,5"), "Een halve is 0,5."),
            fill("g6-2", "Procent", "50% van 20 = __", "10", "50% is de helft; de helft van 20 is 10."),
            mc("g6-3", "Koppelen", "Welke combinatie klopt?", "25% = 1/4", listOf("25% = 1/4", "25% = 1/2", "50% = 1/4"), "25% is één kwart."),
            listen("g6-4", "Luister", "Luister en kies.", "nul komma vijfenzeventig", "0,75", listOf("0,57", "0,75", "7,5"), "Nul komma vijfenzeventig schrijf je als 0,75."),
            fill("g6-5", "Geld", "€2,50 + €1,25 = €__", "3,75", "2,50 + 1,25 = 3,75."),
        ),
        lesson(7, "g7-math-ratios-v1", "g7-math-ratios", "Verhoudingen", "Rekenen & Wiskunde",
            mc("g7-1", "Schaal", "2 bekers sap vragen 6 bekers water. Hoeveel water bij 4 bekers sap?", "12", listOf("8", "10", "12"), "Alles verdubbelt: 6 × 2 = 12."),
            fill("g7-2", "Prijs", "3 schriftjes kosten €6. Eén schriftje kost €__", "2", "6 gedeeld door 3 is 2."),
            mc("g7-3", "Tabel", "5 km duurt 30 min. Bij hetzelfde tempo duurt 10 km?", "60 min", listOf("45 min", "60 min", "90 min"), "Dubbele afstand bij gelijk tempo is dubbele tijd."),
            listen("g7-4", "Luister", "Luister naar de verhouding.", "één op vier", "1:4", listOf("1:2", "1:4", "4:1"), "Één op vier noteer je als 1:4."),
            fill("g7-5", "Recept", "Voor 4 personen heb je 300 g rijst nodig. Voor 8 personen: __ g.", "600", "8 is dubbel 4, dus 300 × 2 = 600."),
        ),
        lesson(8, "g8-math-percent-data-v1", "g8-math-percent-data", "Procenten & data", "Rekenen & Wiskunde",
            fill("g8-1", "Korting", "Een jas van €80 heeft 25% korting. De korting is €__", "20", "25% is een kwart; 80 ÷ 4 = 20."),
            mc("g8-2", "Gemiddelde", "Wat is het gemiddelde van 6, 8 en 10?", "8", listOf("7", "8", "9"), "6 + 8 + 10 = 24; 24 ÷ 3 = 8."),
            fill("g8-3", "Toename", "Een bedrag stijgt van 50 naar 60. De stijging is __%.", "20", "De stijging is 10 op 50: 10/50 = 20%."),
            mc("g8-4", "Grafiek lezen", "Een klas leest ma 12, di 18, wo 15 pagina's. Welke dag is het hoogst?", "dinsdag", listOf("maandag", "dinsdag", "woensdag"), "18 is het hoogste aantal."),
            listen("g8-5", "Luister", "Luister en kies het percentage.", "vijfenzeventig procent", "75%", listOf("25%", "50%", "75%"), "Vijfenzeventig procent schrijf je als 75%."),
        ),
    )

    private fun lesson(group: Int, id: String, skill: String, title: String, subject: String, vararg steps: LessonStep) =
        LessonDefinition(id, skill, title, subject, group, 8, steps.toList(), emptyMap())

    private fun mc(id: String, title: String, prompt: String, answer: String, options: List<String>, explanation: String) =
        LessonStep(id, title, prompt, LessonInteractionType.MultipleChoice, title.lowercase(), options.map { LessonOption(it, it) }, answer, hint = explanation, explanation = explanation)

    private fun fill(id: String, title: String, prompt: String, answer: String, explanation: String) =
        LessonStep(id, title, prompt, LessonInteractionType.FillBlank, title.lowercase(), acceptedAnswers = listOf(answer), hint = explanation, explanation = explanation)

    private fun listen(id: String, title: String, prompt: String, spoken: String, answer: String, options: List<String>, explanation: String) =
        LessonStep(id, title, prompt, LessonInteractionType.ListenChoose, title.lowercase(), options.map { LessonOption(it, it) }, answer, speakText = spoken, hint = explanation, explanation = explanation)
}
