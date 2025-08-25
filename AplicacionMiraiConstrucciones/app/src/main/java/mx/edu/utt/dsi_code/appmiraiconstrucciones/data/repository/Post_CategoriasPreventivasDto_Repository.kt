package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_CategoriasPreventivasDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_CategoriasPreventivasDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto

class Post_CategoriasPreventivasDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_CategoriasPreventivasDto> = apiService.getAllCategoriasPreventivas()

    suspend fun create(post: Create_CategoriasPreventivasDto_2): Post_CategoriasPreventivasDto = apiService.createCategoriaPreventiva(post)

    suspend fun delete(id: Int) = apiService.deleteCategoriaPreventiva(id)

    suspend fun getById(id: Int): Post_CategoriasPreventivasDto = apiService.getCategoriaPreventivaById(id)

    suspend fun update(id: Int, post: Create_CategoriasPreventivasDto_2): Create_CategoriasPreventivasDto_2 = apiService.updateCategoriaPreventiva(id, post)

}