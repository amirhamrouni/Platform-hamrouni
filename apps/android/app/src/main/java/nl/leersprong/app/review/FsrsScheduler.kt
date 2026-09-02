package nl.leersprong.app.review

import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

/**
 * Adapted from open-spaced-repetition/android-fsrs (MIT, Copyright 2023 Open Spaced Repetition).
 * LeerSprong stores only the state needed for skill-level review scheduling.
 */
enum class FsrsRating(val value: Int) { Again(1), Hard(2), Good(3), Easy(4) }

data class FsrsState(
    val stability: Double = 0.0,
    val difficulty: Double = 0.0,
    val reps: Int = 0,
    val lapses: Int = 0,
    val lastReviewAtEpochMs: Long = 0L,
)

data class FsrsScheduleResult(
    val state: FsrsState,
    val nextReviewAtEpochMs: Long,
    val intervalDays: Int,
)

object FsrsScheduler {
    private const val DAY_MS = 86_400_000L
    private const val REQUEST_RETENTION = 0.90
    private const val MAX_INTERVAL_DAYS = 36500.0

    private val w = listOf(
        0.4, 0.6, 2.4, 5.8, 4.93, 0.94, 0.86, 0.01, 1.49,
        0.14, 0.94, 2.18, 0.05, 0.34, 1.26, 0.29, 2.61,
    )

    fun schedule(previous: FsrsState?, rating: FsrsRating, now: Long = System.currentTimeMillis()): FsrsScheduleResult {
        val prior = previous ?: FsrsState()
        val isNew = prior.reps == 0 || prior.stability <= 0.0 || prior.difficulty <= 0.0

        val nextDifficulty: Double
        val nextStability: Double
        val lapses = prior.lapses + if (rating == FsrsRating.Again) 1 else 0

        if (isNew) {
            nextDifficulty = initialDifficulty(rating)
            nextStability = initialStability(rating)
        } else {
            val elapsedDays = max(0.0, (now - prior.lastReviewAtEpochMs).toDouble() / DAY_MS)
            val retrievability = (1 + elapsedDays / (9 * prior.stability)).pow(-1.0)
            nextDifficulty = nextDifficulty(prior.difficulty, rating)
            nextStability = if (rating == FsrsRating.Again) {
                nextForgetStability(nextDifficulty, prior.stability, retrievability)
            } else {
                nextRecallStability(nextDifficulty, prior.stability, retrievability, rating)
            }
        }

        val interval = when (rating) {
            FsrsRating.Again -> 0
            FsrsRating.Hard -> nextInterval(nextStability).coerceAtMost(2)
            FsrsRating.Good -> nextInterval(nextStability)
            FsrsRating.Easy -> max(nextInterval(nextStability), 2)
        }
        val due = if (rating == FsrsRating.Again) now + 10 * 60_000L else now + interval * DAY_MS

        return FsrsScheduleResult(
            state = FsrsState(
                stability = nextStability,
                difficulty = nextDifficulty,
                reps = prior.reps + 1,
                lapses = lapses,
                lastReviewAtEpochMs = now,
            ),
            nextReviewAtEpochMs = due,
            intervalDays = interval,
        )
    }

    fun ratingForMastery(masteryPercent: Int): FsrsRating = when {
        masteryPercent < 50 -> FsrsRating.Again
        masteryPercent < 70 -> FsrsRating.Hard
        masteryPercent < 90 -> FsrsRating.Good
        else -> FsrsRating.Easy
    }

    private fun initialStability(rating: FsrsRating): Double = max(w[rating.value - 1], 0.1)

    private fun initialDifficulty(rating: FsrsRating): Double =
        min(max(w[4] - w[5] * (rating.value - 3), 1.0), 10.0)

    private fun nextInterval(stability: Double): Int {
        val interval = stability * 9 * (1 / REQUEST_RETENTION - 1)
        return min(max(round(interval), 1.0), MAX_INTERVAL_DAYS).toInt()
    }

    private fun nextDifficulty(difficulty: Double, rating: FsrsRating): Double {
        val next = difficulty - w[6] * (rating.value - 3)
        return min(max(meanReversion(w[4], next), 1.0), 10.0)
    }

    private fun meanReversion(initial: Double, current: Double): Double =
        w[7] * initial + (1 - w[7]) * current

    private fun nextRecallStability(d: Double, s: Double, r: Double, rating: FsrsRating): Double {
        val hardPenalty = if (rating == FsrsRating.Hard) w[15] else 1.0
        val easyBonus = if (rating == FsrsRating.Easy) w[16] else 1.0
        return s * (1 + exp(w[8]) * (11 - d) * s.pow(-w[9]) * (exp((1 - r) * w[10]) - 1) * hardPenalty * easyBonus)
    }

    private fun nextForgetStability(d: Double, s: Double, r: Double): Double =
        w[11] * d.pow(-w[12]) * ((s + 1.0).pow(w[13]) - 1) * exp((1 - r) * w[14])
}
