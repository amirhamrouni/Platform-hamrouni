package nl.leersprong.app.diagnostic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DiagnosticRoute(
    learnerGroup: Int,
    onDone: () -> Unit,
    viewModel: DiagnosticViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(learnerGroup) { viewModel.start(learnerGroup) }
    DiagnosticScreen(
        state = state,
        group = learnerGroup,
        onAnswer = viewModel::answer,
        onSave = { viewModel.save(learnerGroup, onDone) },
    )
}

@Composable
private fun DiagnosticScreen(
    state: DiagnosticUiState,
    group: Int,
    onAnswer: (Int) -> Unit,
    onSave: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Slimme Niveautest", style = MaterialTheme.typography.headlineMedium)
        Text("Groep $group · Nederlands + Rekenen")
        Spacer(Modifier.height(20.dp))

        val result = state.result
        if (result != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Startprofiel klaar", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Nederlands")
                        Text("${result.dutchPercent}%")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Rekenen")
                        Text("${result.mathPercent}%")
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("LeerSprong gebruikt dit alleen als startpunt. Elke oefening verfijnt je niveau verder.")
                }
            }
            Spacer(Modifier.height(20.dp))
            Button(onClick = onSave, enabled = !state.saving, modifier = Modifier.fillMaxWidth()) {
                Text(if (state.saving) "Opslaan…" else "Start mijn leerpad")
            }
            return@Column
        }

        val question = state.questions.getOrNull(state.index)
        if (question == null) {
            Text("Niveautest wordt voorbereid…")
            return@Column
        }

        val progress = (state.index + 1).toFloat() / state.questions.size.coerceAtLeast(1)
        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        Text("Vraag ${state.index + 1} van ${state.questions.size}", style = MaterialTheme.typography.labelLarge)
        Text(
            if (question.domain == DiagnosticDomain.Nederlands) "Nederlands" else "Rekenen",
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(10.dp))
        Text(question.prompt, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        question.options.forEachIndexed { index, option ->
            OutlinedButton(
                onClick = { onAnswer(index) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            ) {
                Text(option)
            }
        }
    }
}
