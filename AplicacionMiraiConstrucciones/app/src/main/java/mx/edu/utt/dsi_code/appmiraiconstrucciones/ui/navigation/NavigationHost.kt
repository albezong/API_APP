package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.PantallaPrincipal
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_info
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_search
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.DetalleMaterialScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn

/*
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
    }
}*/

@Composable
fun NavigationHost(
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    navController: NavHostController) {
    //val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Pantalla principal con la lista/buscador
        composable("login") {
            Post_LogIn(navController)
        }

        composable("/lista_maquinarias") {
            PostListaTodasMaquiunarias(viewModel = viewModel, navController = navController)
            //PantallaPrincipal(viewModel = viewModel, navController = navController)
        }

        composable("detalle_maquinaria/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                DetalleMaterialScreen(navController, id, viewModel) // 👈 pasa ambos
            }
        }
    }
}
