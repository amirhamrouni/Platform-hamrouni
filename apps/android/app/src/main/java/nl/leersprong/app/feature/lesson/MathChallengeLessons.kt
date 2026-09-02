package nl.leersprong.app.feature.lesson

import kotlin.random.Random
import nl.leersprong.app.practice.SmartDistractorGenerator

object MathChallengeLessons {
    val lessons: List<LessonDefinition> = (2..8).map(::challengeForGroup)

    private fun challengeForGroup(group: Int): LessonDefinition {
        val random = Random(group * 104729)
        val steps = (1..5).map { index -> buildStep(group, index, random) }
        return LessonDefinition(
            id = "g${group}-math-smart-challenge-v1",
            skillId = "g${group}-math-smart-challenge",
            title = "RekenChallenge",
            subject = "Rekenen & Wiskunde",
            group = group,
            estimatedMinutes = 7,
            steps = steps,
            remedialSteps = emptyMap(),
        )
    }

    private fun buildStep(group: Int, index: Int, random: Random): LessonStep {
        val (prompt, answer) = when (group) {
            2 -> {
                val a = random.nextInt(1, 9)
                val b = random.nextInt(1, 11 - a)
                "$a + $b = ?" to a + b
            }
            3 -> {
                if (index % 2 == 0) {
                    val a = random.nextInt(8, 21)
                    val b = random.nextInt(1, a)
                    "$a − $b = ?" to a - b
                } else {
                    val a = random.nextInt(5, 16)
                    val b = random.nextInt(1, 21 - a)
                    "$a + $b = ?" to a + b
                }
            }
            4 -> {
                val a = random.nextInt(2, 11)
                val b = random.nextInt(2, 11)
                "$a × $b = ?" to a * b
            }
            5 -> {
                val divisor = random.nextInt(2, 11)
                val result = random.nextInt(2, 13)
                "${divisor * result} ÷ $divisor = ?" to result
            }
            6 -> {
                val base = listOf(20, 40, 60, 80, 100).random(random)
                val percent = listOf(10, 25, 50).random(random)
                "$percent% van $base = ?" to (base * percent / 100)
            }
            7 -> {
                val unit = random.nextInt(2, 8)
                val factor = random.nextInt(2, 7)
                val count = random.nextInt(2, 6)
                "$count × ${unit * factor} = ?" to count * unit * factor
            }
            else -> {
                val average = random.nextInt(6, 16)
                val delta = random.nextInt(1, 5)
                val values = listOf(average - delta, average, average + delta)
                "Gemiddelde van ${values.joinToString(", ")} = ?" to average
            }
        }

        val distractors = SmartDistractorGenerator.generate(
            correctAnswer = answer,
            baseRange = if (answer < 20) 5 else 10,
            maxOffset = if (answer < 50) 20 else 40,
            random = random,
        )
        val values = (distractors + answer).distinct().shuffled(random)
        return LessonStep(
            id = "g${group}-challenge-${index}",
            title = "Slim kiezen",
            prompt = prompt,
            interaction = LessonInteractionType.MultipleChoice,
            conceptTag = "mental-math",
            options = values.map { LessonOption(it.toString(), it.toString()) },
            correctOptionId = answer.toString(),
            hint = "Reken eerst zelf en vergelijk daarna met de antwoorden.",
            explanation = "Het juiste antwoord is $answer. De andere keuzes zijn veelvoorkomende rekenfouten.",
        )
    }
}
