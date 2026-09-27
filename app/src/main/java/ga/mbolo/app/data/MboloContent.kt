package ga.mbolo.app.data

/**
 * Type de contenu, côté interface uniquement.
 * Les valeurs « Chaine » seront exploitées à l'étape live/TV.
 */
enum class MboloContentType {
    Film,
    Serie,
    Chaine,
}

/**
 * Ambiance chromatique de la vignette de démonstration.
 * Chaque tonalité est ensuite résolue dans le Design System Mbolo
 * (ui/components/MboloPoster.kt) — aucune couleur n'est définie ici.
 */
enum class MboloPosterTone {
    Foret,
    Lagon,
    Or,
    Volcan,
    Nuit,
    Aube,
}

/**
 * Contenu de démonstration de Mbolo TV.
 *
 * Modèle volontairement restreint aux propriétés affichées par l'UI.
 * Une future source de données réelle devra produire ce même type :
 * les données locales de [MboloDemoData] pourront alors être remplacées
 * sans toucher aux composants.
 */
data class MboloContent(
    val id: String,
    val title: String,
    val description: String,
    val type: MboloContentType,
    val tone: MboloPosterTone,
    val year: Int,
    val durationMinutes: Int,
    val ageRating: String,
    val genres: List<String>,
    val matchPercent: Int? = null,
    /** Progression visuelle de lecture, entre 0f et 1f. Uniquement pour « Continuer à regarder ». */
    val progress: Float? = null,
)

/** Présentation d'une rangée de la page d'accueil. */
enum class MboloSectionLayout {
    /** Vignettes verticales poster (films, séries, tendances). */
    Poster,

    /** Vignettes larges 16:9 avec barre de progression. */
    ContinueWatching,
}

/**
 * Section de la page d'accueil : un titre et des contenus,
 * prêts à être parcourus horizontalement.
 */
data class MboloHomeSection(
    val id: String,
    val title: String,
    val layout: MboloSectionLayout,
    val items: List<MboloContent>,
)

/** Libellé de durée compact, par exemple « 1 h 52 ». */
fun MboloContent.formattedDuration(): String = mboloDurationLabel(durationMinutes)

private fun mboloDurationLabel(minutes: Int): String {
    val hours = minutes / 60
    val remainder = minutes % 60
    return when {
        hours > 0 && remainder > 0 -> "$hours h ${remainder.toString().padStart(2, '0')}"
        hours > 0 -> "$hours h"
        else -> "$minutes min"
    }
}

/** Temps restant estimé, purement visuel, pour « Continuer à regarder ». */
fun MboloContent.remainingTimeLabel(): String? {
    val progress = progress ?: return null
    val remaining = kotlin.math
        .ceil(durationMinutes * (1f - progress) - 0.05f)
        .toInt()
        .coerceAtLeast(0)
    return if (remaining == 0) "Prêt à reprendre" else "Il reste ${mboloDurationLabel(remaining)}"
}
