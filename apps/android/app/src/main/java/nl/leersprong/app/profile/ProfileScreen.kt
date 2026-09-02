package nl.leersprong.app.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val languages = listOf("Nederlands", "العربية", "Türkçe", "Polski", "Українська", "English", "Français")

@Composable
fun ProfileScreen(
    profile: LearnerProfile,
    firstRun: Boolean,
    onSave: (String, Int, String, Boolean) -> Unit,
    onDone: () -> Unit,
) {
    var name by remember { mutableStateOf(profile.name) }
    var group by remember { mutableIntStateOf(profile.group) }
    var language by remember { mutableStateOf(profile.homeLanguage) }
    var supportLanguage by remember { mutableStateOf(profile.supportLanguageEnabled) }
    var saved by remember { mutableStateOf(false) }

    LaunchedEffect(profile.completed, saved) {
        if (saved && profile.completed) onDone()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF4F7FC)).verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(if (firstRun) "WELKOM BIJ LEERSPRONG" else "MIJN PROFIEL", color = Color(0xFF607089), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
            Text(if (firstRun) "Maak je leerprofiel" else "Pas je leerprofiel aan", color = Color(0xFF062A70), fontWeight = FontWeight.Black, fontSize = 30.sp)
            Text("We gebruiken dit alleen om lessen, uitleg en niveau beter bij jou te laten passen.", color = Color(0xFF5F7188), lineHeight = 21.sp)
        }

        Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("Naam", fontWeight = FontWeight.Black)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it.take(40) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Voornaam") },
                    singleLine = true,
                )
            }
        }

        Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Groep", fontWeight = FontWeight.Black)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..8).forEach { value ->
                        FilterChip(
                            selected = group == value,
                            onClick = { group = value },
                            label = { Text("Groep $value") },
                        )
                    }
                }
            }
        }

        Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Thuistaal", fontWeight = FontWeight.Black)
                Text("Nederlands blijft de leertaal. Thuistaalhulp kan moeilijke uitleg extra verduidelijken.", color = Color(0xFF65758B), fontSize = 13.sp)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    languages.forEach { value ->
                        FilterChip(
                            selected = language == value,
                            onClick = { language = value },
                            label = { Text(value) },
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Thuistaalhulp", fontWeight = FontWeight.Bold)
                        Text("Extra steun bij moeilijke uitleg", color = Color(0xFF65758B), fontSize = 12.sp)
                    }
                    Switch(checked = supportLanguage, onCheckedChange = { supportLanguage = it })
                }
            }
        }

        Button(
            onClick = {
                saved = true
                onSave(name.trim(), group, language, supportLanguage)
            },
            enabled = name.trim().length >= 2,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (firstRun) "Maak mijn leerroute" else "Profiel opslaan", fontWeight = FontWeight.Black)
        }
    }
}
