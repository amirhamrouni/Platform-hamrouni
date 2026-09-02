package nl.leersprong.app.feature.lesson

/** Single native lesson registry used by Home, Leerwereld and Lesson Player. */
object LessonLibrary {
    val lessons: List<LessonDefinition> = AllLessons.lessons + UpperPrimaryLanguageLessons.lessons

    fun get(id: String): LessonDefinition =
        lessons.firstOrNull { it.id == id } ?: AllLessons.get(id)

    fun forGroup(group: Int): List<LessonDefinition> =
        lessons.filter { it.group == group }
}
