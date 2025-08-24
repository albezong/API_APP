package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleMaterialScreen(
    navController: NavController,
    id: Int,
    viewModel: Post_MaquinariasYVehiculosDto_ViewModel
) {
    val material by viewModel.selectedPost.collectAsState()

    // Llamamos al detalle cuando entra
    LaunchedEffect(id) {
        viewModel.getPostById(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de maquinaria") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Info, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            material?.let {
                Text("Codigo Articulo: ${it.codigo_articulo}")
                Text("Nombre Articulo: ${it.nombre_articulo}")
                Text("Nombre Comercial: ${it.nombre_comercial}")
                Text("Numero Identificador: ${it.num_identificador}")
                Text("Descripcion: ${it.descripcion}")
                Text("Marca: ${it.marca}")
                Text("Modelo: ${it.modelo}")
                Text("Fecha de Registro: ${it.fechade_registro}")
                Text("Ubicacion Nombre: ${it.ubicacion_nombre}")
                Text("Unidad Nombre: ${it.unidad_nombre}")
                Text("Estatus Nombre: ${it.estatus_nombre}")
                Text("Tipo Maquinaria Nombre: ${it.tipo_maquinaria_nombre}")
                Text("Nombre Lugar: ${it.nombreLugar}")
            } ?: Text("Cargando...")
        }
    }
}
