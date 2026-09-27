package ga.mbolo.app.ui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ga.mbolo.app.ui.components.MboloButton
import ga.mbolo.app.ui.components.MboloCard
import ga.mbolo.app.ui.components.MboloSectionTitle
import ga.mbolo.app.ui.components.MboloSurface
import ga.mbolo.app.ui.components.MboloTextButton
import ga.mbolo.app.ui.theme.MboloFocusRing
import ga.mbolo.app.ui.theme.MboloShape
import ga.mbolo.app.ui.theme.MboloSpacing
import ga.mbolo.app.ui.theme.MboloTheme
import ga.mbolo.app.ui.theme.mboloScreenPadding

/**
 * Page de démonstration interne du design system Mbolo.
 *
 * Uniquement une page de développement : elle n'est PAS l'écran d'accueil
 * définitif de l'application.
 */
@Composable
fun DesignSystemScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .mboloScreenPadding(vertical = MboloSpacing.xl),
            verticalArrangement = Arrangement.spacedBy(MboloSpacing.sectionGap),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(MboloSpacing.xs)) {
                Text(
                    text = "Mbolo Design System",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Page de démonstration interne — développement uniquement.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            ColorsSection()
            TypographySection()
            ButtonsSection()
            CardSection()
            SpacingSection()
            SurfacesSection()
        }
    }
}

// ─── Couleurs ───────────────────────────────────────────────────────────────

private data class Swatch(val name: String, val color: Color)

@Composable
private fun ColorsSection() {
    val scheme = MaterialTheme.colorScheme
    val swatches = listOf(
        Swatch("primary", scheme.primary),
        Swatch("onPrimary", scheme.onPrimary),
        Swatch("primaryContainer", scheme.primaryContainer),
        Swatch("secondary", scheme.secondary),
        Swatch("tertiary", scheme.tertiary),
        Swatch("background", scheme.background),
        Swatch("surface", scheme.surface),
        Swatch("surfaceContainer", scheme.surfaceContainer),
        Swatch("surfaceContainerHigh", scheme.surfaceContainerHigh),
        Swatch("onSurface", scheme.onSurface),
        Swatch("onSurfaceVariant", scheme.onSurfaceVariant),
        Swatch("outline", scheme.outline),
        Swatch("error", scheme.error),
        Swatch("focusRing", MboloFocusRing),
    )

    SectionBlock(title = "Couleurs") {
        swatches.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(MboloSpacing.lg)) {
                row.forEach { SwatchItem(swatch = it, modifier = Modifier.weight(1f)) }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun SwatchItem(swatch: Swatch, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(MboloShape.card)
                .background(swatch.color)
                .border(1.dp, MaterialTheme.colorScheme.outline, MboloShape.card),
        )
        Spacer(Modifier.width(MboloSpacing.md))
        Column {
            Text(
                text = swatch.name,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = swatch.color.toHex(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

private fun Color.toHex(): String = "#%06X".format(toArgb() and 0xFFFFFF)

// ─── Typographie ────────────────────────────────────────────────────────────

@Composable
private fun TypographySection() {
    SectionBlock(title = "Typographie") {
        TypeSample(name = "displaySmall", text = "Mbolo TV", style = MaterialTheme.typography.displaySmall)
        TypeSample(
            name = "headlineSmall",
            text = "Nouveautés de la semaine",
            style = MaterialTheme.typography.headlineSmall,
        )
        TypeSample(
            name = "titleMedium",
            text = "Titre de programme",
            style = MaterialTheme.typography.titleMedium,
        )
        TypeSample(
            name = "bodyLarge",
            text = "Description du programme, lisible sur mobile comme sur TV.",
            style = MaterialTheme.typography.bodyLarge,
        )
        TypeSample(
            name = "labelLarge",
            text = "BOUTON / ÉTIQUETTE",
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun TypeSample(name: String, text: String, style: TextStyle) {
    Column {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = text,
            style = style,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

// ─── Boutons ────────────────────────────────────────────────────────────────

@Composable
private fun ButtonsSection() {
    SectionBlock(title = "Boutons") {
        MboloButton(text = "Regarder", onClick = {})
        MboloButton(text = "Bouton désactivé", onClick = {}, enabled = false)
        MboloTextButton(text = "En savoir plus", onClick = {})
        MboloTextButton(text = "Bouton texte désactivé", onClick = {}, enabled = false)
    }
}

// ─── Carte ──────────────────────────────────────────────────────────────────

@Composable
private fun CardSection() {
    SectionBlock(title = "Carte générique") {
        MboloCard(
            modifier = Modifier.wrapContentWidth(),
            onClick = {},
        ) {
            Text(
                text = "Titre de la carte",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(MboloSpacing.xs))
            Text(
                text = "Contenu générique de démonstration. Les cartes de programmes arriveront à l'étape catalogue.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ─── Espacements ────────────────────────────────────────────────────────────

@Composable
private fun SpacingSection() {
    SectionBlock(title = "Espacements") {
        SpacingBar(name = "xs", value = MboloSpacing.xs)
        SpacingBar(name = "sm", value = MboloSpacing.sm)
        SpacingBar(name = "md", value = MboloSpacing.md)
        SpacingBar(name = "lg", value = MboloSpacing.lg)
        SpacingBar(name = "xl", value = MboloSpacing.xl)
        SpacingBar(name = "xxl", value = MboloSpacing.xxl)
        SpacingBar(name = "cardGap", value = MboloSpacing.cardGap)
        SpacingBar(name = "sectionGap", value = MboloSpacing.sectionGap)
        SpacingBar(name = "screenMargin", value = MboloSpacing.screenMargin)
    }
}

@Composable
private fun SpacingBar(name: String, value: Dp) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.width(140.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Box(
            modifier = Modifier
                .height(16.dp)
                .width(value)
                .clip(MboloShape.button)
                .background(MaterialTheme.colorScheme.primary),
        )
        Spacer(Modifier.width(MboloSpacing.md))
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// ─── Surfaces ───────────────────────────────────────────────────────────────

@Composable
private fun SurfacesSection() {
    SectionBlock(title = "Surfaces") {
        SurfaceSample(
            label = "surfaceContainer",
            color = MaterialTheme.colorScheme.surfaceContainer,
        )
        SurfaceSample(
            label = "surfaceContainerHigh",
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
        SurfaceSample(
            label = "primaryContainer",
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
private fun SurfaceSample(
    label: String,
    color: Color,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    MboloSurface(
        modifier = Modifier.wrapContentWidth(),
        color = color,
        contentColor = contentColor,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = contentColor,
        )
        Spacer(Modifier.height(MboloSpacing.xs))
        Text(
            text = "Surface générique Mbolo",
            style = MaterialTheme.typography.bodySmall,
            color = contentColor,
        )
    }
}

// ─── Bloc de section ────────────────────────────────────────────────────────

@Composable
private fun SectionBlock(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(MboloSpacing.lg)) {
        MboloSectionTitle(title = title)
        content()
    }
}

// ─── Previews ───────────────────────────────────────────────────────────────

@Preview(name = "Sombre — smartphone", showBackground = true, widthDp = 411, heightDp = 891, backgroundColor = 0xFF070A09)
@Composable
private fun DesignSystemScreenDarkPreview() {
    MboloTheme(darkTheme = true) {
        DesignSystemScreen()
    }
}

@Preview(name = "Clair — smartphone", showBackground = true, widthDp = 411, heightDp = 891, backgroundColor = 0xFFF5F8F6)
@Composable
private fun DesignSystemScreenLightPreview() {
    MboloTheme(darkTheme = false) {
        DesignSystemScreen()
    }
}

@Preview(name = "Sombre — TV paysage", showBackground = true, widthDp = 960, heightDp = 540, backgroundColor = 0xFF070A09)
@Composable
private fun DesignSystemScreenTvPreview() {
    MboloTheme(darkTheme = true) {
        DesignSystemScreen()
    }
}

@Preview(name = "Sombre — texte agrandi", showBackground = true, widthDp = 411, heightDp = 891, fontScale = 1.5f, backgroundColor = 0xFF070A09)
@Composable
private fun DesignSystemScreenLargeTextPreview() {
    MboloTheme(darkTheme = true) {
        DesignSystemScreen()
    }
}
