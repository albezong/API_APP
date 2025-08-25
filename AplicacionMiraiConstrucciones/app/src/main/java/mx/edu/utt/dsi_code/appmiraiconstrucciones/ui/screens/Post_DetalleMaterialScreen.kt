package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.R
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_MaquinariasYVehiculosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleMaterialScreen(
    navController: NavController,
    id: Int,
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel
) {
    val material by viewModel.selectedPost.collectAsState()

    // Pedimos el detalle al entrar
    LaunchedEffect(id) {
        viewModel.getPostById(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalle de maquinaria",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Contenedor desplazable
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (material == null) {
                // Estado de carga simple
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
                // Contenido del detalle
                val m = material!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Header: nombre y código
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = m.nombre_articulo ?: "—",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Código: ${m.codigo_articulo ?: "—"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Card con datos en dos columnas
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Row con dos columnas
                            @Composable
                            fun KeyValueRow(label: String, value: String) {
                                Row(
                                    modifier = Modifier
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Descripción (sección separada)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Descripción", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = m.descripcion ?: "Sin descripción",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lugar resaltado
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Lugar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Se encuentra en", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text(
                                    text = m.nombreLugar ?: "Sin lugar asignado",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de acción (ejemplo: ir a mapa o editar)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Button(onClick = { /* acción extra, por ejemplo editar o mostrar en mapa */ }) {
                            Text("Acciones")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
