package nl.leersprong.app.practice

import kotlin.math.max
import kotlin.random.Random

/**
 * Strategy adapted from stephenWanjala/Multiply QuestionsGen.kt (MIT).
 * Copyright (c) 2025 WANJALA STEPHEN.
 */
object SmartDistractorGenerator {
    fun generate(
        correctAnswer: Int,
        baseRange: Int,
        maxOffset: Int,
        random: Random = Random.Default,
    ): List<Int> {
        val wrong = linkedSetOf<Int>()
        listOf(
            correctAnswer + 1,
            correctAnswer - 1,
            correctAnswer + 2,
            correctAnswer - 2,
            correctAnswer * 2,
            correctAnswer / 2,
            correctAnswer + 10,
            correctAnswer - 10,
        ).filterTo(wrong) { it >= 0 && it != correctAnswer }

        val safeBase = max(1, baseRange)
        val safeMax = max(safeBase + 1, maxOffset)
        var guard = 0
        while (wrong.size < 6 && guard < 64) {
            val offset = when (random.nextInt(6)) {
                0, 1 -> random.nextInt(1, safeBase + 1)
                2, 3 -> random.nextInt(safeBase, max(safeBase + 1, safeMax / 2 + 1))
                else -> random.nextInt(max(1, safeMax / 2), safeMax + 1)
            }
            val candidate = if (random.nextBoolean()) correctAnswer + offset else correctAnswer - offset
            if (candidate >= 0 && candidate != correctAnswer) wrong += candidate
            guard++
        }

        var fallback = 1
        while (wrong.size < 3) {
            val candidate = correctAnswer + fallback
            if (candidate != correctAnswer) wrong += candidate
            fallback++
        }

        return wrong.shuffled(random).take(3)
    }
}
