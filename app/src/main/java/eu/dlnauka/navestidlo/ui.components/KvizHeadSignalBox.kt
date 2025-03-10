package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KvizHeadSignalBox(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    showLines: Boolean = true,
    onSignalColorsChange: (List<AllBlinkingColors>) -> Unit,
    scale: Float = 1.0f,
    resetTrigger: Int
) {
    // Vytvářím seznam spektra barev pro tlačítka
    val buttonColorSpectrums = listOf(
        listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color(0xFFFFA500)),
            AllBlinkingColors.Blinking(Color(0xFFFFA500), Color.DarkGray, 1111),
            AllBlinkingColors.Blinking(Color(0xFFFFA500), Color.DarkGray, 600)
        ),
        listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.Green),
            AllBlinkingColors.Blinking(Color.Green, Color.DarkGray, 1111),
            AllBlinkingColors.Blinking(Color.Green, Color.DarkGray, 600)
        ),
        listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.Red)
        ),
        listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.White),
            AllBlinkingColors.Blinking(Color.White, Color.DarkGray, 1111),
            AllBlinkingColors.SolidColor(Color.Blue),
            AllBlinkingColors.Blinking(Color.Blue, Color.DarkGray, 1111)
        ),
        listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color(0xFFFFA500))
        )
    )
    // Index označuje, která barva je zvolena
    val currentIndices = remember { mutableStateListOf(*Array(buttonColorSpectrums.size) { 0 }) }

    // Hodnota pro klíče animací, které mohou být použity pro obnovení animace.
    val animationKeys = remember { mutableStateListOf(*Array(buttonColorSpectrums.size) { 0 }) }

    // Tento efekt se spustí při změně hodnoty "resetTrigger"
    LaunchedEffect(resetTrigger) {
        currentIndices.indices.forEach { index ->
            currentIndices[index] = 0
            animationKeys[index]++
        }

        onSignalColorsChange(buttonColorSpectrums.map { it[0] })
    }
    // Pokud je isVisible == true, vykreslí se celé UI.
    if (isVisible) {

        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .width((65 * scale).dp)
                    .background(Color.Black)
                    .padding((8 * scale).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height((8 * scale).dp))

                // Pro každé tlačítko vykreslíme animaci
                buttonColorSpectrums.forEachIndexed { index, colors ->
                    val currentColor = colors[currentIndices[index]]

                    BlinkingButton(
                        blinkingColor = currentColor,
                        onClick = {

                            // Při kliknutí na tlačítko se změní index v seznamu barev
                            currentIndices[index] = (currentIndices[index] + 1) % colors.size
                            animationKeys[index]++
                            onSignalColorsChange(buttonColorSpectrums.mapIndexed { idx, colorList ->
                                colorList[currentIndices[idx]]
                            })
                        },
                        modifier = Modifier.size((50 * scale).dp),
                        animationKey = animationKeys[index]
                    )
                    Spacer(modifier = Modifier.height((8 * scale).dp))
                }
            }
            // Pokud je nastavena hodnota showLines na true, vykreslí se pomocné signální pruhy.
            if (showLines) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = (2 * scale).dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Definice barev pruhů, které jsou střídavě červené a bílé.
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
