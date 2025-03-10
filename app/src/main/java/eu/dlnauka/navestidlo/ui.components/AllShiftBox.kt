package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Definice kompozice pro zobrazení boxu s hlavním posunem
@Composable
fun AllShiftBox(
    modifier: Modifier = Modifier,
    shiftColors: List<AllBlinkingColors>,
    isVisible: Boolean = true,
    scale: Float = 1.0f
){
    // Zobrazuje komponentu pouze, pokud je nastavena viditelnost
    if (isVisible) {
        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Hlavní sloupec s nastavením pro posunové návěstidlo
            Column(
                modifier = Modifier
                    .width((65 * scale).dp)
                    .background(Color.Black)
                    .padding((8 * scale).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height((8 * scale).dp))
                shiftColors.forEach { color ->
                    BlinkingButton(
                        blinkingColor = color,
                        onClick = null,
                        modifier = Modifier.size((50 * scale).dp)
                    )
                    Spacer(modifier = Modifier.height((8 * scale).dp))
                }
            }
            // Vedlejší sloupec s pomocnými pruhy
            Column(
                modifier = Modifier.wrapContentSize()
                    .padding(start = (2 * scale).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val lineColors = listOf(Color(0xFF6200EE), Color.White)
                lineColors.forEach { color ->
                    Box(
                        modifier = Modifier.width((15 * scale).dp)
                            .height((64 * scale).dp)
                            .background(color)
                    )
                }
            }
        }
    }
}
