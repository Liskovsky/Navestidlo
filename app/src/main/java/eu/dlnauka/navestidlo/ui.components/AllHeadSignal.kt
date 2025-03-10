package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Definice kompozice pro zobrazení všech komponent návěstidla
@Composable
fun AllHeadSignal(
    isKvizScreen: Boolean,
    modifier: Modifier = Modifier,
    isAllTab120Visible: Boolean,
    isAllHeadSignalVisible: Boolean,
    isShowLinesVisible: Boolean,
    signalColors: List<AllBlinkingColors>,
    isAllSpeedLinesVisible: Boolean,
    speedLinesColors: List<AllBlinkingColors>,
    isAllTabNumberVisible: Boolean,
    allTabNumberText: String,
    isAllTabPictureVisible: Boolean,
    allTabPictureText: String,
    isAllShiftBoxVisible: Boolean,
    shiftColors: List<AllBlinkingColors>,
    onSignalColorsChange: (List<AllBlinkingColors>) -> Unit,
    onAllTabPictureTextChange: (String) -> Unit,
    onAllTabNumberTextChange: (String) -> Unit,
    onSpeedLinesColorsChange: (List<AllBlinkingColors>) -> Unit,
    scale: Float = 1.0f
) {
    // Hlavní sloupec obsahující jednotlivé komponenty celého návěstidla
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding((16 * scale).dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy((8 * scale).dp)
    ) {
        // Podmíněné zobrazení komponenty AllTab120
        if (isAllTab120Visible) {
            AllTab120(
                modifier = Modifier,
                scale = scale)
        }
        // Podmíněné zobrazení komponenty AllHeadSignalBox nebo KvizHeadSignalBox, podle aktuální obrazovky
        if (isAllHeadSignalVisible) {
            if (isKvizScreen) {
                KvizHeadSignalBox(
                    isVisible = true,
                    showLines = isShowLinesVisible,
                    onSignalColorsChange = onSignalColorsChange,
                    resetTrigger = 1,
                    modifier = Modifier,
                    scale = scale
                )
            } else {
                AllHeadSignalBox(
                    signalColors = signalColors,
                    isVisible = true,
                    showLines = isShowLinesVisible,
                    scale = scale,
                    modifier = Modifier

                )
            }
        }
        // Podmíněné zobrazení komponenty AllSpeedLines nebo KvizSpeedLines, podle aktuální obrazovky
        if (isAllSpeedLinesVisible) {
            if (isKvizScreen) {
                KvizSpeedLines(
                    isVisible = true,
                    resetState = false,
                    onSpeedLinesColorsChange = onSpeedLinesColorsChange,
                    resetTrigger = 1,
                    modifier = Modifier
                )
            } else {
                AllSpeedLines(
                    speedLinesColors = speedLinesColors,
                    modifier = Modifier,
                    scale = scale)
            }
        }
        // Podmíněné zobrazení komponenty AllTabNumber
        if (isAllTabNumberVisible) {
            AllTabNumber(
                allTabNumberText = allTabNumberText,
                onAllTabNumberTextChange = onAllTabNumberTextChange,
                modifier = Modifier,
                scale = scale)
        }
        // Podmíněné zobrazení komponenty AllTabPicture
        if (isAllTabPictureVisible) {
            AllTabPicture(
                allTabPictureText = allTabPictureText,
                onAllTabPictureTextChange = onAllTabPictureTextChange,
                modifier = Modifier,
                scale = scale)
        }
        // Podmíněné zobrazení komponenty AllShiftBox
        if (isAllShiftBoxVisible) {
            AllShiftBox(
                shiftColors = shiftColors,
                modifier = Modifier,
                scale = scale)
        }
    }
}
// Funkce pro náhled komponenty AllHeadSignal
@Preview
@Composable
fun PreviewAllHeadSignal() {
    AllHeadSignal(
        isKvizScreen = false,
        isAllTab120Visible = false,
        isAllHeadSignalVisible = true,
        isShowLinesVisible = true,
        signalColors = listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.Red),
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray),
        ),
        isAllSpeedLinesVisible = true,
        speedLinesColors = listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray)
        ),
        isAllTabNumberVisible = false,
        allTabNumberText = "",
        isAllTabPictureVisible = false,
        allTabPictureText = "",
        isAllShiftBoxVisible = false,
        shiftColors = listOf(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray),
        ),
        onSignalColorsChange = {},
        onAllTabNumberTextChange = {},
        onAllTabPictureTextChange = {},
        onSpeedLinesColorsChange = {},
        scale = 1.0f
    )
}
