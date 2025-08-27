package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.DetalleMaterialScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.RevisionPreventivaScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.RevisionPreventivaViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo
import mx.edu.utt.dsi_code.appmiraiconstrucciones.PantallaPrincipal
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.DetalleRevisionPreventivaScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.HistorialServiciosScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.DetalleRevisionPreventivaViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.HistorialServiciosViewModel


fun NavGraphBuilder.appRoutes(
    navController: NavController,
    postMaquinasViewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    usuariosViewModel: UsuariosViewModelLogeo,
    revisionPreventivaViewModel: RevisionPreventivaViewModel,
    historialServiciosViewModel: HistorialServiciosViewModel,
    detalleRevisionPreventivaViewModel: DetalleRevisionPreventivaViewModel
) {
    // Login screen
    composable(Routes.LOGIN.name) {
        Post_LogIn(
            navListaMaquinaria = navController,
            usuarioViewModelLogeo = usuariosViewModel
        )
    }

    // Lista principal con parámetro de correo
    composable("${Routes.LISTA_MAQUINARIAS.name}/{correo}") { backStackEntry ->
        val correo = backStackEntry.arguments?.getString("correo") ?: ""
        PantallaPrincipal(
            viewModel = postMaquinasViewModel,
            navController = navController,
            nombre = correo
        )
    }

    // Detalle de maquinaria con parámetro de ID
    composable("${Routes.DETALLE_MAQUINARIA.name}/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
        if (id != null) {
            DetalleMaterialScreen(
                navController = navController,
                id = id,
                viewModel = postMaquinasViewModel
            )
        }
    }

    // Historial de servicio
    composable(Routes.SERVICE_HISTORIAL.name) {
        HistorialServiciosScreen(
            navController = navController,
            viewModel = historialServiciosViewModel

        )
    }

    // Crear nuevo servicio
    composable("${Routes.REVISIONES_PREVENTIVAS.name}/{id}") {
        val id = it.arguments?.getString("id")?.toIntOrNull()
        RevisionPreventivaScreen(
            navController = navController,
            viewModel = revisionPreventivaViewModel,
            revisionId = id
        )
    }

    composable("${Routes.DETALLE_REVISIONES_PREVENTIVAS.name}/{id}") {
        val id = it.arguments?.getString("id")?.toIntOrNull()
        if (id != null) {
            DetalleRevisionPreventivaScreen(
                navController = navController,
                revisionId = id,
                viewModel = detalleRevisionPreventivaViewModel
            )
        }
    }
}
