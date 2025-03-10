package eu.dlnauka.navestidlo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.dlnauka.navestidlo.ui.classes.GoogleAdBox
import eu.dlnauka.navestidlo.ui.components.*
import eu.dlnauka.navestidlo.ui.classes.TestViewModel
import eu.dlnauka.navestidlo.ui.classes.TestViewModelFactory
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository

@Composable
fun Test(
    navController: NavController,
    repository: NavestiRepository,
    onExitApp: () -> Unit
) {
    // Inicializace ViewModelu pro obrazovku Test
    val viewModel: TestViewModel = viewModel(factory = TestViewModelFactory(repository))

    // Řízení zobrazení rozbalovacího menu
    var expanded by remember { mutableStateOf(false) }

    // Stav pro zobrazení popisu
    var showDescription by remember { mutableStateOf(false) }

    // Hlavní Box pro celé zobrazení obrazovky test
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Horní menu s možnostmi pro přechod mezi obrazovkami
            AllMenuBox(
                screenName = "T e s t",
                menuOptions = listOf(Destinations.KVIZ, Destinations.TRENAZER),
                onMenuOptionSelected = { selectedScreen -> navController.navigate(selectedScreen) },
                onExitApp = onExitApp,
                expanded = expanded,
                onExpandedChange = { expanded = it }
            )
            // Podmíněné zobrazení podle aktuálního stavu testu
            when {
                // Zobrazení modálního okna pro výběr počtu otázek
                viewModel.state.showQuestionSelect -> {
                    ModalDialog(
                        onDismiss = { showDescription = false },
                        content = {
                            AllDescriptionBox(
                                description = "Vyberte počet otázek pro test:",
                                onClose = { showDescription = false },
                                showCloseButton = false,
                                descriptionTextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                                descriptionAlignment = TextAlign.Center,
                                content = {
                                    TestCountSelect(onSelectionChanged = { count ->
                                        showDescription = false
                                        viewModel.setQuestionCount(count)
                                    })
                                }
                            )
                        }
                    )
                }
                // Zobrazení výsledků testu
                viewModel.state.showResults -> {
                    TestResultsView(
                        state = viewModel.state,
                        onRestart = {
                            viewModel.restartTest()
                            expanded = false
                        },
                        onMenuExpand = { expanded = true }
                    )
                }
                else -> {
                    // Zobrazení průběhu testu
                    Column {
                        // Blok pro zobrazení zadání
                        AllTextBlock(
                            assignment = "Co svítí na návěstidle?",
                            textColor = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.4f))
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            fontSize = 18,
                            backgroundColor = Color.Transparent
                        )
                        // Zobrazení aktuálního zadání
                        TestQuestionView(
                            state = viewModel.state,

                            // Zpracování vybrané odpovědi
                            onAnswerSelected = viewModel::selectAnswer,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                        // Zobrazení indikátorů průběhu testu
                        TestProgressIndicators(
                            questionCount = viewModel.state.questionCount,
                            maxQuestionCount = viewModel.state.maxQuestionCount,
                            correctAnswers = viewModel.state.correctAnswers
                        )
                        Spacer(modifier = Modifier.height(100.dp))

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
                }
            }
        }
    }
}
