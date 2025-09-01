package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import mx.edu.utt.dsi_code.appmiraiconstrucciones.R
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes

data class NavigationItem(
    val title: String,
    val route: String,
    val icon: Int,
    val hasNews: Boolean = false,
    val hasBadge: Int? = null
)

val navigationItems = listOf(
    NavigationItem(
        title = "Información",
        route = Routes.INFO.name,
        icon = R.drawable.baseline_info_outline_24,
        hasNews = false
    ),
    NavigationItem(
        title = "Búsqueda",
        route = Routes.SEARCH.name,
        icon = R.drawable.baseline_search_24,
        hasNews = false
    ),
    /*NavigationItem(
        title = "Historial de Servicio",
        route = Routes.SERVICE_HISTORIAL.name,
        icon = R.drawable.baseline_search_24,
        hasNews = false
    )*/
)