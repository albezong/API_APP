package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

/*
data class Material(
    val nombre: String,
    val cantidad: String,
    val unidad: String,
    val codigo: String,
    val categoria: String
)*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListaTodasMaquiunarias(
    navController: NavController,
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,
) {
    // Dentro de tu Composable
    val posts = viewModel.posts.collectAsState(initial = emptyList()).value

    //Esto toma todos los items de cada Post_MaquinariasYVehiculosDto
    val materiales = posts.flatMap { it.items }


    var search by remember { mutableStateOf("") }

    // Cuando entra la pantalla, dispara la carga inicial
    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de toda la Maquinaria y/o equipo, herramientas y consumibles") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                    // cada vez que cambia, vuelve a buscar filtrado
                    //viewModel.fetchPosts(search = it)
                },
                label = { Text("Buscar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                // Agrupamos por tipo o categoría si tu DTO lo tiene (ejemplo: it.tipo)
                //val agrupados = materiales.groupBy { it.id_equipos ?: "General" }
                //agrupados.forEach { (categoria, lista) ->
                /*
                item {
                    Text(
                        text = categoria,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }*/

                items(materiales) { material ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Maquinaria/Equipo: ${material.nombre_comercial ?: "-"}")
                            Text(text = "Cantidad: 1")
                            Text(text = "Unidad: ${material.unidad_nombre ?: "-"}")
                            Text(text = "Código: ${material.codigo_articulo ?: "-"}")
                        }

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable {
                                    // navegación al detalle con el id
                                    navController.navigate("detalle/${material.id_equipos}")
                                }
                        )
                    }
                    Divider()

                }
            }
        }
    }
}
