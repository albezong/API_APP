package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_TiposMaquinariasDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_UbicacionesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TiposMaquinariasDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UbicacionesDto

class Post_UbicacionesDto_Repository (private val apiService: ApiService){
    suspend fun getAll(): List<Post_UbicacionesDto> = apiService.getAllUbicaciones()

    suspend fun create(post: Create_UbicacionesDto_2): Post_UbicacionesDto = apiService.createUbicacion(post)

    suspend fun delete(id: Int) = apiService.deleteUbicacion(id)

    suspend fun getById(id: Int): Post_UbicacionesDto = apiService.getUbicacionById(id)

    suspend fun update(id: Int, post: Create_UbicacionesDto_2): Create_UbicacionesDto_2 = apiService.updateUbicacion(id, post)

}