package ga.mbolo.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ga.mbolo.app.data.MboloContent
import ga.mbolo.app.data.MboloHomeSection
import ga.mbolo.app.data.MboloSectionLayout
import ga.mbolo.app.ui.device.MboloUiMode
import ga.mbolo.app.ui.device.rememberMboloUiMode
import ga.mbolo.app.ui.shell.MboloShellLayout
import ga.mbolo.app.ui.shell.currentMboloShellLayout
import ga.mbolo.app.ui.theme.MboloSpacing

/**
 * Rangée horizontale de contenus : titre de section + carrousel.
 *
 * Composant générique de toutes les sections de la page d'accueil.
 * En contexte TV, les cartes sont plus grandes, les marges suivent
 * [MboloSpacing.screenMarginTv] et un espace vertical est réservé
 * pour que l'anneau de focus ne soit jamais rogné.
 */
@Composable
fun MboloContentRow(
    section: MboloHomeSection,
    modifier: Modifier = Modifier,
    onItemClick: ((MboloContent) -> Unit)? = null,
) {
    val isTv = rememberMboloUiMode() == MboloUiMode.Tv
    val cardStyle = when (section.layout) {
        MboloSectionLayout.Poster -> MboloContentCardStyle.Poster
        MboloSectionLayout.ContinueWatching -> MboloContentCardStyle.ContinueWatching
    }
    val itemWidth = if (isTv) {
        mboloTvItemWidth(section.layout)
    } else {
        mboloRowItemWidth(section.layout, currentMboloShellLayout())
    }
    val horizontalMargin = if (isTv) MboloSpacing.screenMarginTv else MboloSpacing.screenMargin

    Column(modifier = modifier) {
        MboloSectionTitle(
            title = section.title,
            titleStyle = if (isTv) {
                MaterialTheme.typography.headlineMedium
            } else {
                MaterialTheme.typography.headlineSmall
            },
            modifier = Modifier.padding(horizontal = horizontalMargin),
        )
        Spacer(Modifier.height(MboloSpacing.sectionTitleGap))
        LazyRow(
            contentPadding = PaddingValues(
                horizontal = horizontalMargin,
                vertical = if (isTv) MboloSpacing.md else MboloSpacing.none,
            ),
            horizontalArrangement = Arrangement.spacedBy(MboloSpacing.cardGap),
        ) {
            items(section.items, key = { it.id }) { content ->
                MboloContentCard(
                    content = content,
                    style = cardStyle,
                    modifier = Modifier.width(itemWidth),
                    onClick = onItemClick?.let { handler -> { handler(content) } },
                )
            }
        }
    }
}

private fun mboloRowItemWidth(layout: MboloSectionLayout, shell: MboloShellLayout): Dp =
    when (layout) {
        MboloSectionLayout.Poster -> when (shell) {
            MboloShellLayout.Compact -> 144.dp
            MboloShellLayout.Medium -> 164.dp
            MboloShellLayout.Expanded -> 188.dp
        }
        MboloSectionLayout.ContinueWatching -> when (shell) {
            MboloShellLayout.Compact -> 280.dp
            MboloShellLayout.Medium -> 320.dp
            MboloShellLayout.Expanded -> 360.dp
        }
    }

private fun mboloTvItemWidth(layout: MboloSectionLayout): Dp =
    when (layout) {
        MboloSectionLayout.Poster -> 220.dp
        MboloSectionLayout.ContinueWatching -> 420.dp
    }
