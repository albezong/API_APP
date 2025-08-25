package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post_EditMaquinariaScreen(
    navController: NavHostController,
    id: Int,
    viewModel: Post_EquiposDto_ViewModel
) {
    val contexto = LocalContext.current
    val scope = rememberCoroutineScope()
    val material by viewModel.selectedPost.collectAsState()
    val updateState by viewModel.updateState.collectAsState()

    // estados locales para los campos (inicializados cuando llega `material`)
    var codigo by remember { mutableStateOf(TextFieldValue("")) }
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var nombreComercial by remember { mutableStateOf(TextFieldValue("")) }
    var numIdentificador by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var marca by remember { mutableStateOf(TextFieldValue("")) }
    var modelo by remember { mutableStateOf(TextFieldValue("")) }
    var fechaRegistro by remember { mutableStateOf(TextFieldValue("")) }
    var ubicacion by remember { mutableStateOf(TextFieldValue("")) }
    var unidad by remember { mutableStateOf(TextFieldValue("")) }
    var estatus by remember { mutableStateOf(TextFieldValue("")) }
    var tipoMaquina by remember { mutableStateOf(TextFieldValue("")) }
    var nombreLugar by remember { mutableStateOf(TextFieldValue("")) }

    // carga inicial
    LaunchedEffect(id) {
        viewModel.getPostById(id)
    }

    // cuando llegue material, mapear a los estados locales
    LaunchedEffect(material) {
        material?.let { m ->
            codigo = TextFieldValue(m.codigoArticulo ?: "")
            nombre = TextFieldValue(m.nombreArticulo ?: "")
            nombreComercial = TextFieldValue(m.nombreComercial ?: "")
            numIdentificador = TextFieldValue(m.numIdentificador ?: "")
            descripcion = TextFieldValue(m.descripcion ?: "")
            marca = TextFieldValue(m.marca ?: "")
            modelo = TextFieldValue(m.modelo ?: "")
            fechaRegistro = TextFieldValue(m.fechadeRegistro ?: "")
            ubicacion = TextFieldValue(m.idfUbicaciones ?: "")
            unidad = TextFieldValue(m.idfUnidades ?: "")
            estatus = TextFieldValue(m.estatus_nombre ?: "")
            tipoMaquina = TextFieldValue(m.tipo_maquinaria_nombre ?: "")
            nombreLugar = TextFieldValue(m.nombreLugar ?: "")
        }
    }

    // reaccionar al resultado de update
    LaunchedEffect(updateState) {
        updateState?.let { res ->
            if (res.isSuccess) {
                Toast.makeText(contexto, "Actualización correcta", Toast.LENGTH_SHORT).show()
                viewModel.clearUpdateState()
                navController.popBackStack() // volver
            } else {
                val ex = res.exceptionOrNull()
                Toast.makeText(contexto, "Error al actualizar: ${ex?.localizedMessage}", Toast.LENGTH_LONG).show()
                viewModel.clearUpdateState()
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
            // Si no llegó todavía, mostrar mensaje
            if (material == null) {
                Text("Cargando...", modifier = Modifier.padding(8.dp))
                return@Column
            }

            // Form fields
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

            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text("Ubicación (nombre)") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )

            OutlinedTextField(
                value = unidad,
                onValueChange = { unidad = it },
                label = { Text("Unidad") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )

            OutlinedTextField(
                value = estatus,
                onValueChange = { estatus = it },
                label = { Text("Estatus") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )

            OutlinedTextField(
                value = tipoMaquina,
                onValueChange = { tipoMaquina = it },
                label = { Text("Tipo de maquinaria") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )

            OutlinedTextField(
                value = nombreLugar,
                onValueChange = { nombreLugar = it },
                label = { Text("Nombre del lugar") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botones: Guardar y Cancelar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Cancelar")
                }

                Button(onClick = {
                    // Construir objeto actualizado (usa las mismas propiedades que tu modelo)
                    // Asegúrate de usar la clase de datos/DTO correcta. Aquí uso Maquinaria tal como la tienes.
                    val updated = material!!.copy(
                        codigo_articulo = codigo.text,
                        nombre_articulo = nombre.text,
                        nombre_comercial = nombreComercial.text,
                        num_identificador = numIdentificador.text,
                        descripcion = descripcion.text,
                        marca = marca.text,
                        modelo = modelo.text,
                        fechade_registro = fechaRegistro.text,
                        ubicacion_nombre = ubicacion.text,
                        unidad_nombre = unidad.text,
                        estatus_nombre = estatus.text,
                        tipo_maquinaria_nombre = tipoMaquina.text,
                        nombreLugar = nombreLugar.text
                    )

                    // Llamar al ViewModel para hacer PUT
                    viewModel.updateMaquinaria(id, updated)
                }) {
                    Text("Guardar cambios")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
