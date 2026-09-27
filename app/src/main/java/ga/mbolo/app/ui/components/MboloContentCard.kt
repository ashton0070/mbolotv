package ga.mbolo.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ga.mbolo.app.data.MboloContent
import ga.mbolo.app.data.formattedDuration
import ga.mbolo.app.data.remainingTimeLabel
import ga.mbolo.app.ui.device.MboloUiMode
import ga.mbolo.app.ui.device.rememberMboloUiMode
import ga.mbolo.app.ui.theme.MboloShape
import ga.mbolo.app.ui.theme.MboloSpacing
import ga.mbolo.app.ui.tv.mboloTvFocusable

/** Formats de carte pris en charge par [MboloContentCard]. */
enum class MboloContentCardStyle {
    /** Poster vertical 2:3, titre et métadonnées sous l'image. */
    Poster,

    /** Vignette large 16:9 avec barre de progression visuelle. */
    ContinueWatching,
}

/**
 * Carte de contenu réutilisable de Mbolo TV.
 *
 * Conçue pour accueillir plus tard films, séries, chaînes et
 * recommandations. [onClick] est optionnel : sans action réelle
 * disponible, la carte reste un simple affichage.
 *
 * En contexte TV, la carte est focalisable (anneau Mbolo + léger
 * scale) même sans action, pour que le D-pad parcoure les rangées ;
 * la touche « OK » ne déclenche rien tant que la fiche de contenu
 * n'existe pas. [focusRequester] permet de cibler le focus initial.
 */
@Composable
fun MboloContentCard(
    content: MboloContent,
    modifier: Modifier = Modifier,
    style: MboloContentCardStyle = MboloContentCardStyle.Poster,
    onClick: (() -> Unit)? = null,
    focusRequester: FocusRequester? = null,
) {
    val isTv = rememberMboloUiMode() == MboloUiMode.Tv

    val rootModifier = if (isTv) {
        modifier.mboloTvFocusable(
            enabled = true,
            shape = MboloShape.card,
            focusRequester = focusRequester,
            onSelect = onClick,
        )
    } else {
        modifier.then(
            if (onClick != null) {
                Modifier
                    .clip(MboloShape.card)
                    .clickable(onClick = onClick)
            } else {
                Modifier
            },
        )
    }

    Column(modifier = rootModifier) {
        when (style) {
            MboloContentCardStyle.Poster -> PosterBody(content, isTv)
            MboloContentCardStyle.ContinueWatching -> ContinueWatchingBody(content, isTv)
        }
    }
}

@Composable
private fun PosterBody(content: MboloContent, isTv: Boolean) {
    MboloPoster(
        tone = content.tone,
        watermarkTitle = content.title,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f),
        shape = MboloShape.card,
    )
    Spacer(Modifier.height(MboloSpacing.sm))
    val titleStyle = if (isTv) {
        MaterialTheme.typography.titleSmall
    } else {
        MaterialTheme.typography.bodyMedium
    }
    val twoLineTitleHeight = with(LocalDensity.current) { (titleStyle.lineHeight * 2).toDp() }
    Text(
        text = content.title,
        style = titleStyle,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.heightIn(min = twoLineTitleHeight),
    )
    Spacer(Modifier.height(MboloSpacing.xxs))
    Text(
        text = "${content.year} · ${content.formattedDuration()} · ${content.ageRating}",
        style = cardMetaStyle(isTv),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun ContinueWatchingBody(content: MboloContent, isTv: Boolean) {
    val progress = content.progress ?: 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(MboloShape.card),
    ) {
        MboloPoster(
            tone = content.tone,
            watermarkTitle = content.title,
            modifier = Modifier.matchParentSize(),
        )
        MboloProgressBar(
            progress = progress,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = MboloSpacing.xs),
        )
    }
    Spacer(Modifier.height(MboloSpacing.sm))
    Text(
        text = content.title,
        style = if (isTv) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
    Spacer(Modifier.height(MboloSpacing.xxs))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = content.remainingTimeLabel() ?: content.formattedDuration(),
            style = cardMetaStyle(isTv),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            modifier = Modifier.weight(1f, fill = false),
        )
    }
}

@Composable
private fun cardMetaStyle(isTv: Boolean): TextStyle =
    if (isTv) MaterialTheme.typography.labelLarge else MaterialTheme.typography.labelSmall

@Composable
private fun MboloProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.30f)
    val fillColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .height(3.dp)
            .background(trackColor)
            .semantics {
                progressBarRangeInfo = ProgressBarRangeInfo(progress, 0f..1f)
                contentDescription = "Progression de lecture"
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(3.dp)
                .background(fillColor),
        )
    }
}
