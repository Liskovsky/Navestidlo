package eu.dlnauka.navestidlo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import eu.dlnauka.navestidlo.R

// Definice pro kompozici zobrazení pozadí s obrázkem
@Composable
fun BackgroundImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.navestidla2),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}
// Náhled kompozice BackgroundImage
@Preview
@Composable
fun PreviewBackgroundImage() {
    BackgroundImage()
}
