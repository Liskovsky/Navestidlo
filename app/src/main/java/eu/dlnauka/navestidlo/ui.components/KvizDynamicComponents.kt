package eu.dlnauka.navestidlo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import eu.dlnauka.navestidlo.ui.classes.KvizScreenState

// Definice kompozice pro dynamické zobrazení prvků kvízu
@Composable
fun KvizDynamicComponents(
    kvizScreenStates: KvizScreenState,

    // Určuje, zda se jedná o kvízovou obrazovku, pro použití interaktivních prvků
    isKvizScreen: Boolean,

    // Callbacky pro změnu barev signálů a tabulek
    onSignalColorsChange: (List<AllBlinkingColors>) -> Unit,
    onAllTabNumberTextChange: (String) -> Unit,
    onAllTabPictureTextChange: (String) -> Unit,
    onSpeedLinesColorsChange: (List<AllBlinkingColors>) -> Unit,

    // Trigger pro resetování barev
    resetTrigger: Int
) {
    // Automatická inicializace barev při změně resetTriggeru
    LaunchedEffect(resetTrigger) {
        onSpeedLinesColorsChange(
            listOf(
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray)
            )
        )
        onSignalColorsChange(
            listOf(
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray)
            )
        )
    }
    // Zobrazení Indikátoru 120, pokud je aktivní
    if (kvizScreenStates.isAllTab120Visible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            AllTab120()
        }
    }
    // Zobrazení interaktivního návěstidla
    if (kvizScreenStates.isKvizHeadSignalBoxVisible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isKvizScreen) {
                KvizHeadSignalBox(
                    modifier = Modifier,
                    isVisible = true,
                    showLines = false,
                    onSignalColorsChange = onSignalColorsChange,
                    resetTrigger = resetTrigger
                )
            } else {
                AllHeadSignalBox(
                    modifier = Modifier,
                    signalColors = listOf(),
                    isVisible = true,
                    showLines = true
                )
            }
        }
    }
    // Dynamické přepínání mezi různými tabulkami a rychlostními pruhy
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            kvizScreenStates.isAllTabNumberVisible -> {
                if (kvizScreenStates.currentAllTabNumberIndex in 0 until
                    kvizScreenStates.numbers.size
                ) {
                    AllTabNumber(
                        modifier = Modifier,
                        allTabNumberText =
                        kvizScreenStates.numbers[kvizScreenStates.currentAllTabNumberIndex],
                        onAllTabNumberTextChange = onAllTabNumberTextChange
                    )
                }
            }
            kvizScreenStates.isAllTabPictureVisible -> {
                if (kvizScreenStates.currentAllTabPictureIndex in 0 until
                    kvizScreenStates.pictures.size
                ) {
                    AllTabPicture(
                        modifier = Modifier,
                        allTabPictureText =
                        kvizScreenStates.pictures[kvizScreenStates.currentAllTabPictureIndex],
                        onAllTabPictureTextChange = onAllTabPictureTextChange
                    )
                }
            }
            else -> {
                if (isKvizScreen) {
                    KvizSpeedLines(
                        modifier = Modifier,
                        isVisible = true,
                        resetState = !kvizScreenStates.isKvizSpeedLinesVisible,
                        onSpeedLinesColorsChange = onSpeedLinesColorsChange,
                        resetTrigger = resetTrigger
                    )
                } else {
                    AllSpeedLines(
                        modifier = Modifier,
                        speedLinesColors = kvizScreenStates.speedLinesColors
                    )
                }
            }
        }
    }
}
