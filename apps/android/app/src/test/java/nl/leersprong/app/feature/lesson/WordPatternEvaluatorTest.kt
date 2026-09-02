package nl.leersprong.app.feature.lesson

import org.junit.Assert.assertEquals
import org.junit.Test

class WordPatternEvaluatorTest {
    @Test
    fun exactWordMarksEveryLetterCorrect() {
        val result = WordPatternEvaluator.evaluate("fiets", "fiets")
        assertEquals(List(5) { LetterMatch.Correct }, result.map { it.match })
    }

    @Test
    fun repeatedLettersAreConsumedOnlyOnce() {
        val result = WordPatternEvaluator.evaluate("pappa", "appel")
        assertEquals(
            listOf(
                LetterMatch.Present,
                LetterMatch.Present,
                LetterMatch.Correct,
                LetterMatch.Absent,
                LetterMatch.Absent,
            ),
            result.map { it.match },
        )
    }

    @Test
    fun normalizationTrimsAndIgnoresCase() {
        assertEquals("school", WordPatternEvaluator.normalize("  SCHOOL "))
    }
}
