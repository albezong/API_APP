// imports: asegúrate de usar sólo material3 aquí
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_MaquinariasYVehiculosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

@Composable
fun KeyValueRow(label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleMaterialScreen(
    navController: NavHostController,
    id: Int,
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel
) {
    val material by viewModel.selectedPost.collectAsState()
    LaunchedEffect(id) { viewModel.getPostById(id) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Detalle de maquinaria", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (material == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Cargando detalle...", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                val m = material!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // header, cards...
                    Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = m.nombre_articulo ?: "—", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Código: ${m.codigo_articulo ?: "—"}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            KeyValueRow("Nombre comercial", m.nombre_comercial ?: "—")
                            KeyValueRow("Número identificador", m.num_identificador ?: "—")
                            KeyValueRow("Marca", m.marca ?: "—")
                            KeyValueRow("Modelo", m.modelo ?: "—")
                            KeyValueRow("Fecha registro", m.fechade_registro ?: "—")
                            KeyValueRow("Ubicación", m.ubicacion_nombre ?: "—")
                            KeyValueRow("Unidad", m.unidad_nombre ?: "—")
                            KeyValueRow("Estatus", m.estatus_nombre ?: "—")
                            KeyValueRow("Tipo maquinaria", m.tipo_maquinaria_nombre ?: "—")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // dentro de DetalleMaterialScreen, reemplaza la Row/Button existente
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Button(onClick = {
                            val idEquipos = m.id_equipos
                            if (idEquipos == null || idEquipos == 0) {
                                // opcional: mostrar snackbar o Toast en lugar de navegar
                                // Toast.makeText(context, "ID inválido para edición", Toast.LENGTH_SHORT).show()
                            } else {
                                navController.navigate("edit_maquinaria/$idEquipos") {
                                    launchSingleTop = true
                                }
                            }
                        }) {
                            Text("Editar Maquinaria")
                        }
                    }


                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
