package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.DetalleRevisionPreventivaViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.ItemRevisionDetalle
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.RevisionPreventivaDetalle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRevisionPreventivaScreen(
    navController: NavController,
    viewModel: DetalleRevisionPreventivaViewModel,
    revisionId: Int
) {
    val revisionDetalle by viewModel.revisionDetalle.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Cargar datos al iniciar la pantalla
    LaunchedEffect(revisionId) {
        viewModel.cargarDetalleRevision(revisionId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    "Detalle de Revisión Preventiva",
                    fontSize = 16.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                }
            }
        )

        // Contenido principal
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            Text(
                                text = errorMessage!!,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Button(
                                onClick = { viewModel.cargarDetalleRevision(revisionId) },
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
            }
            revisionDetalle != null -> {
                DetalleRevisionContent(revisionDetalle!!)
            }
        }
    }
}

@Composable
fun DetalleRevisionContent(detalle: RevisionPreventivaDetalle) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Información básica del equipo
        DetalleSection(title = "Descripción del equipo") {
            DetalleField(label = "Equipo:", value = detalle.equipo)
            DetalleField(label = "Marca/Modelo:", value = detalle.marcaModelo)
            DetalleField(label = "No. de serie:", value = detalle.numeroSerie)
            DetalleField(
                label = "Descripción detallada del equipo:",
                value = detalle.descripcionDetallada,
                isMultiline = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Empresa encargada
        DetalleSection(title = "Empresa encargada del mantenimiento") {
            DetalleField(label = "Nombre:", value = detalle.empresaEncargada)
            DetalleField(label = "Fecha de revisión programada:", value = detalle.fechaRevision)
            DetalleField(label = "No. de reporte:", value = detalle.numeroReporte)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Datos del técnico
        DetalleSection(title = "Datos del técnico encargado de la revisión") {
            DetalleField(label = "Nombre:", value = detalle.nombreTecnico)
            DetalleField(label = "Teléfono:", value = detalle.telefonoTecnico)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Revisión preventiva
        Text(
            text = "Revisión preventiva",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // Secciones de revisión
        DetalleRevisionSection(
            title = "Encendido",
            items = mapOf(
                "Batería" to detalle.revisionEncendido.bateria,
                "Fusión corriente" to detalle.revisionEncendido.fusionCorriente,
                "Fusibles y relays" to detalle.revisionEncendido.fusiblesRelays
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetalleRevisionSection(
            title = "Motor",
            items = mapOf(
                "Aceite" to detalle.revisionMotor.aceite,
                "Ventilador" to detalle.revisionMotor.ventilador,
                "Bandas" to detalle.revisionMotor.bandas
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetalleRevisionSection(
            title = "Lubricación",
            items = mapOf(
                "Aceite" to detalle.revisionLubricacion.aceite,
                "De engranajes" to detalle.revisionLubricacion.deEngranajes,
                "De transmisión" to detalle.revisionLubricacion.deTransmision
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetalleRevisionSection(
            title = "Filtros",
            items = mapOf(
                "Aire" to detalle.revisionFiltros.aire,
                "Aceite" to detalle.revisionFiltros.aceite,
                "Motor" to detalle.revisionFiltros.motor
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Descripción y conclusiones
        DetalleSection(title = "Descripción y Conclusiones") {
            DetalleField(
                label = "Descripción de lo que se realizó:",
                value = detalle.descripcionRealizado,
                isMultiline = true
            )
            DetalleField(
                label = "Conclusiones:",
                value = detalle.conclusiones,
                isMultiline = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Referentes
        DetalleSection(title = "Referentes a la revisión") {
            DetalleReferenteSection(
                title = "Realizó la revisión preventiva",
                nombre = detalle.nombreRevisionPreventiva,
                imagenINE = detalle.imagenINERevision
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetalleReferenteSection(
                title = "Vo.Bo Técnico de la revisión",
                nombre = detalle.nombreTecnicoRevision,
                imagenINE = detalle.imagenINETecnico
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetalleReferenteSection(
                title = "Autoriza mantto. Correctivo",
                nombre = detalle.nombreCorrectivo,
                imagenINE = detalle.imagenINECorrectivo
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun DetalleSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            content()
        }
    }
}

@Composable
fun DetalleField(
    label: String,
    value: String,
    isMultiline: Boolean = false
) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Text(
                text = value.ifEmpty { "Sin información" },
                fontSize = 14.sp,
                color = if (value.isEmpty()) Color.Gray else Color.Black,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun DetalleRevisionSection(
    title: String,
    items: Map<String, ItemRevisionDetalle>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Encabezados de la tabla
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Parte", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Estado", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Comentarios", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Observaciones", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            items.entries.forEachIndexed { index, (parte, item) ->
                DetalleRevisionItemRow(
                    parte = parte,
                    item = item
                )
                if (index < items.size - 1) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun DetalleRevisionItemRow(
    parte: String,
    item: ItemRevisionDetalle
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = parte,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = when (item.estado) {
                    "Bueno" -> Color(0xFFE8F5E8)
                    "Regular" -> Color(0xFFFFF3E0)
                    "Malo" -> Color(0xFFFFEBEE)
                    else -> Color.White
                }
            )
        ) {
            Text(
                text = item.estado.ifEmpty { "Sin evaluar" },
                modifier = Modifier.padding(8.dp),
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = when (item.estado) {
                    "Bueno" -> Color(0xFF2E7D32)
                    "Regular" -> Color(0xFFE65100)
                    "Malo" -> Color(0xFFC62828)
                    else -> Color.Gray
                }
            )
        }

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = item.comentarios.ifEmpty { "-" },
                modifier = Modifier.padding(8.dp),
                fontSize = 10.sp,
                color = if (item.comentarios.isEmpty()) Color.Gray else Color.Black
            )
        }

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = item.observaciones.ifEmpty { "-" },
                modifier = Modifier.padding(8.dp),
                fontSize = 10.sp,
                color = if (item.observaciones.isEmpty()) Color.Gray else Color.Black
            )
        }
    }
}

@Composable
fun DetalleReferenteSection(
    title: String,
    nombre: String,
    imagenINE: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            DetalleField(label = "Nombre:", value = nombre)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Imagen de INE:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (imagenINE.isNotEmpty()) Color(0xFFE8F5E8) else Color(0xFFFFEBEE)
                    )
                ) {
                    Text(
                        text = if (imagenINE.isNotEmpty()) "Imagen disponible" else "Sin imagen",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(8.dp),
                        color = if (imagenINE.isNotEmpty()) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }
        }
    }
}