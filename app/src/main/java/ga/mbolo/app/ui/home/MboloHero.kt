package ga.mbolo.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ga.mbolo.app.data.MboloContent
import ga.mbolo.app.data.formattedDuration
import ga.mbolo.app.ui.components.MboloButton
import ga.mbolo.app.ui.components.MboloPoster
import ga.mbolo.app.ui.components.MboloTextButton
import ga.mbolo.app.ui.device.MboloUiMode
import ga.mbolo.app.ui.device.rememberMboloUiMode
import ga.mbolo.app.ui.shell.MboloShellLayout
import ga.mbolo.app.ui.shell.currentMboloShellLayout
import ga.mbolo.app.ui.theme.MboloShape
import ga.mbolo.app.ui.theme.MboloSpacing
import ga.mbolo.app.ui.tv.mboloTvFocusable

/**
 * Hero de la page d'accueil : contenu principal pleine largeur,
 * intégré au fond sombre par des dégradés de lisibilité.
 *
 * Téléphone portrait : visuel vertical, texte en bas.
 * Paysage / tablette : visuel large, texte centré à gauche.
 * TV : cartes arrondies, typographie agrandie, le hero est l'élément
 * focal initial ([focusRequester]).
 *
 * Le ratio ne fixe qu'une hauteur minimale : le bloc texte peut
 * agrandir le hero aux grandes tailles de police, sans écraser les
 * actions.
 */
@Composable
fun MboloHero(
    content: MboloContent,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
) {
    val uiMode = rememberMboloUiMode()
    val shell = currentMboloShellLayout()
    val isTv = uiMode == MboloUiMode.Tv
    val immersive = shell != MboloShellLayout.Compact
    val viewportHeight = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.height.toDp()
    }
    val heroAspectRatio = when {
        isTv -> 16f / 9f
        immersive && viewportHeight < 480.dp -> 2f
        immersive -> 16f / 9f
        else -> 3f / 4f
    }
    val backgroundColor = MaterialTheme.colorScheme.background

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
    ) {
        val heroMinHeight = maxWidth / heroAspectRatio

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = heroMinHeight)
                .then(if (isTv) Modifier.clip(MboloShape.card) else Modifier)
                .mboloTvFocusable(
                    enabled = isTv,
                    shape = MboloShape.card,
                    focusRequester = focusRequester,
                    scale = 1f,
                ),
        ) {
            MboloPoster(
                tone = content.tone,
                watermarkTitle = content.title,
                modifier = Modifier.fillMaxSize(),
            )

            // Scrim vertical : assombrit la bande du texte (moitié basse)
            // et la zone du header, sans voiler l'affiche au centre.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to backgroundColor.copy(alpha = 0.55f),
                            0.18f to Color.Transparent,
                            0.45f to backgroundColor.copy(alpha = 0.25f),
                            0.70f to backgroundColor.copy(alpha = 0.78f),
                            1f to backgroundColor,
                        ),
                    ),
            )
            if (immersive) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                0f to backgroundColor.copy(alpha = 0.92f),
                                0.55f to backgroundColor.copy(alpha = 0.50f),
                                0.85f to Color.Transparent,
                            ),
                        ),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (immersive) {
                            Modifier.align(Alignment.CenterStart)
                        } else {
                            Modifier.align(Alignment.BottomStart)
                        },
                    )
                    .statusBarsPadding()
                    .padding(
                        start = MboloSpacing.screenMargin,
                        end = MboloSpacing.screenMargin,
                        top = if (immersive) MboloSpacing.gigantic else MboloSpacing.xxl,
                        bottom = if (immersive) MboloSpacing.lg else MboloSpacing.xxl,
                    )
                    .widthIn(max = 540.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Original Mbolo",
                    style = if (isTv) {
                        MaterialTheme.typography.labelLarge
                    } else {
                        MaterialTheme.typography.labelMedium
                    },
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.5.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.height(MboloSpacing.sm))
                Text(
                    text = content.title,
                    style = if (isTv) {
                        MaterialTheme.typography.displayMedium
                    } else {
                        MaterialTheme.typography.displaySmall
                    },
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(MboloSpacing.md))
                MboloHeroMetaLine(content = content)
                Spacer(Modifier.height(MboloSpacing.xs))
                Text(
                    text = content.description,
                    style = if (isTv) {
                        MaterialTheme.typography.bodyLarge
                    } else {
                        MaterialTheme.typography.bodyMedium
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = if (immersive) 2 else 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(max = 480.dp),
                )
                Spacer(Modifier.height(if (immersive) MboloSpacing.md else MboloSpacing.lg))
                MboloHeroActions()
            }
        }
    }
}

@Composable
private fun MboloHeroMetaLine(content: MboloContent) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MboloSpacing.xs),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MboloSpacing.md),
        ) {
            if (content.matchPercent != null) {
                Text(
                    text = "${content.matchPercent} % pertinent",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                )
            }
            Text(
                text = "${content.year} · ${content.formattedDuration()}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )
            Text(
                text = content.ageRating,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                modifier = Modifier
                    .heightIn(min = 20.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                        shape = MboloShape.pill,
                    )
                    .padding(horizontal = MboloSpacing.sm, vertical = MboloSpacing.xxs),
            )
        }
        Text(
            text = content.genres.joinToString(" · "),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun MboloHeroActions() {
    // Lecture et fiches de contenu arrivent aux étapes suivantes :
    // les actions sont affichées désactivées, sans promettre une
    // fonctionnalité inexistante.
    Row(horizontalArrangement = Arrangement.spacedBy(MboloSpacing.md)) {
        MboloButton(
            text = "Regarder",
            onClick = {},
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            },
        )
        MboloTextButton(
            text = "Détails",
            onClick = {},
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            },
        )
    }
}
