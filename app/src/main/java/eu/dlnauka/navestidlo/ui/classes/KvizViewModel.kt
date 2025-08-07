package eu.dlnauka.navestidlo.ui.classes

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import eu.dlnauka.navestidlo.R
import eu.dlnauka.navestidlo.ui.components.AllBlinkingColors
import eu.dlnauka.navestidlo.ui.datastore.Event
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository

class KvizViewModel(
    private val repository: NavestiRepository
) : ViewModel() {

    val isKvizHeadSignalBoxVisible = mutableStateOf(true)
    private val isAllShiftBoxVisible = mutableStateOf(false)
    private val shiftColors: MutableState<List<AllBlinkingColors>> =
        mutableStateOf(
            List(2) { AllBlinkingColors.SolidColor(Color.DarkGray) }
        )
    val speedLinesColors: MutableState<List<AllBlinkingColors>> =
        mutableStateOf(
            List(2) { AllBlinkingColors.SolidColor(Color.DarkGray) }
        )
    val isKvizSpeedLinesVisible = mutableStateOf(true)
    val signalColors: MutableState<List<AllBlinkingColors>> =
        mutableStateOf(
            List(5) { AllBlinkingColors.SolidColor(Color.DarkGray) }
        )
    private val isShowLinesVisible = mutableStateOf(false)
    val isAllTab120Visible = mutableStateOf(false)
    val onAllTabNumberText = mutableStateOf("")
    val isAllTabNumberVisible = mutableStateOf(false)
    val onAllTabPictureText = mutableStateOf("")
    val isAllTabPictureVisible = mutableStateOf(false)
    val fineMessage = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)
    val resultCheck = mutableStateOf<String?>(null)
    val assignment = mutableStateOf<Map<String, String>>(emptyMap())
    val eventId = mutableStateOf("")
    val isAllDescriptionBoxVisible = mutableStateOf(false)
    val resetTrigger = mutableIntStateOf(0)

    suspend fun loadRandomEvent(context: Context) {
        resetNavestidlo()
        val res = context.resources
        try {
            var randomEventDocument = repository.getRandomEventDocument()
            var attempts = 5
            while (randomEventDocument != null && attempts-- > 0) {
                val randomEvent = repository.getEvent(randomEventDocument.id)
                if (randomEvent != null &&
                    !randomEventDocument.id.equals("PosunDovolenPN", true) &&
                    !randomEventDocument.id.equals("PosunZakazanPN", true) &&
                    !randomEventDocument.id.equals("PosunDovolenHLN", true)
                ) {
                    eventId.value = randomEventDocument.id
                    assignment.value = randomEvent.name
                    return
                }
                randomEventDocument = repository.getRandomEventDocument()
            }
            assignment.value = mapOf(
                "cs" to res.getString(R.string.event_not_found),
                "de" to res.getString(R.string.event_not_found)
            )
        } catch (e: Exception) {
            Log.e("KvizViewModel", "Chyba při načítání eventu", e)
            assignment.value = mapOf(
                "cs" to res.getString(R.string.event_load_error),
                "de" to res.getString(R.string.event_load_error)
            )
        }
    }

    fun resetNavestidlo() {
        val newState = KvizScreenState(
            isAllTab120Visible = false,
            isAllTabNumberVisible = false,
            isAllTabPictureVisible = false,
            isKvizHeadSignalBoxVisible = true,
            isKvizSpeedLinesVisible = true,
            speedLinesColors = List(2) { AllBlinkingColors.SolidColor(Color.DarkGray) },
            signalColors = List(5) { AllBlinkingColors.SolidColor(Color.DarkGray) }
        )
        isKvizHeadSignalBoxVisible.value = newState.isKvizHeadSignalBoxVisible
        isAllShiftBoxVisible.value = false
        speedLinesColors.value = newState.speedLinesColors
        signalColors.value = newState.signalColors
        isShowLinesVisible.value = false
        isKvizSpeedLinesVisible.value = newState.isKvizSpeedLinesVisible
        isAllTab120Visible.value = newState.isAllTab120Visible
        onAllTabNumberText.value = ""
        isAllTabNumberVisible.value = newState.isAllTabNumberVisible
        onAllTabPictureText.value = ""
        isAllTabPictureVisible.value = newState.isAllTabPictureVisible
        resetTrigger.intValue += 1
    }

    fun checkSettings(expectedEvent: Event?, context: Context) {
        val res = context.resources
        if (expectedEvent == null) {
            resultCheck.value = res.getString(R.string.event_not_found)
            return
        }

        val userEvent = createUserEvent()
        val errors = mutableListOf<String>()

        fun msg(@androidx.annotation.StringRes id: Int) = res.getString(id)

        fun checkVisibilityAndCompare(
            isExpectedVisible: Boolean?,
            isUserVisible: Boolean,
            expectedValue: Any,
            userValue: Any,
            messageId: Int
        ) {
            if (isExpectedVisible == true) {
                if (expectedValue is List<*> && userValue is List<*>) {
                    val expected = expectedValue.map { it as? AllBlinkingColors }
                    val actual = userValue.map { it as? AllBlinkingColors }
                    if (expected != actual) errors.add(msg(messageId))
                } else if (expectedValue != userValue) {
                    errors.add(msg(messageId))
                }
            } else if (isExpectedVisible != isUserVisible) {
                errors.add(msg(messageId))
            }
        }

        checkVisibilityAndCompare(expectedEvent.isAllHeadSignalVisible, userEvent.isAllHeadSignalVisible, expectedEvent.signalColors, userEvent.signalColors, R.string.error_head_signal)
        checkVisibilityAndCompare(expectedEvent.isAllSpeedLinesVisible, userEvent.isAllSpeedLinesVisible, expectedEvent.speedLinesColors, userEvent.speedLinesColors, R.string.error_speed_lines)
        checkVisibilityAndCompare(expectedEvent.isAllTabNumberVisible, userEvent.isAllTabNumberVisible, expectedEvent.allTabNumberText, userEvent.allTabNumberText, R.string.error_number_indicator)
        checkVisibilityAndCompare(expectedEvent.isAllTabPictureVisible, userEvent.isAllTabPictureVisible, expectedEvent.allTabPictureText, userEvent.allTabPictureText, R.string.error_picture_tab)
        checkVisibilityAndCompare(expectedEvent.isAllShiftBoxVisible, userEvent.isAllShiftBoxVisible, expectedEvent.shiftColors, userEvent.shiftColors, R.string.error_shift_box)
        checkVisibilityAndCompare(expectedEvent.isAllTab120Visible, userEvent.isAllTab120Visible, expectedEvent.isAllTab120Visible, userEvent.isAllTab120Visible, R.string.error_indicator_120)

        if (errors.isEmpty()) {
            val messages = res.getStringArray(R.array.success_messages)
            resultCheck.value = res.getString(R.string.result_success)
            fineMessage.value = messages.random()
        } else {
            resultCheck.value = res.getString(R.string.result_failure)
            errorMessage.value = errors.joinToString("\n")
        }

        isAllDescriptionBoxVisible.value = true
    }

    private fun createUserEvent(): Event {
        return Event(
            description = mapOf("cs" to ""),
            name = assignment.value,
            isAllTab120Visible = isAllTab120Visible.value,
            isAllHeadSignalVisible = isKvizHeadSignalBoxVisible.value,
            isShowLinesVisible = isShowLinesVisible.value,
            signalColors = signalColors.value,
            isAllSpeedLinesVisible = isKvizSpeedLinesVisible.value,
            speedLinesColors = speedLinesColors.value,
            isAllTabNumberVisible = isAllTabNumberVisible.value,
            allTabNumberText = onAllTabNumberText.value,
            isAllTabPictureVisible = isAllTabPictureVisible.value,
            allTabPictureText = onAllTabPictureText.value,
            isAllShiftBoxVisible = isAllShiftBoxVisible.value,
            shiftColors = shiftColors.value
        )
    }
}
