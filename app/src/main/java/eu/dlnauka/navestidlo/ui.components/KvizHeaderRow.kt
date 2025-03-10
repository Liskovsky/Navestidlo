package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.font.FontWeight

// Deklarace composable funkce pro vytvoření hlavičky kvízu
@Composable
fun KvizHeaderRow(

    // Funkce pro načtení náhodné události
    loadRandomEvent: () -> Unit,

    // Funkce pro kontrolu nastavení
    checkSettings: () -> Unit
) {
    // Hlavní řádek, který obsahuje text a dvě tlačítka
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.4f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Nastav na návěstidle:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            // Tlačítko pro načtení náhodné události
            AllCustomButton(
                text = "Další",
                onClick = loadRandomEvent,
                containerColor = Color.Black,
                contentColor = Color.White,
                modifier = Modifier.width(90.dp)
            )
            // Tlačítko pro kontrolu nastavení
            AllCustomButton(
                text = "Kontrola",
                onClick = checkSettings,
                containerColor = Color.Black,
                contentColor = Color(0xFFFFA500),
                modifier = Modifier.width(120.dp)
            )
        }
    }
}
