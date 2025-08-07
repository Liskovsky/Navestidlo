// Definuje balíček pro tuto třídu
package eu.dlnauka.navestidlo.ui.classes

// Import potřebných tříd a knihoven
import android.util.Log
import androidx.compose.ui.graphics.Color
import eu.dlnauka.navestidlo.ui.components.AllBlinkingColors
import androidx.core.graphics.toColorInt

object ColorParser {

    // Definice konstant pro dobu trvání blikání (v milisekundách)
    private const val BLINK_DURATION_SLOW = 1111
    private const val BLINK_DURATION_FAST = 600

    // Vytvořím mapu, kde klíčem je řetězec a hodnotou je instance z AllBlinkingColors.Blinking nebo AllBlinkingColors.SolidColor
    private val colorMap = mapOf(
        "blikWhite" to AllBlinkingColors.Blinking(Color.White, Color.DarkGray, BLINK_DURATION_SLOW),
        "blikBlue" to AllBlinkingColors.Blinking(Color.Blue, Color.DarkGray, BLINK_DURATION_SLOW),
        "fastOrange" to AllBlinkingColors.Blinking(
            Color(0xFFFFA500),
            Color.DarkGray,
            BLINK_DURATION_FAST
        ),
        "slowOrange" to AllBlinkingColors.Blinking(
            Color(0xFFFFA500),
            Color.DarkGray,
            BLINK_DURATION_SLOW
        ),
        "slowGreen" to AllBlinkingColors.Blinking(Color.Green, Color.DarkGray, BLINK_DURATION_SLOW),
        "fastGreen" to AllBlinkingColors.Blinking(Color.Green, Color.DarkGray, BLINK_DURATION_FAST),
        "red" to AllBlinkingColors.SolidColor(Color.Red),
        "blue" to AllBlinkingColors.SolidColor(Color.Blue),
        "orange" to AllBlinkingColors.SolidColor(Color(0xFFFFA500)),
        "white" to AllBlinkingColors.SolidColor(Color.White),
        "green" to AllBlinkingColors.SolidColor(Color.Green),
        "gray" to AllBlinkingColors.SolidColor(Color.DarkGray)
    )

    // Funkce parseBlinkColor převádí řetězec na odpovídající AllBlinkingColors objekt
    fun parseBlinkColor(colorString: String): AllBlinkingColors {

        // Pokus o nalezení barvy v mapě
        return colorMap[colorString] ?:

        // Pokud není nalezena, zpracuje řetězec podle jeho formátu
        try {
            if (colorString.contains(",")) {

                // Rozdělení řetězce na části podle čárky a vytvoří blikající barvu
                val parts = colorString.split(",").takeIf { it.size >= 2 }
                    ?: return AllBlinkingColors.SolidColor(Color.Gray)

                AllBlinkingColors.Blinking(
                    color1 = Color(parts[0].trim().toColorInt()),
                    color2 = Color(parts[1].trim().toColorInt()),
                    speed = parts.getOrNull(2)?.toIntOrNull() ?: 500
                )
            } else {

                // Pokud řetězec neobsahuje žádnou čárku, považuje se za jednoduchou barvu
                AllBlinkingColors.SolidColor(Color(colorString.toColorInt()))
            }
        } catch (e: Exception) {
            Log.e("ColorParser", "Chyba při parsování barvy: $colorString", e)
            return AllBlinkingColors.SolidColor(Color.Gray)
        }
    }
}
