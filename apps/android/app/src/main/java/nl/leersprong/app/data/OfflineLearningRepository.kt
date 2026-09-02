package nl.leersprong.app.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import nl.leersprong.app.data.local.LearningDatabase
import nl.leersprong.app.data.local.LessonAttemptEntity
import nl.leersprong.app.data.local.SkillReviewEntity
import java.util.UUID

class OfflineLearningRepository(context: Context) {
    private val dao = LearningDatabase.get(context).learningDao()

    fun observeReviews(): Flow<List<SkillReviewEntity>> = dao.observeReviews()
    fun observeTotalXp(): Flow<Int> = dao.observeTotalXp()

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

    suspend fun updateReview(
        skillId: String,
        masteryPercent: Int,
        evidenceCount: Int,
        nextReviewAtEpochMs: Long,
    ) {
        dao.upsertReview(
            SkillReviewEntity(
                skillId = skillId,
                masteryPercent = masteryPercent.coerceIn(0, 100),
                evidenceCount = evidenceCount.coerceAtLeast(0),
                nextReviewAtEpochMs = nextReviewAtEpochMs,
                updatedAtEpochMs = System.currentTimeMillis(),
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
