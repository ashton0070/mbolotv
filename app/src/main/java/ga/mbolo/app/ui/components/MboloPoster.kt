package ga.mbolo.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ga.mbolo.app.data.MboloPosterTone
import ga.mbolo.app.ui.theme.MboloBlue
import ga.mbolo.app.ui.theme.MboloBlueContainer
import ga.mbolo.app.ui.theme.MboloErrorContainer
import ga.mbolo.app.ui.theme.MboloGold
import ga.mbolo.app.ui.theme.MboloGoldContainer
import ga.mbolo.app.ui.theme.MboloGreen
import ga.mbolo.app.ui.theme.MboloGreenContainer
import ga.mbolo.app.ui.theme.MboloGreenDark
import ga.mbolo.app.ui.theme.MboloOnSurface
import ga.mbolo.app.ui.theme.MboloSpacing
import ga.mbolo.app.ui.theme.MboloSurfaceHigh

private data class MboloPosterPalette(
    val base: Color,
    val accent: Color,
)

/**
 * Résolution des tonalités de contenu en teintes du Design System Mbolo.
 * Les affiches de démonstration sont des visuels générés, jamais des
 * images externes : la même signature sert de placeholder élégant tant
 * que de vraies images ne sont pas fournies.
 */
private fun mboloPosterPalette(tone: MboloPosterTone): MboloPosterPalette = when (tone) {
    MboloPosterTone.Foret -> MboloPosterPalette(MboloGreenContainer, MboloGreen)
    MboloPosterTone.Lagon -> MboloPosterPalette(MboloBlueContainer, MboloBlue)
    MboloPosterTone.Or -> MboloPosterPalette(MboloGoldContainer, MboloGold)
    MboloPosterTone.Volcan -> MboloPosterPalette(MboloErrorContainer, MboloGold)
    MboloPosterTone.Nuit -> MboloPosterPalette(MboloSurfaceHigh, MboloGreen)
    MboloPosterTone.Aube -> MboloPosterPalette(MboloGreenDark, MboloGold)
}

/**
 * Vignette visuelle de contenu Mbolo : dégradé cinématographique, halo
 * et motif d'arcs discrets, dessinés à partir des couleurs du thème.
 *
 * [watermarkTitle] affiche la première lettre du titre en filigrane ;
 * [contentDescription] est nécessaire quand l'image porte du sens seule.
 */
@Composable
fun MboloPoster(
    tone: MboloPosterTone,
    modifier: Modifier = Modifier,
    watermarkTitle: String? = null,
    contentDescription: String? = null,
    shape: Shape = RectangleShape,
) {
    val palette = mboloPosterPalette(tone)
    val arcColor = lerp(MboloOnSurface, Color.Transparent, 0.86f)

    Box(
        modifier = modifier
            .semantics {
                if (contentDescription != null) {
                    this.contentDescription = contentDescription
                }
            }
            .clip(shape),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val short = size.minDimension

            drawRect(
                brush = Brush.verticalGradient(
                    0f to lerp(palette.base, palette.accent, 0.10f),
                    0.5f to palette.base,
                    1f to lerp(palette.base, Color.Black, 0.55f),
                ),
            )

            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(palette.accent.copy(alpha = 0.30f), Color.Transparent),
                    center = Offset(size.width * 0.74f, size.height * 0.16f),
                    radius = short * 1.15f,
                ),
            )

            val arcCenter = Offset(size.width * 0.18f, size.height * 0.9f)
            val stroke = Stroke(width = short * 0.014f, cap = StrokeCap.Round)
            drawArc(
                color = arcColor,
                startAngle = 250f,
                sweepAngle = 120f,
                useCenter = false,
                topLeft = Offset(arcCenter.x - short * 0.34f, arcCenter.y - short * 0.34f),
                size = Size(short * 0.68f, short * 0.68f),
                style = stroke,
            )
            drawArc(
                color = arcColor,
                startAngle = 240f,
                sweepAngle = 140f,
                useCenter = false,
                topLeft = Offset(arcCenter.x - short * 0.52f, arcCenter.y - short * 0.52f),
                size = Size(short * 1.04f, short * 1.04f),
                style = stroke,
            )
            drawArc(
                color = arcColor,
                startAngle = 235f,
                sweepAngle = 150f,
                useCenter = false,
                topLeft = Offset(arcCenter.x - short * 0.7f, arcCenter.y - short * 0.7f),
                size = Size(short * 1.4f, short * 1.4f),
                style = stroke,
            )

            drawRect(
                brush = Brush.verticalGradient(
                    0.6f to Color.Transparent,
                    1f to lerp(palette.base, Color.Black, 0.5f).copy(alpha = 0.7f),
                ),
            )
        }

        if (watermarkTitle != null) {
            Text(
                text = watermarkTitle.take(1),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Black,
                ),
                color = MboloOnSurface.copy(alpha = 0.10f),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MboloSpacing.md)
                    .clearAndSetSemantics { },
            )
        }
    }
}
