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
        if (groupLessons.isEmpty()) return emptyList()

        val preliminarilyOrdered = groupLessons.sortedWith(
            compareBy<LessonDefinition> { explicitBlockPriority(it) }
                .thenBy { skillPriority(it) }
                .thenBy { subjectPriority(it.subject) }
                .thenBy { it.title },
        )

        val inferredBlocks = preliminarilyOrdered.mapIndexed { index, lesson ->
            lesson to (SchoolYearCurriculumMap.blockFor(lesson.id)
                ?: SchoolYearBlock.entries[
                    ((index.toDouble() / preliminarilyOrdered.size) * SchoolYearBlock.entries.size)
                        .toInt().coerceIn(0, SchoolYearBlock.entries.lastIndex)
                ])
        }

        return inferredBlocks
            .sortedWith(
                compareBy<Pair<LessonDefinition, SchoolYearBlock>> { it.second.ordinal }
                    .thenBy { skillPriority(it.first) }
                    .thenBy { subjectPriority(it.first.subject) }
                    .thenBy { it.first.title },
            )
            .mapIndexed { index, pair -> ScheduledLesson(pair.first, pair.second, index + 1) }
    }

    private fun explicitBlockPriority(lesson: LessonDefinition): Int =
        SchoolYearCurriculumMap.blockFor(lesson.id)?.ordinal ?: Int.MAX_VALUE

    private fun subjectPriority(subject: String): Int = when (subject) {
        "Nederlands" -> 0
        "Rekenen & Wiskunde" -> 1
        "Engels" -> 2
        "Wereldoriëntatie" -> 3
        "Natuur & techniek" -> 4
        "Burgerschap" -> 5
        "Digitale geletterdheid" -> 6
        "Kunst & Cultuur" -> 7
        "NT2 / Thuistaalhulp" -> 8
        else -> 9
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
