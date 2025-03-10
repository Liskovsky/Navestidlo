package eu.dlnauka.navestidlo.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.dlnauka.navestidlo.ui.classes.GoogleAdBox
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.datastore.Event
import eu.dlnauka.navestidlo.ui.components.*

@Composable
fun Trenazer(
    navController: NavController,
    repository: NavestiRepository,
    onExitApp: () -> Unit
) {
    val scrollState = rememberScrollState()

    // Výchozí událost pro trenazer (s návěstidlem nastaveno na "Stůj")
    val defaultEvent = Event(
        description = "Návěst Stůj zakazuje strojvedoucímu jízdu vlaku. " +
                "Čelo jedoucího vlaku musí zastavit 10 m před hlavním návěstidlem. " +
                "Tam, kde hlavní návěstidlo není přímo u koleje, musí čelo vlaku zastavit před návěstidlem s návěstí Konec vlakové cesty. " +
                "Vzdáleností 10 m před hlavním návěstidlem je stanoveno obvyklé místo zastavení. " +
                "Strojvedoucí může zastavit co nejblíže před hlavním návěstidlem v případě, že je to nutné vzhledem k délce vlaku nebo na pokyn výpravčího.",
                name = "Stůj",
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
            AllBlinkingColors.SolidColor(Color.DarkGray)
        )
    )
    // Uchovávání vybrané události a jejího ID
    var selectedEvent by remember { mutableStateOf(defaultEvent) }
    var selectedEventId by remember { mutableStateOf("") }

    // Stav pro zobrazení popisu vybrané události
    var showDescription by remember { mutableStateOf(false) }

    // Stav pro rozbalení menu
    var expanded by remember { mutableStateOf(false) }

    // Načítání nové události při změně ID - výběru nové události
    LaunchedEffect(selectedEventId) {
        if (selectedEventId.isNotEmpty()) {

            // Načítání události podle ID
            val newEvent = repository.getEvent(selectedEventId)
            if (newEvent != null) {
                selectedEvent = newEvent
            }
        }
    }
    // Hlavní Box pro celé zobrazení obrazovky
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Horní menu s možnostmi pro přechod mezi obrazovkami
            AllMenuBox(
                screenName = "T r e n a ž e r",
                menuOptions = listOf(Destinations.KVIZ, Destinations.TEST),
                onMenuOptionSelected = { selectedScreen -> navController.navigate(selectedScreen) },
                onExitApp = onExitApp,
                expanded = expanded,
                onExpandedChange = { expanded = it }
            )
            Spacer(modifier = Modifier.height(1.dp))

            // Zobrazení vybrané události na návěstidle
            key(selectedEvent) {
                AllHeadSignal(
                    isKvizScreen = false,
                    isAllTab120Visible = selectedEvent.isAllTab120Visible,
                    isAllHeadSignalVisible = selectedEvent.isAllHeadSignalVisible,
                    isShowLinesVisible = selectedEvent.isShowLinesVisible,
                    signalColors = selectedEvent.signalColors,
                    isAllSpeedLinesVisible = selectedEvent.isAllSpeedLinesVisible,
                    speedLinesColors = selectedEvent.speedLinesColors,
                    isAllTabNumberVisible = selectedEvent.isAllTabNumberVisible,
                    allTabNumberText = selectedEvent.allTabNumberText,
                    isAllTabPictureVisible = selectedEvent.isAllTabPictureVisible,
                    allTabPictureText = selectedEvent.allTabPictureText,
                    isAllShiftBoxVisible = selectedEvent.isAllShiftBoxVisible,
                    shiftColors = selectedEvent.shiftColors,
                    onSignalColorsChange = {},
                    onAllTabNumberTextChange = {},
                    onAllTabPictureTextChange = {},
                    onSpeedLinesColorsChange = {}
                )
            }
            // Mezera s podmínkou pro případné zobrazení posunového boxu, který je menší
            Spacer(
                modifier = Modifier.height(
                    if (selectedEvent.isAllShiftBoxVisible) 245.dp else 10.dp
                )
            )
            // Výběr události pomocí rozevíracího seznamu
            TrenazerSpinner(
                repository = repository,
                onItemSelected = { selectedId ->
                    selectedEventId = selectedId
                }
            )
            Spacer(modifier = Modifier.height(50.dp))

            // Reklamní banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                GoogleAdBox.AdBanner(context = LocalContext.current)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        // Zobrazení popisu události v modálním okně
        if (showDescription) {
            ModalDialog(
                onDismiss = { showDescription = false },
                content = {
                    AllDescriptionBox(
                        description = null,
                        onClose = { showDescription = false },
                        showCloseButton = true,
                        descriptionTextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        descriptionAlignment = TextAlign.Center,
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = selectedEvent.description,
                                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    )
                }
            )
        } else {
            // Tlačítko pro zobrazení popisu
            Button(
                onClick = { showDescription = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
            ) {
                Text(
                    text = "Popis",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
        }
    }
}
