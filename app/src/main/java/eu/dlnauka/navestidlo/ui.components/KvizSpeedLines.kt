package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KvizSpeedLines(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    resetState: Boolean = false,
    onSpeedLinesColorsChange: (List<AllBlinkingColors>) -> Unit,
    resetTrigger: Int
) {
    // Vytvoříme seznam spektra barev pro tlačítka
    val buttonColorSpectrums = listOf(
        listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color(0xFFFFA500)),
            AllBlinkingColors.SolidColor(Color.Green)
        ),
        listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.Green)
        )
    )

    // Index označuje, která barva je zvolena
    val currentIndices = remember { mutableStateListOf(*Array(buttonColorSpectrums.size) { 0 }) }

    // Tento efekt se spustí při změně hodnoty "resetTrigger"
    LaunchedEffect(resetTrigger) {
        currentIndices.indices.forEach { currentIndices[it] = 0 }
        onSpeedLinesColorsChange(buttonColorSpectrums.mapIndexed { idx, colors -> colors[currentIndices[idx]] })
    }

    // Pokud je resetState == true, resetují se všechny indexy
    if (resetState) {
        currentIndices.indices.forEach { currentIndices[it] = 0 }
        onSpeedLinesColorsChange(buttonColorSpectrums.mapIndexed { idx, colors -> colors[currentIndices[idx]] })
    }

    // Pokud je true, vykreslí se celé UI.
    if (isVisible) {
        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Tlačítka, která budou mít nastavenu velikost a barvu podle výběru uživatele
            Column(
                modifier = Modifier
                    .width(82.dp)
                    .background(Color.Black)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Pro každé tlačítko vykreslíme tlačítko s odpovídající barvou, která se změní po kliknutí
                buttonColorSpectrums.forEachIndexed { index, colors ->
                    val currentColor = colors[currentIndices[index]]

                    BlinkingButton(
                        blinkingColor = currentColor,
                        onClick = {

                            currentIndices[index] = (currentIndices[index] + 1) % colors.size
                            onSpeedLinesColorsChange(buttonColorSpectrums.mapIndexed { idx, colorList ->
                                colorList[currentIndices[idx]]
                            })
                        },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                    )
                }
            }
        }
    }
}
