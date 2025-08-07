package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.dlnauka.navestidlo.ui.classes.TestScreenState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import eu.dlnauka.navestidlo.R
import eu.dlnauka.navestidlo.ui.localization.LocalLocalizedContext
import eu.dlnauka.navestidlo.ui.utils.localizedString

@Composable
fun TestResultsView(
    state: TestScreenState,
    onRestart: () -> Unit,
    onMenuExpand: () -> Unit
) {

    // Vypočítání procentuální úspěšnosti na základě správných odpovědí a celkového počtu otázek
    val successRate = if (state.questionCount > 0) {
        (state.correctAnswers.toFloat() / state.questionCount) * 100
    } else 0f

    // Výsledek testu na základě úspěšnosti
    val resultText = if (successRate >= 80) localizedString(R.string.result_success) else localizedString(R.string.result_failure)

    // Stav pro zobrazení výsledku testu
    val (showDescriptionBox, setShowDescriptionBox) = remember { mutableStateOf(true) }

    // Pokud je zobrazení povoleno, vykreslí se motivační zpráva
    if (showDescriptionBox) {
        MotivationalMessages(successRate, resultText, onRestart) {
            onMenuExpand()  // Otevře menu
            setShowDescriptionBox(false)  // Skryje popis
        }
    }
}
@Composable
fun MotivationalMessages(successRate: Float, resultText: String, onRestart: () -> Unit, onMenuExpand: () -> Unit) {

    val context = LocalLocalizedContext.current
    // Zprávy, které se zobrazí podle získané úspěšnosti
    val messages = if (successRate >= 80) {
        context.resources.getStringArray(R.array.success_messages)
    } else {
        context.resources.getStringArray(R.array.failed_messages)
    }

    // Náhodně vyberu jednu zprávu
    val randomMessage = messages.random()

    // Vytvoření modálního okna s výsledkem testu a motivačními zprávami
    ModalDialog(
        onDismiss = {},
        content = {
            AllDescriptionBox(
                description = resultText,
                onClose = {},
                showCloseButton = false,
                closeButtonText = localizedString(R.string.button_close),
                descriptionTextStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                descriptionAlignment = TextAlign.Center,
                content = {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        // Zobrazení procentuální úspěšnosti
                        Text(
                            text = localizedString(R.string.test_result, successRate.toInt()),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Zobrazení náhodně vybrané motivační zprávy
                        Text(
                            text = randomMessage,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        // Tlačítka pro restartování testu nebo návrat do menu
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            AllDescriptionButton(
                                text = localizedString(R.string.button_retry),
                                onClick = onRestart,
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            )
                            AllDescriptionButton(
                                text = localizedString(R.string.button_menu),
                                onClick = onMenuExpand,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            )
        }
    )
}
