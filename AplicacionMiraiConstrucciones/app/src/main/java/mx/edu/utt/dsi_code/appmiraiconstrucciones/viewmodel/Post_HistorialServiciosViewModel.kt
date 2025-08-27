package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.HistorialServiciosRepository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_HistorialServiciosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_HistorialServiciosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.RefaccionDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.DetallePreventivoDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.ServicioCorrectivo
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.ServicioPreventivo

class HistorialServiciosViewModel(
    private val historialServiciosRepository: HistorialServiciosRepository
) : ViewModel() {

    private val _serviciosPreventivos = MutableStateFlow<List<ServicioPreventivo>>(emptyList())
    val serviciosPreventivos: StateFlow<List<ServicioPreventivo>> = _serviciosPreventivos

    private val _serviciosCorrectivos = MutableStateFlow<List<ServicioCorrectivo>>(emptyList())
    val serviciosCorrectivos: StateFlow<List<ServicioCorrectivo>> = _serviciosCorrectivos

    private val _historialServicios = MutableStateFlow<List<Post_HistorialServiciosDto>>(emptyList())
    val historialServicios: StateFlow<List<Post_HistorialServiciosDto>> = _historialServicios

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _deleteSuccess = MutableStateFlow<String?>(null)
    val deleteSuccess: StateFlow<String?> = _deleteSuccess

    // Listas originales para filtrado
    private var historialServiciosOriginales = listOf<Post_HistorialServiciosDto>()

    init {
        cargarServicios()
    }

    fun cargarServicios() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                // Cargar datos del repositorio
                val historialList = historialServiciosRepository.getAllHistorialServicios()

                // Guardar originales para filtrado
                historialServiciosOriginales = historialList

                // Actualizar StateFlows
                _historialServicios.value = historialList

                // Convertir a modelos de UI
                convertirAModelosUI(historialList)

                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al cargar servicios"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    private fun convertirAModelosUI(historialList: List<Post_HistorialServiciosDto>) {
        // Convertir historial a servicios preventivos basados en detalles preventivos
        val serviciosPreventivos = historialList.flatMap { historial ->
            historial.detallesPreventivos.map { detalle ->
                ServicioPreventivo(
                    id = detalle.idDetallesPreventivos ?: 0,
                    nombreMaquina = detalle.nombreParte ?: "Equipo sin nombre",
                    marcaEquipo = "ID Equipo: ${historial.idfEquipos}",
                    fechaUltimoServicio = historial.fecha,
                    tipoServicio = "Preventivo"
                )
            }
        }

        // Convertir refacciones a servicios correctivos
        val serviciosCorrectivos = historialList.flatMap { historial ->
            historial.refacciones.map { refaccion ->
                ServicioCorrectivo(
                    id = refaccion.idRefacciones ?: 0,
                    fechaServicio = refaccion.fecha ?: "Sin fecha",
                    tipoServicio = "Correctivo - ${refaccion.nombreRefaccion}",
                    descripcion = refaccion.observaciones
                )
            }
        }

        _serviciosPreventivos.value = serviciosPreventivos
        _serviciosCorrectivos.value = serviciosCorrectivos
    }

    fun filtrarServicios(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                convertirAModelosUI(historialServiciosOriginales)
            } else {
                // Filtrar historial servicios
                val historialFiltrado = historialServiciosOriginales.filter {
                    it.descripcion?.contains(query, ignoreCase = true) == true ||
                            it.fecha?.contains(query, ignoreCase = true) == true ||
                            it.detallesPreventivos.any { detalle ->
                                detalle.nombreParte?.contains(query, ignoreCase = true) == true ||
                                        detalle.comentarios?.contains(query, ignoreCase = true) == true
                            } ||
                            it.refacciones.any { refaccion ->
                                refaccion.nombreRefaccion?.contains(query, ignoreCase = true) == true ||
                                        refaccion.observaciones?.contains(query, ignoreCase = true) == true
                            }
                }

                convertirAModelosUI(historialFiltrado)
            }
        }
    }

    fun eliminarServicio(id: Int, tipo: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                when (tipo) {
                    "historial" -> {
                        historialServiciosRepository.deleteHistorialServicio(id)
                        _deleteSuccess.value = "Servicio eliminado correctamente"
                    }
                }

                // Recargar datos después de eliminar
                cargarServicios()

            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar el servicio: ${e.message}"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun crearHistorialServicio(historial: Create_HistorialServiciosDto) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                historialServiciosRepository.createHistorialServicio(historial)
                cargarServicios() // Recargar para mostrar el nuevo servicio

            } catch (e: Exception) {
                _errorMessage.value = "Error al crear servicio: ${e.message}"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun actualizarHistorialServicio(id: Int, historial: Create_HistorialServiciosDto) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                historialServiciosRepository.updateHistorialServicio(id, historial)
                cargarServicios() // Recargar para mostrar los cambios

            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar servicio: ${e.message}"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun obtenerHistorialServicioPorId(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val servicio = historialServiciosRepository.getHistorialServicioById(id)
                // Puedes emitir este servicio a un StateFlow específico si necesitas mostrar detalles

                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener servicio: ${e.message}"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun obtenerServicioPreventivo(id: Int): ServicioPreventivo? {
        return _serviciosPreventivos.value.find { it.id == id }
    }

    fun obtenerServicioCorrectivo(id: Int): ServicioCorrectivo? {
        return _serviciosCorrectivos.value.find { it.id == id }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearDeleteSuccess() {
        _deleteSuccess.value = null
    }
}

// Factory para HistorialServiciosViewModel
class HistorialServiciosViewModelFactory(
    private val repository: HistorialServiciosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistorialServiciosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistorialServiciosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}