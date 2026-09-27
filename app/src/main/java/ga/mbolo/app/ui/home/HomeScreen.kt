package ga.mbolo.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import ga.mbolo.app.data.MboloContent
import ga.mbolo.app.data.MboloDemoData
import ga.mbolo.app.data.MboloHomeSection
import ga.mbolo.app.ui.components.MboloContentRow
import ga.mbolo.app.ui.device.MboloUiMode
import ga.mbolo.app.ui.device.rememberMboloUiMode
import ga.mbolo.app.ui.theme.MboloSpacing
import ga.mbolo.app.ui.theme.MboloTheme
import kotlinx.coroutines.delay

/**
 * Page d'accueil Mbolo TV.
 *
 * Structure : header + hero, puis les rangées de contenu.
 * Les données proviennent de [MboloDemoData] (démo locale) et peuvent
 * être remplacées par une vraie source via les paramètres [featured]
 * et [sections], sans modifier l'interface.
 *
 * Sur TV : le header est posé au-dessus d'un hero encadré (l'anneau
 * de focus reste entièrement visible) et le focus initial est placé
 * sur le hero, élément principal de l'accueil.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onContentClick: (MboloContent) -> Unit = {},
    featured: MboloContent = MboloDemoData.featured,
    sections: List<MboloHomeSection> = MboloDemoData.homeSections,
) {
    val isTv = rememberMboloUiMode() == MboloUiMode.Tv
    val heroFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isTv) {
        if (isTv) {
            // Le hero peut ne pas être encore attaché : on réessaie
            // brièvement jusqu'à obtenir le focus.
            repeat(6) {
                val acquired = runCatching { heroFocusRequester.requestFocus() }.isSuccess
                if (acquired) return@LaunchedEffect
                delay(100)
            }
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = if (isTv) MboloSpacing.huge else MboloSpacing.xxxl,
            ),
            verticalArrangement = Arrangement.spacedBy(
                if (isTv) MboloSpacing.xxxl else MboloSpacing.sectionGap,
            ),
        ) {
            item(key = "hero") {
                if (isTv) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MboloSpacing.screenMarginTv,
                                end = MboloSpacing.screenMarginTv,
                                top = MboloSpacing.xl,
                            ),
                    ) {
                        MboloHomeHeader(onSearchClick = onSearchClick)
                        Spacer(Modifier.height(MboloSpacing.lg))
                        MboloHero(
                            content = featured,
                            focusRequester = heroFocusRequester,
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        MboloHero(
                            content = featured,
                            modifier = Modifier.align(Alignment.TopCenter),
                        )
                        MboloHomeHeader(
                            onSearchClick = onSearchClick,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .statusBarsPadding(),
                        )
                    }
                }
            }
            items(sections, key = { it.id }) { section ->
                MboloContentRow(
                    section = section,
                    onItemClick = onContentClick,
                )
            }
        }
    }
}

@Preview(name = "Accueil — portrait sombre", showBackground = true, backgroundColor = 0xFF070A09, widthDp = 360, heightDp = 800)
@Composable
private fun HomeScreenPortraitPreview() {
    MboloTheme(darkTheme = true) {
        HomeScreen()
    }
}

@Preview(name = "Accueil — portrait clair", showBackground = true, backgroundColor = 0xFFF5F8F6, widthDp = 360, heightDp = 800)
@Composable
private fun HomeScreenPortraitLightPreview() {
    MboloTheme(darkTheme = false) {
        HomeScreen()
    }
}

@Preview(name = "Accueil — paysage", showBackground = true, backgroundColor = 0xFF070A09, widthDp = 800, heightDp = 360)
@Composable
private fun HomeScreenLandscapePreview() {
    MboloTheme(darkTheme = true) {
        HomeScreen()
    }
}

@Preview(name = "Accueil — tablette", showBackground = true, backgroundColor = 0xFF070A09, widthDp = 700, heightDp = 1000)
@Composable
private fun HomeScreenTabletPreview() {
    MboloTheme(darkTheme = true) {
        HomeScreen()
    }
}
