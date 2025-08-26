package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_TiposMantenimientosDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TiposMantenimientosDto

class Post_TiposMantenimientosDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_TiposMantenimientosDto> = apiService.getAllTiposMantenimientos()

    suspend fun create(post: Create_TiposMantenimientosDto_2): Post_TiposMantenimientosDto = apiService.createTipoMantenimiento(post)

    suspend fun delete(id: Int) = apiService.deleteTipoMantenimiento(id)

    suspend fun getById(id: Int): Post_TiposMantenimientosDto = apiService.getTipoMantenimientoById(id)

    suspend fun update(id: Int, post: Create_TiposMantenimientosDto_2): Create_TiposMantenimientosDto_2 = apiService.updateTipoMantenimiento(id, post)

}