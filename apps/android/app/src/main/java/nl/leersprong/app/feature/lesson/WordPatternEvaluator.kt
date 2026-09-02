package nl.leersprong.app.feature.lesson

/**
 * Adapted from opatry/wordle-kt WordleRules.kt (MIT, Copyright 2022 Olivier Patry).
 * The two-pass evaluation correctly handles repeated letters.
 */
enum class LetterMatch { Correct, Present, Absent }

data class LetterFeedback(val letter: Char, val match: LetterMatch)

object WordPatternEvaluator {
    fun evaluate(guess: String, target: String): List<LetterFeedback> {
        val normalizedGuess = normalize(guess)
        val normalizedTarget = normalize(target)
        require(normalizedGuess.length == normalizedTarget.length) {
            "Guess and target must have the same length"
        }

        val matches = MutableList(normalizedGuess.length) { LetterMatch.Absent }
        val candidates = normalizedTarget.toMutableList()

        normalizedGuess.forEachIndexed { index, char ->
            if (char == normalizedTarget[index]) {
                matches[index] = LetterMatch.Correct
                candidates.remove(char)
            }
        }

        normalizedGuess.forEachIndexed { index, char ->
            if (matches[index] == LetterMatch.Correct) return@forEachIndexed
            val candidateIndex = candidates.indexOf(char)
            if (candidateIndex >= 0) {
                matches[index] = LetterMatch.Present
                candidates.removeAt(candidateIndex)
            }
        }

        return normalizedGuess.mapIndexed { index, char -> LetterFeedback(char, matches[index]) }
    }

    fun normalize(value: String): String = value.trim().lowercase()
}
