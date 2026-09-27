package ga.mbolo.app.ui.explore

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ga.mbolo.app.ui.PlaceholderScreen
import ga.mbolo.app.ui.theme.MboloTheme

/**
 * Écran Explorer (placeholder — développé dans une prochaine étape).
 */
@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(title = "Explorer", modifier = modifier)
}

@Preview(name = "Explorer", showBackground = true, backgroundColor = 0xFF070A09)
@Composable
private fun ExploreScreenPreview() {
    MboloTheme {
        ExploreScreen()
    }
}
