package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TestCountSelect(onSelectionChanged: (Int) -> Unit) {

    // Možnosti pro výběr počtu otázek
    val options = listOf(10, 20, 30, 40, 50)

    // Uchovává se aktuálně vybraná možnost
    var selectedOption by remember { mutableIntStateOf(options.first()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))

        // Radio button pro každou možnost
        options.forEach { count ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                // RadioButton pro výběr počtu
                RadioButton(
                    selected = (count == selectedOption),
                    onClick = { selectedOption = count }
                )
                Text(
                    text = "$count",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tlačítko pro potvrzení výběru.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AllDescriptionButton(
                text = "Potvrdit",
                onClick = { onSelectionChanged(selectedOption) }
            )
        }
    }
}
