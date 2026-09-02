package nl.leersprong.app.feature.lesson

/**
 * Runnable Groep 4 starter tracks for creative learning and NT2 language support.
 * They deliberately reuse the native Lesson Player so evidence, XP, relearning and review
 * behave exactly like the existing core lessons.
 */
object CreativeNt2Lessons {
    val lessons: List<LessonDefinition> = listOf(
        artsAndCulture(),
        nt2LanguageSupport(),
    )

    private fun artsAndCulture() = LessonDefinition(
        id = "g4-arts-story-image-v1",
        skillId = "g4-arts-story-image",
        title = "Beeld vertelt een verhaal",
        subject = "Kunst & Cultuur",
        group = 4,
        estimatedMinutes = 9,
        steps = listOf(
            LessonStep("g4-art-01", "Kijken als een maker", "Welke keuze maakt een figuur het duidelijkst het belangrijkste deel van een tekening?", LessonInteractionType.MultipleChoice, "visual-focus", listOf(LessonOption("a", "Alles even groot tekenen"), LessonOption("b", "Het belangrijkste groter of opvallender maken"), LessonOption("c", "Geen vormen gebruiken")), "b", hint = "Denk aan waar je oog als eerste naartoe gaat.", explanation = "Grootte, plaats en contrast kunnen de aandacht naar het belangrijkste deel sturen."),
            LessonStep("g4-art-02", "Sfeer kiezen", "Welke woorden passen het best bij een rustige sfeer?", LessonInteractionType.MultipleChoice, "mood", listOf(LessonOption("calm", "zacht · stil · langzaam"), LessonOption("busy", "hard · druk · snel"), LessonOption("random", "vierkant · zeven · links")), "calm", hint = "Sfeer gaat over wat een beeld of verhaal je laat voelen.", explanation = "Zacht, stil en langzaam kunnen samen een rustige sfeer oproepen."),
            LessonStep("g4-art-03", "Verhaalvolgorde", "Zet een mini-verhaal in een logische volgorde.", LessonInteractionType.Ordering, "story-sequence", options = listOf(LessonOption("start", "Een kind vindt een lege doos"), LessonOption("make", "Het kind maakt er een raket van"), LessonOption("end", "De raket krijgt een plek in de tentoonstelling")), correctOrder = listOf("start", "make", "end"), hint = "Wat moet eerst gebeuren voordat de raket kan worden getoond?", explanation = "Een verhaal krijgt betekenis door een herkenbaar begin, ontwikkeling en einde."),
            LessonStep("g4-art-04", "Reflecteren", "Vul in: Ik kan uitleggen waarom ik een kleur, vorm of materiaal ___.", LessonInteractionType.FillBlank, "reflection", acceptedAnswers = listOf("kies", "gekozen heb", "gebruik"), hint = "Een maker kan vertellen over zijn of haar keuze.", explanation = "Reflecteren betekent dat je kunt vertellen wat je maakte en waarom je bepaalde keuzes maakte."),
        ),
        remedialSteps = mapOf(
            "visual-focus" to LessonStep("g4-art-r1", "Eén blikvanger", "Je wilt dat iedereen eerst de zon ziet. Wat helpt?", LessonInteractionType.MultipleChoice, "visual-focus", listOf(LessonOption("big", "Maak de zon groter"), LessonOption("hide", "Verstop de zon")), "big", hint = "Maak het belangrijkste makkelijker te zien.", explanation = "Een grotere blikvanger trekt sneller aandacht."),
            "story-sequence" to LessonStep("g4-art-r2", "Eerst en daarna", "Wat komt eerst?", LessonInteractionType.MultipleChoice, "story-sequence", listOf(LessonOption("seed", "Je plant een zaadje"), LessonOption("flower", "Je ziet een bloem")), "seed", hint = "Denk aan oorzaak en gevolg.", explanation = "Eerst plant je het zaadje, later kan er een bloem groeien."),
        ),
    )

    private fun nt2LanguageSupport() = LessonDefinition(
        id = "g4-nt2-school-language-v1",
        skillId = "g4-nt2-school-language",
        title = "Schooltaal slim begrijpen",
        subject = "NT2 / Thuistaalhulp",
        group = 4,
        estimatedMinutes = 8,
        steps = listOf(
            LessonStep("g4-nt2-01", "Opdrachtwoorden", "Wat moet je doen als er staat: 'Vergelijk de twee antwoorden'?", LessonInteractionType.MultipleChoice, "instruction-words", listOf(LessonOption("copy", "Alles overschrijven"), LessonOption("compare", "Kijken wat hetzelfde en anders is"), LessonOption("skip", "De vraag overslaan")), "compare", hint = "Vergelijken betekent dat je naar overeenkomsten én verschillen kijkt.", explanation = "Bij vergelijken kijk je wat hetzelfde is en wat anders is."),
            LessonStep("g4-nt2-02", "Luister naar schooltaal", "Luister en kies wat het woord betekent.", LessonInteractionType.ListenChoose, "instruction-words", listOf(LessonOption("tell", "vertellen wat iets betekent"), LessonOption("draw", "alleen tekenen"), LessonOption("erase", "wissen")), "tell", speakText = "Leg uit", hint = "Je hoort dit vaak bij vragen waarop je meer dan één woord antwoordt.", explanation = "'Leg uit' vraagt je om met woorden duidelijk te maken hoe of waarom iets zo is."),
            LessonStep("g4-nt2-03", "Een goede zin", "Zet de woorden in een duidelijke Nederlandse zin.", LessonInteractionType.Ordering, "sentence-order", options = listOf(LessonOption("ik", "Ik"), LessonOption("lees", "lees"), LessonOption("boek", "het boek")), correctOrder = listOf("ik", "lees", "boek"), hint = "Begin met wie iets doet.", explanation = "Een eenvoudige Nederlandse hoofdzin kan beginnen met onderwerp + werkwoord + aanvulling."),
            LessonStep("g4-nt2-04", "Betekenis uit context", "Mila was uitgeput na de lange wandeling. Welk woord past bij 'uitgeput'?", LessonInteractionType.MultipleChoice, "context", listOf(LessonOption("tired", "heel moe"), LessonOption("happy", "heel vrolijk"), LessonOption("small", "heel klein")), "tired", hint = "Kijk naar wat er vóór het moeilijke woord gebeurde.", explanation = "Na een lange wandeling kan iemand heel moe zijn; de context helpt je de betekenis te vinden."),
        ),
        remedialSteps = mapOf(
            "instruction-words" to LessonStep("g4-nt2-r1", "Wat vraagt de opdracht?", "'Noem twee dieren.' Wat moet je doen?", LessonInteractionType.MultipleChoice, "instruction-words", listOf(LessonOption("two", "Twee dieren opschrijven of zeggen"), LessonOption("story", "Een lang verhaal schrijven")), "two", hint = "Let op het woord 'twee'.", explanation = "'Noem' vraagt om voorbeelden; hier precies twee."),
            "sentence-order" to LessonStep("g4-nt2-r2", "Korte zin", "Zet goed: eet / Sam / brood", LessonInteractionType.Ordering, "sentence-order", options = listOf(LessonOption("sam", "Sam"), LessonOption("eet", "eet"), LessonOption("brood", "brood")), correctOrder = listOf("sam", "eet", "brood"), hint = "Wie doet iets? Wat doet die persoon?", explanation = "Sam eet brood."),
        ),
    )
}
