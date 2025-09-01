
package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.*

class RevisionesRepository(
    private val api: ApiService
) {

    suspend fun getAllRevisiones(): List<Post_RevisionesDto> {
        return api.getAllRevisiones()
    }

    suspend fun getRevisionById(id: Int): Post_RevisionesDto {
        return api.getRevisionById(id)
    }

    suspend fun createRevision(revision: Create_RevisionesDto_2): Post_RevisionesDto {
        return api.createRevision(revision)
    }

    suspend fun updateRevision(id: Int, revision: Post_RevisionesDto): Post_RevisionesDto {
        return api.updateRevision(id, revision)
    }

    suspend fun deleteRevision(id: Int) {
        api.deleteRevision(id)
    }

    // Métodos para entidades relacionadas
    suspend fun getAllEquipos(): List<Post_EquiposDto> {
        return api.getAllEquipos()
    }

    suspend fun getAllTiposMantenimientos(): List<Post_TiposMantenimientosDto> {
        return api.getAllTiposMantenimientos()
    }

    suspend fun getAllUsuarios(): List<Post_UsuariosDto> {
        return api.getAllUsuarios()
    }

    suspend fun getAllEmpresas(): List<Post_EmpresasDto> {
        return api.getAllEmpresas()
    }

    suspend fun getAllDetallesPreventivos(): List<Post_DetallesPreventivosDto> {
        return api.getAllDetallesPreventivos()
    }

    suspend fun createDetallePreventivo(detalle: Create_DetallesPreventivosDto_2): Post_DetallesPreventivosDto {
        return api.createDetallePreventivo(detalle)
    }

    suspend fun getAllCategoriasPreventivas(): List<Post_CategoriasPreventivasDto> {
        return api.getAllCategoriasPreventivas()
    }

    suspend fun getAllPrioridades(): List<Post_PrioridadesDto> {
        return api.getAllPrioridades()
    }
}