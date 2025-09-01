package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_TiposMaquinariasDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TiposMaquinariasDto

class Post_TiposMaquinariasDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_TiposMaquinariasDto> = apiService.getAllTiposMaquinarias()

    suspend fun create(post: Create_TiposMaquinariasDto_2): Post_TiposMaquinariasDto = apiService.createTipoMaquinaria(post)

    suspend fun delete(id: Int) = apiService.deleteTipoMaquinaria(id)

    suspend fun getById(id: Int): Post_TiposMaquinariasDto = apiService.getTipoMaquinariaById(id)

    suspend fun update(id: Int, post: Post_TiposMaquinariasDto): Post_TiposMaquinariasDto = apiService.updateTipoMaquinaria(id, post)

}