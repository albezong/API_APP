package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.PantallaPrincipal
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.RetrofitClient
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UsuariosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_info
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_search
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.DetalleMaterialScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelFactory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo


@Composable
fun NavigationHost(
    postMaquinasViewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    usuariosViewModel: UsuariosViewModelLogeo,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "login") {
        // login (usa usuariosViewModel)
        composable("login") {
            Post_LogIn(navListaMaquinaria = navController, usuarioViewModelLogeo = usuariosViewModel)
        }

        // lista principal recibe correo en la ruta
        composable("lista_maquinarias/{correo}") { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            PantallaPrincipal(
                viewModel = postMaquinasViewModel,
                navController = navController,
                nombre = correo // o "correo" según param en tu PantallaPrincipal
            )
        }

        // detalle
        composable("detalle_maquinaria/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                DetalleMaterialScreen(navController = navController, id = id, viewModel = postMaquinasViewModel)
            }
        }
    }
}
