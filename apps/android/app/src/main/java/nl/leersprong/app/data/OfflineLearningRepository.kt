package nl.leersprong.app.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import nl.leersprong.app.data.local.LearningDatabase
import nl.leersprong.app.data.local.LessonAttemptEntity
import nl.leersprong.app.data.local.SkillReviewEntity
import nl.leersprong.app.review.FsrsScheduler
import nl.leersprong.app.review.FsrsState
import java.util.UUID

class OfflineLearningRepository(context: Context) {
    private val dao = LearningDatabase.get(context).learningDao()

    fun observeReviews(): Flow<List<SkillReviewEntity>> = dao.observeReviews()
    fun observeTotalXp(): Flow<Int> = dao.observeTotalXp()
    fun observePracticeTimestamps(): Flow<List<Long>> = dao.observePracticeTimestamps()

    suspend fun recordAttempt(
        lessonId: String,
        skillId: String,
        activityId: String,
        correct: Boolean,
        hintsUsed: Int,
        earnedXp: Int,
        attemptedAtEpochMs: Long = System.currentTimeMillis(),
    ) {
        dao.insertAttempt(
            LessonAttemptEntity(
                id = UUID.randomUUID().toString(),
                lessonId = lessonId,
                skillId = skillId,
                activityId = activityId,
                correct = correct,
                hintsUsed = hintsUsed,
                earnedXp = earnedXp,
                attemptedAtEpochMs = attemptedAtEpochMs,
            ),
        )
    }

    suspend fun scheduleReview(
        skillId: String,
        masteryPercent: Int,
        evidenceCount: Int,
        now: Long = System.currentTimeMillis(),
    ): Long {
        val previous = dao.getReview(skillId)
        val priorState = previous?.let {
            FsrsState(
                stability = it.fsrsStability,
                difficulty = it.fsrsDifficulty,
                reps = it.fsrsReps,
                lapses = it.fsrsLapses,
                lastReviewAtEpochMs = it.fsrsLastReviewAtEpochMs,
            )
        }
        val rating = FsrsScheduler.ratingForMastery(masteryPercent)
        val result = FsrsScheduler.schedule(priorState, rating, now)

        dao.upsertReview(
            SkillReviewEntity(
                skillId = skillId,
                masteryPercent = masteryPercent.coerceIn(0, 100),
                evidenceCount = evidenceCount.coerceAtLeast(0),
                nextReviewAtEpochMs = result.nextReviewAtEpochMs,
                updatedAtEpochMs = now,
                pendingSync = true,
                fsrsStability = result.state.stability,
                fsrsDifficulty = result.state.difficulty,
                fsrsReps = result.state.reps,
                fsrsLapses = result.state.lapses,
                fsrsLastReviewAtEpochMs = result.state.lastReviewAtEpochMs,
            ),
        )
        return result.nextReviewAtEpochMs
    }

    suspend fun updateReview(
        skillId: String,
        masteryPercent: Int,
        evidenceCount: Int,
        nextReviewAtEpochMs: Long,
    ) {
        val existing = dao.getReview(skillId)
        dao.upsertReview(
            SkillReviewEntity(
                skillId = skillId,
                masteryPercent = masteryPercent.coerceIn(0, 100),
                evidenceCount = evidenceCount.coerceAtLeast(0),
                nextReviewAtEpochMs = nextReviewAtEpochMs,
                updatedAtEpochMs = System.currentTimeMillis(),
                fsrsStability = existing?.fsrsStability ?: 0.0,
                fsrsDifficulty = existing?.fsrsDifficulty ?: 0.0,
                fsrsReps = existing?.fsrsReps ?: 0,
                fsrsLapses = existing?.fsrsLapses ?: 0,
                fsrsLastReviewAtEpochMs = existing?.fsrsLastReviewAtEpochMs ?: 0L,
            ),
        )
    }

    suspend fun pendingPayload(): PendingLearningPayload = PendingLearningPayload(
        attempts = dao.pendingAttempts(),
        reviews = dao.pendingReviews(),
    )

    suspend fun markSynced(payload: PendingLearningPayload) {
        if (payload.attempts.isNotEmpty()) dao.markAttemptsSynced(payload.attempts.map { it.id })
        if (payload.reviews.isNotEmpty()) dao.markReviewsSynced(payload.reviews.map { it.skillId })
    }
}

data class PendingLearningPayload(
    val attempts: List<LessonAttemptEntity>,
    val reviews: List<SkillReviewEntity>,
)
