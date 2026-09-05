package nl.leersprong.app.feature.learn

/**
 * Explicit placement for authored depth lessons. Existing lessons still use the
 * developmental inference in SchoolYearLearningPath; these entries guarantee that
 * the new repetition wave appears across the middle and later school-year blocks.
 */
object SchoolYearCurriculumMap {
    private val blocks: Map<String, SchoolYearBlock> = buildMap {
        // Groep 1
        put("g1-nl-sounds-letters-depth-v1", SchoolYearBlock.AUTUMN)
        put("g1-math-patterns-depth-v1", SchoolYearBlock.WINTER)
        put("g1-world-seasons-depth-v1", SchoolYearBlock.SPRING)
        // Groep 2
        put("g2-nl-sentences-depth-v1", SchoolYearBlock.AUTUMN)
        put("g2-math-numberline-depth-v1", SchoolYearBlock.WINTER)
        put("g2-math-clock-depth-v1", SchoolYearBlock.SPRING)
        // Groep 3
        put("g3-nl-spelling-depth-v1", SchoolYearBlock.AUTUMN)
        put("g3-math-to100-depth-v1", SchoolYearBlock.WINTER)
        put("g3-world-map-depth-v1", SchoolYearBlock.SPRING)
        // Groep 4
        put("g4-nl-mainidea-depth-v1", SchoolYearBlock.AUTUMN)
        put("g4-math-division-depth-v1", SchoolYearBlock.WINTER)
        put("g4-math-moneytime-depth-v1", SchoolYearBlock.SPRING)
        // Groep 5
        put("g5-nl-informative-depth-v1", SchoolYearBlock.AUTUMN)
        put("g5-math-multdiv-depth-v1", SchoolYearBlock.WINTER)
        put("g5-world-netherlands-depth-v1", SchoolYearBlock.SPRING)
        // Groep 6
        put("g6-nl-textstructure-depth-v1", SchoolYearBlock.AUTUMN)
        put("g6-math-fractions-decimals-depth-v1", SchoolYearBlock.WINTER)
        put("g6-world-ecosystem-depth-v1", SchoolYearBlock.SPRING)
        // Groep 7
        put("g7-nl-sourceargument-depth-v1", SchoolYearBlock.AUTUMN)
        put("g7-math-proportion-depth-v1", SchoolYearBlock.WINTER)
        put("g7-english-everyday-depth-v1", SchoolYearBlock.SPRING)
        // Groep 8
        put("g8-nl-media-depth-v1", SchoolYearBlock.AUTUMN)
        put("g8-math-data-depth-v1", SchoolYearBlock.WINTER)
        put("g8-english-functional-depth-v1", SchoolYearBlock.SPRING)
    }

    fun blockFor(lessonId: String): SchoolYearBlock? = blocks[lessonId]
}
