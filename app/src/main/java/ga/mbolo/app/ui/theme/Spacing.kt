package ga.mbolo.app.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Échelle d'espacement Mbolo.
 *
 * Tous les écrans doivent puiser dans cette échelle plutôt que
 * d'utiliser des valeurs arbitraires.
 */
object MboloSpacing {
    val none = 0.dp
    val xxs = 2.dp
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 24.dp
    val xxl = 32.dp
    val xxxl = 40.dp
    val huge = 48.dp
    val gigantic = 64.dp

    // Sémantique
    val screenMargin = 20.dp
    val screenMarginTv = 56.dp
    val cardGap = 12.dp
    val cardPadding = 16.dp
    val componentGap = 8.dp
    val sectionGap = 32.dp
    val sectionTitleGap = 12.dp
}

/** Marges d'écran standard, à appliquer sur le conteneur racine d'un écran. */
fun Modifier.mboloScreenPadding(
    horizontal: Dp = MboloSpacing.screenMargin,
    vertical: Dp = MboloSpacing.xl,
): Modifier = padding(horizontal = horizontal, vertical = vertical)
