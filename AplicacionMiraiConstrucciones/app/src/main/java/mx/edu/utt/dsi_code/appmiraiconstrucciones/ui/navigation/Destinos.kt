package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import mx.edu.utt.dsi_code.appmiraiconstrucciones.R

sealed class Destinos(
    val icon: Int,
    val title: String,
    val ruta: String
) {
    object ico_info : Destinos(R.drawable.baseline_info_outline_24, "ICO_INFO", "info_")
    object ico_search : Destinos(R.drawable.baseline_search_24, "ICO_SEARCH", "search_")
}