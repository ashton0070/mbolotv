package ga.mbolo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ga.mbolo.app.ui.navigation.MboloApp
import ga.mbolo.app.ui.theme.MboloTheme

/**
 * Point d'entrée unique de l'application native Mbolo.
 *
 * Aucune WebView, aucun contenu web : uniquement des composants Jetpack Compose.
 * L'activité reste minimale : elle délègue entièrement l'interface à [MboloApp].
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MboloTheme {
                MboloApp()
            }
        }
    }
}
