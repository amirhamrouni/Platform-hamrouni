package nl.leersprong.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_attempts")
data class LessonAttemptEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val skillId: String,
    val activityId: String,
    val correct: Boolean,
    val hintsUsed: Int,
    val earnedXp: Int,
    val attemptedAtEpochMs: Long,
    val pendingSync: Boolean = true,
)

@Entity(tableName = "skill_reviews")
data class SkillReviewEntity(
    @PrimaryKey val skillId: String,
    val masteryPercent: Int,
    val evidenceCount: Int,
    val nextReviewAtEpochMs: Long,
    val updatedAtEpochMs: Long,
    val pendingSync: Boolean = true,
    val fsrsStability: Double = 0.0,
    val fsrsDifficulty: Double = 0.0,
    val fsrsReps: Int = 0,
    val fsrsLapses: Int = 0,
    val fsrsLastReviewAtEpochMs: Long = 0L,
)
