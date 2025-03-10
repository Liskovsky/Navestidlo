package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Definice kompozice pro zobrazení tabulky rychlostních pruhů
@Composable
fun AllSpeedLines(
    modifier: Modifier = Modifier,
    speedLinesColors: List<AllBlinkingColors>,
    isVisible: Boolean = true,
    scale: Float = 1.0f
) {
    // Zobrazuje komponentu pouze, pokud je nastavena viditelnost
    if (isVisible) {

        // Vytvoří řádek pro rychlostní pruhy
        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Vytvoří sloupec pro jednotlivé rychlostní pruhy
            Column(
                modifier = Modifier
                    .width((82 * scale).dp)
                    .background(Color.Black)
                    .padding((8 * scale).dp),
                verticalArrangement = Arrangement.spacedBy((8 * scale).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                speedLinesColors.forEach { color ->
                    BlinkingButton(
                        blinkingColor = color,
                        onClick = null,
                        shape = RoundedCornerShape((4 * scale).dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((15 * scale).dp)
                    )
                }
            }
        }
    }
}
