package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

@Composable
fun Post_EditMaquinariaScreen(){}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post_EditMaquinariaScreen(
    navController: NavHostController,
    id: Int,
    viewModel: Post_EquiposDto_ViewModel,
    viewModelMaquinariaYVehi: Post_MaquinariasYVehiculosDto_ViewModel,
) {
    val contexto = LocalContext.current
    val scope = rememberCoroutineScope()
    val updateState by viewModel.updateState.collectAsState()

    val material by viewModel.selectedPost.collectAsState()
    // no uses el nombre duplicado: usa el selectedPost del otro viewmodel solo si lo necesitas
    val detalleMaqui by viewModelMaquinariaYVehi.selectedPost.collectAsState()


    // campos locales
    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var nombreComercial by remember { mutableStateOf("") }
    var numIdentificador by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var fechaRegistro by remember { mutableStateOf("") }

    // --- DROPDOWNS: guardan id seleccionado ---
    var selectedUbicacionId by remember { mutableStateOf<Int?>(null) }
    var selectedTipoId by remember { mutableStateOf<Int?>(null) }
    var selectedLugarId by remember { mutableStateOf<Int?>(null) }

    // Exposed dropdown expanded flags
    var expandedUb by remember { mutableStateOf(false) }
    var expandedTipo by remember { mutableStateOf(false) }
    var expandedLugar by remember { mutableStateOf(false) }

    // Catálogos desde viewModel (asegúrate de llamar loadCatalogs())
    val ubicaciones by viewModel.ubicaciones.collectAsState()
    val tipos by viewModel.tiposMaquinarias.collectAsState()
    val lugares by viewModel.lugares.collectAsState()

    // carga inicial: traer el material y catálogos
    LaunchedEffect(id) {
        viewModel.getPostById(id)
        viewModel.loadCatalogs()
        viewModelMaquinariaYVehi.getPostById(id) // si lo necesitas
    }

    // cuando llegue material, poblar campos y selected ids
    LaunchedEffect(material) {
        material?.let { m ->
            codigo = m.codigoArticulo ?: ""
            nombre = m.nombreArticulo ?: ""
            nombreComercial = m.nombreComercial ?: ""
            numIdentificador = m.numIdentificador ?: ""
            descripcion = m.descripcion ?: ""
            marca = m.marca ?: ""
            modelo = m.modelo ?: ""
            fechaRegistro = m.fechadeRegistro ?: ""

            // Asignar ids del equipo si existen en el DTO (ajusta nombres)
            selectedUbicacionId = (m.idfUbicaciones).takeIf { it != 0 } // si 0 no válido
            selectedTipoId = (m.idfTiposMaquinarias).takeIf { it != 0 }
            // selectedLugarId = m.idLugar ?: selectedLugarId
        }
    }

    // reaccionar al update
    LaunchedEffect(updateState) {
        updateState?.let { res ->
            if (res.isSuccess) {
                Toast.makeText(contexto, "Actualización correcta", Toast.LENGTH_SHORT).show()
                viewModel.clearUpdateState()
                navController.popBackStack()
            } else {
                val ex = res.exceptionOrNull()
                Toast.makeText(contexto, "Error al actualizar: ${ex?.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar maquinaria") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // justo antes de Column:
            if (material == null) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cargando...", modifier = Modifier.padding(8.dp))
                    }
                }
                return
            }

            // Campos libres
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código del artículo") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del artículo") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = nombreComercial,
                onValueChange = { nombreComercial = it },
                label = { Text("Nombre comercial") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = numIdentificador,
                onValueChange = { numIdentificador = it },
                label = { Text("Número identificador") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = fechaRegistro,
                onValueChange = { fechaRegistro = it },
                label = { Text("Fecha de registro") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- Dropdown Ubicaciones ---
            ExposedDropdownMenuBox(
                expanded = expandedUb,
                onExpandedChange = { expandedUb = !expandedUb }
            ) {
                val selectedUbText = ubicaciones.firstOrNull { it.idUbicaciones == selectedUbicacionId }?.nombre ?: "Seleccionar ubicación"
                OutlinedTextField(
                    value = selectedUbText,
                    onValueChange = {},
                    label = { Text("Ubicación") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedUb) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedUb,
                    onDismissRequest = { expandedUb = false }
                ) {
                    ubicaciones.forEach { u ->
                        DropdownMenuItem(text = { Text(u.nombre) }, onClick = {
                            selectedUbicacionId = u.idUbicaciones
                            expandedUb = false
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- Dropdown Tipos Maquinarias ---
            ExposedDropdownMenuBox(
                expanded = expandedTipo,
                onExpandedChange = { expandedTipo = !expandedTipo }
            ) {
                val selectedTipoText = tipos.firstOrNull { it.idTiposMaquinarias == selectedTipoId }?.nombre ?: "Seleccionar tipo"
                OutlinedTextField(
                    value = selectedTipoText,
                    onValueChange = {},
                    label = { Text("Tipo de maquinaria") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedTipo,
                    onDismissRequest = { expandedTipo = false }
                ) {
                    tipos.forEach { t ->
                        DropdownMenuItem(text = { Text(t.nombre) }, onClick = {
                            selectedTipoId = t.idTiposMaquinarias
                            expandedTipo = false
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- Dropdown Lugares (si corresponde) ---
            ExposedDropdownMenuBox(
                expanded = expandedLugar,
                onExpandedChange = { expandedLugar = !expandedLugar }
            ) {
                val selectedLugarText = lugares.firstOrNull { it.idLugares == selectedLugarId }?.nombreLugar ?: "Seleccionar lugar"
                OutlinedTextField(
                    value = selectedLugarText,
                    onValueChange = {},
                    label = { Text("Lugar") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLugar) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedLugar,
                    onDismissRequest = { expandedLugar = false }
                ) {
                    lugares.forEach { l ->
                        DropdownMenuItem(text = { Text(l.nombreLugar) }, onClick = {
                            selectedLugarId = l.idLugares
                            expandedLugar = false
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Guardar/Cancelar
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Cancelar")
                }

                Button(onClick = {
                    // Validaciones básicas
                    if (selectedUbicacionId == null || selectedTipoId == null) {
                        Toast.makeText(contexto, "Selecciona ubicación y tipo.", Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    // Construir payload (ajusta nombres a Create_EquiposDto_2)
                    val payload = Create_EquiposDto_2(
                        codigoArticulo = codigo,
                        nombreArticulo = nombre,
                        nombreComercial = nombreComercial,
                        numIdentificador = numIdentificador,
                        descripcion = descripcion,
                        marca = marca,
                        modelo = modelo,
                        fechadeRegistro = fechaRegistro,
                        idfUbicaciones = selectedUbicacionId!!,
                        idfUnidades = material?.idfUnidades ?: 0, // si tienes unidad dropdown añade su select
                        idfEstatus = material?.idfEstatus ?: 0,   // si tienes estatus dropdown añade su select
                        idfTiposMaquinarias = selectedTipoId!!
                    )

                    viewModel.updatePost(id, payload)
                }) {
                    Text("Guardar cambios")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}*/