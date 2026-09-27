package ga.mbolo.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Destinations principales de Mbolo TV.
 *
 * Route, libellé et icône sont centralisés ici : aucune chaîne de navigation
 * n'est écrite ailleurs dans le code.
 */
enum class MboloDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    Home(route = "home", label = "Accueil", icon = Icons.Rounded.Home),
    Explore(route = "explore", label = "Explorer", icon = Icons.Rounded.Explore),
    Search(route = "search", label = "Recherche", icon = Icons.Rounded.Search),
    MyList(route = "my_list", label = "Ma liste", icon = Icons.Rounded.BookmarkBorder);

    companion object {
        fun fromRoute(route: String?): MboloDestination? =
            entries.firstOrNull { it.route == route }

        /**
         * Onglet à surligner pour une route donnée : les écrans de détail
         * rattachés à l'accueil conservent la surbrillance « Accueil ».
         */
        fun tabFor(route: String?): MboloDestination? =
            fromRoute(route)
                ?: route?.takeIf { MboloDetailsDestination.isDetailsRoute(it) }
                    ?.let { Home }
    }
}

/**
 * Destination « détails d'un contenu », typée et centralisée ici :
 * le gabarit de route et la fabrication des routes ne vivent nulle part ailleurs.
 */
object MboloDetailsDestination {
    const val ARG_CONTENT_ID = "contentId"
    const val ROUTE = "details/{$ARG_CONTENT_ID}"
    private const val PREFIX = "details/"

    fun routeFor(contentId: String): String = PREFIX + contentId

    fun isDetailsRoute(route: String?): Boolean = route?.startsWith(PREFIX) == true
}
