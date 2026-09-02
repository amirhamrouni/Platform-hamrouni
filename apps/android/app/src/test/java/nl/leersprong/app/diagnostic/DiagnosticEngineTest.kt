package nl.leersprong.app.diagnostic

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DiagnosticEngineTest {
    @Test
    fun everyGroupHasBalancedRunnableDiagnostic() {
        (1..8).forEach { group ->
            val questions = DiagnosticEngine.questions(group)
            assertEquals(6, questions.size)
            assertEquals(3, questions.count { it.domain == DiagnosticDomain.Nederlands })
            assertEquals(3, questions.count { it.domain == DiagnosticDomain.Rekenen })
            assertEquals(questions.size, questions.map { it.id }.distinct().size)
            questions.forEach { question ->
                assertTrue(question.prompt.isNotBlank())
                assertTrue(question.options.size >= 3)
                assertTrue(question.correctIndex in question.options.indices)
            }
        }
    }

    @Test
    fun perfectAnswersProducePerfectBaseline() {
        val questions = DiagnosticEngine.questions(4)
        val answers = questions.associate { it.id to it.correctIndex }
        val result = DiagnosticEngine.score(questions, answers)
        assertEquals(100, result.dutchPercent)
        assertEquals(100, result.mathPercent)
    }

    @Test
    fun missingAnswersAreCountedAsIncorrect() {
        val questions = DiagnosticEngine.questions(6)
        val firstDutch = questions.first { it.domain == DiagnosticDomain.Nederlands }
        val firstMath = questions.first { it.domain == DiagnosticDomain.Rekenen }
        val result = DiagnosticEngine.score(
            questions,
            mapOf(firstDutch.id to firstDutch.correctIndex, firstMath.id to firstMath.correctIndex),
        )
        assertEquals(33, result.dutchPercent)
        assertEquals(33, result.mathPercent)
    }
}
