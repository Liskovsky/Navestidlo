package eu.dlnauka.navestidlo

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import eu.dlnauka.navestidlo.ui.classes.TrenazerActivity
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestBlinkingPerformance {

    @Test
    fun testBlinkingActivity() {
        println("Spouštím test blikání aktivity TrenazerActivity")

        // Spuštění aktivity v testovacím prostředí
        val scenario = ActivityScenario.launch(TrenazerActivity::class.java)

        scenario.use { activityScenario ->
            activityScenario.onActivity { activity ->
                println("Aktivita byla spuštěna, začínáme test")

                // Zaznamenání času před začátkem testu
                val start = System.nanoTime()

                // Simulace blikání aktivity – 1000x
                repeat(1000) {
                    activity.recreate()
                }

                // Zaznamenání času po dokončení testu
                val end = System.nanoTime()
                val durationMs = (end - start) / 1_000_000

                println("Test dokončen: Celková doba rekreace = $durationMs ms")

                // Ověření, že reakce aktivity je dostatečně rychlá (méně než 500 ms)
                assertTrue("Reakce trvá příliš dlouho! ($durationMs ms)", durationMs < 500)
            }
        }
    }
}
