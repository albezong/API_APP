package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.flow.forEach
import mx.edu.utt.dsi_code.appmiraiconstrucciones.R
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EstatusDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TiposMaquinariasDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UbicacionesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UnidadesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.Grey80
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EstatusDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_TiposMaquinariasDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UbicacionesDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UnidadesDto_ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreateTodasMaquiunarias(
    navController: NavController,
    viewModel: Post_EquiposDto_ViewModel,
    viewModelUbicaciones : Post_UbicacionesDto_ViewModel,
    viewModelUnidades : Post_UnidadesDto_ViewModel,
    viewModelEstatus : Post_EstatusDto_ViewModel,
    viewModelTiposMaquinarias : Post_TiposMaquinariasDto_ViewModel
) {
    var codigoArticulo by remember { mutableStateOf("") }
    var nombreArticulo by remember { mutableStateOf("") }
    var nombreComercial by remember { mutableStateOf("") }
    var numIdentificador by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var fechadeRegistro by remember { mutableStateOf("") }
    //Ubicaciones
    var idfUbicaciones by remember { mutableStateOf(0) }
    var expandidoUbicaciones by remember { mutableStateOf(false) }
    val ubicacionesCollectToDropDown by viewModelUbicaciones._posts.collectAsState()
    var seleccionUbicaciones by remember { mutableStateOf<Post_UbicacionesDto?>(null) }
    //Unidades
    var idfUnidades by remember { mutableStateOf(0) }
    var expandidoUnidades by remember { mutableStateOf(false) }
    val unidadesCollectToDropDown by viewModelUnidades._posts.collectAsState()
    var seleccionUnidades by remember { mutableStateOf<Post_UnidadesDto?>(null) }
    //Estatus
    var idfEstatus by remember { mutableStateOf(0) }
    var expandidoEstatus by remember { mutableStateOf(false) }
    val estatusCollectToDropDown by viewModelEstatus._posts.collectAsState()
    var seleccionEstatus by remember { mutableStateOf<Post_EstatusDto?>(null) }
    //TiposMaquinarias
    var idfTiposMaquinarias by remember { mutableStateOf(0) }
    var expandidoTiposMaquinarias by remember { mutableStateOf(false) }
    val tiposMaquinariasCollectToDropDown by viewModelTiposMaquinarias._posts.collectAsState()
    var seleccionTiposMaquinarias by remember { mutableStateOf<Post_TiposMaquinariasDto?>(null) }

    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    // Llamamos a la API al cargar el Composable
    LaunchedEffect(Unit) {
        viewModelUbicaciones.fetchPosts()
        viewModelUnidades.fetchPosts()
        viewModelEstatus.fetchPosts()
        viewModelTiposMaquinarias.fetchPosts()
    }
    /*LaunchedEffect(viewModelUbicaciones.posts.collectAsState().value) {
        val list = viewModelUbicaciones.posts.value
        list.forEach { Log.d("DebugUbicaciones", "ubi.id=${it.idUbicaciones} nombre=${it.nombre}") }
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
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
                    text = "Agregar Maquinaria y /o equipo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(48.dp) // área táctil
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Atras",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre título y barra

        //********OUTLINES***********
        //codigoArticulo
        OutlinedTextField(
            value = codigoArticulo,
            onValueChange = { codigoArticulo = it},
            label = { Text("Codigo del Articulo")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        //nombreArticulo
        OutlinedTextField(
            value = nombreArticulo,
            onValueChange = { nombreArticulo = it},
            label = { Text("Nombre del Articulo")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        //nombreComercial
        OutlinedTextField(
            value = nombreComercial,
            onValueChange = {nombreComercial = it},
            label = { Text("Nombre Comercial")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        //numIdentificador
        OutlinedTextField(
            value = numIdentificador,
            onValueChange = { numIdentificador = it},
            label = { Text("Numero Identificador")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        //descripcion
        OutlinedTextField(
            value = descripcion,
            onValueChange = {descripcion = it},
            label = { Text("Descripcion del equipo")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        //marca
        OutlinedTextField(
            value = marca,
            onValueChange = { marca = it},
            label = { Text("Marca")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        //modelo
        OutlinedTextField(
            value = modelo,
            onValueChange = {modelo = it},
            label = { Text("Modelo")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        //fechadeRegistro
        OutlinedTextField(
            value = fechadeRegistro,
            onValueChange = { fechadeRegistro = it},
            label = { Text("Fecha de Registro")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        //idfUbicaciones
        ExposedDropdownMenuBox(
            expanded = expandidoUbicaciones,
            onExpandedChange = { expandidoUbicaciones = !expandidoUbicaciones }
        ) {
            TextField(
                value = seleccionUbicaciones?.nombre ?: "Selecciona la Ubicacion",
                onValueChange = {},
                readOnly = true,
                label = { Text("Ubicaciones") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoUbicaciones)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandidoUbicaciones,
                onDismissRequest = { expandidoUbicaciones = false }
            ) {
                ubicacionesCollectToDropDown.forEach { ubicacionesDropDown ->
                    DropdownMenuItem(
                        text = { Text(ubicacionesDropDown.nombre) },
                        onClick = {
                            seleccionUbicaciones = ubicacionesDropDown
                            idfUbicaciones = ubicacionesDropDown.idUbicaciones
                            expandidoUbicaciones = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Unidades
        ExposedDropdownMenuBox(
            expanded = expandidoUnidades,
            onExpandedChange = { expandidoUnidades = !expandidoUnidades }
        ) {
            TextField(
                value = seleccionUnidades?.nombre ?: "Selecciona una Unidad",
                onValueChange = {},
                readOnly = true,
                label = { Text("Unidad") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoUnidades)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandidoUnidades,
                onDismissRequest = { expandidoUnidades = false }
            ) {
                unidadesCollectToDropDown.forEach { unidadDropDown ->
                    DropdownMenuItem(
                        text = { Text(unidadDropDown.nombre) },
                        onClick = {
                            seleccionUnidades = unidadDropDown
                            idfUnidades = unidadDropDown.idUnidades
                            expandidoUnidades = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //idfEstatus
        ExposedDropdownMenuBox(
            expanded = expandidoEstatus,
            onExpandedChange = { expandidoEstatus = !expandidoEstatus }
        ) {
            TextField(
                value = seleccionEstatus?.nombre ?: "Selecciona el Estatus",
                onValueChange = {},
                readOnly = true,
                label = { Text("Estatus") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEstatus)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandidoEstatus,
                onDismissRequest = { expandidoEstatus = false }
            ) {
                estatusCollectToDropDown.forEach { estatusDropDown ->
                    DropdownMenuItem(
                        text = { Text(estatusDropDown.nombre) },
                        onClick = {
                            seleccionEstatus = estatusDropDown
                            idfEstatus = estatusDropDown.idEstatus
                            expandidoEstatus = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //idfTiposMaquinarias
        ExposedDropdownMenuBox(
            expanded = expandidoTiposMaquinarias,
            onExpandedChange = { expandidoTiposMaquinarias = !expandidoTiposMaquinarias }
        ) {
            TextField(
                value = seleccionTiposMaquinarias?.nombre ?: "Selecciona el Tipo de Maquinaria",
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de Maquinaria") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoTiposMaquinarias)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandidoTiposMaquinarias,
                onDismissRequest = { expandidoTiposMaquinarias = false }
            ) {
                tiposMaquinariasCollectToDropDown.forEach { tiposMaquinariasDropDown ->
                    DropdownMenuItem(
                        text = { Text(tiposMaquinariasDropDown.nombre) },
                        onClick = {
                            seleccionTiposMaquinarias = tiposMaquinariasDropDown
                            idfTiposMaquinarias = tiposMaquinariasDropDown.idTiposMaquinarias
                            expandidoTiposMaquinarias = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //-------------------VALIDACION PARA CREAR UNA MAQUINARIA

        if (errorMessage.isNotEmpty()){
            Text(text = errorMessage, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if(
                    codigoArticulo.isNotEmpty() &&
                    nombreArticulo.isNotEmpty() &&
                    nombreComercial.isNotEmpty() &&
                    numIdentificador.isNotEmpty() &&
                    descripcion.isNotEmpty() &&
                    marca.isNotEmpty() &&
                    modelo.isNotEmpty() &&
                    fechadeRegistro.isNotEmpty() &&
                    idfUbicaciones!= 0&&
                    idfUnidades!= 0&&
                    idfEstatus!= 0&&
                    idfTiposMaquinarias!= 0
                ){
                    viewModel.addPost(
                        Create_EquiposDto_2(
                            codigoArticulo,
                            nombreArticulo,
                            nombreComercial,
                            numIdentificador,
                            descripcion,
                            marca,
                            modelo,
                            fechadeRegistro,
                            idfUbicaciones!!,
                            idfUnidades!!,
                            idfEstatus!!,
                            idfTiposMaquinarias!!,
                        )
                    )
                    Log.d("Formulario", "codigoArticulo: $codigoArticulo")
                    Log.d("Formulario", "nombreArticulo: $nombreArticulo")
                    Log.d("Formulario", "nombreComercial: $nombreComercial")
                    Log.d("Formulario", "numIdentificador: $numIdentificador")
                    Log.d("Formulario", "descripcion: $descripcion")
                    Log.d("Formulario", "marca: $marca")
                    Log.d("Formulario", "modelo: $modelo")
                    Log.d("Formulario", "fechadeRegistro: $fechadeRegistro")
                    Log.d("Formulario", "idfUbicaciones: $idfUbicaciones")
                    Log.d("Formulario", "idfUnidades: $idfUnidades")
                    Log.d("Formulario", "idfEstatus: $idfEstatus")
                    Log.d("Formulario", "idfTiposMaquinarias: $idfTiposMaquinarias")
                    navController.popBackStack()
                } else {
                    errorMessage = "Todos los campos son requeridos!"
                    Log.d("Formulario", "AQUI YA EXISTE UN ERROR")
                    Log.d("Formulario", "codigoArticulo: $codigoArticulo")
                    Log.d("Formulario", "nombreArticulo: $nombreArticulo")
                    Log.d("Formulario", "nombreComercial: $nombreComercial")
                    Log.d("Formulario", "numIdentificador: $numIdentificador")
                    Log.d("Formulario", "descripcion: $descripcion")
                    Log.d("Formulario", "marca: $marca")
                    Log.d("Formulario", "modelo: $modelo")
                    Log.d("Formulario", "fechadeRegistro: $fechadeRegistro")
                    Log.d("Formulario", "idfUbicaciones: $idfUbicaciones")
                    Log.d("Formulario", "idfUnidades: $idfUnidades")
                    Log.d("Formulario", "idfEstatus: $idfEstatus")
                    Log.d("Formulario", "idfTiposMaquinarias: $idfTiposMaquinarias")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "ENVIAR")
        }
    }
}
