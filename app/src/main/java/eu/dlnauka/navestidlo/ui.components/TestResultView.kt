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
    val resultText = if (successRate >= 80) "✅ USPĚL ✅" else "❌ NEUSPĚL ❌"

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

    // Zprávy, které se zobrazí podle získané úspěšnosti
    val motivationalMessages = if (successRate >= 80) {
        listOf(
            "Výborně, z Tebe už je hotovej fíra!",
            "Ty už jezdíš hodně dlouho, co?",
            "Ty máš paměť jako slon. :-D"
        )
    } else {
        listOf(
            "Nevadí, příště to zvládneš!",
            "Hlavu vzhůru, zkus to znovu!",
            "Cvičení dělá mistra!",
            "Nepřestávej se učit a zlepšíš se.",
            "Každý neúspěch je krokem k úspěchu!"
        )
    }
    // Náhodně vyberu jednu zprávu
    val randomMessage = motivationalMessages.random()

    // Vytvoření modálního okna s výsledkem testu a motivačními zprávami
    ModalDialog(
        onDismiss = {},
        content = {
            AllDescriptionBox(
                description = resultText,
                onClose = {},
                showCloseButton = false,
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
                            text = "Hodnocení testu: ${"%.0f".format(successRate)}%",
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
                                text = "Znovu",
                                onClick = onRestart,
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            )
                            AllDescriptionButton(
                                text = "Menu",
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
