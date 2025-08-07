package eu.dlnauka.navestidlo.ui.localization

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "settings")
private val LANGUAGE_KEY = stringPreferencesKey("language")

object LanguagePreferenceManager {

    suspend fun saveLanguage(context: Context, lang: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = lang
        }
    }

    private suspend fun getSavedLanguage(context: Context): String? {
        return context.dataStore.data.first()[LANGUAGE_KEY]
    }

    suspend fun resolveAppLanguage(
        context: Context,
        supportedLanguages: List<String> = AppLanguage.entries.map { it.code }
    ): String {
        val saved = getSavedLanguage(context)
        if (saved != null && saved in supportedLanguages) return saved

        val systemLang = Locale.getDefault().language
        return if (systemLang in supportedLanguages) systemLang else "cs"
    }
}
