package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.RevisionesRepository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.*

class DetalleRevisionPreventivaViewModel(
    private val revisionesRepository: RevisionesRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _revisionDetalle = MutableStateFlow<RevisionPreventivaDetalle?>(null)
    val revisionDetalle: StateFlow<RevisionPreventivaDetalle?> = _revisionDetalle

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun cargarDetalleRevision(revisionId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                // Cargar la revisión principal
                val revision = revisionesRepository.getRevisionById(revisionId)

                // Cargar datos relacionados
                val equipo = revision.idfEquipos?.let {
                    revisionesRepository.getAllEquipos().find { it.idEquipos == revision.idfEquipos }
                }
                val empresa = revision.idfEmpresas?.let {
                    revisionesRepository.getAllEmpresas().find { it.idEmpresas == revision.idfEmpresas }
                }
                val usuario = revision.idfUsuarios?.let {
                    revisionesRepository.getAllUsuarios().find { it.idUsuarios == revision.idfUsuarios }
                }
                val tipoMantenimiento = revision.idfTiposMantenimientos?.let {
                    revisionesRepository.getAllTiposMantenimientos().find { it.idTiposMantenimientos == revision.idfTiposMantenimientos }
                }

                // Cargar detalles preventivos de esta revisión
                val detallesPreventivos = revisionesRepository.getAllDetallesPreventivos()
                    .filter { it.idfRevisiones == revisionId }

                // Crear el objeto detalle completo
                val detalleCompleto = construirRevisionPreventivaDetalle(
                    revision,
                    equipo,
                    empresa,
                    usuario,
                    tipoMantenimiento,
                    detallesPreventivos
                )

                _revisionDetalle.value = detalleCompleto
                _isLoading.value = false

            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al cargar los detalles de la revisión"
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    private suspend fun construirRevisionPreventivaDetalle(
        revision: Post_RevisionesDto,
        equipo: Post_EquiposDto?,
        empresa: Post_EmpresasDto?,
        usuario: Post_UsuariosDto?,
        tipoMantenimiento: Post_TiposMantenimientosDto?,
        detallesPreventivos: List<Post_DetallesPreventivosDto>
    ): RevisionPreventivaDetalle {

        // Organizar detalles preventivos por categorías
        val categoriasPreventivas = revisionesRepository.getAllCategoriasPreventivas()
        val prioridades = revisionesRepository.getAllPrioridades()

        // Crear objetos de revisión organizados por tipo
        val revisionEncendido = crearRevisionEncendido(detallesPreventivos, categoriasPreventivas, prioridades)
        val revisionMotor = crearRevisionMotor(detallesPreventivos, categoriasPreventivas, prioridades)
        val revisionLubricacion = crearRevisionLubricacion(detallesPreventivos, categoriasPreventivas, prioridades)
        val revisionFiltros = crearRevisionFiltros(detallesPreventivos, categoriasPreventivas, prioridades)

        return RevisionPreventivaDetalle(
            id = revision.idRevisiones ?: 0,
            equipo = equipo?.nombreComercial ?: "Equipo no especificado",
            marcaModelo = "${equipo?.marca ?: "N/A"} - ${equipo?.modelo ?: "N/A"}",
            numeroSerie = equipo?.numIdentificador ?: "N/A",
            descripcionDetallada = equipo?.descripcion ?: "Sin descripción disponible",
            empresaEncargada = empresa?.nombre ?: "Empresa no especificada",
            fechaRevision = revision.fecha ?: "Fecha no disponible",
            numeroReporte = "RPV-${revision.idRevisiones}-${revision.fecha?.take(4) ?: "2024"}",
            nombreTecnico = usuario?.nombre ?: "Técnico no especificado",
            telefonoTecnico = usuario?.telefono ?: "N/A",
            descripcionRealizado = revision.descripcion ?: "Sin descripción de trabajo realizado",
            conclusiones = generarConclusiones(detallesPreventivos, prioridades),
            revisionEncendido = revisionEncendido,
            revisionMotor = revisionMotor,
            revisionLubricacion = revisionLubricacion,
            revisionFiltros = revisionFiltros,
            nombreRevisionPreventiva = usuario?.nombre ?: "N/A",
            imagenINERevision = "ine_revision_${revision.idRevisiones}.jpg",
            nombreTecnicoRevision = usuario?.nombre ?: "N/A",
            imagenINETecnico = "ine_tecnico_${revision.idRevisiones}.jpg",
            nombreCorrectivo = usuario?.nombre ?: "N/A",
            imagenINECorrectivo = "ine_correctivo_${revision.idRevisiones}.jpg"
        )
    }

    private fun crearRevisionEncendido(
        detallesPreventivos: List<Post_DetallesPreventivosDto>,
        categoriasPreventivas: List<Post_CategoriasPreventivasDto>,
        prioridades: List<Post_PrioridadesDto>
    ): RevisionEncendidoDetalle {
        // Buscar categoría "Encendido" o similar
        val categoriaEncendido = categoriasPreventivas.find {
            it.nombreCategorias?.lowercase()?.contains("encendido") == true ||
                    it.nombreCategorias?.lowercase()?.contains("eléctrico") == true ||
                    it.nombreCategorias?.lowercase()?.contains("bateria") == true
        }

        val detallesEncendido = detallesPreventivos.filter {
            it.idfCategoriasPreventivas == categoriaEncendido?.idCategoriasPreventivas
        }

        return RevisionEncendidoDetalle(
            bateria = encontrarItemPorNombre(detallesEncendido, "bateria", prioridades),
            fusionCorriente = encontrarItemPorNombre(detallesEncendido, "corriente", prioridades),
            fusiblesRelays = encontrarItemPorNombre(detallesEncendido, "fusible", prioridades)
        )
    }

    private fun crearRevisionMotor(
        detallesPreventivos: List<Post_DetallesPreventivosDto>,
        categoriasPreventivas: List<Post_CategoriasPreventivasDto>,
        prioridades: List<Post_PrioridadesDto>
    ): RevisionMotorDetalle {
        val categoriaMotor = categoriasPreventivas.find {
            it.nombreCategorias?.lowercase()?.contains("motor") == true ||
                    it.nombreCategorias?.lowercase()?.contains("mecanico") == true
        }

        val detallesMotor = detallesPreventivos.filter {
            it.idfCategoriasPreventivas == categoriaMotor?.idCategoriasPreventivas
        }

        return RevisionMotorDetalle(
            aceite = encontrarItemPorNombre(detallesMotor, "aceite", prioridades),
            ventilador = encontrarItemPorNombre(detallesMotor, "ventilador", prioridades),
            bandas = encontrarItemPorNombre(detallesMotor, "banda", prioridades)
        )
    }

    private fun crearRevisionLubricacion(
        detallesPreventivos: List<Post_DetallesPreventivosDto>,
        categoriasPreventivas: List<Post_CategoriasPreventivasDto>,
        prioridades: List<Post_PrioridadesDto>
    ): RevisionLubricacionDetalle {
        val categoriaLubricacion = categoriasPreventivas.find {
            it.nombreCategorias?.lowercase()?.contains("lubricacion") == true ||
                    it.nombreCategorias?.lowercase()?.contains("fluidos") == true
        }

        val detallesLubricacion = detallesPreventivos.filter {
            it.idfCategoriasPreventivas == categoriaLubricacion?.idCategoriasPreventivas
        }

        return RevisionLubricacionDetalle(
            aceite = encontrarItemPorNombre(detallesLubricacion, "aceite", prioridades),
            deEngranajes = encontrarItemPorNombre(detallesLubricacion, "engranaje", prioridades),
            deTransmision = encontrarItemPorNombre(detallesLubricacion, "transmision", prioridades)
        )
    }

    private fun crearRevisionFiltros(
        detallesPreventivos: List<Post_DetallesPreventivosDto>,
        categoriasPreventivas: List<Post_CategoriasPreventivasDto>,
        prioridades: List<Post_PrioridadesDto>
    ): RevisionFiltrosDetalle {
        val categoriaFiltros = categoriasPreventivas.find {
            it.nombreCategorias?.lowercase()?.contains("filtro") == true ||
                    it.nombreCategorias?.lowercase()?.contains("filtracion") == true
        }

        val detallesFiltros = detallesPreventivos.filter {
            it.idfCategoriasPreventivas == categoriaFiltros?.idCategoriasPreventivas
        }

        return RevisionFiltrosDetalle(
            aire = encontrarItemPorNombre(detallesFiltros, "aire", prioridades),
            aceite = encontrarItemPorNombre(detallesFiltros, "aceite", prioridades),
            motor = encontrarItemPorNombre(detallesFiltros, "motor", prioridades)
        )
    }

    private fun encontrarItemPorNombre(
        detalles: List<Post_DetallesPreventivosDto>,
        nombreBuscar: String,
        prioridades: List<Post_PrioridadesDto>
    ): ItemRevisionDetalle {
        val detalle = detalles.find {
            it.nombreParte?.lowercase()?.contains(nombreBuscar.lowercase()) == true
        }

        return if (detalle != null) {
            val prioridad = prioridades.find { it.idPrioridades == detalle.idfEstadoPrioridades }
            ItemRevisionDetalle(
                estado = prioridad?.nombre ?: "No especificado",
                comentarios = detalle.comentarios ?: "Sin comentarios",
                observaciones = detalle.observaciones ?: "Sin observaciones"
            )
        } else {
            ItemRevisionDetalle(
                estado = "No evaluado",
                comentarios = "No se encontró información",
                observaciones = "Sin datos disponibles"
            )
        }
    }

    private fun generarConclusiones(
        detallesPreventivos: List<Post_DetallesPreventivosDto>,
        prioridades: List<Post_PrioridadesDto>
    ): String {
        if (detallesPreventivos.isEmpty()) {
            return "No se encontraron detalles para generar conclusiones."
        }

        val problemasAltos = detallesPreventivos.filter { detalle ->
            val prioridad = prioridades.find { it.idPrioridades == detalle.idfEstadoPrioridades }
            prioridad?.nombre?.lowercase()?.contains("alto") == true ||
                    prioridad?.nombre?.lowercase()?.contains("critico") == true ||
                    prioridad?.nombre?.lowercase()?.contains("malo") == true
        }

        val problemasMedios = detallesPreventivos.filter { detalle ->
            val prioridad = prioridades.find { it.idPrioridades == detalle.idfEstadoPrioridades }
            prioridad?.nombre?.lowercase()?.contains("medio") == true ||
                    prioridad?.nombre?.lowercase()?.contains("regular") == true
        }

        val conclusion = StringBuilder()

        when {
            problemasAltos.isNotEmpty() -> {
                conclusion.append("ATENCIÓN REQUERIDA: Se encontraron ${problemasAltos.size} problema(s) crítico(s) que requieren atención inmediata. ")
                conclusion.append("Componentes afectados: ${problemasAltos.mapNotNull { it.nombreParte }.joinToString(", ")}. ")
            }
            problemasMedios.isNotEmpty() -> {
                conclusion.append("El equipo presenta ${problemasMedios.size} problema(s) menor(es) que requieren monitoreo. ")
            }
            else -> {
                conclusion.append("El equipo se encuentra en condiciones operativas aceptables. ")
            }
        }

        conclusion.append("Se recomienda continuar con el programa de mantenimiento preventivo programado.")

        return conclusion.toString()
    }
}

// ViewModelFactory para inyección de dependencias
class DetalleRevisionPreventivaViewModelFactory(
    private val revisionesRepository: RevisionesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleRevisionPreventivaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetalleRevisionPreventivaViewModel(revisionesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Data classes para los detalles de revisión
data class RevisionPreventivaDetalle(
    val id: Int,
    val equipo: String,
    val marcaModelo: String,
    val numeroSerie: String,
    val descripcionDetallada: String,
    val empresaEncargada: String,
    val fechaRevision: String,
    val numeroReporte: String,
    val nombreTecnico: String,
    val telefonoTecnico: String,
    val descripcionRealizado: String,
    val conclusiones: String,
    val revisionEncendido: RevisionEncendidoDetalle,
    val revisionMotor: RevisionMotorDetalle,
    val revisionLubricacion: RevisionLubricacionDetalle,
    val revisionFiltros: RevisionFiltrosDetalle,
    val nombreRevisionPreventiva: String,
    val imagenINERevision: String,
    val nombreTecnicoRevision: String,
    val imagenINETecnico: String,
    val nombreCorrectivo: String,
    val imagenINECorrectivo: String
)

data class ItemRevisionDetalle(
    val estado: String,
    val comentarios: String,
    val observaciones: String
)

data class RevisionEncendidoDetalle(
    val bateria: ItemRevisionDetalle,
    val fusionCorriente: ItemRevisionDetalle,
    val fusiblesRelays: ItemRevisionDetalle
)

data class RevisionMotorDetalle(
    val aceite: ItemRevisionDetalle,
    val ventilador: ItemRevisionDetalle,
    val bandas: ItemRevisionDetalle
)

data class RevisionLubricacionDetalle(
    val aceite: ItemRevisionDetalle,
    val deEngranajes: ItemRevisionDetalle,
    val deTransmision: ItemRevisionDetalle
)

data class RevisionFiltrosDetalle(
    val aire: ItemRevisionDetalle,
    val aceite: ItemRevisionDetalle,
    val motor: ItemRevisionDetalle
)