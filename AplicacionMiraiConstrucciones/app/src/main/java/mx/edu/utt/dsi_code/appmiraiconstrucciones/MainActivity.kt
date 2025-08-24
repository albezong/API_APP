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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.RetrofitClient
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_MaquinariasYVehiculosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_info
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_search
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.NavigationHost
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.APPMiraiConstruccionesTheme
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel_Factory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = Post_MaquinariasYVehiculosDto_Repository(RetrofitClient.apiService)
        val viewModelFactory = Post_MaquinariasYVehiculosDto_ViewModel_Factory(repository)
        val postViewModel = ViewModelProvider(this, viewModelFactory)[Post_MaquinariasYVehiculosDto_ViewModel::class.java]

        setContent {
            APPMiraiConstruccionesTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "post_list") {
                    composable("post_list") {
                        PantallaPrincipal(viewModel = postViewModel, navController = navController)
                    }
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PantallaPrincipal(
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    navController: androidx.navigation.NavHostController
) {
    val navController = rememberNavController()
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
                menu_items = navigationItems
            )
        }
    ) {
        //PostListScreen_list(navController = navController, viewModel = viewModel)
        NavigationHost(navController = navController, viewModel = viewModel)
    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(
        title = { androidx.compose.material.Text(text = "MIRAI CONSTRUCCIONES") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icono de menú")
            }
        }
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    menu_items: List<Destinos>) {
    /*
    val menuItems = listOf(
        "Tu mascota",
        "Contenidos",
        "Busca una mascota",
        "Eventos",
        "Premium"
    )
    */

    Column(){
        Image(
            painterResource(id = R.drawable.baseline_account_circle_24),
            contentDescription = "Menu de ociones",
            modifier = Modifier
                .size(65.dp)
                .clip(RoundedCornerShape(50)), // opcional, para que quede redonda
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(15.dp))

        menu_items.forEach { item ->
            DrawerItem(item = item){
                navController.navigate(item.ruta){
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
fun DrawerItem(item: Destinos,
               onItemClick: (Destinos)->Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12))
            .padding(8.dp)
            .clickable { onItemClick(item) }
    ){
        Image(
            painterResource(id = item.icon),
            contentDescription = item.title)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = item.title,
            style = MaterialTheme.typography.body1)
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    APPMiraiConstruccionesTheme {
        // Simulación de datos para el preview
        val dummyViewModel = Post_MaquinariasYVehiculosDto_ViewModel(Post_MaquinariasYVehiculosDto_Repository(RetrofitClient.apiService))
        val navController = rememberNavController()
        PantallaPrincipal(viewModel = dummyViewModel, navController = navController)
    }
}
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