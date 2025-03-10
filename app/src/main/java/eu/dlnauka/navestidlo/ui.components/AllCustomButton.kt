package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definice kompozice pro vlastní tlačítko
@Composable
fun AllCustomButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    // Stav pro sledování, zda bylo tlačítko kliknuto
    val buttonClicked = remember { mutableStateOf(false) }

    // Vytvoření tlačítka s nastavenými barvami, velikostí a funkcí kliknutí
    Button(
        onClick = {

            // Při kliknutí na tlačítko se nastaví stav na "true" a zavolá se předaná funkce onClick
            buttonClicked.value = true
            onClick()
        },
        // Nastavení barev tlačítka
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        // Nastavení tvaru tlačítka
        shape = MaterialTheme.shapes.small,

        contentPadding = PaddingValues(4.dp),

        modifier = modifier
            .height(45.dp)
            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small)
    ) {
        // Text na tlačítku
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}
