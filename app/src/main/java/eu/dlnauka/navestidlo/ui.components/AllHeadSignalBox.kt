package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Definice kompozice pro zobrazení boxu s hlavním návěstidlem
@Composable
fun AllHeadSignalBox(
    modifier: Modifier = Modifier,
    signalColors: List<AllBlinkingColors>,
    isVisible: Boolean = true,
    showLines: Boolean = true,
    scale: Float = 1.0f
) {
    // Zobrazuje komponentu pouze, pokud je nastavena viditelnost
    if (isVisible) {
        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Hlavní sloupec s návěstidly
            Column(
                modifier = Modifier
                    .width((65 * scale).dp)
                    .background(Color.Black)
                    .padding((8 * scale).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height((8 * scale).dp))
                signalColors.forEach { color ->
                    BlinkingButton(blinkingColor = color, onClick = null, modifier = Modifier.size((50 * scale).dp))
                    Spacer(modifier = Modifier.height((8 * scale).dp))
                }
            }
            // Zobrazení pomocných pruhů, pokud je nastavena jejich viditelnost
            if (showLines) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = (2 * scale).dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val lineColors = listOf(
                        Color.Red,
                        Color.White,
                        Color.Red,
                        Color.White,
                        Color.Red
                    )
                    lineColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .width((15 * scale).dp)
                                .height(if (color == Color.White) (50 * scale).dp else (70 * scale).dp)
                                .background(color)
                        )
                    }
                }
            }
        }
    }
}
