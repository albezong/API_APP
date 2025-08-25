package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto


class Post_EquiposDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_EquiposDto> = apiService.getAllEquipos()

    suspend fun create(post: Create_EquiposDto_2): Post_EquiposDto = apiService.createEquipo(post)

    suspend fun delete(id: Int) = apiService.deleteEquipo(id)

    suspend fun getById(id: Int): Post_EquiposDto = apiService.getEquipoById(id)

    suspend fun update(id: Int, post: Create_EquiposDto_2): Create_EquiposDto_2 = apiService.updateEquipo(id, post)

}