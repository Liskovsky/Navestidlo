package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.dlnauka.navestidlo.ui.classes.TestScreenState

@Composable
fun TestQuestionView(
    state: TestScreenState,
    onAnswerSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Hlavní Box, který obaluje všechny komponenty
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        state.currentQuestion?.let { question ->

            // Vykreslení události na návěstidle, pokud existuje
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Levá část - návěstidlo
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(0.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    key(state.currentQuestion) {

                        // Vykreslení návěstidla pro aktuální otázku.
                        AllHeadSignal(
                            isKvizScreen = false,
                            isAllTab120Visible = question.isAllTab120Visible,
                            isAllHeadSignalVisible = question.isAllHeadSignalVisible,
                            isShowLinesVisible = question.isShowLinesVisible,
                            signalColors = question.signalColors,
                            isAllSpeedLinesVisible = question.isAllSpeedLinesVisible,
                            speedLinesColors = question.speedLinesColors,
                            isAllTabNumberVisible = question.isAllTabNumberVisible,
                            allTabNumberText = question.allTabNumberText,
                            isAllTabPictureVisible = question.isAllTabPictureVisible,
                            allTabPictureText = question.allTabPictureText,
                            isAllShiftBoxVisible = question.isAllShiftBoxVisible,
                            shiftColors = question.shiftColors,
                            onSignalColorsChange = {},
                            onAllTabNumberTextChange = {},
                            onAllTabPictureTextChange = {},
                            onSpeedLinesColorsChange = {},
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(1.dp),
                            scale = 0.6f
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Pravá část - tlačítka pro odpovědi
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(horizontal = 0.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Získání unikátních odpovědí z možností
                    val uniqueAnswers = state.answerOptions.distinct()

                    uniqueAnswers.forEach { answer ->

                        // Určujeme barvu pozadí na základě výběru odpovědi
                        val backgroundColor = when {
                            state.showAnswers && answer == question.correctAnswer -> Color.Green
                            state.showAnswers && answer == state.selectedAnswer -> Color.Red
                            else -> Color.Black
                        }
                        // Barva textu je nastavena na základě barvy pozadí
                        val textColor = if (backgroundColor == Color.Green || backgroundColor == Color.Red) Color.Black else Color.White

                        // Vytvoření tlačítka pro každou odpověď
                        Button(
                            onClick = { onAnswerSelected(answer) },
                            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = Color.White),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 70.dp)
                                .padding(horizontal = 0.dp)
                                .border(1.dp, Color.White, shape = MaterialTheme.shapes.small)
                        ) {
                            Text(
                                text = answer.replace("km/h", "km\u00A0/\u00A0h"),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = textColor,
                                overflow = TextOverflow.Clip,
                                softWrap = true
                            )
                        }
                    }
                }
            }
        }
    }
}
