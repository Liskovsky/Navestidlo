package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definice kompozice pro zobrazení rychlostních tabulek
@Composable
fun AllTabNumber(
    modifier: Modifier = Modifier,
    allTabNumberText: String,
    isVisible: Boolean = true,
    onAllTabNumberTextChange: (String) -> Unit,
    scale: Float = 1.0f
) {
    // Zobrazuje komponentu pouze, pokud je nastavena viditelnost
    if (isVisible) {

        // Vytvoří box pro zobrazení textu s pozadím
        Box(
            modifier = modifier
                .size((80 * scale).dp)
                .background(Color.Black)
                .padding((6 * scale).dp)
        ) {
            Text(
                text = allTabNumberText,
                color = Color.White,
                fontSize = (55 * scale).sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxSize()
            )
        }
        // Volá funkci změny tabulky při změně textu
        onAllTabNumberTextChange(allTabNumberText)
    }
}
