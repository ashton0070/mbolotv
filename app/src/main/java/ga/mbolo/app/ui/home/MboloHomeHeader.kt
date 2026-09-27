package ga.mbolo.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ga.mbolo.app.ui.device.MboloUiMode
import ga.mbolo.app.ui.device.rememberMboloUiMode
import ga.mbolo.app.ui.theme.MboloGreen
import ga.mbolo.app.ui.theme.MboloGreenContainer
import ga.mbolo.app.ui.theme.MboloGreenDark
import ga.mbolo.app.ui.theme.MboloOnGreenContainer
import ga.mbolo.app.ui.theme.MboloShape
import ga.mbolo.app.ui.theme.MboloSpacing

/**
 * Header minimal de la page d'accueil Mbolo.
 *
 * Identité de la plateforme, accès à la recherche et emplacement réservé
 * au profil. Aucun compte réel, aucune notification : ces zones sont
 * purement compositionnelles.
 *
 * En contexte TV, logo, typographie et icônes sont agrandis pour rester
 * lisibles à plusieurs mètres.
 */
@Composable
fun MboloHomeHeader(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isTv = rememberMboloUiMode() == MboloUiMode.Tv
    val markSize = if (isTv) 44.dp else 34.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = MboloSpacing.screenMargin,
                vertical = MboloSpacing.sm,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MboloBrandMark(size = markSize)
        Spacer(Modifier.width(MboloSpacing.sm))
        Text(
            text = "Mbolo",
            style = if (isTv) {
                MaterialTheme.typography.headlineSmall
            } else {
                MaterialTheme.typography.titleLarge
            },
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.width(MboloSpacing.xs))
        Text(
            text = "TV",
            style = if (isTv) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.labelMedium
            },
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier.size(if (isTv) 64.dp else 48.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Rechercher",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(if (isTv) 28.dp else 24.dp),
            )
        }
        ProfilePlaceholder(size = markSize)
    }
}

@Composable
private fun MboloBrandMark(
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                brush = Brush.linearGradient(listOf(MboloGreen, MboloGreenDark)),
                shape = MboloShape.pill,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "M",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black,
            color = MboloOnGreenContainer,
            modifier = Modifier.clearAndSetSemantics { },
        )
    }
}

@Composable
private fun ProfilePlaceholder(
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(start = MboloSpacing.sm)
            .size(size)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh, MboloShape.pill)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MboloShape.pill)
            .semantics { contentDescription = "Profil — disponible prochainement" },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(if (size == 44.dp) 24.dp else 20.dp),
        )
    }
}
