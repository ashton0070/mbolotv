package ga.mbolo.app.data

import ga.mbolo.app.data.MboloContentType.Film
import ga.mbolo.app.data.MboloContentType.Serie
import ga.mbolo.app.data.MboloPosterTone.Aube
import ga.mbolo.app.data.MboloPosterTone.Foret
import ga.mbolo.app.data.MboloPosterTone.Lagon
import ga.mbolo.app.data.MboloPosterTone.Nuit
import ga.mbolo.app.data.MboloPosterTone.Or
import ga.mbolo.app.data.MboloPosterTone.Volcan

/**
 * Source de données locale de démonstration pour la page d'accueil.
 *
 * Aucun réseau, aucune base de données, aucune synchronisation :
 * uniquement des contenus fictifs pour bâtir l'interface.
 * Plus tard, une vraie source devra exposer le même contrat
 * ([MboloContent] / [MboloHomeSection]) et remplacer cet objet.
 */
object MboloDemoData {

    /** Contenu mis en avant dans le Hero. */
    val featured: MboloContent = MboloContent(
        id = "la-nuit-des-tam-tams",
        title = "La Nuit des Tam-Tams",
        description = "Au festival des Masques, une jeune musicienne de Libreville " +
            "retrouve un tam-tam oublié. Entre l'Ogooué et la mer, une saga vibrante " +
            "où la mémoire bat la mesure.",
        type = Film,
        tone = Volcan,
        year = 2026,
        durationMinutes = 112,
        ageRating = "13+",
        genres = listOf("Drame", "Musique"),
        matchPercent = 96,
    )

    /** Retrouve un contenu local par identifiant (hero + toutes les rangées). */
    fun contentById(id: String): MboloContent? =
        if (featured.id == id) {
            featured
        } else {
            homeSections.asSequence()
                .flatMap { it.items.asSequence() }
                .firstOrNull { it.id == id }
        }

    /** Rangées de la page d'accueil, dans l'ordre d'affichage. */
    val homeSections: List<MboloHomeSection> = listOf(
        MboloHomeSection(
            id = "continue",
            title = "Continuer à regarder",
            layout = MboloSectionLayout.ContinueWatching,
            items = listOf(
                MboloContent(
                    id = "rivages",
                    title = "Rivages",
                    description = "Trois générations d'une famille de pêcheurs, " +
                        "entre la lagune et la ville qui grandit trop vite.",
                    type = Serie,
                    tone = Lagon,
                    year = 2025,
                    durationMinutes = 24,
                    ageRating = "13+",
                    genres = listOf("Drame"),
                    progress = 0.78f,
                ),
                MboloContent(
                    id = "le-sens-de-la-hyene",
                    title = "Le Sens de la Hyène",
                    description = "Un gardien de parc poursuit un braconnier " +
                        "que toute la vallée croit connaître.",
                    type = Film,
                    tone = Volcan,
                    year = 2024,
                    durationMinutes = 106,
                    ageRating = "16+",
                    genres = listOf("Thriller"),
                    progress = 0.34f,
                ),
                MboloContent(
                    id = "dimanches-a-libreville",
                    title = "Dimanches à Libreville",
                    description = "Les mésaventures tendres et drôles d'un " +
                        "quartier qui refuse de dormir.",
                    type = Serie,
                    tone = Aube,
                    year = 2026,
                    durationMinutes = 22,
                    ageRating = "Tous publics",
                    genres = listOf("Comédie"),
                    progress = 0.55f,
                ),
                MboloContent(
                    id = "au-bout-de-la-foret",
                    title = "Au Bout de la Forêt",
                    description = "Six mois avec celles et ceux qui veillent " +
                        "sur la forêt primaire.",
                    type = Film,
                    tone = Foret,
                    year = 2023,
                    durationMinutes = 94,
                    ageRating = "Tous publics",
                    genres = listOf("Documentaire"),
                    progress = 0.12f,
                ),
            ),
        ),
        MboloHomeSection(
            id = "tendances",
            title = "Tendances sur Mbolo",
            layout = MboloSectionLayout.Poster,
            items = listOf(
                MboloContent(
                    id = "etoiles-de-lambarene",
                    title = "Étoiles de Lambaréné",
                    description = "Un observateur solitaire cartographie le ciel " +
                        "depuis les eaux de l'Ogooué.",
                    type = Serie,
                    tone = Foret,
                    year = 2026,
                    durationMinutes = 44,
                    ageRating = "13+",
                    genres = listOf("Drame", "Aventure"),
                    matchPercent = 98,
                ),
                MboloContent(
                    id = "la-mer-nombreuse",
                    title = "La Mer Nombreuse",
                    description = "Port-Piment, dernier port franc d'Afrique " +
                        "centrale. Tout s'y négocie, rien ne s'oublie.",
                    type = Film,
                    tone = Lagon,
                    year = 2025,
                    durationMinutes = 118,
                    ageRating = "16+",
                    genres = listOf("Thriller"),
                    matchPercent = 95,
                ),
                MboloContent(
                    id = "masques-parlants",
                    title = "Les Masques Parlants",
                    description = "Quand les masques dansent, les vivants " +
                        "entendent les anciens.",
                    type = Serie,
                    tone = Or,
                    year = 2026,
                    durationMinutes = 35,
                    ageRating = "13+",
                    genres = listOf("Fantastique"),
                    matchPercent = 92,
                ),
                MboloContent(
                    id = "route-bantoue",
                    title = "La Route Bantoue",
                    description = "Un routier hérite d'un chargement " +
                        "que personne ne réclame.",
                    type = Film,
                    tone = Nuit,
                    year = 2024,
                    durationMinutes = 102,
                    ageRating = "13+",
                    genres = listOf("Road-movie"),
                    matchPercent = 90,
                ),
                MboloContent(
                    id = "golf-bleu",
                    title = "Golf Bleu",
                    description = "Sur la côte, une école de surf " +
                        "devient le dernier refuge d'un quartier menacé.",
                    type = Serie,
                    tone = Lagon,
                    year = 2025,
                    durationMinutes = 30,
                    ageRating = "Tous publics",
                    genres = listOf("Drame", "Sport"),
                    matchPercent = 88,
                ),
                MboloContent(
                    id = "soleils-d-equateur",
                    title = "Soleils d'Équateur",
                    description = "Frères ennemis sous le même ciel : " +
                        "une héritière, un géologue, une mine.",
                    type = Film,
                    tone = Aube,
                    year = 2026,
                    durationMinutes = 126,
                    ageRating = "13+",
                    genres = listOf("Drame"),
                    matchPercent = 87,
                ),
                MboloContent(
                    id = "secret-de-la-foret",
                    title = "Le Secret de la Forêt",
                    description = "Une botaniste revient au village " +
                        "pour une plante que les cartes ignorent.",
                    type = Serie,
                    tone = Foret,
                    year = 2024,
                    durationMinutes = 40,
                    ageRating = "13+",
                    genres = listOf("Mystère"),
                    matchPercent = 85,
                ),
                MboloContent(
                    id = "gare-de-lewilu",
                    title = "Gare de Léwilu",
                    description = "Le dernier train s'arrête encore " +
                        "là où tout a commencé.",
                    type = Film,
                    tone = Nuit,
                    year = 2023,
                    durationMinutes = 96,
                    ageRating = "Tous publics",
                    genres = listOf("Comédie", "Drame"),
                    matchPercent = 84,
                ),
            ),
        ),
        MboloHomeSection(
            id = "films",
            title = "Films",
            layout = MboloSectionLayout.Poster,
            items = listOf(
                MboloContent(
                    id = "ogooue-sous-la-pluie",
                    title = "Ogooué sous la Pluie",
                    description = "Une traversée de trois jours " +
                        "devient l'histoire d'une vie.",
                    type = Film,
                    tone = Foret,
                    year = 2025,
                    durationMinutes = 108,
                    ageRating = "13+",
                    genres = listOf("Drame", "Aventure"),
                    matchPercent = 94,
                ),
                MboloContent(
                    id = "baobab-de-retour",
                    title = "Le Baobab de Retour",
                    description = "Celui qui part sans dire au revoir " +
                        "revient toujours par là.",
                    type = Film,
                    tone = Or,
                    year = 2024,
                    durationMinutes = 92,
                    ageRating = "Tous publics",
                    genres = listOf("Famille"),
                    matchPercent = 89,
                ),
                MboloContent(
                    id = "murmures-oublies",
                    title = "Murmures Oubliés",
                    description = "Une radio libre diffuse des confessions " +
                        "que la ville voulait taire.",
                    type = Film,
                    tone = Nuit,
                    year = 2026,
                    durationMinutes = 101,
                    ageRating = "16+",
                    genres = listOf("Thriller"),
                    matchPercent = 91,
                ),
                MboloContent(
                    id = "derniere-peche",
                    title = "La Dernière Pêche",
                    description = "Avant l'autoroute, un village " +
                        "fait une dernière marée ensemble.",
                    type = Film,
                    tone = Lagon,
                    year = 2023,
                    durationMinutes = 88,
                    ageRating = "Tous publics",
                    genres = listOf("Documentaire"),
                    matchPercent = 86,
                ),
                MboloContent(
                    id = "festival-de-mbanda",
                    title = "Le Festival de Mbanda",
                    description = "Quatre nuits de danse pour sauver " +
                        "un rythme que plus personne ne joue.",
                    type = Film,
                    tone = Aube,
                    year = 2026,
                    durationMinutes = 115,
                    ageRating = "13+",
                    genres = listOf("Musique"),
                    matchPercent = 93,
                ),
                MboloContent(
                    id = "bombe-sans-nom",
                    title = "Bombe sans Nom",
                    description = "Un ingénieur découvre un plan " +
                        "que son entreprise nie avoir dessiné.",
                    type = Film,
                    tone = Volcan,
                    year = 2025,
                    durationMinutes = 110,
                    ageRating = "16+",
                    genres = listOf("Action"),
                    matchPercent = 82,
                ),
                MboloContent(
                    id = "orbites-equatoriales",
                    title = "Orbites Équatoriales",
                    description = "Premier satellite fait ici : " +
                        "la Terre vue d'en bas.",
                    type = Film,
                    tone = Nuit,
                    year = 2026,
                    durationMinutes = 121,
                    ageRating = "13+",
                    genres = listOf("Science-fiction"),
                    matchPercent = 90,
                ),
            ),
        ),
        MboloHomeSection(
            id = "series",
            title = "Séries",
            layout = MboloSectionLayout.Poster,
            items = listOf(
                MboloContent(
                    id = "concession-famille-mvogh",
                    title = "La Concession",
                    description = "Une grande cour, trois ménages, " +
                        "un seul règlement : le matin gagne.",
                    type = Serie,
                    tone = Aube,
                    year = 2025,
                    durationMinutes = 26,
                    ageRating = "Tous publics",
                    genres = listOf("Comédie"),
                    matchPercent = 92,
                ),
                MboloContent(
                    id = "taxis-de-nuit",
                    title = "Les Taxis de Nuit",
                    description = "Ce que les passagers laissent " +
                        "sur la banquette arrière.",
                    type = Serie,
                    tone = Nuit,
                    year = 2026,
                    durationMinutes = 32,
                    ageRating = "16+",
                    genres = listOf("Anthologie"),
                    matchPercent = 95,
                ),
                MboloContent(
                    id = "ecole-de-la-colline",
                    title = "L'École de la Colline",
                    description = "Un pensionnat au-dessus des nuages, " +
                        "et une élève qui pose trop de questions.",
                    type = Serie,
                    tone = Foret,
                    year = 2024,
                    durationMinutes = 45,
                    ageRating = "13+",
                    genres = listOf("Mystère"),
                    matchPercent = 88,
                ),
                MboloContent(
                    id = "quartier-des-mille",
                    title = "Quartier des Mille",
                    description = "Mille maisons, mille histoires : " +
                        "la vie d'un quartier qui invente la ville.",
                    type = Serie,
                    tone = Or,
                    year = 2026,
                    durationMinutes = 24,
                    ageRating = "13+",
                    genres = listOf("Docu-fiction"),
                    matchPercent = 87,
                ),
                MboloContent(
                    id = "notaires-du-fleuve",
                    title = "Les Notaires du Fleuve",
                    description = "Sur l'Ogooué, on ne signe pas : " +
                        "on rame ensemble.",
                    type = Serie,
                    tone = Lagon,
                    year = 2025,
                    durationMinutes = 38,
                    ageRating = "Tous publics",
                    genres = listOf("Drame"),
                    matchPercent = 90,
                ),
                MboloContent(
                    id = "marees-de-pongara",
                    title = "Marées de Pongara",
                    description = "Entre la réserve et le port, " +
                        "une gardienne refuse les deux mondes.",
                    type = Serie,
                    tone = Foret,
                    year = 2026,
                    durationMinutes = 42,
                    ageRating = "13+",
                    genres = listOf("Drame", "Nature"),
                    matchPercent = 93,
                ),
                MboloContent(
                    id = "le-dernier-tambourier",
                    title = "Le Dernier Tambourier",
                    description = "Il ne reste qu'un maître, " +
                        "et aucun élève digne de lui.",
                    type = Serie,
                    tone = Volcan,
                    year = 2024,
                    durationMinutes = 36,
                    ageRating = "13+",
                    genres = listOf("Drame", "Musique"),
                    matchPercent = 91,
                ),
            ),
        ),
    )
}
