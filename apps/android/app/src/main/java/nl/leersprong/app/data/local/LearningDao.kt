package nl.leersprong.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: LessonAttemptEntity)

    @Upsert
    suspend fun upsertReview(review: SkillReviewEntity)

    @Query("SELECT * FROM skill_reviews ORDER BY nextReviewAtEpochMs ASC")
    fun observeReviews(): Flow<List<SkillReviewEntity>>

    @Query("SELECT COALESCE(SUM(earnedXp), 0) FROM lesson_attempts")
    fun observeTotalXp(): Flow<Int>

    @Query("SELECT attemptedAtEpochMs FROM lesson_attempts ORDER BY attemptedAtEpochMs DESC")
    fun observePracticeTimestamps(): Flow<List<Long>>

    @Query("SELECT * FROM lesson_attempts WHERE pendingSync = 1 ORDER BY attemptedAtEpochMs ASC")
    suspend fun pendingAttempts(): List<LessonAttemptEntity>

    @Query("SELECT * FROM skill_reviews WHERE pendingSync = 1 ORDER BY updatedAtEpochMs ASC")
    suspend fun pendingReviews(): List<SkillReviewEntity>

    @Query("UPDATE lesson_attempts SET pendingSync = 0 WHERE id IN (:ids)")
    suspend fun markAttemptsSynced(ids: List<String>)

    @Query("UPDATE skill_reviews SET pendingSync = 0 WHERE skillId IN (:skillIds)")
    suspend fun markReviewsSynced(skillIds: List<String>)
}
