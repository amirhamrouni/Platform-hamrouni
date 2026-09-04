package nl.leersprong.app.feature.lesson

/**
 * Cross-curricular school-year expansion for Groep 1–8.
 * Content is original LeerSprong material and stays intentionally short, concrete and
 * age-appropriate so it can be scheduled alongside the core Nederlands/Rekenen path.
 */
object SchoolYearBroadLessons {
    val lessons: List<LessonDefinition> = listOf(
        g1World(), g1Art(), g2World(), g2Citizenship(), g3World(), g3Digital(), g4World(), g4English(),
        g5World(), g5English(), g6World(), g6Citizenship(), g7World(), g7Digital(), g8World(), g8English(),
    )

    private fun mc(id: String, title: String, prompt: String, concept: String, correct: String, vararg answers: Pair<String, String>, hint: String, explanation: String) =
        LessonStep(id, title, prompt, LessonInteractionType.MultipleChoice, concept, answers.map { LessonOption(it.first, it.second) }, correct, hint = hint, explanation = explanation)

    private fun fill(id: String, title: String, prompt: String, concept: String, answer: String, hint: String, explanation: String) =
        LessonStep(id, title, prompt, LessonInteractionType.FillBlank, concept, acceptedAnswers = listOf(answer), hint = hint, explanation = explanation)

    private fun lesson(id: String, skill: String, title: String, subject: String, group: Int, minutes: Int, steps: List<LessonStep>): LessonDefinition =
        LessonDefinition(
            id = id,
            skillId = skill,
            title = title,
            subject = subject,
            group = group,
            estimatedMinutes = minutes,
            steps = steps,
            remedialSteps = steps.associate { it.conceptTag to it.copy(id = "${it.id}-r", title = "Samen nog eens") },
        )

    private fun g1World() = lesson("g1-world-seasons-broad-v1", "g1-world-seasons-broad", "Seizoenen ontdekken", "Wereldoriëntatie", 1, 7, listOf(
        mc("g1-w-01", "Lente", "Wat zie je vaak in de lente?", "season", "flowers", "flowers" to "bloemen", "snow" to "sneeuwpop", "leaves" to "vallende bladeren", hint = "Denk aan nieuwe planten.", explanation = "In de lente gaan veel bloemen groeien."),
        mc("g1-w-02", "Winter", "Wat trek je aan als het koud is?", "weather", "coat", "coat" to "een warme jas", "swim" to "een zwembroek", hint = "Je wilt warm blijven.", explanation = "Een warme jas helpt tegen de kou."),
        mc("g1-w-03", "Dag en nacht", "Wanneer zie je meestal de maan het duidelijkst?", "day-night", "night", "day" to "overdag", "night" to "'s nachts", hint = "Denk aan een donkere lucht.", explanation = "De maan zie je vaak het duidelijkst in de nacht."),
    ))

    private fun g1Art() = lesson("g1-art-colors-broad-v1", "g1-art-colors-broad", "Kleuren en vormen", "Kunst & Cultuur", 1, 7, listOf(
        mc("g1-a-01", "Kleur", "Welke kleur krijg je vaak als je rood en geel mengt?", "color", "orange", "orange" to "oranje", "green" to "groen", "blue" to "blauw", hint = "Denk aan een sinaasappel.", explanation = "Rood en geel maken samen oranje."),
        mc("g1-a-02", "Vorm", "Welke vorm heeft drie hoeken?", "shape", "triangle", "triangle" to "driehoek", "circle" to "cirkel", "square" to "vierkant", hint = "Tel de hoeken.", explanation = "Een driehoek heeft drie hoeken."),
        mc("g1-a-03", "Kijken", "Wat kun je gebruiken om iets extra te laten opvallen in een tekening?", "contrast", "big", "big" to "het groter tekenen", "hide" to "het verstoppen", hint = "Maak het belangrijkste duidelijker.", explanation = "Grootte en contrast kunnen de aandacht sturen."),
    ))

    private fun g2World() = lesson("g2-world-plants-animals-broad-v1", "g2-world-plants-animals-broad", "Planten en dieren", "Wereldoriëntatie", 2, 8, listOf(
        mc("g2-w-01", "Plant", "Wat heeft een plant nodig om te groeien?", "plant", "water", "water" to "water en licht", "plastic" to "plastic", hint = "Denk aan een plant op de vensterbank.", explanation = "Planten hebben water en licht nodig."),
        mc("g2-w-02", "Dier", "Welk dier legt eieren?", "animal", "duck", "duck" to "eend", "cat" to "kat", hint = "Denk aan vogels.", explanation = "Een eend legt eieren."),
        mc("g2-w-03", "Leefplek", "Waar leeft een vis?", "habitat", "water", "water" to "in water", "tree" to "in een boom", hint = "Kijk naar hoe een vis ademt en zwemt.", explanation = "Een vis leeft in water."),
    ))

    private fun g2Citizenship() = lesson("g2-citizenship-together-broad-v1", "g2-citizenship-together-broad", "Samen in de klas", "Burgerschap", 2, 8, listOf(
        mc("g2-c-01", "Luisteren", "Wat doe je als een klasgenoot vertelt?", "respect", "listen", "listen" to "luisteren", "interrupt" to "steeds onderbreken", hint = "Geef de ander ruimte.", explanation = "Luisteren laat respect zien."),
        mc("g2-c-02", "Delen", "Er is één schaar en twee kinderen hebben hem nodig. Wat is eerlijk?", "fairness", "take-turns", "take-turns" to "om de beurt", "keep" to "één kind houdt hem de hele tijd", hint = "Iedereen moet een kans krijgen.", explanation = "Om de beurt gebruiken is eerlijk."),
        mc("g2-c-03", "Helpen", "Een kind valt op het plein. Wat kun je doen?", "help", "help", "help" to "helpen en een volwassene roepen", "laugh" to "uitlachen", hint = "Denk aan wat jij fijn zou vinden.", explanation = "Helpen en hulp halen is passend."),
    ))

    private fun g3World() = lesson("g3-world-water-weather-broad-v1", "g3-world-water-weather-broad", "Water en weer", "Wereldoriëntatie", 3, 9, listOf(
        mc("g3-w-01", "Waterkringloop", "Wat gebeurt er met een plas op een warme dag?", "water-cycle", "evaporate", "evaporate" to "het water verdampt langzaam", "grow" to "de plas groeit vanzelf", hint = "Warmte kan water laten verdwijnen in de lucht.", explanation = "Water kan door warmte verdampen."),
        mc("g3-w-02", "Wolk", "Waaruit bestaat een wolk vooral?", "weather", "droplets", "droplets" to "heel kleine waterdruppels", "cotton" to "katoen", hint = "Wolken horen bij de waterkringloop.", explanation = "Wolken bestaan uit heel kleine waterdruppels of ijskristallen."),
        mc("g3-w-03", "Meten", "Waarmee meet je temperatuur?", "measure", "thermometer", "thermometer" to "thermometer", "ruler" to "liniaal", hint = "Je gebruikt dit ook bij koorts.", explanation = "Temperatuur meet je met een thermometer."),
    ))

    private fun g3Digital() = lesson("g3-digital-safe-broad-v1", "g3-digital-safe-broad", "Veilig digitaal beginnen", "Digitale geletterdheid", 3, 9, listOf(
        mc("g3-d-01", "Wachtwoord", "Wat is slimmer voor een wachtwoord?", "password", "secret", "secret" to "een geheim wachtwoord", "share" to "het aan iedereen vertellen", hint = "Een wachtwoord beschermt je account.", explanation = "Je houdt een wachtwoord geheim."),
        mc("g3-d-02", "Foto", "Mag je zomaar een foto van een klasgenoot online zetten?", "privacy", "ask", "ask" to "eerst toestemming vragen", "yes" to "altijd zonder vragen", hint = "De foto is ook van de ander.", explanation = "Vraag eerst toestemming."),
        mc("g3-d-03", "Vreemd bericht", "Een onbekende vraagt online om je adres. Wat doe je?", "safety", "adult", "adult" to "niet geven en een vertrouwde volwassene vertellen", "send" to "meteen sturen", hint = "Persoonlijke gegevens zijn privé.", explanation = "Geef je adres niet aan onbekenden."),
    ))

    private fun g4World() = lesson("g4-world-netherlands-broad-v1", "g4-world-netherlands-broad", "Nederland: water en landschap", "Wereldoriëntatie", 4, 10, listOf(
        mc("g4-w-01", "Dijk", "Waarvoor dient een dijk vooral?", "water", "protect", "protect" to "land beschermen tegen water", "traffic" to "alleen auto's laten rijden", hint = "Nederland heeft veel gebieden dicht bij zee en rivieren.", explanation = "Dijken helpen land tegen hoog water te beschermen."),
        mc("g4-w-02", "Polder", "Wat is een polder?", "landscape", "low-land", "low-land" to "land waar waterpeil wordt geregeld", "mountain" to "een hoge berg", hint = "Denk aan waterbeheer.", explanation = "In een polder wordt het waterpeil geregeld."),
        mc("g4-w-03", "Kaart", "Wat laat een legenda op een kaart zien?", "map", "symbols", "symbols" to "wat symbolen en kleuren betekenen", "weather" to "alleen het weer", hint = "Kijk naar de uitleg bij de kaart.", explanation = "De legenda verklaart kaartsymbolen en kleuren."),
    ))

    private fun g4English() = lesson("g4-english-everyday-broad-v1", "g4-english-everyday-broad", "Everyday English", "Engels", 4, 9, listOf(
        mc("g4-e-01", "Greeting", "What do you say in the morning?", "greeting", "good-morning", "good-morning" to "Good morning", "good-night" to "Good night", hint = "Morning means ochtend.", explanation = "In the morning you can say ‘Good morning’."),
        mc("g4-e-02", "Classroom", "Which word means ‘boek’?", "vocabulary", "book", "book" to "book", "chair" to "chair", "door" to "door", hint = "You read it.", explanation = "‘Book’ means ‘boek’."),
        fill("g4-e-03", "Simple sentence", "Complete: I ___ happy.", "sentence", "am", hint = "With I, use am.", explanation = "The sentence is ‘I am happy.’"),
    ))

    private fun g5World() = lesson("g5-world-history-time-broad-v1", "g5-world-history-time-broad", "Tijdvakken en bronnen", "Wereldoriëntatie", 5, 11, listOf(
        mc("g5-w-01", "Bron", "Wat is een historische bron?", "history-source", "evidence", "evidence" to "iets uit of over het verleden dat informatie geeft", "future" to "een voorspelling over morgen", hint = "Historici gebruiken bronnen om iets over vroeger te weten.", explanation = "Een historische bron geeft informatie over het verleden."),
        mc("g5-w-02", "Tijdlijn", "Wat staat op een tijdlijn meestal van links naar rechts?", "timeline", "old-new", "old-new" to "van vroeger naar later", "random" to "zonder volgorde", hint = "Tijdlijnen ordenen gebeurtenissen.", explanation = "Een tijdlijn zet gebeurtenissen chronologisch."),
        mc("g5-w-03", "Vergelijken", "Waarom vergelijk je meerdere bronnen?", "source", "check", "check" to "om informatie beter te controleren", "faster" to "zodat je minder hoeft te lezen", hint = "Bronnen kunnen verschillen.", explanation = "Door bronnen te vergelijken kun je informatie beter beoordelen."),
    ))

    private fun g5English() = lesson("g5-english-listen-read-broad-v1", "g5-english-listen-read-broad", "English: listen and read", "Engels", 5, 11, listOf(
        mc("g5-e-01", "Meaning", "What does ‘I like football’ mean?", "reading", "like", "like" to "Ik vind voetbal leuk", "hate" to "Ik haat voetbal", hint = "Like betekent leuk vinden.", explanation = "‘I like football’ betekent dat je voetbal leuk vindt."),
        mc("g5-e-02", "Question", "Which is a correct question?", "sentence", "where", "where" to "Where do you live?", "wrong" to "Where you live?", hint = "Use do in this simple question.", explanation = "‘Where do you live?’ is correct."),
        fill("g5-e-03", "Plural", "One book, two ___.", "grammar", "books", hint = "Most plurals add -s.", explanation = "The plural is ‘books’."),
    ))

    private fun g6World() = lesson("g6-world-geography-europe-broad-v1", "g6-world-geography-europe-broad", "Europa en kaarten", "Wereldoriëntatie", 6, 12, listOf(
        mc("g6-w-01", "Europa", "Nederland ligt in...", "geography", "europe", "europe" to "Europa", "asia" to "Azië", hint = "Denk aan de Europese Unie en buurlanden.", explanation = "Nederland ligt in Europa."),
        mc("g6-w-02", "Windrichting", "Duitsland ligt vanuit Nederland vooral in welke richting?", "map", "east", "east" to "oost", "west" to "west", hint = "Kijk naar een kaart van Nederland en Duitsland.", explanation = "Duitsland ligt ten oosten van Nederland."),
        mc("g6-w-03", "Schaal", "Waarom staat een schaal op een kaart?", "map", "distance", "distance" to "om afstanden te kunnen omrekenen", "color" to "alleen om kleuren te kiezen", hint = "Een kaart is kleiner dan de werkelijkheid.", explanation = "De schaal verbindt kaartafstand met echte afstand."),
    ))

    private fun g6Citizenship() = lesson("g6-citizenship-democracy-broad-v1", "g6-citizenship-democracy-broad", "Democratie en afspraken", "Burgerschap", 6, 12, listOf(
        mc("g6-c-01", "Democratie", "Wat past bij democratische besluitvorming?", "democracy", "voice", "voice" to "mensen kunnen meepraten en stemmen", "one" to "één persoon beslist altijd alles", hint = "Denk aan inspraak.", explanation = "In een democratie hebben mensen manieren om invloed uit te oefenen."),
        mc("g6-c-02", "Regels", "Waarom hebben groepen regels?", "rules", "together", "together" to "om afspraken en veiligheid duidelijk te maken", "punish" to "alleen om te straffen", hint = "Regels helpen samenleven organiseren.", explanation = "Regels maken verwachtingen en grenzen duidelijk."),
        mc("g6-c-03", "Meningsverschil", "Wat helpt bij een meningsverschil?", "dialogue", "listen", "listen" to "luisteren en rustig uitleggen", "shout" to "harder schreeuwen", hint = "Je wilt elkaar begrijpen.", explanation = "Luisteren en uitleggen helpen een conflict oplossen."),
    ))

    private fun g7World() = lesson("g7-world-climate-sustainability-broad-v1", "g7-world-climate-sustainability-broad", "Klimaat en duurzaamheid", "Wereldoriëntatie", 7, 13, listOf(
        mc("g7-w-01", "Klimaat", "Wat is het verschil tussen weer en klimaat?", "climate", "long-term", "long-term" to "klimaat gaat over patronen over lange tijd", "same" to "er is geen verschil", hint = "Weer kan per dag veranderen.", explanation = "Klimaat beschrijft weerpatronen over een lange periode."),
        mc("g7-w-02", "Energie", "Welke bron is hernieuwbaar?", "energy", "sun", "sun" to "zon", "coal" to "steenkool", hint = "De bron raakt niet snel op.", explanation = "Zonne-energie is hernieuwbaar."),
        mc("g7-w-03", "Keuze", "Welke keuze kan energie besparen?", "sustainability", "lights", "lights" to "lampen uitdoen als je weggaat", "all-on" to "alles aan laten staan", hint = "Gebruik energie alleen als nodig.", explanation = "Onnodige lampen uitdoen bespaart elektriciteit."),
    ))

    private fun g7Digital() = lesson("g7-digital-media-information-broad-v1", "g7-digital-media-information-broad", "Media en informatie beoordelen", "Digitale geletterdheid", 7, 13, listOf(
        mc("g7-d-01", "Broncheck", "Wat controleer je bij online informatie?", "source", "author-date", "author-date" to "auteur, datum en bron", "font" to "alleen het lettertype", hint = "Je wilt weten waar informatie vandaan komt.", explanation = "Auteur, datum en bron helpen betrouwbaarheid beoordelen."),
        mc("g7-d-02", "Advertentie", "Een influencer krijgt betaald om een product te tonen. Wat is belangrijk?", "media", "commercial", "commercial" to "herkennen dat het reclame kan zijn", "neutral" to "aannemen dat het altijd neutraal advies is", hint = "Geld kan invloed hebben op de boodschap.", explanation = "Betaalde promotie is commerciële communicatie."),
        mc("g7-d-03", "Algoritme", "Wat kan een aanbevelingsalgoritme doen?", "algorithm", "select", "select" to "bepalen welke inhoud je vaker ziet", "truth" to "garanderen dat alles waar is", hint = "Platforms kiezen wat bovenaan komt.", explanation = "Algoritmen kunnen bepalen welke inhoud wordt aanbevolen."),
    ))

    private fun g8World() = lesson("g8-world-society-economy-broad-v1", "g8-world-society-economy-broad", "Samenleving en economie", "Wereldoriëntatie", 8, 14, listOf(
        mc("g8-w-01", "Belasting", "Waarvoor gebruikt een overheid belastinggeld onder andere?", "society", "public", "public" to "publieke voorzieningen", "private" to "alleen privéaankopen", hint = "Denk aan wegen, onderwijs en hulpdiensten.", explanation = "Belastinggeld financiert onder andere publieke voorzieningen."),
        mc("g8-w-02", "Schaarste", "Wat betekent schaarste?", "economy", "limited", "limited" to "er is niet onbeperkt van alles beschikbaar", "infinite" to "alles is onbeperkt gratis", hint = "Mensen moeten keuzes maken.", explanation = "Schaarste betekent dat middelen beperkt zijn."),
        mc("g8-w-03", "Globalisering", "Wat is een voorbeeld van internationale verbondenheid?", "global", "trade", "trade" to "producten worden tussen landen verhandeld", "none" to "landen hebben nooit contact", hint = "Denk aan handel en communicatie.", explanation = "Internationale handel verbindt landen economisch."),
    ))

    private fun g8English() = lesson("g8-english-functional-broad-v1", "g8-english-functional-broad", "Functional English", "Engels", 8, 14, listOf(
        mc("g8-e-01", "Polite request", "Which request is polite?", "speaking", "could", "could" to "Could you help me, please?", "give" to "Give me that now.", hint = "Look for polite language.", explanation = "‘Could you... please?’ is polite."),
        mc("g8-e-02", "Main idea", "Text: ‘Cycling is cheap, healthy and reduces traffic.’ What is the main idea?", "reading", "benefits", "benefits" to "Cycling has several benefits", "cars" to "Cars are always bad", hint = "Choose the idea that covers the whole sentence.", explanation = "The sentence lists several benefits of cycling."),
        fill("g8-e-03", "Past tense", "Yesterday we ___ to the museum. (walk)", "grammar", "walked", hint = "Regular past tense often ends in -ed.", explanation = "The correct form is ‘walked’."),
    ))
}
