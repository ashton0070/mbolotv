package ga.mbolo.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import ga.mbolo.app.ui.theme.MboloShape
import ga.mbolo.app.ui.theme.MboloSpacing

/**
 * Surface générique Mbolo : base des conteneurs de l'application.
 *
 * Hérite des couleurs et formes du thème Material 3 Mbolo.
 */
@Composable
fun MboloSurface(
    modifier: Modifier = Modifier,
    shape: Shape = MboloShape.surface,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(MboloSpacing.cardPadding),
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
    ) {
        Column(modifier = Modifier.padding(contentPadding), content = content)
    }
}
