package ga.mbolo.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Formes principales de Mbolo : cartes, boutons, champs, dialogues, surfaces.
 * Les coins sont modernes (arrondis marqués mais pas excessifs) et cohérents.
 */
object MboloShape {
    val button = RoundedCornerShape(14.dp)
    val field = RoundedCornerShape(14.dp)
    val card = RoundedCornerShape(18.dp)
    val surface = RoundedCornerShape(22.dp)
    val dialog = RoundedCornerShape(26.dp)
    val pill = RoundedCornerShape(percent = 50)
}

/** Correspondance avec l'échelle Material 3. */
val MboloShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = MboloShape.button,
    medium = MboloShape.card,
    large = MboloShape.surface,
    extraLarge = MboloShape.dialog,
)
