package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import eu.dlnauka.navestidlo.R

// Definice kompozice pro zobrazení popisného okna
@Composable
fun AllDescriptionBox(
    description: String?,
    onClose: () -> Unit,
    showCloseButton: Boolean,
    closeButtonText: String,
    descriptionTextStyle: TextStyle,
    descriptionAlignment: TextAlign,
    content: @Composable () -> Unit
) {
    // Vytvoří se stav pro posouvání obsahu, pro případ obsáhlého textu
    val scrollState = rememberScrollState()

    // Vytvoření hlavního kontejneru pro popisné okno
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        // Vytvoření vnitřního kontejneru
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Black)
                .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
        ) {
            // Uspořádá obsah v sloupci s vertikálním posouváním
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Zobrazí popis boxu, pokud není prázdný
                if (!description.isNullOrEmpty()) {
                    Text(
                        text = description,
                        style = descriptionTextStyle.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = descriptionAlignment
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(bottom = 8.dp)
                    )
                }
                // Zobrazí obsah předaný do funkce
                content()
                Spacer(modifier = Modifier.height(8.dp))
                if (showCloseButton) {
                    AllDescriptionButton(
                        text = closeButtonText,
                        onClick = onClose
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
// Definice kompozice pro zobrazení modálního dialogu
@Composable
fun ModalDialog(content: @Composable () -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
