package eu.dlnauka.navestidlo.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Definice kompozice pro zobrazení tabulky s obrázkem
@Composable
fun AllTabPicture(
    modifier: Modifier = Modifier,
    allTabPictureText: String,
    isVisible: Boolean = true,
    onAllTabPictureTextChange: (String) -> Unit,
    scale: Float = 1.0f
) {
    // Zobrazuje komponentu pouze, pokud je nastavena viditelnost
    if (isVisible) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .background(Color.Black)
        ) {
            // Získá ID zdroje obrázku podle jeho názvu
            val imageResourceId = getDrawableResIdByName(allTabPictureText)
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .size((90 * scale).dp)
                    .padding((4 * scale).dp),
                contentScale = ContentScale.Fit
            )
        }
        // Volá funkci změny tabulky při změně textu
        onAllTabPictureTextChange(allTabPictureText)
    }
}
// Kompozitní funkce pro získání ID zdroje obrázku podle jména
@SuppressLint("DiscouragedApi")
@Composable
fun getDrawableResIdByName(name: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}
