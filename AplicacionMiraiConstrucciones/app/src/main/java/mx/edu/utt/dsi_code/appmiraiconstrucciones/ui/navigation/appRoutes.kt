package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_DetalleMaterialScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.RevisionPreventivaScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.RevisionPreventivaViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo
import mx.edu.utt.dsi_code.appmiraiconstrucciones.PantallaPrincipal
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.DetalleRevisionPreventivaScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.HistorialServiciosScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostCreateTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_EditMaquinariaScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.DetalleRevisionPreventivaViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.HistorialServiciosViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EstatusDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_TiposMaquinariasDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UbicacionesDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UnidadesDto_ViewModel


fun NavGraphBuilder.appRoutes(
    navController: NavController,
    post_MaquinariasYVehiculosDto_ViewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    post_EquiposDto_ViewModel: Post_EquiposDto_ViewModel,
    post_UsuariosViewModelLogeo: UsuariosViewModelLogeo,
    post_RevisionPreventivaViewModel: RevisionPreventivaViewModel,
    post_HistorialServiciosViewModel: HistorialServiciosViewModel,
    post_DetalleRevisionPreventivaViewModel: DetalleRevisionPreventivaViewModel,
    post_UbicacionesDto_ViewModel: Post_UbicacionesDto_ViewModel,
    post_UnidadesDto_ViewModel: Post_UnidadesDto_ViewModel,
    post_EstatusDto_ViewModel: Post_EstatusDto_ViewModel,
    post_TiposMaquinariasDto_ViewModel: Post_TiposMaquinariasDto_ViewModel,
) {
    // Login screen
    composable(Routes.LOGIN.name) {
        Post_LogIn(
            navController = navController,
            usuarioViewModelLogeo = post_UsuariosViewModelLogeo
        )
    }

    // Lista principal con parámetro de nombre
    composable("${Routes.LISTA_MAQUINARIAS.name}/{nombre}") { backStackEntry ->
        val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
        PantallaPrincipal(
            viewModel = post_MaquinariasYVehiculosDto_ViewModel,
            viewModelEquiposDto = post_EquiposDto_ViewModel,
            usuarioViewModelLogeo = post_UsuariosViewModelLogeo,
            nombre = nombre,
            navController = navController,
        )
    }

    // Lista principal sin parámetro de correo,
    // se eliminara el parametro de nombre para que la ruta
    // se utilize para regresar a la lista
    composable(Routes.LISTA_MAQUINARIAS.name) { backStackEntry ->
        /*val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
        PantallaPrincipal(
            viewModel = post_MaquinariasYVehiculosDto_ViewModel,
            viewModelEquiposDto = post_EquiposDto_ViewModel,
            usuarioViewModelLogeo = post_UsuariosViewModelLogeo,
            nombre = nombre,
            navController = navController,
        )*/
        PostListaTodasMaquiunarias(
            navController = navController,
            viewModel = post_MaquinariasYVehiculosDto_ViewModel,
            viewModelEquiposDto = post_EquiposDto_ViewModel,
        )
    }

    // Crear nueva maquinaria
    composable(Routes.CREATE_MAQUINARIA.name) { backStackEntry ->
        PostCreateTodasMaquiunarias(
            navController = navController,
            viewModel = post_EquiposDto_ViewModel,
            viewModelUbicaciones = post_UbicacionesDto_ViewModel,
            viewModelUnidades = post_UnidadesDto_ViewModel,
            viewModelEstatus = post_EstatusDto_ViewModel,
            viewModelTiposMaquinarias = post_TiposMaquinariasDto_ViewModel
        )
    }

    // Detalle de maquinaria con parámetro de ID
    composable("${Routes.DETALLE_MAQUINARIA.name}/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
        if (id != null) {
            Post_DetalleMaterialScreen(
                navController = navController,
                idEquipos = id,
                viewModelMAquinarias = post_MaquinariasYVehiculosDto_ViewModel,
                viewModelEquiposDto = post_EquiposDto_ViewModel
            )
        }
    }

    // Editar maquinaria con parámetro de ID
    composable("${Routes.EDIT_MAQUINARIA.name}/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
        if (id != null) {
            Post_EditMaquinariaScreen(
                navController = navController,
                id = id,
                viewModel = post_EquiposDto_ViewModel,
                viewModelUbicaciones = post_UbicacionesDto_ViewModel,
                viewModelUnidades = post_UnidadesDto_ViewModel,
                viewModelEstatus = post_EstatusDto_ViewModel,
                viewModelTiposMaquinarias = post_TiposMaquinariasDto_ViewModel
            )
        }
    }

    // Historial de servicio
    composable(Routes.SERVICE_HISTORIAL.name) {
        HistorialServiciosScreen(
            navController = navController,
            viewModel = post_HistorialServiciosViewModel

        )
    }

    // Crear nuevo servicio
    composable("${Routes.REVISIONES_PREVENTIVAS.name}/{id}") {
        val id = it.arguments?.getString("id")?.toIntOrNull()
        RevisionPreventivaScreen(
            navController = navController,
            viewModel = post_RevisionPreventivaViewModel
        )
    }

    composable("${Routes.DETALLE_REVISIONES_PREVENTIVAS.name}/{id}") {
        val id = it.arguments?.getString("id")?.toIntOrNull()
        if (id != null) {
            DetalleRevisionPreventivaScreen(
                navController = navController,
                revisionId = id,
                viewModel = post_DetalleRevisionPreventivaViewModel
            )
        }
    }
}
