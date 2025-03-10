package eu.dlnauka.navestidlo.ui.classes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

object GoogleAdBox {

    // Definuje funkci AdBanner jako Composable, která zobrazuje reklamní banner
    @Composable
    fun AdBanner(context: Context) {

        // Inicializuje MobileAds sadu SDK
        MobileAds.initialize(context)

        // Používá importovaný AndroidView k zobrazení AdView komponenty
        AndroidView(
            factory = { ctx ->
                AdView(ctx).apply {

                    // Nastavení velikost reklamy na předdefinovaný banner
                    setAdSize(AdSize.BANNER)

                    // Nastavení ID reklamní jednotky
                    adUnitId = "ca-app-pub-9922878117886700/8183185303"

                    // Načte reklamu s použitím AdRequest
                    loadAd(AdRequest.Builder().build())
                }
            },
            update = { adView ->

                // Načte novou reklamu při aktualizaci zobrazení
                adView.loadAd(AdRequest.Builder().build())
            }
        )
    }
}
