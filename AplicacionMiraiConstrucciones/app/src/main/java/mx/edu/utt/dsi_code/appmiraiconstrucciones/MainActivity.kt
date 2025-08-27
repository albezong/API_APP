package mx.edu.utt.dsi_code.appmiraiconstrucciones

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.RetrofitClient
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_MaquinariasYVehiculosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UsuariosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.HistorialServiciosRepository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.RevisionesRepository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.components.AppDrawer
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.NavigationHost
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.utils.NavigationUtils
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.APPMiraiConstruccionesTheme
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.Grey80
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👇 edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Inicialización de repositorios
        val apiService = RetrofitClient.apiService

        // Repositorio de maquinarias
        val repoMaquinas = Post_MaquinariasYVehiculosDto_Repository(apiService)
        val factoryMaquinas = Post_MaquinariasYVehiculosDto_ViewModel_Factory(repoMaquinas)
        val postViewModel = ViewModelProvider(this, factoryMaquinas)[Post_MaquinariasYVehiculosDto_ViewModel::class.java]

        // Repositorio de usuarios
        val usuariosRepo = Post_UsuariosDto_Repository(apiService)
        val usuariosFactory = UsuariosViewModelFactory(usuariosRepo)
        val usuariosViewModel = ViewModelProvider(this, usuariosFactory)[UsuariosViewModelLogeo::class.java]

        // Repositorio de historial de servicios
        val historialServiciosRepo = HistorialServiciosRepository(apiService)
        val historialServiciosFactory = HistorialServiciosViewModelFactory(historialServiciosRepo)
        val historialServiciosViewModel = ViewModelProvider(this, historialServiciosFactory)[HistorialServiciosViewModel::class.java]

        // Repositorio de revisiones
        val revisionesRepo = RevisionesRepository(apiService)
        val revisionPreventivaFactory = RevisionPreventivaViewModelFactory(revisionesRepo)
        val revisionPreventivaViewModel = ViewModelProvider(this, revisionPreventivaFactory)[RevisionPreventivaViewModel::class.java]

        // ViewModel de detalle de revisión preventiva
        val detalleRevisionFactory = DetalleRevisionPreventivaViewModelFactory(revisionesRepo)
        val detalleRevisionPreventivaViewModel = ViewModelProvider(this, detalleRevisionFactory)[DetalleRevisionPreventivaViewModel::class.java]

        setContent {
            APPMiraiConstruccionesTheme {
                AppMainScreen(
                    postMaquinasViewModel = postViewModel,
                    usuariosViewModel = usuariosViewModel,
                    revisionPreventivaViewModel = revisionPreventivaViewModel,
                    historialServiciosViewModel = historialServiciosViewModel,
                    detalleRevisionPreventivaViewModel = detalleRevisionPreventivaViewModel
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppMainScreen(
    postMaquinasViewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    usuariosViewModel: UsuariosViewModelLogeo,
    revisionPreventivaViewModel: RevisionPreventivaViewModel,
    historialServiciosViewModel: HistorialServiciosViewModel,
    detalleRevisionPreventivaViewModel: DetalleRevisionPreventivaViewModel
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var userName by remember { mutableStateOf("Usuario") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (NavigationUtils.shouldShowTopBar(currentRoute)) {
                TopAppBar(
                    title = { Text(text = NavigationUtils.getTopBarTitle(currentRoute)) },
                    navigationIcon = if (NavigationUtils.shouldShowDrawer(currentRoute)) {
                        {
                            IconButton(onClick = {
                                scope.launch { scaffoldState.drawerState.open() }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menú")
                            }
                        }
                    } else null,
                    backgroundColor = Grey80,
                    modifier = Modifier.statusBarsPadding()
                )
            }
        },
        drawerContent = if (NavigationUtils.shouldShowDrawer(currentRoute)) {
            {
                AppDrawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    nombre = userName
                )
            }
        } else null,
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavigationHost(
                postMaquinasViewModel = postMaquinasViewModel,
                usuariosViewModel = usuariosViewModel,
                revisionPreventivaViewModel = revisionPreventivaViewModel,
                historialServiciosViewModel = historialServiciosViewModel,
                detalleRevisionPreventivaViewModel = detalleRevisionPreventivaViewModel,
                navController = navController
            )
        }
    }
}