package nl.leersprong.app.feature.learn

import nl.leersprong.app.feature.lesson.LessonDefinition
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SchoolYearRecommendationTest {
    private fun scheduled(id: String, block: SchoolYearBlock, sequence: Int) = ScheduledLesson(
        lesson = LessonDefinition(
            id = id,
            group = 4,
            subject = "Nederlands",
            title = id,
            skillId = id,
            estimatedMinutes = 8,
            steps = emptyList(),
        ),
        block = block,
        sequence = sequence,
    )

    @Test
    fun `prefers first lesson in current block`() {
        val path = listOf(
            scheduled("start", SchoolYearBlock.START, 1),
            scheduled("autumn-1", SchoolYearBlock.AUTUMN, 2),
            scheduled("autumn-2", SchoolYearBlock.AUTUMN, 3),
        )
        assertEquals("autumn-1", SchoolYearRecommendation.nextLesson(path, SchoolYearBlock.AUTUMN)?.lesson?.id)
    }

    @Test
    fun `falls forward when current block has no lesson`() {
        val path = listOf(
            scheduled("start", SchoolYearBlock.START, 1),
            scheduled("spring", SchoolYearBlock.SPRING, 2),
        )
        assertEquals("spring", SchoolYearRecommendation.nextLesson(path, SchoolYearBlock.WINTER)?.lesson?.id)
    }

    @Test
    fun `empty path has no recommendation`() {
        assertNull(SchoolYearRecommendation.nextLesson(emptyList(), SchoolYearBlock.START))
    }
}
