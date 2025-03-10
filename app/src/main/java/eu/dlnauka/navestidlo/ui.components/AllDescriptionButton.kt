package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Definice kompozice pro vytvoření vlastního tlačítka
@Composable
fun AllDescriptionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Tlačítko s vlastními styly
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .width(120.dp)
            .height(50.dp)
            .padding(2.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 3.dp, vertical = 3.dp)
        )
    }
}
