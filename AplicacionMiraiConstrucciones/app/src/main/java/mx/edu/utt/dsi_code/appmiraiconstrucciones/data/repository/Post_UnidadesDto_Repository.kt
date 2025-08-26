package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_UnidadesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UnidadesDto

class Post_UnidadesDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_UnidadesDto> = apiService.getAllUnidades()

    suspend fun create(post: Create_UnidadesDto_2): Post_UnidadesDto = apiService.createUnidad(post)

    suspend fun delete(id: Int) = apiService.deleteUnidad(id)

    suspend fun getById(id: Int): Post_UnidadesDto = apiService.getUnidadById(id)

    suspend fun update(id: Int, post: Create_UnidadesDto_2): Create_UnidadesDto_2 = apiService.updateUnidad(id, post)

}