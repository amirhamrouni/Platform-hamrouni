package nl.leersprong.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

enum class LearnerTab { Home, Learn, Buddy, Progress, Profile }

@Composable
fun LearnerBottomBar(
    selected: LearnerTab,
    onSelect: (LearnerTab) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == LearnerTab.Home,
            onClick = { onSelect(LearnerTab.Home) },
            icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
            label = { Text("Home") },
        )
        NavigationBarItem(
            selected = selected == LearnerTab.Learn,
            onClick = { onSelect(LearnerTab.Learn) },
            icon = { Icon(Icons.Rounded.AutoStories, contentDescription = null) },
            label = { Text("Leren") },
        )
        NavigationBarItem(
            selected = selected == LearnerTab.Buddy,
            onClick = { onSelect(LearnerTab.Buddy) },
            icon = { Icon(Icons.Rounded.SmartToy, contentDescription = null) },
            label = { Text("Maatje") },
        )
        NavigationBarItem(
            selected = selected == LearnerTab.Progress,
            onClick = { onSelect(LearnerTab.Progress) },
            icon = { Icon(Icons.Rounded.Leaderboard, contentDescription = null) },
            label = { Text("Voortgang") },
        )
        NavigationBarItem(
            selected = selected == LearnerTab.Profile,
            onClick = { onSelect(LearnerTab.Profile) },
            icon = { Icon(Icons.Rounded.AccountCircle, contentDescription = null) },
            label = { Text("Profiel") },
        )
    }
}
