package ga.mbolo.app.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ga.mbolo.app.data.MboloContent
import ga.mbolo.app.data.MboloContentType
import ga.mbolo.app.data.MboloDemoData
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
import ga.mbolo.app.ui.theme.MboloTheme
import ga.mbolo.app.ui.tv.mboloTvFocusable
import kotlinx.coroutines.delay

/**
 * Page de détails d'un contenu Mbolo TV.
 *
 * Reçoit un identifiant, retrouve le contenu dans les données locales
 * ([MboloDemoData.contentById]) et affiche un état propre « Contenu
 * introuvable » si l'identifiant ne correspond à rien.
 *
 * Les actions « Regarder » et « Ma liste » sont volontairement
 * désactivées : aucun lecteur et aucune liste persistante n'existent
 * encore.
 */
@Composable
fun ContentDetailsScreen(
    contentId: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    val content = MboloDemoData.contentById(contentId)
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (content == null) {
            ContentNotFound(onBack = onBack)
        } else {
            ContentDetails(content = content, onBack = onBack)
        }
    }
}

@Composable
private fun ContentDetails(
    content: MboloContent,
    onBack: () -> Unit,
) {
    val isTv = rememberMboloUiMode() == MboloUiMode.Tv
    val shell = currentMboloShellLayout()
    val margin = if (isTv) MboloSpacing.screenMarginTv else MboloSpacing.screenMargin
    // Sur TV, la bannière cinémascope permet d'afficher toutes les
    // informations à l'écran sans défilement.
    val bannerAspectRatio = when {
        isTv -> 21f / 9f
        shell == MboloShellLayout.Compact -> 3f / 4f
        else -> 16f / 9f
    }
    val backgroundColor = MaterialTheme.colorScheme.background
    val backFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isTv) {
        if (isTv) {
            // Le bouton retour est le premier élément réellement
            // interactif de la page : le lecteur arrive plus tard.
            repeat(6) {
                val acquired = runCatching { backFocusRequester.requestFocus() }.isSuccess
                if (acquired) return@LaunchedEffect
                delay(100)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = margin, vertical = MboloSpacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isTv) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .mboloTvFocusable(
                            enabled = true,
                            shape = MboloShape.pill,
                            focusRequester = backFocusRequester,
                            onSelect = onBack,
                            scale = 1.06f,
                        )
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHigh,
                            MboloShape.pill,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Retour",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(28.dp),
                    )
                }
            } else {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Retour",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(bannerAspectRatio),
        ) {
            MboloPoster(
                tone = content.tone,
                watermarkTitle = content.title,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to backgroundColor.copy(alpha = 0.35f),
                            0.45f to Color.Transparent,
                            0.80f to backgroundColor.copy(alpha = 0.72f),
                            1f to backgroundColor,
                        ),
                    ),
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = margin, vertical = MboloSpacing.lg),
            ) {
                Text(
                    text = content.type.labelFr(),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            shape = MboloShape.pill,
                        )
                        .padding(horizontal = MboloSpacing.md, vertical = MboloSpacing.xxs),
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
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = margin, vertical = MboloSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(MboloSpacing.md),
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
            Text(
                text = content.description,
                style = if (isTv) {
                    MaterialTheme.typography.bodyLarge
                } else {
                    MaterialTheme.typography.bodyMedium
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(MboloSpacing.xs))
            ContentDetailsActions()
        }
    }
}

@Composable
private fun ContentDetailsActions() {
    // Aucun lecteur et aucune liste persistante n'existent encore :
    // les actions restent affichées mais désactivées.
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
            text = "Ma liste",
            onClick = {},
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.BookmarkAdd,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            },
        )
    }
}

@Composable
private fun ContentNotFound(onBack: () -> Unit) {
    val isTv = rememberMboloUiMode() == MboloUiMode.Tv
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isTv) {
        if (isTv) {
            repeat(6) {
                val acquired = runCatching { focusRequester.requestFocus() }.isSuccess
                if (acquired) return@LaunchedEffect
                delay(100)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(MboloSpacing.screenMargin),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MboloSpacing.lg),
        ) {
            Text(
                text = "Contenu introuvable",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "Ce contenu n'existe pas dans la sélection locale de Mbolo TV.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            MboloButton(
                text = "Retour",
                onClick = onBack,
                modifier = if (isTv) Modifier.focusRequester(focusRequester) else Modifier,
            )
        }
    }
}

private fun MboloContentType.labelFr(): String = when (this) {
    MboloContentType.Film -> "Film"
    MboloContentType.Serie -> "Série"
    MboloContentType.Chaine -> "Chaîne"
}

@Preview(name = "Détails — sombre", showBackground = true, backgroundColor = 0xFF070A09, widthDp = 360, heightDp = 800)
@Composable
private fun ContentDetailsPreview() {
    MboloTheme(darkTheme = true) {
        ContentDetailsScreen(contentId = "le-sens-de-la-hyene")
    }
}

@Preview(name = "Détails — introuvable", showBackground = true, backgroundColor = 0xFF070A09, widthDp = 360, heightDp = 800)
@Composable
private fun ContentNotFoundPreview() {
    MboloTheme(darkTheme = true) {
        ContentDetailsScreen(contentId = "inexistant")
    }
}

@Preview(name = "Détails — TV", showBackground = true, backgroundColor = 0xFF070A09, widthDp = 960, heightDp = 540)
@Composable
private fun ContentDetailsTvPreview() {
    MboloTheme(darkTheme = true) {
        ContentDetailsScreen(contentId = "etoiles-de-lambarene")
    }
}
