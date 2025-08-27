package mx.edu.utt.dsi_code.appmiraiconstrucciones

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
//-------------------------------------------------
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch
//-------------------------------------------------
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.RetrofitClient
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EquiposDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EstatusDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_MaquinariasYVehiculosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_TiposMaquinariasDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UbicacionesDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UnidadesDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UsuariosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_info
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_search
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.NavigationHost
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.*
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.*
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EstatusDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EstatusDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_TiposMaquinariasDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_TiposMaquinariasDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UbicacionesDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UbicacionesDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UnidadesDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UnidadesDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelFactory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Repos + factories + viewmodels (instancias)
        val repoMaquinas = Post_MaquinariasYVehiculosDto_Repository(RetrofitClient.apiService)
        val factoryMaquinas = Post_MaquinariasYVehiculosDto_ViewModel_Factory(repoMaquinas)
        val postMaquinasVm = ViewModelProvider(this, factoryMaquinas)[Post_MaquinariasYVehiculosDto_ViewModel::class.java]

        val repoEquipos = Post_EquiposDto_Repository(RetrofitClient.apiService)
        val factoryEquipos = Post_EquiposDto_ViewModel_Factory(repoEquipos)
        val postEquiposVm = ViewModelProvider(this, factoryEquipos)[Post_EquiposDto_ViewModel::class.java]

        val usuariosRepo = Post_UsuariosDto_Repository(RetrofitClient.apiService)
        val usuariosFactory = UsuariosViewModelFactory(usuariosRepo)
        val usuariosVm = ViewModelProvider(this, usuariosFactory)[UsuariosViewModelLogeo::class.java]

        val ubicacionesRepo = Post_UbicacionesDto_Repository(RetrofitClient.apiService)
        val ubicacionesFactory = Post_UbicacionesDto_ViewModel_Factory(ubicacionesRepo)
        val ubicacionesVm = ViewModelProvider(this, ubicacionesFactory)[Post_UbicacionesDto_ViewModel::class.java]

        val unidadesRepo = Post_UnidadesDto_Repository(RetrofitClient.apiService)
        val unidadesFactory = Post_UnidadesDto_ViewModel_Factory(unidadesRepo)
        val unidadesVm = ViewModelProvider(this, unidadesFactory)[Post_UnidadesDto_ViewModel::class.java]

        val estatusRepo = Post_EstatusDto_Repository(RetrofitClient.apiService)
        val estatusFactory = Post_EstatusDto_ViewModel_Factory(estatusRepo)
        val estatusVm = ViewModelProvider(this, estatusFactory)[Post_EstatusDto_ViewModel::class.java]

        val tiposMaquinariasRepo = Post_TiposMaquinariasDto_Repository(RetrofitClient.apiService)
        val tiposMaquinariasFactory = Post_TiposMaquinariasDto_ViewModel_Factory(tiposMaquinariasRepo)
        val tiposMaquinariasVm = ViewModelProvider(this, tiposMaquinariasFactory)[Post_TiposMaquinariasDto_ViewModel::class.java]

        setContent {
            APPMiraiConstruccionesTheme {
                val navController = rememberNavController()

                NavigationHost(
                    postMaquinasViewModel = postMaquinasVm,
                    postEquiposViewModel = postEquiposVm,
                    usuariosViewModel = usuariosVm,
                    viewModelUbicaciones = ubicacionesVm,
                    viewModelUnidades = unidadesVm,
                    viewModelEstatus = estatusVm,
                    viewModelTiposMaquinarias = tiposMaquinariasVm,
                    navController = navController
                )
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PantallaPrincipal(
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    navController: androidx.navigation.NavHostController,
    nombre: String,
) {
    //val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navigationItems = listOf(
        ico_info,
        ico_search,
    )

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope, scaffoldState) },
        drawerContent = {
            Drawer(
                scope,
                scaffoldState,
                navController,
                menu_items = navigationItems,
                nombre = nombre
            )
        }
    ) { innerPadding ->
        // contenido principal: por ejemplo tu lista
        PostListaTodasMaquiunarias(navController = navController, viewModel = viewModel)
    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
) {
    TopAppBar(
        title = { androidx.compose.material.Text(text = "MIRAI CONSTRUCCIONES") },
        /*colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF009688) // aquí pones el color que quieras
        ),*/
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icono de menú")
            }
        },
        backgroundColor = Grey80
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    menu_items: List<Destinos>,
    nombre: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey80) // 👈 Fondo con tu color Grey80
            .padding(16.dp)     // opcional, para dar espacio interno
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de usuario
            Icon(
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = "Usuario",
                tint = Color.Black, // 👈 icono en negro
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Texto del usuario
            Text(
                text = "Hola, $nombre",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black, // 👈 texto en negro
                modifier = Modifier.weight(1f) // ocupa el espacio libre
            )

            // Botón salir (solo el ícono)
            IconButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("lista_maquinarias") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                    contentDescription = "Salir",
                    tint = Color.Black, // 👈 icono en negro
                    modifier = Modifier.size(24.dp)
                )
            }
        }






        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )

        menu_items.forEach { item ->
            DrawerItem(item = item) {
                navController.navigate(item.ruta) {
                    launchSingleTop = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }
    }
}


@Composable
fun DrawerItem(
    item: Destinos,
    onItemClick: (Destinos) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12))
            .padding(8.dp)
            .clickable { onItemClick(item) }
    ) {
        Image(
            painterResource(id = item.icon),
            contentDescription = item.title
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.body1
        )
    }
}


/*/
@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    APPMiraiConstruccionesTheme {
        // Simulación de datos para el preview
        val dummyViewModel = Post_MaquinariasYVehiculosDto_ViewModel(
            Post_MaquinariasYVehiculosDto_Repository(RetrofitClient.apiService)
        )
        val navController = rememberNavController()
        PantallaPrincipal(viewModel = dummyViewModel, navController = navController)
    }
}*/
/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APPMiraiConstruccionesTheme {
        Greeting("Android")
    }
}*/