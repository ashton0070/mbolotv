package ga.mbolo.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ga.mbolo.app.ui.details.ContentDetailsScreen
import ga.mbolo.app.ui.explore.ExploreScreen
import ga.mbolo.app.ui.home.HomeScreen
import ga.mbolo.app.ui.mylist.MyListScreen
import ga.mbolo.app.ui.search.SearchScreen

/**
 * NavHost des grandes sections de Mbolo TV.
 *
 * Les routes proviennent uniquement de [MboloDestination] et de
 * [MboloDetailsDestination].
 */
@Composable
fun MboloNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MboloDestination.Home.route,
        modifier = modifier,
    ) {
        composable(MboloDestination.Home.route) {
            HomeScreen(
                onSearchClick = {
                    navController.navigate(MboloDestination.Search.route) {
                        launchSingleTop = true
                    }
                },
                onContentClick = { content ->
                    navController.navigate(MboloDetailsDestination.routeFor(content.id))
                },
            )
        }
        composable(MboloDestination.Explore.route) { ExploreScreen() }
        composable(MboloDestination.Search.route) { SearchScreen() }
        composable(MboloDestination.MyList.route) { MyListScreen() }
        composable(MboloDetailsDestination.ROUTE) { entry ->
            ContentDetailsScreen(
                contentId = entry.arguments
                    ?.getString(MboloDetailsDestination.ARG_CONTENT_ID)
                    .orEmpty(),
                onBack = { navController.popBackStack() },
            )
        }
    }
}
