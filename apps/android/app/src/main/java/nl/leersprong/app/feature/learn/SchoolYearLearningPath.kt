package nl.leersprong.app.feature.learn

import nl.leersprong.app.feature.lesson.LessonDefinition

/**
 * Pedagogical ordering for LeerSprong, not a claim that every Dutch school uses one
 * identical weekly method. SLO kerndoelen define national curriculum direction while
 * schools retain room for their own curriculum. Blocks mirror the Dutch school-year
 * rhythm so the native learner world can present lessons in a familiar sequence.
 */
enum class SchoolYearBlock(val label: String, val period: String) {
    START("Blok 1 · Start schooljaar", "augustus – oktober"),
    AUTUMN("Blok 2 · Herfst", "oktober – december"),
    WINTER("Blok 3 · Winter", "januari – februari"),
    SPRING("Blok 4 · Voorjaar", "maart – april"),
    FINAL("Blok 5 · Naar de zomer", "mei – juli"),
}

data class ScheduledLesson(
    val lesson: LessonDefinition,
    val block: SchoolYearBlock,
    val sequence: Int,
)

object SchoolYearLearningPath {
    fun forGroup(group: Int, lessons: List<LessonDefinition>): List<ScheduledLesson> {
        val groupLessons = lessons.filter { it.group == group }
        // Order by developmental stage first, then alternate the core subjects. This avoids
        // presenting every Dutch lesson before every maths lesson and feels closer to a
        // normal primary-school week while still keeping prerequisites early in the year.
        val ordered = groupLessons.sortedWith(
            compareBy<LessonDefinition> { skillPriority(it) }
                .thenBy { subjectPriority(it.subject) }
                .thenBy { it.title },
        )
        if (ordered.isEmpty()) return emptyList()

        return ordered.mapIndexed { index, lesson ->
            val blockIndex = ((index.toDouble() / ordered.size) * SchoolYearBlock.entries.size)
                .toInt().coerceIn(0, SchoolYearBlock.entries.lastIndex)
            ScheduledLesson(lesson, SchoolYearBlock.entries[blockIndex], index + 1)
        }
    }

    private fun subjectPriority(subject: String): Int = when (subject) {
        "Nederlands" -> 0
        "Rekenen & Wiskunde" -> 1
        "Engels" -> 2
        "Wereldoriëntatie" -> 3
        "Burgerschap" -> 4
        "Digitale geletterdheid" -> 5
        "Kunst & Cultuur" -> 6
        "NT2 / Thuistaalhulp" -> 7
        else -> 8
    }

    private fun skillPriority(lesson: LessonDefinition): Int {
        val text = "${lesson.skillId} ${lesson.title}".lowercase()
        return when {
            listOf("count", "getal", "number", "klank", "sound", "letter", "shape", "vorm").any(text::contains) -> 0
            listOf("addition", "subtraction", "optel", "aftrek", "vowel", "spelling", "short-words", "sentence").any(text::contains) -> 1
            listOf("multiplication", "tafel", "division", "delen", "reading", "lezen", "time", "money", "measure").any(text::contains) -> 2
            listOf("fraction", "breuk", "decimal", "percent", "procent", "text", "tekst", "argument", "ratio", "verhouding").any(text::contains) -> 3
            listOf("source", "critical", "data", "scale", "probability", "summary").any(text::contains) -> 4
            else -> 2
        }
    }
}
