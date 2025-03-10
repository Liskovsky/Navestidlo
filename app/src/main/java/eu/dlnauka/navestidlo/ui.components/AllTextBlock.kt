package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definice kompozice pro zobrazení textového bloku
@Composable
fun AllTextBlock(
    assignment: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color(0xFFFFD700),
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center,
    maxLines: Int = 2
) {
    // Opraví formátování textu, důležité při zalamování textu
    val fixedAssignment = assignment.replace("km/h", "km\u00A0/\u00A0h")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            text = fixedAssignment,
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = MaterialTheme.shapes.small)
                .padding(horizontal = 6.dp, vertical = 2.dp),
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}
