package mx.edu.utt.dsi_code.appmiraiconstrucciones

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
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

import androidx.compose.material.*

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.RetrofitClient
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EquiposDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_MaquinariasYVehiculosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UsuariosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_info
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_search
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.NavigationHost
//import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.DetalleMaterialScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.APPMiraiConstruccionesTheme
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.Grey80
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UsuariosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UsuariosDto_ViewModel_Factory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelFactory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- crear repos y factories fuera de setContent ---
        val repoMaquinas = Post_MaquinariasYVehiculosDto_Repository(RetrofitClient.apiService)
        val factoryMaquinas = Post_MaquinariasYVehiculosDto_ViewModel_Factory(repoMaquinas)
        val postMaquinasVm = ViewModelProvider(this, factoryMaquinas)[Post_MaquinariasYVehiculosDto_ViewModel::class.java]

        val repoEquipos = Post_EquiposDto_Repository(RetrofitClient.apiService)
        // Si tienes un factory para equipos:
        val factoryEquipos = Post_EquiposDto_ViewModel_Factory(repoEquipos)
        val postEquiposVm = ViewModelProvider(this, factoryEquipos)[Post_EquiposDto_ViewModel::class.java]

        val usuariosRepo = Post_UsuariosDto_Repository(RetrofitClient.apiService)
        val usuariosFactory = UsuariosViewModelFactory(usuariosRepo) // o Post_UsuariosDto_ViewModel_Factory según tu implementación
        val usuariosVm = ViewModelProvider(this, usuariosFactory)[UsuariosViewModelLogeo::class.java]

        // --- sólo UI dentro de setContent ---
        setContent {
            APPMiraiConstruccionesTheme {
                val navController = rememberNavController()

                NavigationHost(
                    postMaquinasViewModel = postMaquinasVm,
                    postEquiposViewModel = postEquiposVm,
                    usuariosViewModel = usuariosVm,
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