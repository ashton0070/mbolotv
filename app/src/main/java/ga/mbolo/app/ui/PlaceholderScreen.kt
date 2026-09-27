package ga.mbolo.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ga.mbolo.app.ui.components.MboloCard
import ga.mbolo.app.ui.components.MboloSectionTitle
import ga.mbolo.app.ui.theme.mboloScreenPadding

/**
 * Écran placeholder Mbolo : titre de section + indication « en construction ».
 *
 * Réservé aux sections en attente de développement (aucune donnée fictive).
 */
@Composable
fun PlaceholderScreen(
    title: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .mboloScreenPadding(),
            contentAlignment = Alignment.Center,
        ) {
            MboloCard(
                modifier = Modifier.widthIn(max = 480.dp),
            ) {
                MboloSectionTitle(
                    title = title,
                    subtitle = "Cette section sera développée dans une prochaine étape.",
                )
            }
        }
    }
}
