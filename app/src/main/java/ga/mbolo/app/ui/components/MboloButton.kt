package ga.mbolo.app.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ga.mbolo.app.ui.theme.MboloShape
import ga.mbolo.app.ui.theme.MboloSpacing

/** Hauteur minimale garantie pour le toucher (tactile + télécommande TV). */
private val MboloTouchTarget = 48.dp

/**
 * Bouton principal Mbolo.
 *
 * Zone tactile d'au moins 48dp, couleurs issues du thème, prêt pour le focus TV.
 */
@Composable
fun MboloButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = MboloTouchTarget),
        enabled = enabled,
        shape = MboloShape.button,
        colors = ButtonDefaults.buttonColors(),
        contentPadding = PaddingValues(
            horizontal = MboloSpacing.xl,
            vertical = MboloSpacing.md,
        ),
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(Modifier.width(MboloSpacing.sm))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
        )
    }
}

/**
 * Bouton texte Mbolo : action secondaire, sans fond rempli.
 *
 * Zone tactile d'au moins 48dp.
 */
@Composable
fun MboloTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = MboloTouchTarget),
        enabled = enabled,
        shape = MboloShape.button,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        contentPadding = PaddingValues(
            horizontal = MboloSpacing.lg,
            vertical = MboloSpacing.sm,
        ),
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(Modifier.width(MboloSpacing.sm))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
        )
    }
}
