package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
import androidx.navigation.NavHostController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.RevisionPreventivaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisionPreventivaScreen(
    navController: NavController,
    viewModel: RevisionPreventivaViewModel,
    equipoId: Int? = null,
    revisionId: Int? = null
) {
    // Determinar si es modo edición
    val isEditMode = revisionId != null

    // Estados para los campos del formulario
    var equipo by remember { mutableStateOf("") }
    var marcaModelo by remember { mutableStateOf("") }
    var numeroSerie by remember { mutableStateOf("") }
    var descripcionDetallada by remember { mutableStateOf("") }
    var empresaEncargada by remember { mutableStateOf("COMERCIALIZADORA BARRIOS S.A DE C.V") }
    var fechaRevision by remember { mutableStateOf("") }
    var numeroReporte by remember { mutableStateOf("") }
    var nombreTecnico by remember { mutableStateOf("") }
    var telefonoTecnico by remember { mutableStateOf("") }
    var descripcionRealizado by remember { mutableStateOf("") }
    var conclusiones by remember { mutableStateOf("") }

    // Estados para las secciones de revisión
    var revisionEncendido by remember { mutableStateOf(RevisionEncendido()) }
    var revisionMotor by remember { mutableStateOf(RevisionMotor()) }
    var revisionLubricacion by remember { mutableStateOf(RevisionLubricacion()) }
    var revisionFiltros by remember { mutableStateOf(RevisionFiltros()) }

    // Estados para los referentes
    var nombreRevisionPreventiva by remember { mutableStateOf("") }
    var imagenINERevision by remember { mutableStateOf("") }
    var nombreTecnicoRevision by remember { mutableStateOf("") }
    var imagenINETecnico by remember { mutableStateOf("") }
    var nombreCorrectivo by remember { mutableStateOf("") }
    var imagenINECorrectivo by remember { mutableStateOf("") }

    // Estados del ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val revisionData by viewModel.revisionData.collectAsState()

    // Estado para mostrar mensajes de error
    var showErrorSnackbar by remember { mutableStateOf(false) }

    // Cargar datos si es modo edición
    LaunchedEffect(revisionId) {
        if (isEditMode && revisionId != null) {
            viewModel.cargarDatosRevision(revisionId)
        } else if (equipoId != null) {
            viewModel.cargarDatosEquipo(equipoId)
        }
    }

    // Actualizar campos cuando se cargan los datos - CORREGIDO
    LaunchedEffect(revisionData) {
        revisionData?.let { data ->
            // Extraer información de la descripción si está estructurada
            val descripcion = data.descripcion ?: ""

            // Parsear la descripción estructurada si es posible
            val lines = descripcion.split("\n")
            lines.forEach { line ->
                when {
                    line.startsWith("Equipo:") -> equipo = line.substringAfter("Equipo:").trim()
                    line.startsWith("Marca/Modelo:") -> marcaModelo = line.substringAfter("Marca/Modelo:").trim()
                    line.startsWith("No. Serie:") -> numeroSerie = line.substringAfter("No. Serie:").trim()
                    line.startsWith("Descripción:") -> descripcionDetallada = line.substringAfter("Descripción:").trim()
                    line.startsWith("Técnico:") -> nombreTecnico = line.substringAfter("Técnico:").trim()
                    line.startsWith("Teléfono:") -> telefonoTecnico = line.substringAfter("Teléfono:").trim()
                    line.startsWith("No. Reporte:") -> numeroReporte = line.substringAfter("No. Reporte:").trim()
                    line.startsWith("Descripción:") && line.contains("TRABAJO REALIZADO") -> {
                        descripcionRealizado = line.substringAfter("Descripción:").trim()
                    }
                }
            }

            // Obtener nombres usando las funciones del ViewModel
            data.idfEquipos?.let { id ->
                equipo = viewModel.getNombreEquipo(id)
            }

            data.idfEmpresas?.let { id ->
                empresaEncargada = viewModel.getNombreEmpresa(id)
            }

            fechaRevision = data.fecha ?: ""
        }
    }

    // Manejar éxito al guardar
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            navController.popBackStack()
            viewModel.resetSaveSuccess()
        }
    }

    // Mostrar errores - CORREGIDO
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            showErrorSnackbar = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditMode)
                            "Editar revisión preventiva"
                        else
                            "Revisión programada para mantenimiento preventivo",
                        fontSize = 16.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Descripción del equipo
                    Text(
                        text = "Descripción del equipo",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Información básica del equipo
                    OutlinedTextField(
                        value = equipo,
                        onValueChange = { equipo = it },
                        label = { Text("Equipo:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = marcaModelo,
                        onValueChange = { marcaModelo = it },
                        label = { Text("Marca/Modelo:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = numeroSerie,
                        onValueChange = { numeroSerie = it },
                        label = { Text("No. de serie:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = descripcionDetallada,
                        onValueChange = { descripcionDetallada = it },
                        label = { Text("Descripción detallada del equipo:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        minLines = 2
                    )

                    // Empresa encargada del mantenimiento
                    Text(
                        text = "Empresa encargada del mantenimiento",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = empresaEncargada,
                        onValueChange = { empresaEncargada = it },
                        label = { Text("Nombre:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = fechaRevision,
                        onValueChange = { fechaRevision = it },
                        label = { Text("Fecha de revisión programada:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        trailingIcon = {
                            Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                        }
                    )

                    OutlinedTextField(
                        value = numeroReporte,
                        onValueChange = { numeroReporte = it },
                        label = { Text("No. de reporte:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    // Datos del técnico
                    Text(
                        text = "Datos del técnico encargado de la revisión",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = nombreTecnico,
                        onValueChange = { nombreTecnico = it },
                        label = { Text("Nombre:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = telefonoTecnico,
                        onValueChange = { telefonoTecnico = it },
                        label = { Text("Teléfono:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    // Revisión preventiva (secciones)
                    Text(
                        text = "Revisión preventiva",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )

                    // Sección Encendido
                    RevisionSection(
                        title = "Encendido",
                        items = listOf(
                            "Batería" to revisionEncendido.bateria,
                            "Fusión corriente" to revisionEncendido.fusionCorriente,
                            "Fusibles y relays" to revisionEncendido.fusiblesRelays
                        ),
                        onItemChange = { index, estado, comentarios, observaciones ->
                            when (index) {
                                0 -> revisionEncendido = revisionEncendido.copy(
                                    bateria = revisionEncendido.bateria.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                1 -> revisionEncendido = revisionEncendido.copy(
                                    fusionCorriente = revisionEncendido.fusionCorriente.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                2 -> revisionEncendido = revisionEncendido.copy(
                                    fusiblesRelays = revisionEncendido.fusiblesRelays.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sección Motor
                    RevisionSection(
                        title = "Motor",
                        items = listOf(
                            "Aceite" to revisionMotor.aceite,
                            "Ventilador" to revisionMotor.ventilador,
                            "Bandas" to revisionMotor.bandas
                        ),
                        onItemChange = { index, estado, comentarios, observaciones ->
                            when (index) {
                                0 -> revisionMotor = revisionMotor.copy(
                                    aceite = revisionMotor.aceite.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                1 -> revisionMotor = revisionMotor.copy(
                                    ventilador = revisionMotor.ventilador.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                2 -> revisionMotor = revisionMotor.copy(
                                    bandas = revisionMotor.bandas.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sección Lubricación
                    RevisionSection(
                        title = "Lubricación",
                        items = listOf(
                            "Aceite" to revisionLubricacion.aceite,
                            "De engranajes" to revisionLubricacion.deEngranajes,
                            "De transmisión" to revisionLubricacion.deTransmision
                        ),
                        onItemChange = { index, estado, comentarios, observaciones ->
                            when (index) {
                                0 -> revisionLubricacion = revisionLubricacion.copy(
                                    aceite = revisionLubricacion.aceite.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                1 -> revisionLubricacion = revisionLubricacion.copy(
                                    deEngranajes = revisionLubricacion.deEngranajes.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                2 -> revisionLubricacion = revisionLubricacion.copy(
                                    deTransmision = revisionLubricacion.deTransmision.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sección Filtros
                    RevisionSection(
                        title = "Filtros",
                        items = listOf(
                            "Aire" to revisionFiltros.aire,
                            "Aceite" to revisionFiltros.aceite,
                            "Motor" to revisionFiltros.motor
                        ),
                        onItemChange = { index, estado, comentarios, observaciones ->
                            when (index) {
                                0 -> revisionFiltros = revisionFiltros.copy(
                                    aire = revisionFiltros.aire.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                1 -> revisionFiltros = revisionFiltros.copy(
                                    aceite = revisionFiltros.aceite.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                                2 -> revisionFiltros = revisionFiltros.copy(
                                    motor = revisionFiltros.motor.copy(
                                        estado = estado,
                                        comentarios = comentarios,
                                        observaciones = observaciones
                                    )
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Descripción de lo que se realizó
                    OutlinedTextField(
                        value = descripcionRealizado,
                        onValueChange = { descripcionRealizado = it },
                        label = { Text("Descripción de lo que se realizó:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        minLines = 3
                    )

                    // Conclusiones
                    OutlinedTextField(
                        value = conclusiones,
                        onValueChange = { conclusiones = it },
                        label = { Text("Conclusiones:") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        minLines = 3
                    )

                    // Referentes a la revisión
                    Text(
                        text = "Referentes a la revisión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Secciones de referentes
                    ReferenteSection(
                        title = "Realizó la revisión preventiva",
                        nombre = nombreRevisionPreventiva,
                        imagenINE = imagenINERevision,
                        onNombreChange = { nombreRevisionPreventiva = it },
                        onImagenINEChange = { imagenINERevision = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ReferenteSection(
                        title = "Vo.Bo Técnico de la revisión",
                        nombre = nombreTecnicoRevision,
                        imagenINE = imagenINETecnico,
                        onNombreChange = { nombreTecnicoRevision = it },
                        onImagenINEChange = { imagenINETecnico = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ReferenteSection(
                        title = "Autoriza mantto. Correctivo",
                        nombre = nombreCorrectivo,
                        imagenINE = imagenINECorrectivo,
                        onNombreChange = { nombreCorrectivo = it },
                        onImagenINEChange = { imagenINECorrectivo = it }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón para crear/actualizar registro - CORREGIDO
                    Button(
                        onClick = {
                            if (isEditMode && revisionId != null) {
                                viewModel.actualizarRegistro(
                                    revisionId = revisionId,
                                    equipo = equipo,
                                    marcaModelo = marcaModelo,
                                    numeroSerie = numeroSerie,
                                    descripcionDetallada = descripcionDetallada,
                                    empresaEncargada = empresaEncargada,
                                    fechaRevision = fechaRevision,
                                    numeroReporte = numeroReporte,
                                    nombreTecnico = nombreTecnico,
                                    telefonoTecnico = telefonoTecnico,
                                    descripcionRealizado = descripcionRealizado,
                                    nombreRevisionPreventiva = nombreRevisionPreventiva,
                                    imagenINERevision = imagenINERevision,
                                    nombreTecnicoRevision = nombreTecnicoRevision,
                                    imagenINETecnico = imagenINETecnico,
                                    nombreCorrectivo = nombreCorrectivo,
                                    imagenINECorrectivo = imagenINECorrectivo
                                )
                            } else {
                                viewModel.crearNuevoRegistro(
                                    equipo = equipo,
                                    marcaModelo = marcaModelo,
                                    numeroSerie = numeroSerie,
                                    descripcionDetallada = descripcionDetallada,
                                    empresaEncargada = empresaEncargada,
                                    fechaRevision = fechaRevision,
                                    numeroReporte = numeroReporte,
                                    nombreTecnico = nombreTecnico,
                                    telefonoTecnico = telefonoTecnico,
                                    descripcionRealizado = descripcionRealizado,
                                    conclusiones = conclusiones,
                                    nombreRevisionPreventiva = nombreRevisionPreventiva,
                                    imagenINERevision = imagenINERevision,
                                    nombreTecnicoRevision = nombreTecnicoRevision,
                                    imagenINETecnico = imagenINETecnico,
                                    nombreCorrectivo = nombreCorrectivo,
                                    imagenINECorrectivo = imagenINECorrectivo
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text(
                                text = if (isEditMode) "Actualizar registro" else "Crear nuevo registro",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Snackbar para mostrar errores - AGREGADO
        if (showErrorSnackbar && errorMessage != null) {
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(3000)
                showErrorSnackbar = false
                viewModel.clearErrorMessage()
            }

            Snackbar(
                modifier = Modifier.align(Alignment.BottomCenter),
                action = {
                    TextButton(onClick = {
                        showErrorSnackbar = false
                        viewModel.clearErrorMessage()
                    }) {
                        Text("Cerrar")
                    }
                }
            ) {
                Text(errorMessage!!)
            }
        }
    }
}

// Resto de composables sin cambios...
@Composable
fun RevisionSection(
    title: String,
    items: List<Pair<String, ItemRevision>>,
    onItemChange: (Int, String, String, String) -> Unit
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

            items.forEachIndexed { index, (parte, item) ->
                RevisionItemRow(
                    parte = parte,
                    item = item,
                    onItemChange = { estado, comentarios, observaciones ->
                        onItemChange(index, estado, comentarios, observaciones)
                    }
                )
                if (index < items.size - 1) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun RevisionItemRow(
    parte: String,
    item: ItemRevision,
    onItemChange: (String, String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = parte,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp
        )

        Box(modifier = Modifier.weight(1f)) {
            var expanded by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = item.estado,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                textStyle = LocalTextStyle.current.copy(fontSize = 10.sp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Malo", "Regular", "Bueno").forEach { estado ->
                    DropdownMenuItem(
                        text = { Text(estado) },
                        onClick = {
                            onItemChange(estado, item.comentarios, item.observaciones)
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = item.comentarios,
            onValueChange = { onItemChange(item.estado, it, item.observaciones) },
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp)
        )

        OutlinedTextField(
            value = item.observaciones,
            onValueChange = { onItemChange(item.estado, item.comentarios, it) },
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp)
        )
    }
}

@Composable
fun ReferenteSection(
    title: String,
    nombre: String,
    imagenINE: String,
    onNombreChange: (String) -> Unit,
    onImagenINEChange: (String) -> Unit
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

            OutlinedTextField(
                value = nombre,
                onValueChange = onNombreChange,
                label = { Text("Nombre:") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Imagen de INE:",
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        // Función para subir imagen (vacía por ahora)
                        onImagenINEChange("imagen_seleccionada.jpg")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Subir imagen", fontSize = 12.sp)
                }
            }
        }
    }
}

// Data classes para manejar el estado de las revisiones
data class ItemRevision(
    val estado: String = "Regular",
    val comentarios: String = "",
    val observaciones: String = ""
)

data class RevisionEncendido(
    val bateria: ItemRevision = ItemRevision(estado = "Malo"),
    val fusionCorriente: ItemRevision = ItemRevision(),
    val fusiblesRelays: ItemRevision = ItemRevision(estado = "Bueno")
)

data class RevisionMotor(
    val aceite: ItemRevision = ItemRevision(estado = "Malo"),
    val ventilador: ItemRevision = ItemRevision(),
    val bandas: ItemRevision = ItemRevision(estado = "Bueno")
)

data class RevisionLubricacion(
    val aceite: ItemRevision = ItemRevision(estado = "Malo"),
    val deEngranajes: ItemRevision = ItemRevision(),
    val deTransmision: ItemRevision = ItemRevision(estado = "Bueno")
)

data class RevisionFiltros(
    val aire: ItemRevision = ItemRevision(estado = "Malo"),
    val aceite: ItemRevision = ItemRevision(),
    val motor: ItemRevision = ItemRevision(estado = "Bueno")
)

// Data class para manejar los datos de cada item de revisión en el ViewModel
data class ItemRevisionData(
    val estado: String,
    val comentarios: String,
    val observaciones: String
)