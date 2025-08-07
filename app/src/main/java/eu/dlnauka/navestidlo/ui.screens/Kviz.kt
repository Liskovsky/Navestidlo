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
import eu.dlnauka.navestidlo.R
import eu.dlnauka.navestidlo.ui.classes.KvizScreenState
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.classes.KvizViewModel
import eu.dlnauka.navestidlo.ui.classes.KvizViewModelFactory
import eu.dlnauka.navestidlo.ui.localization.LanguagePreferenceManager
import eu.dlnauka.navestidlo.ui.localization.LocalLocalizedContext
import eu.dlnauka.navestidlo.ui.utils.localized
import eu.dlnauka.navestidlo.ui.utils.localizedString
import kotlinx.coroutines.launch

@Composable
fun Kviz(
    navController: NavController,
    repository: NavestiRepository,
    onExitApp: () -> Unit
) {
    val context = LocalLocalizedContext.current
    val langCode by produceState(initialValue = "cs") {
        value = LanguagePreferenceManager.resolveAppLanguage(context)
    }

    val viewModel: KvizViewModel = viewModel(factory = KvizViewModelFactory(repository))
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadRandomEvent(context)
    }

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
            Box(modifier = Modifier.fillMaxWidth()) {
                AllMenuBox(
                    screenName = localizedString(R.string.screen_kviz),
                    menuItems = listOf(
                        MenuItem(Destinations.TRENAZER, R.string.trenazer_btn),
                        MenuItem(Destinations.TEST, R.string.test_btn)),
                    onMenuOptionSelected = { selectedScreen -> navController.navigate(selectedScreen) },
                    onExitApp = onExitApp,
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )

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
                        text = localizedString(R.string.button_reset),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }
            }

            KvizHeaderRow(
                loadRandomEvent = {
                    coroutineScope.launch {
                        viewModel.loadRandomEvent(context)
                    }
                },
                checkSettings = {
                    coroutineScope.launch {
                        val expectedEvent = repository.getEvent(viewModel.eventId.value)
                        viewModel.checkSettings(expectedEvent, context)
                    }
                }
            )

            // Tady lokalizujeme assignment (který by měl být String)
            AllTextBlock(
                assignment = viewModel.assignment.value.localized(langCode),
                textColor = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                fontSize = 15
            )

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

            KvizControlButtons(
                viewModel = viewModel,
                kvizScreenStates = visibilityStates
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (viewModel.isAllDescriptionBoxVisible.value) {
            ModalDialog(
                onDismiss = { viewModel.isAllDescriptionBoxVisible.value = false },
                content = {
                    AllDescriptionBox(
                        description = null,
                        onClose = { viewModel.isAllDescriptionBoxVisible.value = false },
                        showCloseButton = true,
                        closeButtonText = localizedString(R.string.button_close),
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

                                if (viewModel.resultCheck.value == localizedString(R.string.result_success)) {
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
