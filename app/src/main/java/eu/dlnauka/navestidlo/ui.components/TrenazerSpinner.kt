package eu.dlnauka.navestidlo.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.dlnauka.navestidlo.R
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.localization.LocalLocalizedContext
import eu.dlnauka.navestidlo.ui.utils.localizedString
import kotlinx.coroutines.launch

@Composable
fun TrenazerSpinner(

    // Modifikátor pro změnu vzhledu
    modifier: Modifier = Modifier,

    // Repozitář pro získání dat
    repository: NavestiRepository,

    // Callback pro vybrání položky
    onItemSelected: (String) -> Unit
) {
    // Stav pro ovládání zobrazení dropdown menu
    var expanded by remember { mutableStateOf(false) }

    // Stav vybrané položky - uchovává jen klíč, nikoliv přeložený text
    var selectedKey by remember { mutableStateOf<String?>(null) }

    // Seznam položek
    var items by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    // Coroutine scope pro asynchronní operace
    val coroutineScope = rememberCoroutineScope()

    // LaunchedEffect pro načítání položek při zobrazení composable
    val context = LocalLocalizedContext.current

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            items = try {
                repository.getNavestiList()
                    .map { Pair(it.first, it.second.replace("km/h", "km\u00A0/\u00A0h")) }
            } catch (e: Exception) {
                Log.e("TrenazerSpinner", "Chyba načítání", e)

                // Chyba při načítání dat
                listOf(Pair("0", context.getString(R.string.event_load_error)))
            }
        }
    }

    // Box pro obalování UI komponenty
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        // Dynamicky vybraný text – buď hodnota ze seznamu, nebo výchozí text
        val displayedText = selectedKey?.let { key ->
            items.find { it.first == key }?.second
        } ?: localizedString(R.string.dropdown_label)

        // Text zobrazený v rozbaleném spinneru
        Text(
            text = displayedText,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black)
                .clickable { expanded = true }
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 10.dp, vertical = 10.dp)
        )

        // Dropdown menu pro výběr položky
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 10.dp)
                .fillMaxWidth(0.95f)
        ) {
            // Pro každou položku v seznamu položek vytvořím item
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedKey = item.first
                            expanded = false
                            onItemSelected(item.first)
                        }
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = item.second,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black, // Upravena barva textu v seznamu
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth()
                    )
                }
                if (index < items.size - 1) {
                    HorizontalDivider(
                        thickness = 1.dp, // Přidání tenké linky mezi události
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
