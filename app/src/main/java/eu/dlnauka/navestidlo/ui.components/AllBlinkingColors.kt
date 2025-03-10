package eu.dlnauka.navestidlo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

// Definuje vyhrazenou třídu pro barvy, které mohou být buď jednobarevné nebo blikající
sealed class AllBlinkingColors {
    data class SolidColor(val color: Color, val description: String? = null) : AllBlinkingColors()
    data class Blinking(val color1: Color, val color2: Color, val speed: Int, val description: String? = null) : AllBlinkingColors()
}
// Definice kompozice pro blikající tlačítka
@Composable
fun BlinkingButton(
    modifier: Modifier = Modifier,
    blinkingColor: AllBlinkingColors,
    onClick: (() -> Unit)? = null,
    shape: Shape = CircleShape,
    animationKey: Any = Unit
) {
    val color = when (blinkingColor) {
        is AllBlinkingColors.SolidColor -> blinkingColor.color
        is AllBlinkingColors.Blinking -> {

            // Vytváření animace pro blikající barvy
            val transition = remember(animationKey) { Animatable(1f) }
            LaunchedEffect(transition) {
                transition.animateTo(
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = blinkingColor.speed,
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }
            // Přepíná se mezi dvěma barvami podle hodnoty animace
            if (transition.value > 0.5f) blinkingColor.color1 else blinkingColor.color2
        }
    }
    // Definice vzhledu a chování samotného tlačítka
    Button(
        onClick = onClick ?: {},
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = shape,
        modifier = modifier
    ) {
        Text(text = "")
    }
}
