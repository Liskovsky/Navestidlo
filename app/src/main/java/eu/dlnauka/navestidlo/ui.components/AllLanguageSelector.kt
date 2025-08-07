package eu.dlnauka.navestidlo.ui.components

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import eu.dlnauka.navestidlo.ui.localization.AppLanguage
import eu.dlnauka.navestidlo.ui.localization.LanguagePreferenceManager
import kotlinx.coroutines.launch

@Composable
fun LanguageSelector(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppLanguage.entries.forEach { lang ->
            Button(
                onClick = {
                    scope.launch {
                        LanguagePreferenceManager.saveLanguage(context, lang.code)
                        val activity = context as? Activity
                        activity?.finish()
                        activity?.startActivity(activity.intent)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(text = lang.emoji, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
