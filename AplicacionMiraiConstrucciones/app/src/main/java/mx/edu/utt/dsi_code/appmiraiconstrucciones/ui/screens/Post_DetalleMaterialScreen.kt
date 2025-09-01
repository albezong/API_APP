package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

// imports: asegúrate de usar sólo material3 aquí
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.R
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_MaquinariasYVehiculosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
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
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post_DetalleMaterialScreen(
    navController: NavController,
    idEquipos: Int,
    viewModelMAquinarias: Post_MaquinariasYVehiculosDto_ViewModel,
    viewModelEquiposDto: Post_EquiposDto_ViewModel,
) {
    //val postEquiposDto by viewModelEquiposDto.selectedPost.collectAsState()
    /*val isLoading by viewModelEquiposDto.isLoadingSelected.collectAsState()
    val errorMsg by viewModelEquiposDto.errorSelected.collectAsState()*/
    //val postLugaresDto by viewModelEquiposDto.lugares.collectAsState()
    // Dentro del Composable (donde ya tienes 'm' definido)
    val postVMaquinarias by viewModelMAquinarias.selectedPost.collectAsState()

    var showConfirmDelete by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    val uiScope = rememberCoroutineScope()

    val post by viewModelMAquinarias.selectedPost.collectAsState()
    val isLoading by viewModelMAquinarias.isLoading.collectAsState(initial = false) // añade en VM
    val error by viewModelMAquinarias.error.collectAsState()

    LaunchedEffect(idEquipos) {
        if (idEquipos > 0) viewModelMAquinarias.getPostById(idEquipos)
    }

    when {
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        error != null -> Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Error: ${error}", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(12.dp))
            Button(onClick = { viewModelMAquinarias.getPostById(idEquipos) }) { Text("Reintentar") }
        }

        post == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No se encontró la maquinaria.", color = Color.Gray)
        }

        else -> {
            val m = post!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = m.nombreArticulo ?: "—",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Código: ${m.codigoArticulo ?: "—"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        KeyValueRow("Nombre comercial", m.nombreComercial ?: "—")
                        KeyValueRow("Número identificador", m.numIdentificador ?: "—")
                        KeyValueRow("Marca", m.marca ?: "—")
                        KeyValueRow("Modelo", m.modelo ?: "—")
                        KeyValueRow("Fecha registro", m.fechadeRegistro ?: "—")
                        KeyValueRow("Ubicación", m.ubicacionNombre)
                        KeyValueRow("Unidad", m.unidadNombre)
                        KeyValueRow("Estatus", m.estatusNombre)
                        KeyValueRow("Tipo maquinaria", m.tipoMaquinariaNombre)
                        KeyValueRow("Descripción", m.descripcion)
                        //KeyValueRow("Lugares", lugaresConcatenados)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // --- Botón Editar ---
                    Button(
                        onClick = {
                            val id = m.idEquipos
                            if (id != null && id != 0) {
                                navController.navigate("${Routes.EDIT_MAQUINARIA.name}/$id") {
                                    launchSingleTop = true
                                }
                            }
                        },
                        enabled = !isDeleting,
                        modifier = Modifier
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0E0E0), // gris claro del fondo del botón
                            contentColor = Color.Black // color por defecto del contenido (icono)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_edit_24),
                            contentDescription = "Editar",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black // forzamos icono negro
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Editar",
                            color = Color(0xFFFFEB3B), // amarillo (puedes cambiar por Color.Yellow o hex)
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // --- Botón Eliminar ---
                    Button(
                        onClick = { showConfirmDelete = true },
                        enabled = !isDeleting,
                        modifier = Modifier
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0E0E0), // mismo gris claro
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_outline_24),
                            contentDescription = "Eliminar",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black // icono negro
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Eliminar",
                            color = Color.Red,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Diálogo de confirmación
                if (showConfirmDelete) {
                    AlertDialog(
                        onDismissRequest = { if (!isDeleting) showConfirmDelete = false },
                        title = { Text("Confirmar eliminación") },
                        text = {
                            Column {
                                Text("¿Estás seguro que deseas eliminar esta maquinaria? Esta acción no se puede deshacer.")
                                if (isDeleting) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CircularProgressIndicator(
                                            strokeWidth = 2.dp,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Eliminando...",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                // Iniciamos la eliminación en una coroutine
                                showConfirmDelete = false
                                uiScope.launch {
                                    isDeleting = true
                                    val id = m.idEquipos
                                    var success = false
                                    if (id != null && id != 0) {
                                        // Llamada suspend al ViewModel que eliminó y refrescó la lista
                                        success = viewModelEquiposDto.deletePostAndRefresh(id)
                                    }
                                    isDeleting = false

                                    if (success) {
                                        // Navegar hacia la lista y asegurarse de que la pantalla de lista quede visible
                                        // (ajusta la ruta si tu pantalla de lista tiene otro nombre)
                                        navController.navigate(Routes.LISTA_MAQUINARIAS.name) {
                                            popUpTo(Routes.LISTA_MAQUINARIAS.name) {
                                                inclusive = false
                                            }
                                            launchSingleTop = true
                                        }
                                    } else {
                                        // Si quieres mostrar feedback visual pasa a usar Scaffold + SnackbarHostState.
                                        // De momento sólo mostramos un log (puedes reemplazar por Snackbar).

                                        //android.util.Log.e("DetalleScreen", "Error al eliminar el equipo id=$id")
                                        // podrías mostrar Snackbar/Toast aquí
                                        Log.e(
                                            "DetalleScreen",
                                            "Error al eliminar id=${m.idEquipos}"
                                        )
                                    }
                                }
                            }) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { if (!isDeleting) showConfirmDelete = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // --- Botón Editar ---
                    Button(
                        onClick = {
                        },
                        //enabled = !isDeleting,
                        modifier = Modifier
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0E0E0), // gris claro del fondo del botón
                            contentColor = Color.Black // color por defecto del contenido (icono)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_file_download_24),
                            contentDescription = "descargar",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black // forzamos icono negro
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "QR y detalle de maquinaria PDF",
                            color = Color(0xFF88E788), // amarillo (puedes cambiar por Color.Yellow o hex)
                            // #88E788
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}