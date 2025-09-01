package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_DetallesPreventivosDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_DetallesPreventivosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto

class Post_DetallesPreventivosDto_Repository (private val apiService: ApiService){
    suspend fun getAll(): List<Post_DetallesPreventivosDto> = apiService.getAllDetallesPreventivos()

    suspend fun create(post: Create_DetallesPreventivosDto_2): Post_DetallesPreventivosDto = apiService.createDetallePreventivo(post)

    suspend fun delete(id: Int) = apiService.deleteDetallePreventivo(id)

    suspend fun getById(id: Int): Post_DetallesPreventivosDto = apiService.getDetallePreventivoById(id)

    suspend fun update(id: Int, post: Post_DetallesPreventivosDto): Post_DetallesPreventivosDto = apiService.updateDetallePreventivo(id, post)

}