package nl.leersprong.app.feature.lesson

/** Deterministic Dutch spelling practice authored for LeerSprong NL. */
object SpellingChallengeLessons {
    val lessons: List<LessonDefinition> = listOf(
        lesson(4, listOf(
            WordItem("fiets", "Je rijdt ermee naar school."),
            WordItem("school", "Hier leer je samen met je klas."),
            WordItem("kaart", "Je gebruikt dit om een plek of route te bekijken."),
            WordItem("regen", "Water dat uit wolken valt."),
            WordItem("bloem", "Een plant kan er één krijgen."),
        )),
        lesson(5, listOf(
            WordItem("wereld", "De aarde en alles wat erop leeft."),
            WordItem("vriend", "Iemand met wie je graag omgaat."),
            WordItem("herfst", "Het seizoen na de zomer."),
            WordItem("morgen", "De dag na vandaag."),
            WordItem("spelen", "Een spel doen of plezier maken."),
        )),
        lesson(6, listOf(
            WordItem("verhaal", "Een tekst met gebeurtenissen en personages."),
            WordItem("nieuws", "Informatie over wat er recent is gebeurd."),
            WordItem("schrijven", "Woorden en zinnen op papier of scherm zetten."),
            WordItem("moeilijk", "Niet gemakkelijk."),
            WordItem("eigenlijk", "Een woord waarmee je aangeeft hoe iets werkelijk zit."),
        )),
        lesson(7, listOf(
            WordItem("mening", "Wat jij van iets vindt."),
            WordItem("bewijs", "Informatie die laat zien dat iets klopt."),
            WordItem("samenvatting", "Een korte weergave van de belangrijkste informatie."),
            WordItem("informatie", "Gegevens waar je iets van kunt leren."),
            WordItem("betekenis", "Wat een woord, zin of teken inhoudt."),
        )),
        lesson(8, listOf(
            WordItem("argument", "Een reden waarmee je een mening ondersteunt."),
            WordItem("conclusie", "Wat je aan het einde uit informatie afleidt."),
            WordItem("strategie", "Een plan om een doel te bereiken."),
            WordItem("perspectief", "De manier waarop iemand naar iets kijkt."),
            WordItem("betrouwbaar", "Iets of iemand waarop je kunt vertrouwen."),
        )),
    )

    private data class WordItem(val word: String, val clue: String)

    private fun lesson(group: Int, words: List<WordItem>) = LessonDefinition(
        id = "g${group}-dutch-word-pattern-v1",
        skillId = "g${group}-dutch-word-pattern",
        title = "WoordChallenge",
        subject = "Nederlands",
        group = group,
        estimatedMinutes = 8,
        steps = words.mapIndexed { index, item ->
            LessonStep(
                id = "g${group}-word-${index + 1}",
                title = "Raad en spel",
                prompt = "${item.clue} Typ het woord van ${item.word.length} letters.",
                interaction = LessonInteractionType.WordPattern,
                conceptTag = "spelling-pattern",
                targetWord = item.word,
                hint = "Let op klanken, lettercombinaties en de lengte van het woord.",
                explanation = "Het woord is ‘${item.word}’. Groen betekent juiste letter op de juiste plek; geel betekent dat de letter wel in het woord staat maar op een andere plek.",
            )
        },
        remedialSteps = emptyMap(),
    )
}
