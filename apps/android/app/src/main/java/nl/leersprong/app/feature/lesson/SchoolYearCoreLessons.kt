package nl.leersprong.app.feature.lesson

/**
 * Core school-year expansion for Groep 1–8.
 * These lessons are original LeerSprong content, structured around familiar Dutch
 * primary-school progression in Nederlands and Rekenen. The school-year scheduler
 * decides presentation blocks; FSRS/adaptive review remains evidence-driven.
 */
object SchoolYearCoreLessons {
    val lessons: List<LessonDefinition> = listOf(
        g1Dutch(), g1Math(), g2Dutch(), g2Math(), g3Dutch(), g3Math(), g4Dutch(), g4Math(),
        g5Dutch(), g5Math(), g6Dutch(), g6Math(), g7Dutch(), g7Math(), g8Dutch(), g8Math(),
    )

    private fun mc(id: String, title: String, prompt: String, concept: String, correct: String, vararg answers: Pair<String, String>, hint: String, explanation: String) =
        LessonStep(id, title, prompt, LessonInteractionType.MultipleChoice, concept, answers.map { LessonOption(it.first, it.second) }, correct, hint = hint, explanation = explanation)

    private fun fill(id: String, title: String, prompt: String, concept: String, answer: String, hint: String, explanation: String) =
        LessonStep(id, title, prompt, LessonInteractionType.FillBlank, concept, acceptedAnswers = listOf(answer), hint = hint, explanation = explanation)

    private fun lesson(id: String, skill: String, title: String, subject: String, group: Int, minutes: Int, steps: List<LessonStep>): LessonDefinition {
        val remedial = steps.associate { step ->
            step.conceptTag to when (step.interaction) {
                LessonInteractionType.FillBlank -> mc(
                    "${step.id}-r", "Samen nog eens", step.prompt, step.conceptTag,
                    step.acceptedAnswers.firstOrNull().orEmpty(),
                    *(listOf(step.acceptedAnswers.firstOrNull().orEmpty() to step.acceptedAnswers.firstOrNull().orEmpty(), "?" to "Ik weet het nog niet").toTypedArray()),
                    hint = step.hint, explanation = step.explanation,
                )
                else -> step.copy(id = "${step.id}-r", title = "Samen nog eens")
            }
        }
        return LessonDefinition(id, skill, title, subject, group, minutes, steps, remedial)
    }

    private fun g1Dutch() = lesson("g1-nl-sounds-words-v1", "g1-nl-letter-sound-foundations", "Klanken en woorden", "Nederlands", 1, 7, listOf(
        mc("g1-nl-01", "Eerste klank", "Welk woord begint met de m?", "letter-sound", "maan", "maan" to "maan", "vis" to "vis", "roos" to "roos", hint = "Zeg de woorden langzaam.", explanation = "Maan begint met de klank m."),
        mc("g1-nl-02", "Rijmen", "Welk woord rijmt op kat?", "rhyme", "mat", "mat" to "mat", "boom" to "boom", "vis" to "vis", hint = "Luister naar het einde.", explanation = "Kat en mat klinken aan het einde hetzelfde."),
        mc("g1-nl-03", "Lang of kort", "Welk woord is langer?", "word-length", "regenboog", "zon" to "zon", "regenboog" to "regenboog", hint = "Klap de stukjes van het woord.", explanation = "Regenboog heeft meer klankstukken dan zon."),
        mc("g1-nl-04", "Luister goed", "Welke hoort bij een dier?", "vocabulary", "hond", "stoel" to "stoel", "hond" to "hond", "jas" to "jas", hint = "Een dier kan bewegen en leven.", explanation = "Een hond is een dier."),
    ))

    private fun g1Math() = lesson("g1-math-shapes-compare-v1", "g1-math-number-shape-foundations", "Getallen, vormen en vergelijken", "Rekenen & Wiskunde", 1, 7, listOf(
        mc("g1-m-01", "Meer", "Welke groep heeft meer?", "compare", "b", "a" to "● ● ●", "b" to "● ● ● ● ●", hint = "Tel beide groepjes.", explanation = "Vijf is meer dan drie."),
        mc("g1-m-02", "Vorm", "Welke vorm heeft geen hoeken?", "shape", "circle", "square" to "vierkant", "circle" to "cirkel", "triangle" to "driehoek", hint = "Denk aan een wiel.", explanation = "Een cirkel heeft geen hoeken."),
        fill("g1-m-03", "Verder tellen", "Vul in: 6, 7, ___.", "number-order", "8", hint = "Tel één verder.", explanation = "Na 7 komt 8."),
        mc("g1-m-04", "Kleinste", "Welk getal is het kleinst?", "compare", "2", "2" to "2", "5" to "5", "8" to "8", hint = "Denk aan de getallenlijn.", explanation = "2 staat vóór 5 en 8."),
    ))

    private fun g2Dutch() = lesson("g2-nl-reading-short-words-v1", "g2-nl-reading-short-words", "Korte woorden lezen", "Nederlands", 2, 8, listOf(
        mc("g2-nl-01", "Lees", "Welk woord past bij 🐟?", "reading", "vis", "vis" to "vis", "vos" to "vos", "vas" to "vas", hint = "Kijk naar de middelste letter.", explanation = "Vis hoort bij de afbeelding van een vis."),
        mc("g2-nl-02", "Beginletter", "Welk woord begint met dezelfde klank als maan?", "letter-sound", "muis", "muis" to "muis", "huis" to "huis", "reis" to "reis", hint = "Maan begint met m.", explanation = "Muis begint ook met m."),
        fill("g2-nl-03", "Maak het woord", "Vul de letter in: k_t. Het woord is kat.", "spelling", "a", hint = "Zeg kat langzaam.", explanation = "Kat schrijf je k-a-t."),
        mc("g2-nl-04", "Zin", "Welke zin is logisch?", "sentence", "cat", "cat" to "De kat slaapt.", "tree" to "De boom drinkt melk.", hint = "Wat kan een kat doen?", explanation = "Een kat kan slapen."),
    ))

    private fun g2Math() = lesson("g2-math-numberline-20-v1", "g2-math-numberline-addition-20", "Getallenlijn tot 20", "Rekenen & Wiskunde", 2, 8, listOf(
        fill("g2-m-01", "Verder", "Vul in: 12, 13, ___.", "number-order", "14", hint = "Tel één verder.", explanation = "Na 13 komt 14."),
        mc("g2-m-02", "Sprong", "Je staat op 8 en springt 3 vooruit. Waar kom je?", "addition", "11", "10" to "10", "11" to "11", "12" to "12", hint = "9, 10, 11.", explanation = "8 + 3 = 11."),
        fill("g2-m-03", "Terug", "15 − 4 = ___.", "subtraction", "11", hint = "Tel vier terug vanaf 15.", explanation = "15 − 4 = 11."),
        mc("g2-m-04", "Tiental", "Welk getal heeft 1 tiental en 7 eenheden?", "place-value", "17", "7" to "7", "17" to "17", "71" to "71", hint = "1 tiental is 10.", explanation = "10 + 7 = 17."),
    ))

    private fun g3Dutch() = lesson("g3-nl-sentence-reading-v1", "g3-nl-reading-sentences", "Zinnen lezen en begrijpen", "Nederlands", 3, 9, listOf(
        mc("g3-nl-01", "Wie doet het?", "De hond rent naar de bal. Wie rent?", "reading", "dog", "dog" to "de hond", "ball" to "de bal", "naar" to "naar", hint = "Zoek wie iets doet.", explanation = "De hond is degene die rent."),
        mc("g3-nl-02", "Volgorde", "Wat gebeurt eerst: jas aantrekken of naar buiten lopen?", "sequence", "coat", "coat" to "jas aantrekken", "outside" to "naar buiten lopen", hint = "Denk aan wat je doet als het koud is.", explanation = "Je trekt eerst je jas aan."),
        fill("g3-nl-03", "Meervoud", "Eén kat, twee ___.", "plural", "katten", hint = "Bij kat verdubbelt de t.", explanation = "Het meervoud van kat is katten."),
        mc("g3-nl-04", "Hoofdletter", "Welke zin is goed geschreven?", "sentence", "good", "good" to "Mila leest een boek.", "bad" to "mila leest een boek.", hint = "Een zin begint met een hoofdletter.", explanation = "Mila begint met een hoofdletter."),
    ))

    private fun g3Math() = lesson("g3-math-add-sub-100-v1", "g3-math-addition-subtraction-100", "Optellen en aftrekken tot 100", "Rekenen & Wiskunde", 3, 10, listOf(
        fill("g3-m-01", "Tientallen", "30 + 20 = ___.", "addition", "50", hint = "Tel de tientallen.", explanation = "3 tientallen + 2 tientallen = 5 tientallen."),
        mc("g3-m-02", "Slim splitsen", "Wat is 47 + 3?", "addition", "50", "49" to "49", "50" to "50", "51" to "51", hint = "Maak eerst het volgende tiental.", explanation = "47 + 3 = 50."),
        fill("g3-m-03", "Aftrekken", "62 − 20 = ___.", "subtraction", "42", hint = "Haal twee tientallen weg.", explanation = "62 − 20 = 42."),
        mc("g3-m-04", "Geld", "Je hebt €2 en krijgt €3. Hoeveel heb je?", "money", "5", "4" to "€4", "5" to "€5", "6" to "€6", hint = "Tel het geld bij elkaar.", explanation = "€2 + €3 = €5."),
    ))

    private fun g4Dutch() = lesson("g4-nl-reading-main-idea-v1", "g4-nl-reading-main-idea", "Tekst begrijpen: de kern", "Nederlands", 4, 10, listOf(
        mc("g4-nl-01", "Kern", "Een tekst vertelt dat bijen bloemen bezoeken en zo planten helpen. Wat is de kern?", "reading", "bees", "bees" to "Bijen helpen planten", "flowers" to "Bloemen zijn geel", "day" to "Het is vandaag zonnig", hint = "Kies wat de hele tekst samenvat.", explanation = "De hoofdgedachte is dat bijen planten helpen."),
        mc("g4-nl-02", "Signaalwoord", "Welk woord laat een reden zien?", "text", "because", "because" to "omdat", "then" to "daarna", "but" to "maar", hint = "Een reden beantwoordt waarom.", explanation = "Omdat kondigt vaak een reden aan."),
        fill("g4-nl-03", "Werkwoord", "Vul in: Sara ___ naar school. (lopen)", "grammar", "loopt", hint = "Bij Sara hoort loopt.", explanation = "Sara loopt naar school."),
        mc("g4-nl-04", "Samenvatten", "Wat hoort in een korte samenvatting?", "reading", "main", "main" to "de belangrijkste informatie", "all" to "elk klein detail", hint = "Een samenvatting is kort.", explanation = "Je kiest de belangrijkste informatie."),
    ))

    private fun g4Math() = lesson("g4-math-time-money-v1", "g4-math-time-money", "Tijd en geld", "Rekenen & Wiskunde", 4, 10, listOf(
        mc("g4-m-01", "Klokkijken", "Het is half drie. Welke digitale tijd past?", "time", "1430", "1430" to "14:30", "1530" to "15:30", "1330" to "13:30", hint = "Half drie is een half uur vóór drie.", explanation = "Half drie in de middag is 14:30."),
        fill("g4-m-02", "Minuten", "Hoeveel minuten zitten in 2 uur?", "time", "120", hint = "1 uur = 60 minuten.", explanation = "2 × 60 = 120 minuten."),
        mc("g4-m-03", "Wisselgeld", "Je betaalt €10 voor iets van €7. Hoeveel terug?", "money", "3", "2" to "€2", "3" to "€3", "4" to "€4", hint = "10 − 7.", explanation = "Je krijgt €3 terug."),
        fill("g4-m-04", "Totaal", "€2,50 + €1,50 = € ___.", "money", "4", hint = "Tel de euro's en centen op.", explanation = "€2,50 + €1,50 = €4,00."),
    ))

    private fun g5Dutch() = lesson("g5-nl-text-structure-v1", "g5-nl-reading-text-structure", "Tekstopbouw en verbanden", "Nederlands", 5, 11, listOf(
        mc("g5-nl-01", "Oorzaak", "Het regende hard, daarom bleef de wedstrijd binnen. Wat is de oorzaak?", "text", "rain", "rain" to "het regende hard", "inside" to "de wedstrijd bleef binnen", hint = "De oorzaak komt vóór het gevolg.", explanation = "De regen is de oorzaak."),
        mc("g5-nl-02", "Tegenstelling", "Welk signaalwoord past bij een tegenstelling?", "text", "maar", "maar" to "maar", "omdat" to "omdat", "dus" to "dus", hint = "Je verwacht iets anders na dit woord.", explanation = "Maar geeft vaak een tegenstelling aan."),
        fill("g5-nl-03", "Verleden tijd", "Gisteren ___ ik naar huis. (fietsen)", "grammar", "fietste", hint = "Gebruik de verleden tijd.", explanation = "Gisteren fietste ik naar huis."),
        mc("g5-nl-04", "Bron", "Welke bron is het meest geschikt voor de openingstijden van een museum?", "source", "official", "official" to "de officiële website", "random" to "een willekeurige reactie", hint = "Kies de bron van de organisatie zelf.", explanation = "De officiële website is het meest direct."),
    ))

    private fun g5Math() = lesson("g5-math-fractions-measure-v1", "g5-math-fractions-measure", "Breuken en meten", "Rekenen & Wiskunde", 5, 11, listOf(
        mc("g5-m-01", "Breuk", "Welke breuk is hetzelfde als 1/2?", "fraction", "2/4", "2/4" to "2/4", "1/3" to "1/3", "3/4" to "3/4", hint = "Verdubbel teller en noemer.", explanation = "1/2 = 2/4."),
        fill("g5-m-02", "Lengte", "1 meter = ___ centimeter.", "measure", "100", hint = "Centi betekent honderdste.", explanation = "1 meter is 100 centimeter."),
        mc("g5-m-03", "Omtrek", "Een vierkant heeft zijden van 5 cm. Wat is de omtrek?", "geometry", "20", "10" to "10 cm", "20" to "20 cm", "25" to "25 cm", hint = "Tel alle vier zijden.", explanation = "4 × 5 = 20 cm."),
        fill("g5-m-04", "Delen", "84 ÷ 7 = ___.", "division", "12", hint = "Welke tafel van 7 geeft 84?", explanation = "7 × 12 = 84, dus 84 ÷ 7 = 12."),
    ))

    private fun g6Dutch() = lesson("g6-nl-information-argument-v1", "g6-nl-information-opinion", "Feit, mening en argument", "Nederlands", 6, 12, listOf(
        mc("g6-nl-01", "Feit of mening", "Welke zin is een mening?", "opinion", "nice", "nice" to "Ik vind dit boek spannend.", "fact" to "Het boek heeft 120 pagina's.", hint = "Een mening kan per persoon verschillen.", explanation = "‘Ik vind’ geeft een persoonlijke mening aan."),
        mc("g6-nl-02", "Argument", "Welke zin geeft een reden?", "argument", "because", "because" to "Ik ga met de fiets omdat het dichtbij is.", "plain" to "Ik ga met de fiets.", hint = "Zoek naar waarom.", explanation = "‘Omdat het dichtbij is’ is het argument."),
        fill("g6-nl-03", "Persoonsvorm", "Vul de persoonsvorm in: Morgen ___ wij vroeg. (vertrekken)", "grammar", "vertrekken", hint = "Het onderwerp is wij.", explanation = "Wij vertrekken morgen vroeg."),
        mc("g6-nl-04", "Betrouwbaar", "Wat maakt een bron betrouwbaarder?", "source", "author", "author" to "duidelijke auteur en datum", "caps" to "veel hoofdletters", hint = "Je wilt weten wie iets schreef en wanneer.", explanation = "Auteur en datum helpen de bron beoordelen."),
    ))

    private fun g6Math() = lesson("g6-math-decimals-percent-v1", "g6-math-decimals-percent", "Kommagetallen en procenten", "Rekenen & Wiskunde", 6, 12, listOf(
        fill("g6-m-01", "Komma", "2,5 + 1,5 = ___.", "decimal", "4", hint = "Tel hele en halve delen op.", explanation = "2,5 + 1,5 = 4,0."),
        mc("g6-m-02", "Procent", "50% is hetzelfde als...", "percent", "half", "half" to "de helft", "quarter" to "een kwart", "double" to "het dubbele", hint = "50 van de 100.", explanation = "50% is de helft."),
        fill("g6-m-03", "Percentage", "25% van 80 = ___.", "percent", "20", hint = "25% is een kwart.", explanation = "Een kwart van 80 is 20."),
        mc("g6-m-04", "Schaal", "Op een kaart is 1 cm gelijk aan 5 km. Wat is 3 cm?", "scale", "15", "10" to "10 km", "15" to "15 km", "20" to "20 km", hint = "3 × 5.", explanation = "3 cm stelt 15 km voor."),
    ))

    private fun g7Dutch() = lesson("g7-nl-argumentation-summary-v1", "g7-nl-argumentation-summary", "Argumenteren en samenvatten", "Nederlands", 7, 13, listOf(
        mc("g7-nl-01", "Standpunt", "Welke zin is een standpunt?", "argument", "view", "view" to "Schooldagen zouden later moeten beginnen.", "fact" to "De les begint om 8:30.", hint = "Een standpunt is iets waarover je kunt discussiëren.", explanation = "Later beginnen is een mening/standpunt."),
        mc("g7-nl-02", "Sterk argument", "Welk argument ondersteunt ‘meer bewegen op school’ het best?", "argument", "health", "health" to "Bewegen ondersteunt gezondheid en concentratie.", "color" to "Sportschoenen zijn vaak kleurrijk.", hint = "Het argument moet direct bij het standpunt passen.", explanation = "Gezondheid en concentratie ondersteunen het standpunt."),
        fill("g7-nl-03", "Verbindingswoord", "Vul in: Het regende; ___ ging de wedstrijd door.", "text", "toch", hint = "Je zoekt een tegenstelling.", explanation = "‘Toch’ laat zien dat iets ondanks de regen gebeurt."),
        mc("g7-nl-04", "Samenvatting", "Wat laat je meestal weg uit een samenvatting?", "summary", "detail", "detail" to "onbelangrijke voorbeelden", "main" to "de hoofdgedachte", hint = "Een samenvatting bevat de kern.", explanation = "Kleine voorbeelden kunnen meestal weg."),
    ))

    private fun g7Math() = lesson("g7-math-ratio-percent-v1", "g7-math-ratio-percent", "Verhoudingen en procenten", "Rekenen & Wiskunde", 7, 13, listOf(
        mc("g7-m-01", "Verhouding", "In een recept is de verhouding water:sap = 3:1. Bij 6 glazen water horen...", "ratio", "2", "1" to "1 glas sap", "2" to "2 glazen sap", "3" to "3 glazen sap", hint = "6 is twee keer 3.", explanation = "Dus sap wordt ook twee keer 1: 2 glazen."),
        fill("g7-m-02", "Korting", "Een jas van €80 heeft 25% korting. De korting is € ___.", "percent", "20", hint = "25% is een kwart.", explanation = "Een kwart van €80 is €20."),
        fill("g7-m-03", "Nieuwe prijs", "Na €20 korting op €80 betaal je € ___.", "percent", "60", hint = "80 − 20.", explanation = "€80 − €20 = €60."),
        mc("g7-m-04", "Negatief", "Welke temperatuur is het koudst?", "negative", "-5", "-5" to "−5 °C", "0" to "0 °C", "3" to "3 °C", hint = "Op de getallenlijn ligt −5 het verst links.", explanation = "−5 °C is het koudst."),
    ))

    private fun g8Dutch() = lesson("g8-nl-critical-reading-v1", "g8-nl-critical-reading-source", "Kritisch lezen en bronnen", "Nederlands", 8, 14, listOf(
        mc("g8-nl-01", "Doel", "Een advertentie zegt: ‘Nu kopen, beste keuze ooit!’ Wat is vooral het doel?", "purpose", "persuade", "persuade" to "overtuigen", "inform" to "alleen informeren", hint = "Een advertentie wil gedrag beïnvloeden.", explanation = "De tekst probeert de lezer te overtuigen."),
        mc("g8-nl-02", "Bewijs", "Welke uitspraak is het best onderbouwd?", "source", "data", "data" to "Een onderzoek met methode en cijfers beschrijft het resultaat.", "rumor" to "Iemand zei het online.", hint = "Zoek controleerbare informatie.", explanation = "Methode en cijfers maken de onderbouwing controleerbaar."),
        fill("g8-nl-03", "Formeel", "Vul een passend formeel woord in: ___ ontvang ik graag uw reactie.", "writing", "Graag", hint = "Houd de toon beleefd en zakelijk.", explanation = "‘Graag ontvang ik uw reactie’ past in een formele tekst."),
        mc("g8-nl-04", "Conclusie", "Waarop moet een goede conclusie aansluiten?", "summary", "question", "question" to "de hoofdvraag en argumenten", "new" to "een volledig nieuw onderwerp", hint = "Een conclusie rondt af wat al besproken is.", explanation = "De conclusie sluit aan op hoofdvraag en argumenten."),
    ))

    private fun g8Math() = lesson("g8-math-proportion-data-v1", "g8-math-proportion-data", "Verhoudingen, data en schaal", "Rekenen & Wiskunde", 8, 14, listOf(
        fill("g8-m-01", "Proportie", "3 kaartjes kosten €7,50. Eén kaartje kost € ___.", "ratio", "2,50", hint = "Deel €7,50 door 3.", explanation = "€7,50 ÷ 3 = €2,50."),
        mc("g8-m-02", "Gemiddelde", "Wat is het gemiddelde van 6, 8 en 10?", "data", "8", "7" to "7", "8" to "8", "9" to "9", hint = "Tel op en deel door 3.", explanation = "6 + 8 + 10 = 24; 24 ÷ 3 = 8."),
        fill("g8-m-03", "Schaal", "Schaal 1:100. Een muur is 4 cm op de tekening. In werkelijkheid is dat ___ meter.", "scale", "4", hint = "4 cm × 100 = 400 cm.", explanation = "400 cm is 4 meter."),
        mc("g8-m-04", "Kans", "Een eerlijke munt wordt één keer gegooid. Kans op kop?", "probability", "half", "half" to "1/2", "quarter" to "1/4", "certain" to "1", hint = "Er zijn twee even waarschijnlijke uitkomsten.", explanation = "Kop is één van twee even waarschijnlijke uitkomsten: 1/2."),
    ))
}
