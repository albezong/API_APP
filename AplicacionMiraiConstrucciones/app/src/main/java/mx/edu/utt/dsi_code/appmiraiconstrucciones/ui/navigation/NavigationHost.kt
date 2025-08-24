package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_info
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_search
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias


@Composable
fun NavigationHost(
    //agrege
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,

    navController: NavHostController
) {
    NavHost(navController, startDestination = "lista") {
        composable("lista") {
            PostListaTodasMaquiunarias(navController, viewModel)
        }
        /*
        composable("detalle/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            DetalleMaterialScreen(navController, id, viewModel)
        } */
    }
}