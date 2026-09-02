package nl.leersprong.app.profile

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.learnerProfileDataStore by preferencesDataStore(name = "learner_profile")

private object ProfileKeys {
    val name = stringPreferencesKey("name")
    val group = intPreferencesKey("group")
    val homeLanguage = stringPreferencesKey("home_language")
    val supportLanguageEnabled = booleanPreferencesKey("support_language_enabled")
    val completed = booleanPreferencesKey("completed")
    val diagnosticCompleted = booleanPreferencesKey("diagnostic_completed")
}

data class LearnerProfile(
    val name: String = "",
    val group: Int = 4,
    val homeLanguage: String = "Nederlands",
    val supportLanguageEnabled: Boolean = false,
    val completed: Boolean = false,
    val diagnosticCompleted: Boolean = false,
)

class LearnerProfileRepository(private val context: Context) {
    val profile: Flow<LearnerProfile> = context.learnerProfileDataStore.data.map { preferences ->
        LearnerProfile(
            name = preferences[ProfileKeys.name].orEmpty(),
            group = (preferences[ProfileKeys.group] ?: 4).coerceIn(1, 8),
            homeLanguage = preferences[ProfileKeys.homeLanguage] ?: "Nederlands",
            supportLanguageEnabled = preferences[ProfileKeys.supportLanguageEnabled] ?: false,
            completed = preferences[ProfileKeys.completed] ?: false,
            diagnosticCompleted = preferences[ProfileKeys.diagnosticCompleted] ?: false,
        )
    }

    suspend fun save(profile: LearnerProfile) {
        context.learnerProfileDataStore.edit { preferences ->
            preferences[ProfileKeys.name] = profile.name.trim()
            preferences[ProfileKeys.group] = profile.group.coerceIn(1, 8)
            preferences[ProfileKeys.homeLanguage] = profile.homeLanguage
            preferences[ProfileKeys.supportLanguageEnabled] = profile.supportLanguageEnabled
            preferences[ProfileKeys.completed] = profile.name.isNotBlank()
            if (profile.group != (preferences[ProfileKeys.group] ?: profile.group)) {
                preferences[ProfileKeys.diagnosticCompleted] = false
            }
        }
    }

    suspend fun markDiagnosticCompleted() {
        context.learnerProfileDataStore.edit { preferences ->
            preferences[ProfileKeys.diagnosticCompleted] = true
        }
    }
}
