package ga.mbolo.app.ui.mylist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ga.mbolo.app.ui.PlaceholderScreen
import ga.mbolo.app.ui.theme.MboloTheme

/**
 * Écran Ma liste (placeholder — développé dans une prochaine étape).
 */
@Composable
fun MyListScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(title = "Ma liste", modifier = modifier)
}

@Preview(name = "Ma liste", showBackground = true, backgroundColor = 0xFF070A09)
@Composable
private fun MyListScreenPreview() {
    MboloTheme {
        MyListScreen()
    }
}
