package mx.edu.utt.dsi_code.appmiraiconstrucciones

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

@Composable
fun PantallaPrincipal(
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    navController: NavController,
    nombre: String,
) {
    // Esta función ahora se simplifica ya que el Scaffold y Drawer
    // se manejan en el MainActivity
    PostListaTodasMaquiunarias(
        navController = navController,
        viewModel = viewModel,
    )
}