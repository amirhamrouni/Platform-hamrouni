package nl.leersprong.app.feature.lesson

/** Single native lesson registry used by Home, Leerwereld and Lesson Player. */
object LessonLibrary {
    val lessons: List<LessonDefinition> = (
        PlatformLessons.lessons +
            SchoolYearCoreLessons.lessons +
            UpperPrimaryLanguageLessons.lessons +
            MathChallengeLessons.lessons +
            SpellingChallengeLessons.lessons
        ).distinctBy { it.id }

    fun get(id: String): LessonDefinition =
        lessons.firstOrNull { it.id == id } ?: PlatformLessons.get(id)

    fun forGroup(group: Int): List<LessonDefinition> =
        lessons.filter { it.group == group }
}
