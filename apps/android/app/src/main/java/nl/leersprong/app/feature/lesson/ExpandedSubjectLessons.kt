package nl.leersprong.app.feature.lesson

object ExpandedSubjectLessons {
    val lessons = listOf(english4(), world4(), citizenship4(), digital4())

    private fun english4() = LessonDefinition(
        "g4-english-everyday-v1", "g4-english-everyday", "Everyday English", "Engels", 4, 9,
        listOf(
            LessonStep("en4-1","Greetings","What do you say in the morning?",LessonInteractionType.MultipleChoice,"greeting",listOf(LessonOption("morning","Good morning"),LessonOption("night","Good night"),LessonOption("bye","Goodbye")),"morning",hint="Morning = ochtend.",explanation="In the morning you say: Good morning."),
            LessonStep("en4-2","Listen","Listen and choose the word.",LessonInteractionType.ListenChoose,"listening",listOf(LessonOption("book","book"),LessonOption("bag","bag"),LessonOption("bike","bike")),"book",speakText="book",hint="Luister naar de b- en k-klank.",explanation="You heard: book."),
            LessonStep("en4-3","Meaning","What does ‘happy’ mean?",LessonInteractionType.MultipleChoice,"vocabulary",listOf(LessonOption("blij","blij"),LessonOption("boos","boos"),LessonOption("moe","moe")),"blij",hint="Think of a smile.",explanation="Happy betekent blij."),
            LessonStep("en4-4","Sentence","I ___ eight years old.",LessonInteractionType.FillBlank,"sentence",acceptedAnswers=listOf("am"),hint="Bij I hoort am.",explanation="I am eight years old."),
            LessonStep("en4-5","Order","Put the sentence in the right order.",LessonInteractionType.Ordering,"sentence",options=listOf(LessonOption("i","I"),LessonOption("like","like"),LessonOption("school","school")),correctOrder=listOf("i","like","school"),hint="Start with I.",explanation="I like school."),
        ), emptyMap()
    )

    private fun world4() = LessonDefinition(
        "g4-world-water-nature-v1", "g4-world-water-nature", "Water & natuur", "Wereldoriëntatie", 4, 10,
        listOf(
            LessonStep("wo4-1","Waterkringloop","Wat gebeurt er met water als de zon het verwarmt?",LessonInteractionType.MultipleChoice,"water-cycle",listOf(LessonOption("verdampen","Het verdampt"),LessonOption("bevriezen","Het bevriest"),LessonOption("verdwijnt","Het verdwijnt voor altijd")),"verdampen",hint="Warm water kan waterdamp worden.",explanation="Door warmte verdampt water en komt het als waterdamp in de lucht."),
            LessonStep("wo4-2","Planten","Wat heeft een plant nodig om goed te groeien?",LessonInteractionType.MultipleChoice,"plants",listOf(LessonOption("need","Water, licht en voedingsstoffen"),LessonOption("dark","Alleen donker"),LessonOption("plastic","Plastic")),"need",hint="Denk aan wat je een plant thuis geeft.",explanation="Planten hebben onder andere water en licht nodig."),
            LessonStep("wo4-3","Nederland","Welke zee ligt aan de westkant van Nederland?",LessonInteractionType.MultipleChoice,"geography",listOf(LessonOption("north","Noordzee"),LessonOption("med","Middellandse Zee"),LessonOption("black","Zwarte Zee")),"north",hint="De naam begint met Noord.",explanation="Nederland grenst in het westen en noorden aan de Noordzee."),
            LessonStep("wo4-4","Seizoenen","Zet de seizoenen vanaf de lente in volgorde.",LessonInteractionType.Ordering,"seasons",options=listOf(LessonOption("spring","lente"),LessonOption("summer","zomer"),LessonOption("autumn","herfst"),LessonOption("winter","winter")),correctOrder=listOf("spring","summer","autumn","winter"),hint="Na de lente komt de zomer.",explanation="Lente → zomer → herfst → winter."),
            LessonStep("wo4-5","Dieren","Een dier dat planten én dieren eet noemen we een...",LessonInteractionType.MultipleChoice,"food",listOf(LessonOption("omnivoor","omnivoor"),LessonOption("herbivoor","herbivoor"),LessonOption("plant","plant")),"omnivoor",hint="Omni betekent alles.",explanation="Een omnivoor eet zowel plantaardig als dierlijk voedsel."),
        ), emptyMap()
    )

    private fun citizenship4() = LessonDefinition(
        "g4-citizenship-together-v1", "g4-citizenship-together", "Samen in de klas", "Burgerschap", 4, 8,
        listOf(
            LessonStep("bu4-1","Luisteren","Wat helpt bij een meningsverschil?",LessonInteractionType.MultipleChoice,"dialogue",listOf(LessonOption("listen","Naar elkaar luisteren"),LessonOption("shout","Harder schreeuwen"),LessonOption("ignore","Iedereen negeren")),"listen",hint="Een gesprek werkt als beide kanten gehoord worden.",explanation="Luisteren helpt om elkaar te begrijpen."),
            LessonStep("bu4-2","Regels","Waarom hebben we klasregels?",LessonInteractionType.MultipleChoice,"rules",listOf(LessonOption("safe","Om samen veilig en prettig te leren"),LessonOption("punish","Alleen om straf te geven")),"safe",hint="Denk aan samenwerken.",explanation="Duidelijke regels helpen een groep veilig en voorspelbaar te maken."),
            LessonStep("bu4-3","Keuze","Je ziet iemand alleen staan op het plein. Wat is een behulpzame keuze?",LessonInteractionType.MultipleChoice,"empathy",listOf(LessonOption("invite","Vragen of diegene mee wil doen"),LessonOption("laugh","Uitlachen"),LessonOption("walk","Doen alsof je niets ziet")),"invite",hint="Denk aan hoe jij behandeld wilt worden.",explanation="Iemand uitnodigen kan helpen om erbij te horen."),
            LessonStep("bu4-4","Volgorde","Zet een rustige oplossing in volgorde.",LessonInteractionType.Ordering,"conflict",options=listOf(LessonOption("stop","Stop en word rustig"),LessonOption("tell","Vertel wat er gebeurde"),LessonOption("listen","Luister naar de ander"),LessonOption("solution","Zoek samen een oplossing")),correctOrder=listOf("stop","tell","listen","solution"),hint="Begin met rustig worden.",explanation="Rust → vertellen → luisteren → samen oplossen."),
        ), emptyMap()
    )

    private fun digital4() = LessonDefinition(
        "g4-digital-safe-online-v1", "g4-digital-safe-online", "Slim & veilig online", "Digitale geletterdheid", 4, 9,
        listOf(
            LessonStep("dg4-1","Wachtwoord","Welk wachtwoord is het sterkst?",LessonInteractionType.MultipleChoice,"password",listOf(LessonOption("strong","Maan!Fiets27Boom"),LessonOption("weak","123456"),LessonOption("name","emma")),"strong",hint="Lang en moeilijk te raden is beter.",explanation="Een lang wachtwoord met verschillende soorten tekens is moeilijker te raden."),
            LessonStep("dg4-2","Privé","Welke informatie deel je niet zomaar openbaar?",LessonInteractionType.MultipleChoice,"privacy",listOf(LessonOption("address","Je huisadres"),LessonOption("color","Je lievelingskleur")),"address",hint="Kan iemand hiermee precies vinden waar je woont?",explanation="Een huisadres is persoonlijke informatie."),
            LessonStep("dg4-3","Bericht","Een onbekende vraagt online om een foto en je adres. Wat doe je?",LessonInteractionType.MultipleChoice,"safety",listOf(LessonOption("adult","Niet sturen en een vertrouwde volwassene vertellen"),LessonOption("send","Meteen sturen")),"adult",hint="Je hoeft nooit persoonlijke gegevens aan een onbekende te geven.",explanation="Stop het contact en vraag hulp aan een vertrouwde volwassene."),
            LessonStep("dg4-4","Informatie","Je ziet een opvallend bericht online. Wat is een slimme eerste stap?",LessonInteractionType.MultipleChoice,"media",listOf(LessonOption("check","Kijken wie de bron is en vergelijken"),LessonOption("share","Meteen doorsturen")),"check",hint="Niet alles online is automatisch waar.",explanation="Controleer bron, datum en vergelijk met andere betrouwbare informatie."),
            LessonStep("dg4-5","Stappen","Zet veilig reageren op een vreemd bericht in volgorde.",LessonInteractionType.Ordering,"safety",options=listOf(LessonOption("dont","Deel niets persoonlijks"),LessonOption("block","Stop of blokkeer contact"),LessonOption("tell","Vertel het aan een vertrouwde volwassene")),correctOrder=listOf("dont","block","tell"),hint="Bescherm eerst je gegevens.",explanation="Niet delen → contact stoppen → hulp vragen."),
        ), emptyMap()
    )
}

object PlatformLessons {
    val lessons: List<LessonDefinition> = AllLessons.lessons + ExpandedSubjectLessons.lessons
    fun get(id: String): LessonDefinition = lessons.firstOrNull { it.id == id } ?: AllLessons.get(id)
    fun forGroup(group: Int): List<LessonDefinition> = lessons.filter { it.group == group }
}
