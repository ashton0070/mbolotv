package ga.mbolo.app.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ga.mbolo.app.ui.PlaceholderScreen
import ga.mbolo.app.ui.theme.MboloTheme

/**
 * Écran Recherche (placeholder — développé dans une prochaine étape).
 */
@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(title = "Recherche", modifier = modifier)
}

@Preview(name = "Recherche", showBackground = true, backgroundColor = 0xFF070A09)
@Composable
private fun SearchScreenPreview() {
    MboloTheme {
        SearchScreen()
    }
}
