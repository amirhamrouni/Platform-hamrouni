package nl.leersprong.app.feature.lesson

/**
 * Third school-year content wave: three extra lessons per group so core concepts recur
 * with new contexts instead of appearing only once. Content is original and designed
 * to run in the existing adaptive lesson player.
 */
object SchoolYearDepthLessons {
    val lessons: List<LessonDefinition> = listOf(
        // Groep 1
        lesson("g1-nl-sounds-letters-depth-v1", "g1-nl-sounds-letters-depth", "Klanken en letters ontdekken", "Nederlands", 1, 7,
            item("g1d-nl-01", "Begin-klank", "Welk woord begint met de klank m?", "maan", "maan", "vis", "roos"),
            item("g1d-nl-02", "Zelfde klank", "Welke twee woorden beginnen hetzelfde?", "maan-muis", "maan-muis", "vis-roos", "kat-boom"),
            item("g1d-nl-03", "Letter herkennen", "Welke letter hoort bij de klank sss?", "s", "m", "s", "r"),
            item("g1d-nl-04", "Eind-klank", "Welk woord eindigt op t?", "kat", "maan", "kat", "vis"),
        ),
        lesson("g1-math-patterns-depth-v1", "g1-math-patterns-depth", "Tellen, vergelijken en patronen", "Rekenen & Wiskunde", 1, 7,
            item("g1d-m-01", "Tellen", "Hoeveel sterren? ★ ★ ★ ★", "4", "3", "4", "5"),
            item("g1d-m-02", "Meer", "Welke groep heeft meer?", "vijf", "drie", "vijf", "evenveel"),
            item("g1d-m-03", "Patroon", "Wat komt hierna: rood, blauw, rood, blauw, ...?", "rood", "blauw", "rood", "groen"),
            item("g1d-m-04", "Volgorde", "Welk getal komt vóór 7?", "6", "5", "6", "8"),
        ),
        lesson("g1-world-seasons-depth-v1", "g1-world-seasons-depth", "Seizoenen om je heen", "Wereldoriëntatie", 1, 7,
            item("g1d-w-01", "Herfst", "Wat zie je vaak in de herfst?", "vallende bladeren", "vallende bladeren", "sneeuwpoppen", "strandweer"),
            item("g1d-w-02", "Winter", "Wanneer kan water buiten bevriezen?", "winter", "zomer", "winter", "lente"),
            item("g1d-w-03", "Lente", "In welk seizoen groeien veel nieuwe blaadjes?", "lente", "winter", "lente", "herfst"),
            item("g1d-w-04", "Zomer", "Wanneer zijn de dagen meestal het langst?", "zomer", "winter", "zomer", "herfst"),
        ),

        // Groep 2
        lesson("g2-nl-sentences-depth-v1", "g2-nl-sentences-depth", "Zinnen lezen en begrijpen", "Nederlands", 2, 8,
            item("g2d-nl-01", "Wie?", "De hond rent achter de bal. Wie rent?", "de hond", "de hond", "de bal", "niemand"),
            item("g2d-nl-02", "Wat gebeurt?", "Mila eet een appel. Wat doet Mila?", "eten", "slapen", "eten", "lezen"),
            item("g2d-nl-03", "Punt", "Welke zin is netjes af?", "Ik ga naar school.", "Ik ga naar school.", "ik ga naar school", "school naar ik"),
            item("g2d-nl-04", "Betekenis", "Sam trekt zijn jas aan omdat het koud is. Waarom trekt hij zijn jas aan?", "omdat het koud is", "omdat het warm is", "omdat het koud is", "om te zwemmen"),
        ),
        lesson("g2-math-numberline-depth-v1", "g2-math-numberline-depth", "Getallenlijn tot 20", "Rekenen & Wiskunde", 2, 8,
            item("g2d-m-01", "Verder tellen", "Wat komt na 14?", "15", "13", "15", "16"),
            item("g2d-m-02", "Terug tellen", "Wat komt vóór 10?", "9", "8", "9", "11"),
            item("g2d-m-03", "Sprong vooruit", "Je staat op 7 en gaat 3 vooruit. Waar kom je?", "10", "9", "10", "11"),
            item("g2d-m-04", "Vergelijken", "Welk getal is het grootst?", "18", "12", "18", "15"),
        ),
        lesson("g2-math-clock-depth-v1", "g2-math-clock-depth", "Hele uren en dagritme", "Rekenen & Wiskunde", 2, 8,
            item("g2d-t-01", "Hele uur", "De lange wijzer staat op 12 en de korte op 8. Hoe laat is het?", "8 uur", "7 uur", "8 uur", "half 8"),
            item("g2d-t-02", "Ochtend", "Wat doe je meestal in de ochtend?", "ontbijten", "avondeten", "ontbijten", "naar bed gaan"),
            item("g2d-t-03", "Volgorde", "Wat komt meestal eerst?", "ontbijt", "ontbijt", "avondeten", "slapen na middernacht"),
            item("g2d-t-04", "Tijd herkennen", "School begint om 8 uur. Welke tijd hoort daarbij?", "08:00", "08:00", "20:00", "18:00"),
        ),

        // Groep 3
        lesson("g3-nl-spelling-depth-v1", "g3-nl-spelling-depth", "Spelling: klankgroepen oefenen", "Nederlands", 3, 9,
            item("g3d-nl-01", "Korte klank", "Welk woord heeft een korte a?", "kat", "maan", "kat", "boom"),
            item("g3d-nl-02", "Lange klank", "Welk woord heeft een lange oo?", "boom", "bom", "boom", "bus"),
            item("g3d-nl-03", "Tweetekenklank", "In welk woord hoor je ui?", "huis", "huis", "vis", "maan"),
            item("g3d-nl-04", "Goed gespeld", "Welk woord is goed gespeld?", "fiets", "fietz", "fiets", "viets"),
        ),
        lesson("g3-math-to100-depth-v1", "g3-math-to100-depth", "Slim rekenen tot 100", "Rekenen & Wiskunde", 3, 9,
            item("g3d-m-01", "Tientallen", "40 + 30 = ?", "70", "60", "70", "80"),
            item("g3d-m-02", "Over tiental", "38 + 5 = ?", "43", "42", "43", "45"),
            item("g3d-m-03", "Aftrekken", "62 − 20 = ?", "42", "40", "42", "52"),
            item("g3d-m-04", "Verhaalsom", "Er zijn 25 boeken. Er komen 10 bij. Hoeveel zijn er?", "35", "15", "35", "45"),
        ),
        lesson("g3-world-map-depth-v1", "g3-world-map-depth", "Mijn buurt op de kaart", "Wereldoriëntatie", 3, 9,
            item("g3d-w-01", "Kaart", "Waarvoor gebruik je een kaart?", "om te zien waar plekken liggen", "om te zien waar plekken liggen", "om eten te koken", "om muziek te maken"),
            item("g3d-w-02", "Legenda", "Wat legt een legenda op een kaart uit?", "symbolen", "symbolen", "het weer morgen", "de leeftijd van mensen"),
            item("g3d-w-03", "Route", "Je loopt van huis naar school. Wat helpt je de route te volgen?", "straten en herkenningspunten", "straten en herkenningspunten", "alleen kleuren", "een dobbelsteen"),
            item("g3d-w-04", "Noord", "Welke pijl staat op veel kaarten voor noord?", "N", "Z", "N", "O"),
        ),

        // Groep 4
        lesson("g4-nl-mainidea-depth-v1", "g4-nl-mainidea-depth", "Hoofdgedachte vinden", "Nederlands", 4, 10,
            item("g4d-nl-01", "Kern", "Een tekst vertelt vooral hoe bijen honing maken. Wat is de hoofdgedachte?", "hoe bijen honing maken", "hoe bijen honing maken", "waar fietsen staan", "wat katten eten"),
            item("g4d-nl-02", "Titel", "Welke titel past bij een tekst over veilig oversteken?", "Veilig de straat over", "Veilig de straat over", "Mijn favoriete pizza", "De ruimte"),
            item("g4d-nl-03", "Detail", "Wat is meestal een detail?", "een voorbeeld dat de kern uitlegt", "de centrale boodschap", "een voorbeeld dat de kern uitlegt", "de titel altijd"),
            item("g4d-nl-04", "Samenvatten", "Wat hoort in een korte samenvatting?", "de belangrijkste informatie", "alle voorbeelden", "de belangrijkste informatie", "elk woord uit de tekst"),
        ),
        lesson("g4-math-division-depth-v1", "g4-math-division-depth", "Delen als eerlijk verdelen", "Rekenen & Wiskunde", 4, 10,
            item("g4d-m-01", "Verdelen", "12 appels eerlijk over 3 kinderen. Hoeveel krijgt ieder?", "4", "3", "4", "6"),
            item("g4d-m-02", "Keersom helpt", "Welke keersom helpt bij 20 ÷ 5?", "5 × 4 = 20", "5 × 4 = 20", "5 + 4 = 9", "20 + 5 = 25"),
            item("g4d-m-03", "Groepjes", "18 knikkers in groepjes van 3. Hoeveel groepjes?", "6", "5", "6", "9"),
            item("g4d-m-04", "Controle", "Hoe controleer je 24 ÷ 6 = 4?", "6 × 4 = 24", "6 × 4 = 24", "24 − 4 = 20", "24 + 6 = 30"),
        ),
        lesson("g4-math-moneytime-depth-v1", "g4-math-moneytime-depth", "Tijd en geld in het dagelijks leven", "Rekenen & Wiskunde", 4, 10,
            item("g4d-t-01", "Geld", "Je koopt iets van €3 en betaalt €5. Hoeveel krijg je terug?", "€2", "€1", "€2", "€3"),
            item("g4d-t-02", "Kwartier", "Een kwartier is hoeveel minuten?", "15", "10", "15", "30"),
            item("g4d-t-03", "Half uur", "Een half uur is hoeveel minuten?", "30", "20", "30", "60"),
            item("g4d-t-04", "Duur", "De les begint 09:00 en eindigt 09:45. Hoe lang duurt de les?", "45 minuten", "30 minuten", "45 minuten", "60 minuten"),
        ),

        // Groep 5
        lesson("g5-nl-informative-depth-v1", "g5-nl-informative-depth", "Informatieve teksten doorgronden", "Nederlands", 5, 11,
            item("g5d-nl-01", "Tekstdoel", "Een tekst legt uit hoe een vulkaan werkt. Wat is vooral het doel?", "informeren", "informeren", "grapjes maken", "iets verkopen"),
            item("g5d-nl-02", "Tussenkop", "Waarvoor helpt een tussenkop?", "om snel te zien waar een deel over gaat", "om snel te zien waar een deel over gaat", "om de tekst langer te maken", "om cijfers te verbergen"),
            item("g5d-nl-03", "Signaalwoord", "Welk woord geeft vaak een oorzaak aan?", "omdat", "omdat", "maar", "daarna"),
            item("g5d-nl-04", "Bron", "Wat helpt om informatie te controleren?", "kijken wie de bron heeft gemaakt", "alleen de titel lezen", "kijken wie de bron heeft gemaakt", "de eerste reactie geloven"),
        ),
        lesson("g5-math-multdiv-depth-v1", "g5-math-multdiv-depth", "Vermenigvuldigen en delen verdiepen", "Rekenen & Wiskunde", 5, 11,
            item("g5d-m-01", "Keersom", "8 × 7 = ?", "56", "48", "56", "64"),
            item("g5d-m-02", "Delen", "56 ÷ 8 = ?", "7", "6", "7", "8"),
            item("g5d-m-03", "Splitsen", "Welke aanpak helpt bij 6 × 18?", "6 × 10 + 6 × 8", "6 × 10 + 6 × 8", "18 − 6", "18 + 6"),
            item("g5d-m-04", "Verhaal", "7 dozen met 12 potloden. Hoeveel potloden?", "84", "74", "84", "96"),
        ),
        lesson("g5-world-netherlands-depth-v1", "g5-world-netherlands-depth", "Nederland: landschap en water", "Wereldoriëntatie", 5, 11,
            item("g5d-w-01", "Provincies", "Hoeveel provincies heeft Nederland?", "12", "10", "12", "14"),
            item("g5d-w-02", "Water", "Waarom zijn dijken belangrijk?", "ze helpen land tegen hoog water beschermen", "ze helpen land tegen hoog water beschermen", "ze maken bergen hoger", "ze vervangen alle wegen"),
            item("g5d-w-03", "Polder", "Wat is een polder?", "land dat met waterbeheer droog wordt gehouden", "land dat met waterbeheer droog wordt gehouden", "een soort bos", "een hoge berg"),
            item("g5d-w-04", "Rivier", "Welke rivier stroomt door Nederland?", "Rijn", "Rijn", "Nijl", "Amazone"),
        ),

        // Groep 6
        lesson("g6-nl-textstructure-depth-v1", "g6-nl-textstructure-depth", "Tekststructuur en verbanden", "Nederlands", 6, 12,
            item("g6d-nl-01", "Oorzaak-gevolg", "Welk woord past vaak bij een gevolg?", "daardoor", "daardoor", "bijvoorbeeld", "eerst"),
            item("g6d-nl-02", "Tegenstelling", "Welk signaalwoord geeft een tegenstelling?", "maar", "omdat", "maar", "dus"),
            item("g6d-nl-03", "Volgorde", "Welk woord hoort bij een chronologische volgorde?", "daarna", "daarna", "hoewel", "daarom"),
            item("g6d-nl-04", "Alinea", "Wat is vaak het doel van de eerste zin van een alinea?", "het onderwerp van die alinea introduceren", "het onderwerp van die alinea introduceren", "altijd een grap maken", "alle bronnen opsommen"),
        ),
        lesson("g6-math-fractions-decimals-depth-v1", "g6-math-fractions-decimals-depth", "Breuken en kommagetallen verbinden", "Rekenen & Wiskunde", 6, 12,
            item("g6d-m-01", "Halve", "Welke kommagetal hoort bij 1/2?", "0,5", "0,25", "0,5", "0,75"),
            item("g6d-m-02", "Kwart", "Welke kommagetal hoort bij 1/4?", "0,25", "0,2", "0,25", "0,4"),
            item("g6d-m-03", "Vergelijk", "Welke is groter?", "0,7", "0,65", "0,7", "even groot"),
            item("g6d-m-04", "Optellen", "0,4 + 0,35 = ?", "0,75", "0,65", "0,75", "0,85"),
        ),
        lesson("g6-world-ecosystem-depth-v1", "g6-world-ecosystem-depth", "Ecosystemen en voedselketens", "Natuur & techniek", 6, 12,
            item("g6d-w-01", "Producent", "Welke is een producent in een voedselketen?", "gras", "vos", "gras", "muis"),
            item("g6d-w-02", "Voedselketen", "Welke volgorde klopt?", "gras → konijn → vos", "vos → gras → konijn", "gras → konijn → vos", "konijn → vos → gras"),
            item("g6d-w-03", "Habitat", "Wat betekent habitat?", "de leefomgeving van een organisme", "de leefomgeving van een organisme", "alleen het voedsel", "een soort temperatuurmeter"),
            item("g6d-w-04", "Evenwicht", "Wat kan gebeuren als één soort sterk afneemt?", "de voedselketen kan veranderen", "er verandert nooit iets", "de voedselketen kan veranderen", "alle planten verdwijnen altijd"),
        ),

        // Groep 7
        lesson("g7-nl-sourceargument-depth-v1", "g7-nl-sourceargument-depth", "Argumenten en bronnen beoordelen", "Nederlands", 7, 13,
            item("g7d-nl-01", "Argument", "Welk argument ondersteunt ‘meer groen op schoolpleinen’ het best?", "groen kan schaduw en natuur bieden", "groen kan schaduw en natuur bieden", "groen is een kleur", "schoenen zijn soms groen"),
            item("g7d-nl-02", "Feit of mening", "Welke zin is een feit?", "Water bevriest bij 0 °C onder normale omstandigheden.", "Ik vind winter mooi.", "Water bevriest bij 0 °C onder normale omstandigheden.", "Regen is vervelend."),
            item("g7d-nl-03", "Broncheck", "Wat controleer je bij een online bron?", "auteur, datum en bewijs", "alleen het logo", "auteur, datum en bewijs", "hoeveel emoji's er staan"),
            item("g7d-nl-04", "Tegenargument", "Wat is een tegenargument?", "een reden tegen een standpunt", "een reden tegen een standpunt", "de titel van een tekst", "een samenvatting zonder mening"),
        ),
        lesson("g7-math-proportion-depth-v1", "g7-math-proportion-depth", "Verhoudingen stap voor stap", "Rekenen & Wiskunde", 7, 13,
            item("g7d-m-01", "Verhouding", "3 broodjes kosten €6. Wat kosten 5 broodjes bij dezelfde prijs?", "€10", "€8", "€10", "€12"),
            item("g7d-m-02", "Schaal", "1 cm op de kaart is 4 km. 6 cm is?", "24 km", "10 km", "20 km", "24 km"),
            item("g7d-m-03", "Procent", "30% van 200 = ?", "60", "30", "60", "90"),
            item("g7d-m-04", "Factor", "Van 4 naar 20 is welke factor?", "×5", "×4", "×5", "×6"),
        ),
        lesson("g7-english-everyday-depth-v1", "g7-english-everyday-depth", "Everyday English: asking and responding", "Engels", 7, 13,
            item("g7d-en-01", "Polite request", "Which is the most polite request?", "Could you help me, please?", "Help me now.", "Could you help me, please?", "You help."),
            item("g7d-en-02", "Directions", "What does ‘turn left’ mean?", "ga linksaf", "ga rechtdoor", "ga linksaf", "ga rechtsaf"),
            item("g7d-en-03", "Past", "Choose the correct sentence.", "Yesterday I visited my friend.", "Yesterday I visit my friend.", "Yesterday I visited my friend.", "Yesterday visit friend."),
            item("g7d-en-04", "Response", "Someone says ‘Thank you’. What is a natural reply?", "You're welcome.", "Good night yesterday.", "You're welcome.", "Turn left."),
        ),

        // Groep 8
        lesson("g8-nl-media-depth-v1", "g8-nl-media-depth", "Kritisch lezen in media", "Nederlands", 8, 14,
            item("g8d-nl-01", "Clickbait", "Welke titel klinkt het meest als clickbait?", "Dit geloof je NOOIT!", "Gemeente publiceert jaarverslag", "Dit geloof je NOOIT!", "Openingstijden bibliotheek"),
            item("g8d-nl-02", "Onderbouwing", "Welke claim is het best controleerbaar?", "Een rapport vermeldt methode, cijfers en bron.", "Iedereen weet dit.", "Een rapport vermeldt methode, cijfers en bron.", "Mijn buurman zegt het."),
            item("g8d-nl-03", "Perspectief", "Waarom vergelijk je meerdere bronnen?", "om verschillende perspectieven en bewijs te vergelijken", "om verschillende perspectieven en bewijs te vergelijken", "om de langste tekst te kiezen", "om advertenties te tellen"),
            item("g8d-nl-04", "Conclusie", "Wat moet een betrouwbare conclusie doen?", "aansluiten bij het beschikbare bewijs", "aansluiten bij het beschikbare bewijs", "meer beweren dan de bron zegt", "alle twijfel verbergen"),
        ),
        lesson("g8-math-data-depth-v1", "g8-math-data-depth", "Data, grafieken en gemiddelde", "Rekenen & Wiskunde", 8, 14,
            item("g8d-m-01", "Gemiddelde", "Gemiddelde van 12, 14 en 16?", "14", "13", "14", "15"),
            item("g8d-m-02", "Mediaan", "Wat is de mediaan van 3, 7, 9?", "7", "3", "7", "9"),
            item("g8d-m-03", "Grafiek", "Een staaf stijgt van 20 naar 30. Hoeveel is de toename?", "10", "5", "10", "50"),
            item("g8d-m-04", "Percentage", "15 van 60 leerlingen kiezen fiets. Welk percentage is dat?", "25%", "15%", "25%", "40%"),
        ),
        lesson("g8-english-functional-depth-v1", "g8-english-functional-depth", "English for school and travel", "Engels", 8, 14,
            item("g8d-en-01", "Email", "Which opening fits a polite email?", "Dear Ms Brown,", "Hey you,", "Dear Ms Brown,", "Yo!"),
            item("g8d-en-02", "Travel", "What does ‘platform’ mean at a train station?", "perron", "kaartje", "perron", "bagage"),
            item("g8d-en-03", "Future", "Choose the correct sentence.", "I am going to visit London next week.", "I going visit London next week.", "I am going to visit London next week.", "I visited London next week."),
            item("g8d-en-04", "Information", "Which question asks for clarification?", "Could you explain that again, please?", "What colour is Tuesday?", "Could you explain that again, please?", "I am a station."),
        ),
    )

    private data class Item(
        val id: String,
        val title: String,
        val prompt: String,
        val correct: String,
        val options: List<String>,
    )

    private fun item(id: String, title: String, prompt: String, correct: String, vararg options: String) =
        Item(id, title, prompt, correct, options.toList())

    private fun lesson(
        id: String,
        skillId: String,
        title: String,
        subject: String,
        group: Int,
        minutes: Int,
        vararg items: Item,
    ): LessonDefinition {
        val steps = items.mapIndexed { index, item ->
            val options = item.options.distinct().mapIndexed { optionIndex, label ->
                LessonOption("${item.id}-o$optionIndex", label)
            }
            val correctId = options.first { it.label == item.correct }.id
            LessonStep(
                id = item.id,
                title = item.title,
                prompt = item.prompt,
                interaction = LessonInteractionType.MultipleChoice,
                conceptTag = skillId,
                options = options,
                correctOptionId = correctId,
                hint = "Lees of bekijk de vraag rustig en sluit antwoorden uit die niet passen.",
                explanation = "Het juiste antwoord is: ${item.correct}.",
            )
        }
        val first = steps.first()
        val remedialOptions = first.options.take(2).let { shortlist ->
            if (shortlist.any { it.id == first.correctOptionId }) shortlist else listOf(first.options.first { it.id == first.correctOptionId }, shortlist.first())
        }
        return LessonDefinition(
            id = id,
            skillId = skillId,
            title = title,
            subject = subject,
            group = group,
            estimatedMinutes = minutes,
            steps = steps,
            remedialSteps = mapOf(
                skillId to first.copy(
                    id = "$id-remedial",
                    title = "Nog één keer rustig",
                    options = remedialOptions,
                    hint = "Vergelijk de twee mogelijkheden stap voor stap.",
                    explanation = "We oefenen hetzelfde kernidee nog één keer.",
                ),
            ),
        )
    }
}
