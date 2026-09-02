package nl.leersprong.app.engagement

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brush
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.Redo
import androidx.compose.material.icons.rounded.Undo
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class ArtStroke(val points: List<Offset>, val color: Color)

@Composable
fun ArtLabScreen(learnerGroup: Int, onBack: () -> Unit) {
    val strokes = remember { mutableStateListOf<ArtStroke>() }
    val redoStack = remember { mutableStateListOf<ArtStroke>() }
    var activePoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var selectedColor by remember { mutableStateOf(Color(0xFF174EA6)) }
    val prompt = if (learnerGroup <= 4) {
        "Teken een plek waar jij graag speelt. Voeg minstens drie verschillende vormen toe."
    } else {
        "Ontwerp een slimme, duurzame stad van de toekomst. Laat vervoer, natuur en gebouwen zien."
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F4FB))) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF542C7E)).padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            OutlinedButton(onClick = onBack) { Text("← Speelplein", color = Color.White) }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Rounded.Brush, contentDescription = null, tint = Color(0xFFFFD24A), modifier = Modifier.size(34.dp))
                Column {
                    Text("Kunstlab", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                    Text("Teken, probeer, wis en maak opnieuw.", color = Color(0xFFEADDF6))
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1C9)),
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text("Creatieve missie", fontWeight = FontWeight.Black)
                Text(prompt, color = Color(0xFF655128))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf(
                Color(0xFF174EA6),
                Color(0xFFD93025),
                Color(0xFF188038),
                Color(0xFFF9AB00),
                Color(0xFF7B1FA2),
                Color(0xFF1F1F1F),
            ).forEach { color ->
                Box(
                    modifier = Modifier.size(if (selectedColor == color) 42.dp else 34.dp)
                        .background(color, CircleShape)
                        .clickable { selectedColor = color },
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(22.dp),
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize().pointerInput(selectedColor) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            activePoints = listOf(offset)
                            redoStack.clear()
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            activePoints = activePoints + change.position
                        },
                        onDragEnd = {
                            if (activePoints.size > 1) strokes.add(ArtStroke(activePoints, selectedColor))
                            activePoints = emptyList()
                        },
                        onDragCancel = { activePoints = emptyList() },
                    )
                },
            ) {
                fun drawStroke(stroke: ArtStroke) {
                    stroke.points.zipWithNext().forEach { (start, end) ->
                        drawLine(
                            color = stroke.color,
                            start = start,
                            end = end,
                            strokeWidth = 10f,
                            cap = StrokeCap.Round,
                        )
                    }
                }
                strokes.forEach(::drawStroke)
                if (activePoints.size > 1) drawStroke(ArtStroke(activePoints, selectedColor))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedButton(
                enabled = strokes.isNotEmpty(),
                onClick = {
                    if (strokes.isNotEmpty()) {
                        redoStack.add(strokes.removeAt(strokes.lastIndex))
                    }
                },
                modifier = Modifier.weight(1f),
            ) {
                Icon(Icons.Rounded.Undo, contentDescription = null)
                Text(" Undo")
            }
            OutlinedButton(
                enabled = redoStack.isNotEmpty(),
                onClick = {
                    if (redoStack.isNotEmpty()) strokes.add(redoStack.removeAt(redoStack.lastIndex))
                },
                modifier = Modifier.weight(1f),
            ) {
                Icon(Icons.Rounded.Redo, contentDescription = null)
                Text(" Redo")
            }
            Button(
                onClick = {
                    strokes.clear()
                    redoStack.clear()
                },
                modifier = Modifier.weight(1f),
            ) {
                Icon(Icons.Rounded.DeleteSweep, contentDescription = null)
                Text(" Wis")
            }
        }
    }
}
