package eu.dlnauka.navestidlo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import eu.dlnauka.navestidlo.ui.classes.KvizViewModel
import eu.dlnauka.navestidlo.ui.classes.KvizScreenState
import eu.dlnauka.navestidlo.R
import eu.dlnauka.navestidlo.ui.utils.localizedString

// Definice kompozice pro zobrazení ovládacích tlačítek kvízu
@Composable
fun KvizControlButtons(

    // ViewModel obsahující stav aplikace
    viewModel: KvizViewModel,

    // Aktuální stav obrazovky kvízu
    kvizScreenStates: MutableState<KvizScreenState>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        // Tlačítko pro přepnutí viditelnosti "Indikátor 120"
        AllCustomButton(
            text = localizedString(R.string.indikator_120),
            onClick = {
                val newValue = !kvizScreenStates.value.isAllTab120Visible
                kvizScreenStates.value = kvizScreenStates.value.copy(isAllTab120Visible = newValue)
                viewModel.isAllTab120Visible.value = newValue
            },
            containerColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier.weight(1f)
        )
        // Tlačítko pro přepínání mezi indikátory
        AllCustomButton(
            text = localizedString(R.string.indikatory),
            onClick = {
                var newState = kvizScreenStates.value
                if (newState.isAllTabPictureVisible) {
                    newState = newState.resetAllTabPicture()
                    viewModel.isAllTabPictureVisible.value = false
                }
                newState = newState.showNextAllTabNumber()
                kvizScreenStates.value = newState
                viewModel.isAllTabNumberVisible.value = newState.isAllTabNumberVisible
                viewModel.isKvizSpeedLinesVisible.value = newState.isKvizSpeedLinesVisible
            },
            containerColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier.weight(1f)
        )
        // Tlačítko pro přepínání tabulek
        AllCustomButton(
            text = localizedString(R.string.tabulky),
            onClick = {
                var newState = kvizScreenStates.value
                if (newState.isAllTabNumberVisible) {
                    newState = newState.resetAllTabNumber()
                    viewModel.isAllTabNumberVisible.value = false
                }
                newState = newState.showNextAllTabPicture()
                kvizScreenStates.value = newState
                viewModel.isAllTabPictureVisible.value = newState.isAllTabPictureVisible
                viewModel.isKvizSpeedLinesVisible.value = newState.isKvizSpeedLinesVisible
            },
            containerColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier.weight(1f)
        )
    }
}
