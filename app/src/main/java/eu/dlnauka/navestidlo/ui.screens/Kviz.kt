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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.dlnauka.navestidlo.ui.components.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.dlnauka.navestidlo.ui.classes.KvizScreenState
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.classes.KvizViewModel
import eu.dlnauka.navestidlo.ui.classes.KvizViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun Kviz(
    navController: NavController,
    repository: NavestiRepository,
    onExitApp: () -> Unit
) {
    // Vytváření ViewModel pro správu stavu kvízu
    val viewModel: KvizViewModel = viewModel(factory = KvizViewModelFactory(repository))
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    // Na začátku se načte náhodná událost pro kvíz
    LaunchedEffect(Unit) {
        viewModel.loadRandomEvent()
    }
    // Uložení aktuálního stavu obrazovky pro pozdější použití
    val visibilityStates = remember {
        mutableStateOf(
            KvizScreenState(
                isAllTab120Visible = viewModel.isAllTab120Visible.value,
                isAllTabNumberVisible = viewModel.isAllTabNumberVisible.value,
                isAllTabPictureVisible = viewModel.isAllTabPictureVisible.value,
                isKvizHeadSignalBoxVisible = viewModel.isKvizHeadSignalBoxVisible.value,
                isKvizSpeedLinesVisible = viewModel.isKvizSpeedLinesVisible.value,
                speedLinesColors = viewModel.speedLinesColors.value,
                signalColors = viewModel.signalColors.value
            )
        )
    }
    // Při každé změně resetTriggeru se obnoví viditelnost komponent
    LaunchedEffect(viewModel.resetTrigger.intValue) {
        visibilityStates.value = KvizScreenState(
            isAllTab120Visible = viewModel.isAllTab120Visible.value,
            isAllTabNumberVisible = viewModel.isAllTabNumberVisible.value,
            isAllTabPictureVisible = viewModel.isAllTabPictureVisible.value,
            isKvizHeadSignalBoxVisible = viewModel.isKvizHeadSignalBoxVisible.value,
            isKvizSpeedLinesVisible = viewModel.isKvizSpeedLinesVisible.value,
            speedLinesColors = viewModel.speedLinesColors.value,
            signalColors = viewModel.signalColors.value
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {

        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Horní menu s názvem kvízu a možnostmi pro přechod na jiné obrazovky
            Box(modifier = Modifier.fillMaxWidth()) {
                AllMenuBox(
                    screenName = "K v í z",
                    menuOptions = listOf(Destinations.TRENAZER, Destinations.TEST),
                    onMenuOptionSelected = { selectedScreen -> navController.navigate(selectedScreen) },
                    onExitApp = onExitApp,
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )
                // Tlačítko pro resetování nastavení návěstidla
                Button(
                    onClick = { viewModel.resetNavestidlo() },
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
                        text = "Reset",
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }
            }
            // Hlavní řádek kvízu, který zobrazuje text náhodné událost a kontroluje nastavení
            KvizHeaderRow(
                loadRandomEvent = {
                    coroutineScope.launch {

                        // Načtení nové události
                        viewModel.loadRandomEvent()
                    }
                },
                checkSettings = {
                    coroutineScope.launch {

                        // Načtení očekávané události
                        val expectedEvent = repository.getEvent(viewModel.eventId.value)

                        // Kontrola nastavení kvízu
                        viewModel.checkSettings(expectedEvent)
                    }
                }
            )
            // Zobrazení zadání pro uživatele
            AllTextBlock(
                assignment = viewModel.assignment.value,
                textColor = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                fontSize = 15)

            // Dynamické komponenty kvízu, které se mění podle aktuálních stavů - tedy nastavování dle uživatele
            KvizDynamicComponents(
                kvizScreenStates = visibilityStates.value,
                isKvizScreen = true,
                onSignalColorsChange = { colors ->
                    viewModel.signalColors.value = colors
                },
                onSpeedLinesColorsChange = { colors ->
                    viewModel.speedLinesColors.value = colors
                },
                onAllTabNumberTextChange = { viewModel.onAllTabNumberText.value = it },
                onAllTabPictureTextChange = { viewModel.onAllTabPictureText.value = it },
                resetTrigger = viewModel.resetTrigger.intValue
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Ovládací tlačítka pro kvíz
            KvizControlButtons(
                viewModel = viewModel,
                kvizScreenStates = visibilityStates
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        // Modal dialog pro zobrazení výsledků kvízu
        if (viewModel.isAllDescriptionBoxVisible.value) {
            ModalDialog(
                onDismiss = { viewModel.isAllDescriptionBoxVisible.value = false },
                content = {
                    AllDescriptionBox(
                        description = null,
                        onClose = { viewModel.isAllDescriptionBoxVisible.value = false },
                        showCloseButton = true,
                        descriptionTextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        descriptionAlignment = TextAlign.Center,
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = viewModel.resultCheck.value ?: "",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.fillMaxWidth(0.8f)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // Zobrazení zprávy v závislosti na výsledku kvízu
                                if (viewModel.resultCheck.value == "✅ USPĚL ✅") {
                                    Text(
                                        text = viewModel.fineMessage.value ?: "",
                                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else {
                                    Text(
                                        text = viewModel.errorMessage.value ?: "",
                                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    )
                }
            )
        }
    }
}
