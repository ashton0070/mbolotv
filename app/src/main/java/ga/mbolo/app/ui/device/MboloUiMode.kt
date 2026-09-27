package ga.mbolo.app.ui.device

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Contexte d'interface Mbolo.
 *
 * Déduit de la capacité réelle de l'appareil (feature Android TV
 * « leanback »), jamais d'une taille d'écran : une tablette ou un
 * téléphone en paysage reste [Mobile].
 */
enum class MboloUiMode {
    Mobile,
    Tv,
}

/**
 * Règle pure de détection, testable sans appareil :
 * seul le contexte Leanback (télécommande) bascule en [MboloUiMode.Tv].
 */
fun mboloUiModeFor(hasLeanbackFeature: Boolean): MboloUiMode =
    if (hasLeanbackFeature) MboloUiMode.Tv else MboloUiMode.Mobile

/** Mode d'interface courant, mémorisé pour le recomposition. */
@Composable
fun rememberMboloUiMode(context: Context = LocalContext.current): MboloUiMode =
    remember(context) {
        mboloUiModeFor(
            hasLeanbackFeature = context.packageManager
                .hasSystemFeature(PackageManager.FEATURE_LEANBACK),
        )
    }
