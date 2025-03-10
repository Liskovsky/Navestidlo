package eu.dlnauka.navestidlo

import eu.dlnauka.navestidlo.ui.classes.ColorParser
import eu.dlnauka.navestidlo.ui.components.AllBlinkingColors
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ColorParserTest {

    @Test
    fun testParseSolidColor() {
        println("Začátek testu: testParseSolidColor")

        // Pokus o parsing zadané barvy "red"
        println("Parsing  barvy red...")
        val color = ColorParser.parseBlinkColor("red")
        println("Výstup metody parseBlinkColor: $color")

        // Ověření, že výsledkem je instance AllBlinkingColors.SolidColor
        println("Ověřuji, že výsledkem je AllBlinkingColors.SolidColor...")
        assertTrue(color is AllBlinkingColors.SolidColor)
        println("Ověření úspěšné: Výstup je AllBlinkingColors.SolidColor.")

        // Potvrzení dokončení testu
        println("Test testParseSolidColor úspěšně dokončen.")
    }

    @Test
    fun testParseBlinkingColor() {
        println("Začátek testu: testParseBlinkingColor")

        // Pokus o parsing blikající barvy
        println("Parsing blikWhite...")
        val color = ColorParser.parseBlinkColor("blikWhite")
        println("Výstup metody parseBlinkColor: $color")

        // Ověření, že výsledkem je instance AllBlinkingColors.Blinking
        println("Ověřuji, že výsledkem je AllBlinkingColors.Blinking...")
        assertTrue(color is AllBlinkingColors.Blinking)
        println("Ověření úspěšné: Výstup je AllBlinkingColors.Blinking.")

        // Potvrzení dokončení testu
        println("Test testParseBlinkingColor úspěšně dokončen.")
    }
}
