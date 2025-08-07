package eu.dlnauka.navestidlo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.dlnauka.navestidlo.R
import eu.dlnauka.navestidlo.ui.classes.GoogleAdBox
import eu.dlnauka.navestidlo.ui.components.*
import eu.dlnauka.navestidlo.ui.classes.TestViewModel
import eu.dlnauka.navestidlo.ui.classes.TestViewModelFactory
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.localization.LocalLocalizedContext
import eu.dlnauka.navestidlo.ui.utils.localizedString

@Composable
fun Test(
    navController: NavController,
    repository: NavestiRepository,
    onExitApp: () -> Unit
) {
    val viewModel: TestViewModel = viewModel(factory = TestViewModelFactory(repository))
    val context = LocalLocalizedContext.current

    var expanded by remember { mutableStateOf(false) }

    // Stav pro zobrazování modálního okna (automaticky true při startu)
    var showDescription by rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Horní menu
            AllMenuBox(
                screenName = localizedString(R.string.screen_test),
                menuItems = listOf(
                    MenuItem(Destinations.TRENAZER, R.string.trenazer_btn),
                    MenuItem(Destinations.KVIZ, R.string.kviz_btn)
                ),
                onMenuOptionSelected = { navController.navigate(it) },
                onExitApp = onExitApp,
                expanded = expanded,
                onExpandedChange = { expanded = it }
            )

            // Podmíněné UI řízení:
            when {
                // Je aktivní výběr počtu otázek
                showDescription || viewModel.state.showQuestionSelect -> {
                    ModalDialog(
                        onDismiss = { showDescription = false },
                        content = {
                            AllDescriptionBox(
                                description = localizedString(R.string.select_quest_count),
                                onClose = { showDescription = false },
                                showCloseButton = false,
                                closeButtonText = localizedString(R.string.button_close),
                                descriptionTextStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                                descriptionAlignment = TextAlign.Center,
                                content = {
                                    TestCountSelect(
                                        onSelectionChanged = { count ->
                                            showDescription = false
                                            viewModel.setQuestionCount(count)
                                        }
                                    )
                                }
                            )
                        }
                    )
                }

                // Test je dokončen → zobraz výsledky
                viewModel.state.showResults -> {
                    TestResultsView(
                        state = viewModel.state,
                        onRestart = {
                            viewModel.restartTest()
                            expanded = false
                            showDescription = true // Znovu vyvolat výběr otázek
                        },
                        onMenuExpand = { expanded = true }
                    )
                }

                // Probíhá samotný test → UI + otázky
                else -> {
                    Column {
                        AllTextBlock(
                            assignment = localizedString(R.string.quiz_promt),
                            textColor = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.4f))
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            fontSize = 18,
                            backgroundColor = Color.Transparent
                        )

                        // ✅ Bezpečné vykreslení otázky
                        if (viewModel.state.currentQuestion != null) {
                            TestQuestionView(
                                state = viewModel.state,
                                onAnswerSelected = viewModel::selectAnswer,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )

                            TestProgressIndicators(
                                questionCount = viewModel.state.questionCount,
                                maxQuestionCount = viewModel.state.maxQuestionCount,
                                correctAnswers = viewModel.state.correctAnswers
                            )
                        }

                        Spacer(modifier = Modifier.height(100.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        ) {
                            GoogleAdBox.AdBanner(context = context)
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}
