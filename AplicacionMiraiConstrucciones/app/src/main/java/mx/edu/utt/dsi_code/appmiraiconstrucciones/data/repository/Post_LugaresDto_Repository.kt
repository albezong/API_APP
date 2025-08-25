package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_LugaresDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_LugaresDto

class Post_LugaresDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_LugaresDto> = apiService.getAllLugares()

    suspend fun create(post: Create_LugaresDto_2): Post_LugaresDto = apiService.createLugar(post)

    suspend fun delete(id: Int) = apiService.deleteLugar(id)

    suspend fun getById(id: Int): Post_LugaresDto = apiService.getLugarById(id)

    suspend fun update(id: Int, post: Create_LugaresDto_2): Create_LugaresDto_2 = apiService.updateLugar(id, post)

}