package eu.dlnauka.navestidlo.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.dlnauka.navestidlo.R
import eu.dlnauka.navestidlo.ui.utils.localizedString

data class MenuItem(
    val route: String,
    @StringRes val labelRes: Int
)

// Definice kompozice pro zobrazení menu boxu
@Composable
fun AllMenuBox(
    screenName: String,
    menuItems: List<MenuItem>,
    onMenuOptionSelected: (String) -> Unit,
    onExitApp: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // Hlavní sloupec obsahující menu box
    Column(modifier = modifier.fillMaxWidth()) {

        // Horní řádek s tlačítkem pro rozbalení menu a názvem aktuální obrazovky
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onExpandedChange(!expanded) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
            ) {
                Text(
                    text = if (expanded) "<<" else " ≡ ",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            }
            Text(
                text = screenName,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        }

        // Dropdown menu obsahující možnosti celého menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .background(Color.Black)
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onExpandedChange(false)
                        onMenuOptionSelected(item.route)
                    },
                    text = {
                        Text(
                            text = localizedString(item.labelRes),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(Color.Black, shape = MaterialTheme.shapes.medium)
                        .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.White.copy(alpha = 0.3f)
            )
            DropdownMenuItem(
                onClick = {
                    onExpandedChange(false)
                    onExitApp()
                },
                text = {
                    Text(
                        text = localizedString(R.string.close_btn),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .background(Color.Red, shape = MaterialTheme.shapes.medium)
                    .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
            )
            DropdownMenuItem(
                text = { LanguageSelector() },
                onClick = {},
                enabled = false
            )
        }
    }
}
