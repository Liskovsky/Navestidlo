package eu.dlnauka.navestidlo

import android.app.Application
import android.content.Context
import eu.dlnauka.navestidlo.ui.localization.wrapWithLocale

class MyApp : Application() {
    override fun attachBaseContext(base: Context) {
        val prefs = base.getSharedPreferences("settings", MODE_PRIVATE)
        val lang = prefs.getString("language", "cs") ?: "cs"
        super.attachBaseContext(base.wrapWithLocale(lang))
    }
}