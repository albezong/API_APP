
package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.DetalleRevisionPreventivaViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.HistorialServiciosViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.RevisionPreventivaViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo

@Composable
fun NavigationHost(
    postMaquinasViewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    usuariosViewModel: UsuariosViewModelLogeo,
    revisionPreventivaViewModel: RevisionPreventivaViewModel,
    historialServiciosViewModel: HistorialServiciosViewModel,
    detalleRevisionPreventivaViewModel: DetalleRevisionPreventivaViewModel,

    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN.name
    ) {
        appRoutes(
            navController = navController,
            postMaquinasViewModel = postMaquinasViewModel,
            usuariosViewModel = usuariosViewModel,
            revisionPreventivaViewModel = revisionPreventivaViewModel,
            historialServiciosViewModel = historialServiciosViewModel,
            detalleRevisionPreventivaViewModel = detalleRevisionPreventivaViewModel
        )
    }
}
