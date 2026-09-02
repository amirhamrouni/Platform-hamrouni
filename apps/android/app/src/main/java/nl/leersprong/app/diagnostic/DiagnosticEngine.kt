package nl.leersprong.app.diagnostic

data class DiagnosticQuestion(
    val id: String,
    val domain: DiagnosticDomain,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
)

enum class DiagnosticDomain { Nederlands, Rekenen }

data class DiagnosticResult(
    val dutchPercent: Int,
    val mathPercent: Int,
)

object DiagnosticEngine {
    fun questions(group: Int): List<DiagnosticQuestion> {
        val g = group.coerceIn(1, 8)
        return dutch(g) + math(g)
    }

    fun score(questions: List<DiagnosticQuestion>, answers: Map<String, Int>): DiagnosticResult {
        fun domainScore(domain: DiagnosticDomain): Int {
            val subset = questions.filter { it.domain == domain }
            if (subset.isEmpty()) return 0
            val correct = subset.count { answers[it.id] == it.correctIndex }
            return ((correct.toDouble() / subset.size) * 100).toInt().coerceIn(0, 100)
        }
        return DiagnosticResult(
            dutchPercent = domainScore(DiagnosticDomain.Nederlands),
            mathPercent = domainScore(DiagnosticDomain.Rekenen),
        )
    }

    private fun dutch(group: Int): List<DiagnosticQuestion> = when (group) {
        1 -> listOf(q("d1","Welke letter hoor je vooraan in maan?", listOf("m","s","t"),0), q("d2","Welk woord rijmt op kat?", listOf("mat","vis","boom"),0), q("d3","Welk woord hoort bij een dier?", listOf("hond","stoel","maan"),0))
        2 -> listOf(q("d1","Welk woord is goed geschreven?", listOf("boom","bom","bohm"),0), q("d2","Maak de zin af: Ik ___ naar school.", listOf("ga","gaan","gingen"),0), q("d3","Wat is het tegenovergestelde van groot?", listOf("klein","lang","breed"),0))
        3 -> listOf(q("d1","Welk woord heeft een lange klank?", listOf("maan","man","mat"),0), q("d2","Wat is de hoofdgedachte van: Sam trekt zijn jas aan want het regent?", listOf("Het regent en Sam kleedt zich erop.","Sam heeft een rode jas.","Sam gaat zwemmen."),0), q("d3","Kies de juiste zin.", listOf("Morgen ga ik naar school.","Morgen ik ga school naar.","Ik morgen naar school ga."),0))
        4 -> listOf(q("d1","Welk woord is juist?", listOf("bomen","boomen","bomme"),0), q("d2","Wat is een goede samenvatting? De trein had vertraging, daarom kwam Noor later aan.", listOf("Noor kwam later door treinvertraging.","Noor houdt van treinen.","De trein was blauw."),0), q("d3","Welk signaalwoord geeft een reden?", listOf("omdat","daarna","toch"),0))
        5 -> listOf(q("d1","Welke zin bevat de hoofdgedachte?", listOf("Bijen zijn belangrijk omdat ze planten bestuiven.","Een bij is klein.","Sommige bloemen zijn geel."),0), q("d2","Welk woord is correct gespeld?", listOf("bibliotheek","biblioteek","biebliotheek"),0), q("d3","Welke bron past het best bij een werkstuk over het weer?", listOf("KNMI","een reclamefolder","een spelwebsite"),0))
        6 -> listOf(q("d1","Wat is het tekstdoel van een handleiding?", listOf("instrueren","vermaken","overtuigen"),0), q("d2","Welke bron is het meest betrouwbaar voor gemeenteregels?", listOf("de website van de gemeente","een anonieme reactie","een advertentie"),0), q("d3","Welk signaalwoord kondigt een tegenstelling aan?", listOf("maar","omdat","daardoor"),0))
        7 -> listOf(q("d1","Welke zin is een argument?", listOf("We moeten meer bomen planten, want ze geven verkoeling.","Bomen zijn groen.","Ik zie een boom."),0), q("d2","Wat maakt een bron sterker?", listOf("controleerbare auteur en gegevens","veel uitroeptekens","een opvallende titel"),0), q("d3","Wat is een conclusie?", listOf("een gevolgtrekking uit informatie","de titel van een tekst","een los voorbeeld"),0))
        else -> listOf(q("d1","Welke aanpak is het beste bij twee bronnen die elkaar tegenspreken?", listOf("vergelijk bewijs en herkomst","kies de kortste","negeer beide"),0), q("d2","Wat is parafraseren?", listOf("informatie in eigen woorden weergeven","letterlijk kopiëren","alleen de titel noemen"),0), q("d3","Welke structuur past bij een betoog?", listOf("standpunt, argumenten, conclusie","alleen voorbeelden","vragen zonder antwoord"),0))
    }.map { it.copy(domain = DiagnosticDomain.Nederlands) }

    private fun math(group: Int): List<DiagnosticQuestion> = when (group) {
        1 -> listOf(q("m1","Hoeveel stippen: ● ● ● ?", listOf("3","2","4"),0), q("m2","Wat komt na 6?", listOf("7","5","8"),0), q("m3","Wat is meer?", listOf("5","2","evenveel"),0))
        2 -> listOf(q("m1","7 + 2 =", listOf("9","8","10"),0), q("m2","10 - 4 =", listOf("6","5","7"),0), q("m3","Welke is het grootst?", listOf("18","13","8"),0))
        3 -> listOf(q("m1","8 + 7 =", listOf("15","14","16"),0), q("m2","20 - 9 =", listOf("11","10","12"),0), q("m3","4 × 3 =", listOf("12","7","14"),0))
        4 -> listOf(q("m1","6 × 7 =", listOf("42","36","48"),0), q("m2","84 - 29 =", listOf("55","65","45"),0), q("m3","100 ÷ 4 =", listOf("25","20","40"),0))
        5 -> listOf(q("m1","1/2 van 18 =", listOf("9","8","6"),0), q("m2","3 × 25 =", listOf("75","65","85"),0), q("m3","2,5 + 1,5 =", listOf("4","3","5"),0))
        6 -> listOf(q("m1","25% van 80 =", listOf("20","25","15"),0), q("m2","3/4 van 40 =", listOf("30","25","35"),0), q("m3","2,4 × 10 =", listOf("24","2,40","240"),0))
        7 -> listOf(q("m1","3 : 5 = 12 : ?", listOf("20","15","18"),0), q("m2","15% van 200 =", listOf("30","20","25"),0), q("m3","0,75 =", listOf("75%","7,5%","750%"),0))
        else -> listOf(q("m1","Een prijs van €80 stijgt 10%. Nieuwe prijs?", listOf("€88","€90","€82"),0), q("m2","Gemiddelde van 6, 8 en 10 =", listOf("8","9","7"),0), q("m3","3/5 =", listOf("60%","35%","65%"),0))
    }.map { it.copy(domain = DiagnosticDomain.Rekenen) }

    private fun q(id: String, prompt: String, options: List<String>, correct: Int) =
        DiagnosticQuestion(id, DiagnosticDomain.Nederlands, prompt, options, correct)
}
