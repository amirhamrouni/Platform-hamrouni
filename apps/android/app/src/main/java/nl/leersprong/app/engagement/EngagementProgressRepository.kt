package nl.leersprong.app.engagement

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.engagementDataStore by preferencesDataStore(name = "engagement_progress")

private object EngagementKeys {
    val epochDay = longPreferencesKey("epoch_day")
    val missionMask = intPreferencesKey("mission_mask")
    val totalStars = intPreferencesKey("total_stars")
    val completedDays = intPreferencesKey("completed_days")
}

data class EngagementProgress(
    val epochDay: Long = LocalDate.now().toEpochDay(),
    val missionMask: Int = 0,
    val totalStars: Int = 0,
    val completedDays: Int = 0,
) {
    fun missionDone(index: Int): Boolean = missionMask and (1 shl index) != 0
    val completedMissionCount: Int get() = (0..2).count(::missionDone)
    val allDailyMissionsDone: Boolean get() = completedMissionCount == 3
}

class EngagementProgressRepository(private val context: Context) {
    private val today: Long get() = LocalDate.now().toEpochDay()

    val progress: Flow<EngagementProgress> = context.engagementDataStore.data.map { preferences ->
        val storedDay = preferences[EngagementKeys.epochDay] ?: today
        if (storedDay != today) {
            EngagementProgress(
                epochDay = today,
                missionMask = 0,
                totalStars = preferences[EngagementKeys.totalStars] ?: 0,
                completedDays = preferences[EngagementKeys.completedDays] ?: 0,
            )
        } else {
            EngagementProgress(
                epochDay = storedDay,
                missionMask = preferences[EngagementKeys.missionMask] ?: 0,
                totalStars = preferences[EngagementKeys.totalStars] ?: 0,
                completedDays = preferences[EngagementKeys.completedDays] ?: 0,
            )
        }
    }

    suspend fun completeMission(index: Int) {
        require(index in 0..2)
        context.engagementDataStore.edit { preferences ->
            val storedDay = preferences[EngagementKeys.epochDay] ?: today
            var mask = if (storedDay == today) preferences[EngagementKeys.missionMask] ?: 0 else 0
            val bit = 1 shl index
            if (mask and bit != 0) return@edit

            mask = mask or bit
            val nowAllDone = (0..2).all { mask and (1 shl it) != 0 }
            preferences[EngagementKeys.epochDay] = today
            preferences[EngagementKeys.missionMask] = mask
            preferences[EngagementKeys.totalStars] = (preferences[EngagementKeys.totalStars] ?: 0) + 1
            if (nowAllDone) {
                preferences[EngagementKeys.completedDays] = (preferences[EngagementKeys.completedDays] ?: 0) + 1
            }
        }
    }
}
