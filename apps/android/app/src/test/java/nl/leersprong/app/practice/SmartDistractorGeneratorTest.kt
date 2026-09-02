package nl.leersprong.app.practice

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SmartDistractorGeneratorTest {
    @Test
    fun generatesThreeDistinctPlausibleNonNegativeAnswers() {
        val correct = 24
        val result = SmartDistractorGenerator.generate(correct, 10, 20, Random(42))
        assertEquals(3, result.size)
        assertEquals(3, result.distinct().size)
        assertFalse(result.contains(correct))
        assertTrue(result.all { it >= 0 })
    }

    @Test
    fun handlesSmallAnswersWithoutNegativeDistractors() {
        val result = SmartDistractorGenerator.generate(1, 3, 8, Random(7))
        assertEquals(3, result.size)
        assertTrue(result.all { it >= 0 && it != 1 })
    }
}
