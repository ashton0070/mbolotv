package ga.mbolo.app.ui.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ga.mbolo.app.ui.device.MboloUiMode
import ga.mbolo.app.ui.device.rememberMboloUiMode
import ga.mbolo.app.ui.navigation.MboloDestination
import ga.mbolo.app.ui.theme.MboloShape
import ga.mbolo.app.ui.theme.MboloSpacing
import ga.mbolo.app.ui.tv.mboloTvFocusable

/**
 * Shell global de Mbolo TV : accueille la navigation principale.
 *
 * - TV (contexte Leanback) : rail latéral dédié à la télécommande ;
 * - Compact (téléphone) : barre de navigation inférieure ;
 * - Medium / Expanded (tablette) : rail latéral Material.
 */
@Composable
fun MboloAppShell(
    currentDestination: MboloDestination?,
    onDestinationSelected: (MboloDestination) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val isTv = rememberMboloUiMode() == MboloUiMode.Tv
    val layout = currentMboloShellLayout()

    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        if (isTv) {
            MboloTvNavigationRail(
                currentDestination = currentDestination,
                onDestinationSelected = onDestinationSelected,
            )
        } else if (layout != MboloShellLayout.Compact) {
            MboloNavigationRail(
                currentDestination = currentDestination,
                onDestinationSelected = onDestinationSelected,
            )
        }
        Scaffold(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                if (!isTv && layout == MboloShellLayout.Compact) {
                    MboloBottomBar(
                        currentDestination = currentDestination,
                        onDestinationSelected = onDestinationSelected,
                    )
                }
            },
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

enum class MboloShellLayout {
    Compact,
    Medium,
    Expanded,
}

@Composable
internal fun currentMboloShellLayout(): MboloShellLayout {
    val density = LocalDensity.current
    val widthDp = with(density) { LocalWindowInfo.current.containerSize.width.toDp() }
    return when {
        widthDp < 600.dp -> MboloShellLayout.Compact
        widthDp < 840.dp -> MboloShellLayout.Medium
        else -> MboloShellLayout.Expanded
    }
}

@Composable
private fun MboloBottomBar(
    currentDestination: MboloDestination?,
    onDestinationSelected: (MboloDestination) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        MboloDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination,
                onClick = { onDestinationSelected(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label,
                        modifier = Modifier.size(MboloSpacing.xl),
                    )
                },
                label = { Text(destination.label) },
                colors = NavigationBarItemDefaults.colors(),
            )
        }
    }
}

@Composable
private fun MboloNavigationRail(
    currentDestination: MboloDestination?,
    onDestinationSelected: (MboloDestination) -> Unit,
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Spacer(Modifier.height(MboloSpacing.lg))
        MboloDestination.entries.forEach { destination ->
            NavigationRailItem(
                selected = currentDestination == destination,
                onClick = { onDestinationSelected(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label,
                        modifier = Modifier.size(MboloSpacing.xl),
                    )
                },
                label = { Text(destination.label) },
                colors = NavigationRailItemDefaults.colors(),
            )
        }
    }
}

/**
 * Rail de navigation TV : pensé pour la télécommande, pas pour le tactile.
 *
 * Items larges et hauts, icônes et libellés lisibles à distance,
 * focus D-pad avec anneau Mbolo. La destination courante est marquée
 * par un conteneur plein, le focus par l'anneau : les deux restent
 * distincts et le focus ne peut jamais être confondu avec un état
 * désactivé.
 */
@Composable
private fun MboloTvNavigationRail(
    currentDestination: MboloDestination?,
    onDestinationSelected: (MboloDestination) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(210.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = MboloSpacing.lg, vertical = MboloSpacing.xxxl),
        verticalArrangement = Arrangement.spacedBy(MboloSpacing.md),
    ) {
        Text(
            text = "Mbolo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = MboloSpacing.md, bottom = MboloSpacing.lg),
        )
        MboloDestination.entries.forEach { destination ->
            MboloTvNavItem(
                destination = destination,
                selected = currentDestination == destination,
                onSelect = { onDestinationSelected(destination) },
            )
        }
    }
}

@Composable
private fun MboloTvNavItem(
    destination: MboloDestination,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .mboloTvFocusable(
                enabled = true,
                shape = MboloShape.card,
                onSelect = onSelect,
                scale = 1.02f,
            )
            .background(
                if (selected) colorScheme.primaryContainer else Color.Transparent,
                MboloShape.card,
            )
            .padding(horizontal = MboloSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MboloSpacing.md),
    ) {
        Icon(
            imageVector = destination.icon,
            contentDescription = null,
            tint = if (selected) colorScheme.onPrimaryContainer else colorScheme.onSurfaceVariant,
            modifier = Modifier.size(28.dp),
        )
        Text(
            text = destination.label,
            style = MaterialTheme.typography.titleMedium,
            color = if (selected) colorScheme.onPrimaryContainer else colorScheme.onSurface,
        )
    }
}
