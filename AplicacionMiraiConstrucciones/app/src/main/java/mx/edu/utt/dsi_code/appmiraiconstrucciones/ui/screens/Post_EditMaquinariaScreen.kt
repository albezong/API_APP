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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EstatusDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_TiposMaquinariasDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UbicacionesDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UnidadesDto_ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post_EditMaquinariaScreen(
    navController: NavController,
    id: Int,
    viewModel: Post_EquiposDto_ViewModel,
    viewModelUbicaciones: Post_UbicacionesDto_ViewModel,
    viewModelUnidades: Post_UnidadesDto_ViewModel,
    viewModelEstatus: Post_EstatusDto_ViewModel,
    viewModelTiposMaquinarias: Post_TiposMaquinariasDto_ViewModel
) {
    val contexto = LocalContext.current

    // Estado local de los campos
    var codigoArticulo by remember { mutableStateOf("") }
    var nombreArticulo by remember { mutableStateOf("") }
    var nombreComercial by remember { mutableStateOf("") }
    var numIdentificador by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var fechadeRegistro by remember { mutableStateOf("") }

    // Dropdowns
    var idfUbicaciones by remember { mutableStateOf(0) }
    var idfUnidades by remember { mutableStateOf(0) }
    var idfEstatus by remember { mutableStateOf(0) }
    var idfTiposMaquinarias by remember { mutableStateOf(0) }

    var expandidoUbicaciones by remember { mutableStateOf(false) }
    var expandidoUnidades by remember { mutableStateOf(false) }
    var expandidoEstatus by remember { mutableStateOf(false) }
    var expandidoTipos by remember { mutableStateOf(false) }

    val ubicaciones by viewModelUbicaciones._posts.collectAsState()
    val unidades by viewModelUnidades._posts.collectAsState()
    val estatus by viewModelEstatus._posts.collectAsState()
    val tipos by viewModelTiposMaquinarias._posts.collectAsState()

    val equipo by viewModel.selectedPost.collectAsState()

    // Cargar datos iniciales
    LaunchedEffect(id) {
        viewModel.getPostById(id)
        viewModelUbicaciones.fetchPosts()
        viewModelUnidades.fetchPosts()
        viewModelEstatus.fetchPosts()
        viewModelTiposMaquinarias.fetchPosts()
    }

    // Poblar campos cuando llegue el equipo
    LaunchedEffect(equipo) {
        equipo?.let {
            codigoArticulo = it.codigoArticulo ?: ""
            nombreArticulo = it.nombreArticulo ?: ""
            nombreComercial = it.nombreComercial ?: ""
            numIdentificador = it.numIdentificador ?: ""
            descripcion = it.descripcion ?: ""
            marca = it.marca ?: ""
            modelo = it.modelo ?: ""
            fechadeRegistro = it.fechadeRegistro ?: ""

            idfUbicaciones = it.idfUbicaciones ?: 0
            idfUnidades = it.idfUnidades ?: 0
            idfEstatus = it.idfEstatus ?: 0
            idfTiposMaquinarias = it.idfTiposMaquinarias ?: 0
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Maquinaria") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // --- Inputs igual que en create ---
            OutlinedTextField(value = codigoArticulo, onValueChange = { codigoArticulo = it }, label = { Text("Código artículo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = nombreArticulo, onValueChange = { nombreArticulo = it }, label = { Text("Nombre artículo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = nombreComercial, onValueChange = { nombreComercial = it }, label = { Text("Nombre comercial") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = numIdentificador, onValueChange = { numIdentificador = it }, label = { Text("Número identificador") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = fechadeRegistro, onValueChange = { fechadeRegistro = it }, label = { Text("Fecha de registro") }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(12.dp))

            // --- Dropdown Ubicaciones ---
            ExposedDropdownMenuBox(
                expanded = expandidoUbicaciones,
                onExpandedChange = {
                    expandidoUbicaciones = !expandidoUbicaciones
                }) {
                TextField(
                    value = ubicaciones.firstOrNull { it.idUbicaciones == idfUbicaciones }?.nombre ?: "Seleccionar Ubicación",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Ubicación") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoUbicaciones) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = expandidoUbicaciones, onDismissRequest = { expandidoUbicaciones = false }) {
                    ubicaciones.forEach { u ->
                        DropdownMenuItem(
                            text = { Text(u.nombre) },
                            onClick = {
                                idfUbicaciones = u.idUbicaciones
                                expandidoUbicaciones = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // --- Dropdown Unidades ---
            ExposedDropdownMenuBox(
                expanded = expandidoUnidades,
                onExpandedChange = {
                    expandidoUnidades = !expandidoUnidades
                }) {
                TextField(
                    value = unidades.firstOrNull { it.idUnidades == idfUnidades }?.nombre ?: "Seleccionar Unidades",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Unidades") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoUnidades) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = expandidoUnidades, onDismissRequest = { expandidoUnidades = false }) {
                    unidades.forEach { u ->
                        DropdownMenuItem(
                            text = { Text(u.nombre) },
                            onClick = {
                                idfUnidades = u.idUnidades
                                expandidoUnidades = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // --- Dropdown Estatus ---
            ExposedDropdownMenuBox(
                expanded = expandidoEstatus,
                onExpandedChange = {
                    expandidoEstatus = !expandidoEstatus
                }) {
                TextField(
                    value = estatus.firstOrNull { it.idEstatus == idfEstatus }?.nombre ?: "Seleccionar Estatus",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estatus") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEstatus) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = expandidoEstatus, onDismissRequest = { expandidoEstatus = false }) {
                    estatus.forEach { e ->
                        DropdownMenuItem(
                            text = { Text(e.nombre) },
                            onClick = {
                                idfEstatus = e.idEstatus
                                expandidoEstatus = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // --- Ejemplo dropdown Tipos de Maquinarias ---
            ExposedDropdownMenuBox(
                expanded = expandidoTipos,
                onExpandedChange = {
                    expandidoTipos = !expandidoTipos
                }) {
                TextField(
                    value = tipos.firstOrNull { it.idTiposMaquinarias == idfTiposMaquinarias }?.nombre ?: "Seleccionar Tipos de Maquinarias",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipos de Maquinarias") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoTipos) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = expandidoTipos, onDismissRequest = { expandidoTipos = false }) {
                    tipos.forEach { u ->
                        DropdownMenuItem(
                            text = { Text(u.nombre) },
                            onClick = {
                                idfTiposMaquinarias = u.idTiposMaquinarias
                                expandidoTipos = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.updatePost(
                        id,
                        Post_EquiposDto(
                            id,
                            codigoArticulo,
                            nombreArticulo,
                            nombreComercial,
                            numIdentificador,
                            descripcion,
                            marca,
                            modelo,
                            fechadeRegistro,
                            idfUbicaciones,
                            idfUnidades,
                            idfEstatus,
                            idfTiposMaquinarias
                        )
                    )
                    Toast.makeText(contexto, "Actualizando equipo...", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("GUARDAR CAMBIOS")
            }
        }
    }
}
