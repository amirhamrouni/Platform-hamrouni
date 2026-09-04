package nl.leersprong.app.feature.lesson

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LessonLibraryTest {
    @Test
    fun lessonAndSkillIdsAreUnique() {
        assertEquals(LessonLibrary.lessons.size, LessonLibrary.lessons.map { it.id }.toSet().size)
        assertEquals(LessonLibrary.lessons.size, LessonLibrary.lessons.map { it.skillId }.toSet().size)
    }

    @Test
    fun everyLessonHasRunnableActivities() {
        LessonLibrary.lessons.forEach { lesson ->
            assertTrue("${lesson.id} has no steps", lesson.steps.isNotEmpty())
            lesson.steps.forEach { step ->
                assertTrue("${step.id} has no hint", step.hint.isNotBlank())
                assertTrue("${step.id} has no explanation", step.explanation.isNotBlank())
                when (step.interaction) {
                    LessonInteractionType.MultipleChoice,
                    LessonInteractionType.ListenChoose -> {
                        assertTrue("${step.id} needs options", step.options.size >= 2)
                        assertTrue("${step.id} correct option missing", step.options.any { it.id == step.correctOptionId })
                    }
                    LessonInteractionType.FillBlank -> assertFalse("${step.id} needs accepted answers", step.acceptedAnswers.isEmpty())
                    LessonInteractionType.Ordering -> {
                        assertTrue("${step.id} ordering needs options", step.options.size >= 2)
                        assertEquals(step.options.map { it.id }.toSet(), step.correctOrder.toSet())
                    }
                    LessonInteractionType.WordPattern -> {
                        val target = step.targetWord.orEmpty()
                        assertTrue("${step.id} needs a target word", target.isNotBlank())
                        assertTrue("${step.id} target must contain only letters", target.all(Char::isLetter))
                    }
                }
            }
        }
    }

    @Test
    fun allGroupsHaveNativeLearningAndUpperPrimaryHasDutchAndEnglish() {
        (1..8).forEach { group -> assertTrue("Groep $group has no native lesson", LessonLibrary.forGroup(group).isNotEmpty()) }
        (5..8).forEach { group ->
            val subjects = LessonLibrary.forGroup(group).map { it.subject }.toSet()
            assertTrue("Groep $group missing Nederlands", "Nederlands" in subjects)
            assertTrue("Groep $group missing Engels", "Engels" in subjects)
        }
    }

    @Test
    fun everyGroupHasNewDutchAndMathSchoolYearCoreLessons() {
        assertEquals(16, SchoolYearCoreLessons.lessons.size)
        assertEquals(64, SchoolYearCoreLessons.lessons.sumOf { it.steps.size })
        (1..8).forEach { group ->
            val core = SchoolYearCoreLessons.lessons.filter { it.group == group }
            assertEquals("Groep $group should have two new core lessons", 2, core.size)
            assertTrue("Groep $group missing new Nederlands core", core.any { it.subject == "Nederlands" })
            assertTrue("Groep $group missing new Rekenen core", core.any { it.subject == "Rekenen & Wiskunde" })
            assertTrue("Groep $group core activities incomplete", core.all { it.steps.size == 4 })
        }
    }

    @Test
    fun everyGroupHasBroadSchoolYearEnrichment() {
        assertEquals(16, SchoolYearBroadLessons.lessons.size)
        assertEquals(48, SchoolYearBroadLessons.lessons.sumOf { it.steps.size })
        (1..8).forEach { group ->
            val broad = SchoolYearBroadLessons.lessons.filter { it.group == group }
            assertEquals("Groep $group should have two broad lessons", 2, broad.size)
            assertTrue("Groep $group missing Wereldoriëntatie", broad.any { it.subject == "Wereldoriëntatie" })
            assertTrue("Groep $group broad activities incomplete", broad.all { it.steps.size == 3 })
        }
    }

    @Test
    fun groepFourToEightHaveWordChallenge() {
        (4..8).forEach { group ->
            assertTrue(
                "Groep $group missing WoordChallenge",
                LessonLibrary.forGroup(group).any { lesson -> lesson.steps.any { it.interaction == LessonInteractionType.WordPattern } },
            )
        }
    }
}
