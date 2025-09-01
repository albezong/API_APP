package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.RevisionesRepository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.*

class RevisionPreventivaViewModel(
    private val revisionesRepository: RevisionesRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _revisionData = MutableStateFlow<Post_RevisionesDto?>(null)
    val revisionData: StateFlow<Post_RevisionesDto?> = _revisionData

    // Estados para listas auxiliares
    private val _equipos = MutableStateFlow<List<Post_EquiposDto>>(emptyList())
    val equipos: StateFlow<List<Post_EquiposDto>> = _equipos

    private val _tiposMantenimientos = MutableStateFlow<List<Post_TiposMantenimientosDto>>(emptyList())
    val tiposMantenimientos: StateFlow<List<Post_TiposMantenimientosDto>> = _tiposMantenimientos

    private val _usuarios = MutableStateFlow<List<Post_UsuariosDto>>(emptyList())
    val usuarios: StateFlow<List<Post_UsuariosDto>> = _usuarios

    private val _empresas = MutableStateFlow<List<Post_EmpresasDto>>(emptyList())
    val empresas: StateFlow<List<Post_EmpresasDto>> = _empresas

    private val _categoriasPreventivas = MutableStateFlow<List<Post_CategoriasPreventivasDto>>(emptyList())
    val categoriasPreventivas: StateFlow<List<Post_CategoriasPreventivasDto>> = _categoriasPreventivas

    private val _prioridades = MutableStateFlow<List<Post_PrioridadesDto>>(emptyList())
    val prioridades: StateFlow<List<Post_PrioridadesDto>> = _prioridades

    init {
        cargarDatosAuxiliares()
    }

    private fun cargarDatosAuxiliares() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Cargar todas las listas auxiliares en paralelo
                val equipos = revisionesRepository.getAllEquipos()
                val tiposMantenimientos = revisionesRepository.getAllTiposMantenimientos()
                val usuarios = revisionesRepository.getAllUsuarios()
                val empresas = revisionesRepository.getAllEmpresas()
                val categoriasPreventivas = revisionesRepository.getAllCategoriasPreventivas()
                val prioridades = revisionesRepository.getAllPrioridades()

                _equipos.value = equipos
                _tiposMantenimientos.value = tiposMantenimientos
                _usuarios.value = usuarios
                _empresas.value = empresas
                _categoriasPreventivas.value = categoriasPreventivas
                _prioridades.value = prioridades

                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar datos auxiliares: ${e.message}"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    // Método simplificado que coincide con lo que usa la pantalla
    fun crearNuevoRegistro(
        equipo: String,
        marcaModelo: String,
        numeroSerie: String,
        descripcionDetallada: String,
        empresaEncargada: String,
        fechaRevision: String,
        numeroReporte: String,
        nombreTecnico: String,
        telefonoTecnico: String,
        descripcionRealizado: String,
        conclusiones: String,
        nombreRevisionPreventiva: String,
        imagenINERevision: String,
        nombreTecnicoRevision: String,
        imagenINETecnico: String,
        nombreCorrectivo: String,
        imagenINECorrectivo: String
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                // Buscar el equipo por nombre o crear una descripción combinada
                val equipoEncontrado = _equipos.value.find { it.nombreComercial?.contains(equipo, true) == true }
                val equipoId = equipoEncontrado?.idEquipos ?: 1 // Default si no se encuentra

                // Buscar tipo de mantenimiento preventivo
                val tipoPreventivo = _tiposMantenimientos.value.find {
                    it.nombre?.contains("preventivo", true) == true
                } ?: _tiposMantenimientos.value.firstOrNull()
                val tipoId = tipoPreventivo?.idTiposMantenimientos ?: 1

                // Buscar empresa por nombre
                val empresaEncontrada = _empresas.value.find {
                    it.nombre?.contains(empresaEncargada, true) == true
                }
                val empresaId = empresaEncontrada?.idEmpresas ?: 1

                // Usar el primer usuario disponible o un ID por defecto
                val usuarioId = _usuarios.value.firstOrNull()?.idUsuarios ?: 1

                // Crear descripción completa
                val descripcionCompleta = buildString {
                    appendLine("=== INFORMACIÓN DEL EQUIPO ===")
                    appendLine("Equipo: $equipo")
                    appendLine("Marca/Modelo: $marcaModelo")
                    appendLine("No. Serie: $numeroSerie")
                    appendLine("Descripción: $descripcionDetallada")
                    appendLine()
                    appendLine("=== DATOS TÉCNICOS ===")
                    appendLine("Técnico: $nombreTecnico")
                    appendLine("Teléfono: $telefonoTecnico")
                    appendLine("No. Reporte: $numeroReporte")
                    appendLine()
                    appendLine("=== TRABAJO REALIZADO ===")
                    appendLine("Descripción: $descripcionRealizado")
                    appendLine("Conclusiones: $conclusiones")
                    appendLine()
                    appendLine("=== REFERENTES ===")
                    appendLine("Revisión Preventiva: $nombreRevisionPreventiva")
                    appendLine("Técnico VoBo: $nombreTecnicoRevision")
                    appendLine("Autoriza Correctivo: $nombreCorrectivo")
                }

                // Crear la revisión
                val nuevaRevision = Create_RevisionesDto_2(
                    idfTiposMantenimientos = tipoId,
                    idfEquipos = equipoId,
                    idfUsuarios = usuarioId,
                    idfEmpresas = empresaId,
                    fecha = fechaRevision,
                    descripcion = descripcionCompleta
                )

                val revisionCreada = revisionesRepository.createRevision(nuevaRevision)
                println("aqui ----> $revisionCreada")
                _saveSuccess.value = true
                _isLoading.value = false

            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al guardar la revisión"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    // Método simplificado para actualización
    fun actualizarRegistro(
        revisionId: Int,
        equipo: String,
        marcaModelo: String,
        numeroSerie: String,
        descripcionDetallada: String,
        empresaEncargada: String,
        fechaRevision: String,
        numeroReporte: String,
        nombreTecnico: String,
        telefonoTecnico: String,
        descripcionRealizado: String,
        nombreRevisionPreventiva: String,
        imagenINERevision: String,
        nombreTecnicoRevision: String,
        imagenINETecnico: String,
        nombreCorrectivo: String,
        imagenINECorrectivo: String
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                // Buscar el equipo por nombre
                val equipoEncontrado = _equipos.value.find { it.nombreComercial?.contains(equipo, true) == true }
                val equipoId = equipoEncontrado?.idEquipos ?: 1

                // Buscar tipo de mantenimiento preventivo
                val tipoPreventivo = _tiposMantenimientos.value.find {
                    it.nombre?.contains("preventivo", true) == true
                } ?: _tiposMantenimientos.value.firstOrNull()
                val tipoId = tipoPreventivo?.idTiposMantenimientos ?: 1

                // Buscar empresa por nombre
                val empresaEncontrada = _empresas.value.find {
                    it.nombre?.contains(empresaEncargada, true) == true
                }
                val empresaId = empresaEncontrada?.idEmpresas ?: 1

                // Usar el primer usuario disponible
                val usuarioId = _usuarios.value.firstOrNull()?.idUsuarios ?: 1

                // Crear descripción completa
                val descripcionCompleta = buildString {
                    appendLine("=== INFORMACIÓN DEL EQUIPO ===")
                    appendLine("Equipo: $equipo")
                    appendLine("Marca/Modelo: $marcaModelo")
                    appendLine("No. Serie: $numeroSerie")
                    appendLine("Descripción: $descripcionDetallada")
                    appendLine()
                    appendLine("=== DATOS TÉCNICOS ===")
                    appendLine("Técnico: $nombreTecnico")
                    appendLine("Teléfono: $telefonoTecnico")
                    appendLine("No. Reporte: $numeroReporte")
                    appendLine()
                    appendLine("=== TRABAJO REALIZADO ===")
                    appendLine("Descripción: $descripcionRealizado")
                    appendLine()
                    appendLine("=== REFERENTES ===")
                    appendLine("Revisión Preventiva: $nombreRevisionPreventiva")
                    appendLine("Técnico VoBo: $nombreTecnicoRevision")
                    appendLine("Autoriza Correctivo: $nombreCorrectivo")
                }

                val revisionActualizada = Post_RevisionesDto(
                    idRevisiones = revisionId,
                    idfTiposMantenimientos = tipoId,
                    idfEquipos = equipoId,
                    idfUsuarios = usuarioId,
                    idfEmpresas = empresaId,
                    fecha = fechaRevision,
                    descripcion = descripcionCompleta
                )

                revisionesRepository.updateRevision(revisionId, revisionActualizada)
                _saveSuccess.value = true
                _isLoading.value = false

            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al actualizar la revisión"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun cargarDatosRevision(revisionId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val revision = revisionesRepository.getRevisionById(revisionId)
                _revisionData.value = revision
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar datos de la revisión: ${e.message}"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun cargarDatosEquipo(equipoId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                //val equipo = revisionesRepository.getEquipoById(equipoId)
                // Aquí puedes procesar los datos del equipo si es necesario
                // Por ejemplo, pre-llenar algunos campos del formulario

                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar datos del equipo: ${e.message}"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun limpiarDatos() {
        _revisionData.value = null
        _saveSuccess.value = false
        _errorMessage.value = null
    }

    // Funciones auxiliares para obtener nombres por ID
    fun getNombreEquipo(equipoId: Int): String {
        return _equipos.value.find { it.idEquipos == equipoId }?.nombreComercial ?: "Equipo desconocido"
    }

    fun getNombreTipoMantenimiento(tipoId: Int): String {
        return _tiposMantenimientos.value.find { it.idTiposMantenimientos == tipoId }?.nombre ?: "Tipo desconocido"
    }

    fun getNombreUsuario(usuarioId: Int): String {
        return _usuarios.value.find { it.idUsuarios == usuarioId }?.nombre ?: "Usuario desconocido"
    }

    fun getNombreEmpresa(empresaId: Int): String {
        return _empresas.value.find { it.idEmpresas == empresaId }?.nombre ?: "Empresa desconocida"
    }
}

// Factory para RevisionPreventivaViewModel
class RevisionPreventivaViewModelFactory(
    private val repository: RevisionesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RevisionPreventivaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RevisionPreventivaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}