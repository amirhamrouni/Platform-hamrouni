package nl.leersprong.app.feature.lesson

/**
 * Upper-primary language pack aligned to the 2026 SLO direction:
 * understanding texts, purposeful speaking/writing, source awareness and communicative English.
 */
object UpperPrimaryLanguageLessons {
    val lessons: List<LessonDefinition> = listOf(
        g5Dutch(), g5English(),
        g6Dutch(), g6English(),
        g7Dutch(), g7English(),
        g8Dutch(), g8English(),
    )

    private fun g5Dutch() = LessonDefinition(
        id = "g5-dutch-main-idea-v1", skillId = "g5-dutch-main-idea", title = "Hoofdgedachte vinden",
        subject = "Nederlands", group = 5, estimatedMinutes = 10,
        steps = listOf(
            LessonStep("g5-nl-01", "Waar gaat het vooral over?", "Een tekst vertelt dat bijen bloemen bezoeken, stuifmeel meenemen en daardoor planten helpen. Wat is de hoofdgedachte?", LessonInteractionType.MultipleChoice, "main-idea", listOf(LessonOption("a","Bijen kunnen vliegen"), LessonOption("b","Bijen helpen planten door bestuiving"), LessonOption("c","Bloemen hebben kleuren")), "b", hint = "Kies wat de meeste zinnen samen samenvat.", explanation = "De meeste informatie gaat over de rol van bijen bij bestuiving."),
            LessonStep("g5-nl-02", "Belangrijk detail", "Welk detail ondersteunt de hoofdgedachte het best?", LessonInteractionType.MultipleChoice, "supporting-detail", listOf(LessonOption("pollen","Bijen nemen stuifmeel mee"), LessonOption("yellow","Sommige bloemen zijn geel"), LessonOption("summer","In de zomer is het warm")), "pollen", hint = "Zoek een detail dat direct uitlegt hoe bijen planten helpen.", explanation = "Stuifmeel meenemen ondersteunt direct de uitleg over bestuiving."),
            LessonStep("g5-nl-03", "Samenvatten", "Zet de stappen van een korte samenvatting in logische volgorde.", LessonInteractionType.Ordering, "summary", options = listOf(LessonOption("read","Lees de hele tekst"), LessonOption("select","Kies de belangrijkste informatie"), LessonOption("write","Schrijf het kort in eigen woorden")), correctOrder = listOf("read","select","write"), hint = "Je kunt pas kiezen wat belangrijk is nadat je de tekst hebt gelezen.", explanation = "Lezen → selecteren → kort formuleren is een bruikbare samenvatstrategie."),
            LessonStep("g5-nl-04", "In eigen woorden", "Vul aan: Een goede samenvatting bevat vooral de ___ informatie.", LessonInteractionType.FillBlank, "summary", acceptedAnswers = listOf("belangrijkste", "belangrijke"), hint = "Niet elk detail hoeft mee.", explanation = "Een samenvatting bevat de belangrijkste informatie in korte vorm."),
        ),
        remedialSteps = mapOf("main-idea" to LessonStep("g5-nl-r1", "Kernzin", "Welke zin zegt het belangrijkste? 'Het regent. Noor pakt een jas. Ze blijft droog.'", LessonInteractionType.MultipleChoice, "main-idea", listOf(LessonOption("rain","Het regent"), LessonOption("dry","Noor zorgt dat ze droog blijft")), "dry", hint = "Welke zin verbindt de gebeurtenis en het resultaat?", explanation = "De kern is dat Noor zich tegen de regen beschermt.")),
    )

    private fun g5English() = LessonDefinition(
        id = "g5-english-everyday-v1", skillId = "g5-english-everyday", title = "Everyday English",
        subject = "Engels", group = 5, estimatedMinutes = 9,
        steps = listOf(
            LessonStep("g5-en-01", "Listen and choose", "Listen and choose the correct picture-word.", LessonInteractionType.ListenChoose, "listening", listOf(LessonOption("bike","bike"), LessonOption("book","book"), LessonOption("ball","ball")), "bike", speakText = "I ride my bike to school.", hint = "Listen for the object after 'my'.", explanation = "The sentence says: I ride my bike to school."),
            LessonStep("g5-en-02", "Daily phrase", "What is a good answer to: 'How are you?'", LessonInteractionType.MultipleChoice, "conversation", listOf(LessonOption("fine","I'm fine, thank you."), LessonOption("blue","Blue."), LessonOption("seven","Seven.")), "fine", hint = "Choose a natural reply in a short conversation.", explanation = "'I'm fine, thank you' is a common response."),
            LessonStep("g5-en-03", "Build a sentence", "Put the words in the correct order.", LessonInteractionType.Ordering, "sentence-order", options = listOf(LessonOption("i","I"), LessonOption("like","like"), LessonOption("music","music")), correctOrder = listOf("i","like","music"), hint = "Start with who is speaking.", explanation = "I like music."),
            LessonStep("g5-en-04", "Write one word", "Complete: I have a ___. The picture is a cat.", LessonInteractionType.FillBlank, "writing", acceptedAnswers = listOf("cat"), hint = "Use the English word for 'kat'.", explanation = "Cat is the English word for kat."),
        ),
        remedialSteps = mapOf("conversation" to LessonStep("g5-en-r1", "Short reply", "Someone says 'Hello!'. What can you say?", LessonInteractionType.MultipleChoice, "conversation", listOf(LessonOption("hello","Hello!"), LessonOption("banana","Banana")), "hello", hint = "Answer the greeting.", explanation = "Hello is a suitable greeting response.")),
    )

    private fun g6Dutch() = LessonDefinition(
        id = "g6-dutch-source-purpose-v1", skillId = "g6-dutch-source-purpose", title = "Tekstdoel & bron",
        subject = "Nederlands", group = 6, estimatedMinutes = 10,
        steps = listOf(
            LessonStep("g6-nl-01", "Tekstdoel", "Een poster zegt: 'Kom zaterdag naar de sportdag! Meld je nu aan.' Wat is het tekstdoel?", LessonInteractionType.MultipleChoice, "purpose", listOf(LessonOption("inform","alleen informeren"), LessonOption("persuade","overhalen om mee te doen"), LessonOption("story","een verhaal vertellen")), "persuade", hint = "Let op de oproep: meld je aan.", explanation = "De poster wil de lezer aansporen om mee te doen."),
            LessonStep("g6-nl-02", "Bron kiezen", "Je wilt weten hoeveel inwoners jouw gemeente heeft. Welke bron is het meest passend?", LessonInteractionType.MultipleChoice, "source-choice", listOf(LessonOption("official","een officiële gemeentelijke of CBS-bron"), LessonOption("comment","een losse reactie onder een video"), LessonOption("guess","een gok van een vriend")), "official", hint = "Kies een bron die de gegevens beheert of officieel publiceert.", explanation = "Voor feitelijke bevolkingsgegevens is een officiële bron betrouwbaarder."),
            LessonStep("g6-nl-03", "Bron checken", "Zet in volgorde hoe je een online bron controleert.", LessonInteractionType.Ordering, "source-check", options = listOf(LessonOption("author","Kijk wie de afzender is"), LessonOption("date","Controleer datum en actualiteit"), LessonOption("compare","Vergelijk belangrijke informatie met een andere bron")), correctOrder = listOf("author","date","compare"), hint = "Begin bij wie de informatie publiceert.", explanation = "Afzender, actualiteit en vergelijking helpen om een bron beter te beoordelen."),
            LessonStep("g6-nl-04", "Doelgericht schrijven", "Vul aan: Voor een instructie gebruik je duidelijke stappen en ___ woorden.", LessonInteractionType.FillBlank, "writing-purpose", acceptedAnswers = listOf("duidelijke", "precieze", "heldere"), hint = "De lezer moet precies weten wat hij moet doen.", explanation = "Duidelijke en precieze formuleringen passen bij een instructie."),
        ),
        remedialSteps = mapOf("purpose" to LessonStep("g6-nl-r1", "Waarom is deze tekst geschreven?", "'Vandaag wordt het 18 graden met kans op regen.' Wat is vooral het doel?", LessonInteractionType.MultipleChoice, "purpose", listOf(LessonOption("inform","informeren"), LessonOption("sell","iets verkopen")), "inform", hint = "De tekst geeft vooral informatie.", explanation = "Een weerbericht informeert.")),
    )

    private fun g6English() = LessonDefinition(
        id = "g6-english-listen-read-v1", skillId = "g6-english-listen-read", title = "Listen, read & respond",
        subject = "Engels", group = 6, estimatedMinutes = 10,
        steps = listOf(
            LessonStep("g6-en-01", "Listen for information", "Listen. Where is Sam going?", LessonInteractionType.ListenChoose, "listening-detail", listOf(LessonOption("park","the park"), LessonOption("shop","the supermarket"), LessonOption("school","school")), "park", speakText = "Sam is going to the park with his sister.", hint = "Listen after 'going to'.", explanation = "Sam is going to the park."),
            LessonStep("g6-en-02", "Read a short message", "Message: 'Meet me at the library at three o'clock.' Where should you go?", LessonInteractionType.MultipleChoice, "reading-detail", listOf(LessonOption("library","the library"), LessonOption("station","the station"), LessonOption("home","home")), "library", hint = "Look for the place in the message.", explanation = "The meeting place is the library."),
            LessonStep("g6-en-03", "Useful response", "Choose the best reply: 'Would you like some water?'", LessonInteractionType.MultipleChoice, "conversation", listOf(LessonOption("yes","Yes, please."), LessonOption("monday","Monday."), LessonOption("green","Green.")), "yes", hint = "This is an offer.", explanation = "'Yes, please' is an appropriate polite response."),
            LessonStep("g6-en-04", "Write a short sentence", "Complete: My favourite hobby is ___.", LessonInteractionType.FillBlank, "writing", acceptedAnswers = listOf("football", "reading", "gaming", "swimming", "music", "drawing"), hint = "Write one familiar hobby in English.", explanation = "A short familiar phrase is enough for this writing task."),
        ),
        remedialSteps = mapOf("listening-detail" to LessonStep("g6-en-r1", "Listen for one word", "Listen: What animal do you hear?", LessonInteractionType.ListenChoose, "listening-detail", listOf(LessonOption("dog","dog"), LessonOption("cat","cat")), "dog", speakText = "I have a small dog.", hint = "Listen to the final noun.", explanation = "You heard dog.")),
    )

    private fun g7Dutch() = LessonDefinition(
        id = "g7-dutch-deep-reading-v1", skillId = "g7-dutch-deep-reading", title = "Diep lezen & argumenten",
        subject = "Nederlands", group = 7, estimatedMinutes = 11,
        steps = listOf(
            LessonStep("g7-nl-01", "Standpunt herkennen", "'De schooldag moet later beginnen, omdat veel kinderen 's ochtends nog moe zijn.' Wat is het standpunt?", LessonInteractionType.MultipleChoice, "claim", listOf(LessonOption("later","De schooldag moet later beginnen"), LessonOption("tired","Kinderen zijn soms moe"), LessonOption("morning","Het is ochtend")), "later", hint = "Het standpunt is wat de schrijver vindt of wil.", explanation = "De schrijver pleit voor een latere start van de schooldag."),
            LessonStep("g7-nl-02", "Argument vinden", "Welk deel is het argument?", LessonInteractionType.MultipleChoice, "argument", listOf(LessonOption("reason","veel kinderen zijn 's ochtends nog moe"), LessonOption("claim","de schooldag moet later beginnen")), "reason", hint = "Een argument geeft een reden voor het standpunt.", explanation = "Moeheid in de ochtend wordt gebruikt als reden."),
            LessonStep("g7-nl-03", "Sterker onderbouwen", "Zet de aanpak in volgorde.", LessonInteractionType.Ordering, "argument-quality", options = listOf(LessonOption("claim","Formuleer je standpunt"), LessonOption("reason","Geef een duidelijke reden"), LessonOption("evidence","Voeg een passend voorbeeld of betrouwbare bron toe")), correctOrder = listOf("claim","reason","evidence"), hint = "Je moet eerst weten wat je wilt beweren.", explanation = "Standpunt → argument → ondersteuning maakt een redenering duidelijker."),
            LessonStep("g7-nl-04", "Signaalwoord", "Vul aan: Het woord 'omdat' kondigt vaak een ___ aan.", LessonInteractionType.FillBlank, "argument", acceptedAnswers = listOf("reden", "argument", "oorzaak"), hint = "Wat komt meestal na 'omdat'?", explanation = "'Omdat' leidt vaak een reden of argument in."),
        ),
        remedialSteps = mapOf("claim" to LessonStep("g7-nl-r1", "Wat vindt de schrijver?", "'Ik vind dat ieder kind dagelijks moet lezen.' Wat is het standpunt?", LessonInteractionType.MultipleChoice, "claim", listOf(LessonOption("read","Ieder kind moet dagelijks lezen"), LessonOption("child","Er zijn kinderen")), "read", hint = "Zoek wat de schrijver vindt.", explanation = "Het standpunt is dat ieder kind dagelijks moet lezen.")),
    )

    private fun g7English() = LessonDefinition(
        id = "g7-english-communicate-v1", skillId = "g7-english-communicate", title = "Communicate with confidence",
        subject = "Engels", group = 7, estimatedMinutes = 11,
        steps = listOf(
            LessonStep("g7-en-01", "Understand a message", "Read: 'The football match starts at half past four, but players must arrive at four.' When should players arrive?", LessonInteractionType.MultipleChoice, "reading-detail", listOf(LessonOption("four","4:00"), LessonOption("half","4:30"), LessonOption("five","5:00")), "four", hint = "The question asks about arrival, not the start of the match.", explanation = "Players must arrive at four o'clock."),
            LessonStep("g7-en-02", "Join a conversation", "Choose the best response: 'What do you think about the new playground?'", LessonInteractionType.MultipleChoice, "speaking", listOf(LessonOption("opinion","I think it's great because there is more space."), LessonOption("number","Twenty-three."), LessonOption("weather","It is raining.")), "opinion", hint = "The question asks for your opinion.", explanation = "A simple opinion plus a reason fits the conversation."),
            LessonStep("g7-en-03", "Order a message", "Put the message in a natural order.", LessonInteractionType.Ordering, "writing", options = listOf(LessonOption("hello","Hi Alex,"), LessonOption("body","Would you like to come to my birthday party on Saturday?"), LessonOption("close","See you, Sam")), correctOrder = listOf("hello","body","close"), hint = "Greeting first, closing last.", explanation = "A simple message has a greeting, message body and closing."),
            LessonStep("g7-en-04", "Write a reason", "Complete: I like learning English because ___.", LessonInteractionType.FillBlank, "writing", acceptedAnswers = listOf("it is useful", "it's useful", "I can communicate", "I can talk to people", "I like languages"), hint = "Write one simple reason.", explanation = "A familiar reason expressed in a simple sentence meets the communicative goal."),
        ),
        remedialSteps = mapOf("speaking" to LessonStep("g7-en-r1", "Give an opinion", "Choose an opinion sentence.", LessonInteractionType.MultipleChoice, "speaking", listOf(LessonOption("think","I think this game is fun."), LessonOption("clock","It is five o'clock.")), "think", hint = "Look for 'I think'.", explanation = "'I think...' is a common way to express an opinion.")),
    )

    private fun g8Dutch() = LessonDefinition(
        id = "g8-dutch-sources-writing-v1", skillId = "g8-dutch-sources-writing", title = "Bronnen combineren & schrijven",
        subject = "Nederlands", group = 8, estimatedMinutes = 12,
        steps = listOf(
            LessonStep("g8-nl-01", "Twee bronnen", "Bron A zegt dat een park 20 hectare groot is. Bron B zegt 12 hectare. Wat is een verstandige volgende stap?", LessonInteractionType.MultipleChoice, "source-compare", listOf(LessonOption("check","Controleer welke bron actueel en gezaghebbend is"), LessonOption("choose","Kies zomaar het grootste getal"), LessonOption("ignore","Negeer beide bronnen")), "check", hint = "Verschillende cijfers vragen om broncontrole.", explanation = "Bij tegenstrijdige informatie kijk je naar afzender, datum en onderbouwing."),
            LessonStep("g8-nl-02", "Informatie combineren", "Wat hoort in een goede tekst met meerdere bronnen?", LessonInteractionType.MultipleChoice, "synthesis", listOf(LessonOption("combine","Informatie vergelijken en in eigen woorden samenbrengen"), LessonOption("copy","Lange stukken letterlijk kopiëren"), LessonOption("random","Losse feiten zonder verband opsommen")), "combine", hint = "Je tekst moet zelf samenhang maken.", explanation = "Synthetiseren betekent relevante informatie uit meerdere bronnen verbinden."),
            LessonStep("g8-nl-03", "Schrijfplan", "Zet de stappen voor een informatieve tekst in volgorde.", LessonInteractionType.Ordering, "writing-process", options = listOf(LessonOption("goal","Bepaal doel en lezer"), LessonOption("select","Selecteer relevante informatie"), LessonOption("draft","Schrijf en orden de tekst"), LessonOption("revise","Controleer en verbeter")), correctOrder = listOf("goal","select","draft","revise"), hint = "Begin voordat je schrijft met doel en publiek.", explanation = "Doel → informatie → schrijven → reviseren ondersteunt doelgericht schrijven."),
            LessonStep("g8-nl-04", "Eigen woorden", "Vul aan: Als je informatie uit een bron gebruikt, formuleer je die waar mogelijk in je ___ woorden.", LessonInteractionType.FillBlank, "source-use", acceptedAnswers = listOf("eigen"), hint = "Niet letterlijk overnemen.", explanation = "In eigen woorden formuleren laat zien dat je de informatie begrijpt en verwerkt."),
        ),
        remedialSteps = mapOf("source-compare" to LessonStep("g8-nl-r1", "Bron kiezen", "Welke bron is logischer voor actuele openingstijden van een museum?", LessonInteractionType.MultipleChoice, "source-compare", listOf(LessonOption("museum","de officiële museumwebsite"), LessonOption("old","een oude folder zonder datum")), "museum", hint = "Kies de meest directe en actuele bron.", explanation = "De officiële website is doorgaans de meest passende eerste bron voor actuele openingstijden.")),
    )

    private fun g8English() = LessonDefinition(
        id = "g8-english-information-v1", skillId = "g8-english-information", title = "English for real information",
        subject = "Engels", group = 8, estimatedMinutes = 12,
        steps = listOf(
            LessonStep("g8-en-01", "Listen for key information", "Listen. Why is the speaker calling?", LessonInteractionType.ListenChoose, "listening-purpose", listOf(LessonOption("change","to change an appointment"), LessonOption("food","to order food"), LessonOption("game","to talk about a game")), "change", speakText = "Hello, I'm calling because I need to change my appointment from Tuesday to Thursday.", hint = "Listen for the reason after 'because'.", explanation = "The speaker wants to change an appointment."),
            LessonStep("g8-en-02", "Read practical information", "Notice: 'The museum is closed on Mondays. On Tuesday it opens at 10:00.' When can you visit?", LessonInteractionType.MultipleChoice, "reading-info", listOf(LessonOption("mon","Monday 10:00"), LessonOption("tue","Tuesday 10:00"), LessonOption("mon2","Monday afternoon")), "tue", hint = "Monday is closed.", explanation = "Tuesday at 10:00 is possible."),
            LessonStep("g8-en-03", "Write a short request", "Put the request in a clear order.", LessonInteractionType.Ordering, "writing-purpose", options = listOf(LessonOption("greet","Hello,"), LessonOption("request","Could you please send me the information?"), LessonOption("thanks","Thank you.")), correctOrder = listOf("greet","request","thanks"), hint = "Greeting first, polite close last.", explanation = "A simple polite request can be short and clearly structured."),
            LessonStep("g8-en-04", "Culture & context", "Which sentence is most appropriate when asking a stranger for help?", LessonInteractionType.MultipleChoice, "politeness", listOf(LessonOption("polite","Excuse me, could you help me, please?"), LessonOption("command","Help me now."), LessonOption("silent","...")), "polite", hint = "Use a polite opener and request.", explanation = "'Excuse me' and 'could you... please?' fit a polite request to someone you do not know."),
        ),
        remedialSteps = mapOf("politeness" to LessonStep("g8-en-r1", "Polite request", "Choose the polite request.", LessonInteractionType.MultipleChoice, "politeness", listOf(LessonOption("please","Could I have some water, please?"), LessonOption("give","Give me water.")), "please", hint = "Look for a polite question form.", explanation = "'Could I... please?' is a polite request form.")),
    )
}
