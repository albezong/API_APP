package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EstatusDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EstatusDto

class Post_EstatusDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_EstatusDto> = apiService.getAllEstatus()

    suspend fun create(post: Create_EstatusDto_2): Post_EstatusDto = apiService.createEstatus(post)

    suspend fun delete(id: Int) = apiService.deleteEstatus(id)

    suspend fun getById(id: Int): Post_EstatusDto = apiService.getEstatusById(id)

    suspend fun update(id: Int, post: Post_EstatusDto): Post_EstatusDto = apiService.updateEstatus(id, post)

}