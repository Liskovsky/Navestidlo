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

// Definice kompozice pro zobrazení čísla tabulky indikátoru
@Composable
fun AllTab120(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    scale: Float = 1.0f) {

    // Zobrazuje komponentu pouze, pokud je nastavena viditelnost
    if (isVisible) {

        // Vytvoří box pro zobrazení čísla s pozadím
        Box(
            modifier = modifier
                .wrapContentSize()
                .background(Color.Black)
                .padding((6 * scale).dp)
        ) {
            Text(
                text = "12",
                color = Color(0xFFFFD700),
                fontSize = (55 * scale).sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
