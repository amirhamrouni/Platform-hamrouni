package nl.leersprong.app.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LearnerProfileRepository(application)

    val profile: StateFlow<LearnerProfile?> = repository.profile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    fun save(
        name: String,
        group: Int,
        homeLanguage: String,
        supportLanguageEnabled: Boolean,
    ) {
        viewModelScope.launch {
            repository.save(
                LearnerProfile(
                    name = name,
                    group = group,
                    homeLanguage = homeLanguage,
                    supportLanguageEnabled = supportLanguageEnabled,
                    completed = name.isNotBlank(),
                ),
            )
        }
    }
}
