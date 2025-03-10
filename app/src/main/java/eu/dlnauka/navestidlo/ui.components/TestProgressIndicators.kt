package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TestProgressIndicators(
    questionCount: Int,
    maxQuestionCount: Int,
    correctAnswers: Int
) {
    // Obalí obsah do Boxu s černým pozadím, aby vytvořil ztmavený efekt
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column {

            // Zobrazuje text pro počet otázek
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Počet otázek: $questionCount z $maxQuestionCount",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            // Zobrazení progress baru pro počet otázek.
            LinearProgressIndicator(
                progress = { questionCount.toFloat() / maxQuestionCount },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = Color.Green,
                trackColor = Color.Gray,
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Výpočet procentuální úspěšnosti a zobrazení výsledku.
            val percentagePerQuestion = 100f / maxQuestionCount
            val successRate = correctAnswers * percentagePerQuestion

            // Text pro zobrazení procentuální úspěšnosti
            Text(
                text = "Procentuální úspěšnost: ${successRate.toInt()}%",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            // Další progress bar zobrazuje průběžnou úspěšnost
            LinearProgressIndicator(
                progress = { successRate / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = Color.Green,
                trackColor = Color.Red,
            )
        }
    }
}
