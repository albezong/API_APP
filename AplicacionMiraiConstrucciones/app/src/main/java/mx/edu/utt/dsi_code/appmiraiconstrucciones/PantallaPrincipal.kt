package mx.edu.utt.dsi_code.appmiraiconstrucciones

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.components.AppDrawer
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PantallaPrincipal(
    navController: NavController,
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    viewModelEquiposDto: Post_EquiposDto_ViewModel,
    usuarioViewModelLogeo: UsuariosViewModelLogeo,
    nombre: String,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope, scaffoldState) },
        drawerContent = { AppDrawer(
            scope = scope,
            scaffoldState = scaffoldState,
            navController = navController,
            nombre = nombre,
            modifier = Modifier
        ) }
        ) { paddingValues ->
        // pasar el padding si quieres: Modifier.padding(paddingValues)
        PostListaTodasMaquiunarias(
            navController = navController,
            viewModel = viewModel,
            viewModelEquiposDto = viewModelEquiposDto,
        )

        /*Post_LogIn(
            navController = navController,
            usuarioViewModelLogeo = usuarioViewModelLogeo,
        )*/
    }
        //PostListScreen_list(navController = navController, viewModel = viewModel)
        //NavigationHost(navController = navController, viewModel = viewModel)


    // Esta función ahora se simplifica ya que el Scaffold y Drawer
    // se manejan en el MainActivity
    /*PostListaTodasMaquiunarias(
        navController = navController,
        viewModel = viewModel,
        viewModelEquiposDto = viewModelEquiposDto
    )*/
}