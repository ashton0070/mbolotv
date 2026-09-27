package ga.mbolo.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MboloDarkColors = darkColorScheme(
    primary = MboloGreen,
    onPrimary = MboloGreenOnDark,
    primaryContainer = MboloGreenContainer,
    onPrimaryContainer = MboloOnGreenContainer,
    secondary = MboloGold,
    onSecondary = MboloGoldContainer,
    secondaryContainer = MboloGoldContainer,
    onSecondaryContainer = MboloOnGoldContainer,
    tertiary = MboloBlue,
    onTertiary = MboloGreenOnDark,
    tertiaryContainer = MboloBlueContainer,
    onTertiaryContainer = MboloOnBlueContainer,
    background = MboloBackground,
    onBackground = MboloOnSurface,
    surface = MboloSurface,
    onSurface = MboloOnSurface,
    surfaceVariant = MboloSurfaceRaised,
    onSurfaceVariant = MboloOnSurfaceVariant,
    surfaceContainerLowest = MboloBackground,
    surfaceContainerLow = MboloSurface,
    surfaceContainer = MboloSurfaceRaised,
    surfaceContainerHigh = MboloSurfaceHigh,
    surfaceContainerHighest = MboloSurfaceHigh,
    outline = MboloOutline,
    outlineVariant = MboloOutlineVariant,
    inverseSurface = MboloOnSurface,
    inverseOnSurface = MboloSurface,
    inversePrimary = MboloGreenDark,
    error = MboloError,
    onError = MboloOnError,
    errorContainer = MboloErrorContainer,
    onErrorContainer = MboloOnErrorContainer,
)

private val MboloLightColors = lightColorScheme(
    primary = MboloGreenDark,
    onPrimary = MboloSurfaceLight,
    primaryContainer = MboloGreenContainerLight,
    onPrimaryContainer = MboloOnGreenContainerLight,
    secondary = MboloGoldDark,
    onSecondary = MboloSurfaceLight,
    secondaryContainer = MboloGoldContainerLight,
    onSecondaryContainer = MboloOnGoldContainerLight,
    tertiary = MboloBlueDark,
    onTertiary = MboloSurfaceLight,
    tertiaryContainer = MboloBlueContainerLight,
    onTertiaryContainer = MboloOnBlueContainerLight,
    background = MboloBackgroundLight,
    onBackground = MboloOnSurfaceLight,
    surface = MboloSurfaceLight,
    onSurface = MboloOnSurfaceLight,
    surfaceVariant = MboloSurfaceRaisedLight,
    onSurfaceVariant = MboloOnSurfaceVariantLight,
    surfaceContainerLowest = MboloSurfaceLight,
    surfaceContainerLow = MboloBackgroundLight,
    surfaceContainer = MboloSurfaceRaisedLight,
    surfaceContainerHigh = MboloSurfaceHighLight,
    surfaceContainerHighest = MboloSurfaceHighLight,
    outline = MboloOutlineLight,
    outlineVariant = MboloOutlineVariantLight,
    inverseSurface = MboloOnSurfaceLight,
    inverseOnSurface = MboloSurfaceLight,
    inversePrimary = MboloGreen,
    error = MboloErrorLight,
    onError = MboloSurfaceLight,
    errorContainer = MboloErrorContainerLight,
    onErrorContainer = MboloOnErrorContainerLight,
)

/**
 * Thème Material 3 unique de Mbolo.
 *
 * Le mode sombre est le mode principal de l'application ; le mode clair
 * est préparé et complet pour rester accessible.
 */
@Composable
fun MboloTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) MboloDarkColors else MboloLightColors,
        typography = MboloTypography,
        shapes = MboloShapes,
        content = content,
    )
}
