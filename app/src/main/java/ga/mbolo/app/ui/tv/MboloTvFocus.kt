package ga.mbolo.app.ui.tv

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import ga.mbolo.app.ui.theme.MboloFocusRing

/**
 * Fondations du focus Mbolo TV.
 *
 * Un seul modificateur réutilisable pour tous les éléments réellement
 * interactifs d'un contexte [ga.mbolo.app.ui.device.MboloUiMode.Tv] :
 * - anneau [MboloFocusRing] de 3 dp, net et contrasté, visible à distance ;
 * - léger scale animé (120 ms), jamais un gros zoom ;
 * - [onSelect] : action réelle (clickable natif, la touche « OK » de la
 *   télécommande déclenche le clic) ;
 * - sans [onSelect] : l'élément reste focalisable pour la navigation
 *   D-pad sans promettre d'action (cartes de contenu en attente de fiche).
 *
 * En dehors de la TV, [enabled] vaut false et le modificateur est
 * transparent : le comportement tactile existant est inchangé.
 */
@Composable
fun Modifier.mboloTvFocusable(
    enabled: Boolean,
    shape: Shape,
    focusRequester: FocusRequester? = null,
    onSelect: (() -> Unit)? = null,
    scale: Float = 1.04f,
): Modifier {
    if (!enabled) {
        return if (focusRequester != null) this.focusRequester(focusRequester) else this
    }

    var isFocused by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(
        targetValue = if (isFocused) scale else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "mboloTvFocusScale",
    )

    return this
        .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
        .onFocusEvent { state -> isFocused = state.isFocused }
        .then(if (onSelect != null) Modifier.clickable(onClick = onSelect) else Modifier.focusable())
        .graphicsLayer {
            scaleX = animatedScale
            scaleY = animatedScale
        }
        .then(
            // Une bordure de largeur 0.dp dessinerait un trait résiduel
            // (hairline Skia) : on ne l'applique que quand le focus est là.
            if (isFocused) {
                Modifier.border(width = 3.dp, color = MboloFocusRing, shape = shape)
            } else {
                Modifier
            },
        )
}
