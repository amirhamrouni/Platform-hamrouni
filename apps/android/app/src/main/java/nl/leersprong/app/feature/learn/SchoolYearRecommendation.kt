package nl.leersprong.app.feature.learn

/**
 * Keeps the chronological year path useful without turning it into a hard lock.
 * The current block supplies the default next lesson; adaptive/review flows remain
 * free to launch other lessons when learner evidence requires it.
 */
object SchoolYearRecommendation {
    fun nextLesson(
        path: List<ScheduledLesson>,
        currentBlock: SchoolYearBlock,
    ): ScheduledLesson? {
        if (path.isEmpty()) return null

        return path.firstOrNull { it.block == currentBlock }
            ?: path.firstOrNull { it.block.ordinal > currentBlock.ordinal }
            ?: path.lastOrNull()
    }
}
