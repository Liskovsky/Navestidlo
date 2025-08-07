package eu.dlnauka.navestidlo.ui.datastore

import eu.dlnauka.navestidlo.ui.components.AllBlinkingColors

// Definuje datovou třídu Event pro reprezentaci události
data class Event(
    val description: Map<String, String> = emptyMap(),
    val name: Map<String, String> = emptyMap(),
    val isAllTab120Visible: Boolean = false,
    val isAllHeadSignalVisible: Boolean = false,
    val isShowLinesVisible: Boolean = false,
    val signalColors: List<AllBlinkingColors> = emptyList(),
    val isAllSpeedLinesVisible: Boolean = false,
    val speedLinesColors: List<AllBlinkingColors> = emptyList(),
    val isAllTabNumberVisible: Boolean = false,
    val allTabNumberText: String = "",
    val isAllTabPictureVisible: Boolean = false,
    val allTabPictureText: String = "",
    val isAllShiftBoxVisible: Boolean = false,
    val shiftColors: List<AllBlinkingColors> = emptyList(),
    val correctAnswer: String? = null
)
