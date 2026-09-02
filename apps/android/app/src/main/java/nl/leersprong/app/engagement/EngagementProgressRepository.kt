package nl.leersprong.app.engagement

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.util.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.engagementDataStore by preferencesDataStore(name = "engagement_progress")

private object EngagementKeys {
    val dayKey = intPreferencesKey("day_key")
    val missionMask = intPreferencesKey("mission_mask")
    val totalStars = intPreferencesKey("total_stars")
    val completedDays = intPreferencesKey("completed_days")
}

private fun currentDayKey(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR)
}

data class EngagementProgress(
    val dayKey: Int = currentDayKey(),
    val missionMask: Int = 0,
    val totalStars: Int = 0,
    val completedDays: Int = 0,
) {
    fun missionDone(index: Int): Boolean = missionMask and (1 shl index) != 0
    val completedMissionCount: Int get() = (0..2).count(::missionDone)
    val allDailyMissionsDone: Boolean get() = completedMissionCount == 3
}

class EngagementProgressRepository(private val context: Context) {
    private val today: Int get() = currentDayKey()

    val progress: Flow<EngagementProgress> = context.engagementDataStore.data.map { preferences ->
        val storedDay = preferences[EngagementKeys.dayKey] ?: today
        if (storedDay != today) {
            EngagementProgress(
                dayKey = today,
                missionMask = 0,
                totalStars = preferences[EngagementKeys.totalStars] ?: 0,
                completedDays = preferences[EngagementKeys.completedDays] ?: 0,
            )
        } else {
            EngagementProgress(
                dayKey = storedDay,
                missionMask = preferences[EngagementKeys.missionMask] ?: 0,
                totalStars = preferences[EngagementKeys.totalStars] ?: 0,
                completedDays = preferences[EngagementKeys.completedDays] ?: 0,
            )
        }
    }

    suspend fun completeMission(index: Int) {
        require(index in 0..2)
        context.engagementDataStore.edit { preferences ->
            val storedDay = preferences[EngagementKeys.dayKey] ?: today
            var mask = if (storedDay == today) preferences[EngagementKeys.missionMask] ?: 0 else 0
            val bit = 1 shl index
            if (mask and bit != 0) return@edit

            mask = mask or bit
            val nowAllDone = (0..2).all { mask and (1 shl it) != 0 }
            preferences[EngagementKeys.dayKey] = today
            preferences[EngagementKeys.missionMask] = mask
            preferences[EngagementKeys.totalStars] = (preferences[EngagementKeys.totalStars] ?: 0) + 1
            if (nowAllDone) {
                preferences[EngagementKeys.completedDays] = (preferences[EngagementKeys.completedDays] ?: 0) + 1
            }
        }
    }
}
