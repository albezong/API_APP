package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.R
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_MaquinariasYVehiculosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TiposMaquinariasDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UnidadesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.Grey80
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

@Composable
fun PostListaTodasMaquiunarias(
    navController: NavController,
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    viewModelEquiposDto: Post_EquiposDto_ViewModel,
) {
    val posts by viewModel.posts.collectAsState()
    var query by remember { mutableStateOf("") }

    // Llamamos a la API al cargar el Composable
    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Caja con peso para centrar el texto en todo el ancho disponible
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Lista de toda la Maquinaria y /o equipo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            // Botón para agregar — area táctil correcta y navegación
            IconButton(
                onClick = {
                    //navController.navigate("post_maquinarias")
                    navController.navigate(Routes.CREATE_MAQUINARIA.name)
                },
                modifier = Modifier.size(48.dp) // área táctil
            ) {
                Icon(
                    //imageVector = Icons.Default.Add
                    painterResource(R.drawable.baseline_add_circle_outline_24),
                    contentDescription = "Agregar maquinaria",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre título y barra

        // Barra de búsqueda
        CustomizableSearchBar(
            query = query,
            onQueryChange = { newQuery ->
                query = newQuery
                viewModel.fetchPosts(search = newQuery)
            },
            onSearch = { searchText ->
                viewModel.fetchPosts(search = searchText)
            },
            posts = posts,
            onResultClick = { selected ->
                //Log.d("Formulario", "PrimerLog_idEquipos: ${selected.idEquipos}")
                if (selected.idEquipos != null && selected.idEquipos != 0) {
                    //navController.navigate("detalle_maquinaria/${selected.idEquipos}")
                    navController.navigate("${Routes.DETALLE_MAQUINARIA.name}/${selected.idEquipos}")
                    // antes: navController.navigate("detalle_maquinaria/${selected.idEquipos}")
                    //navController.navigate("${Routes.DETALLE_MAQUINARIA.name}/${selected.idEquipos}")



                    //Log.d("Formulario", "PrimerLog_idEquipos: ${selected.idEquipos}")
                } else {
                    //Log.w("Nav", "ID inválido al navegar: ${selected.idEquipos}")
                }
            },
            placeholder = { Text("Buscar maquinaria...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizableSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    posts: List<Maquinaria>,
    onResultClick: (Maquinaria) -> Unit,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            //.fillMaxSize()
            .padding(16.dp),
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChange(it) },
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // importante: evita fillMaxSize anidado
        ) {
            items(posts, key = { it.idEquipos ?: it.hashCode() }) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            onResultClick(post)
                        },
                ) {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        //.background(Color.Gray)
                    ) {
                        Text(
                            text = post.nombreArticulo,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = post.descripcion ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
