package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_TareasDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TareasDto

class Post_TareasDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_TareasDto> = apiService.getAllTareas()

    suspend fun create(post: Create_TareasDto_2): Post_TareasDto = apiService.createTarea(post)

    suspend fun delete(id: Int) = apiService.deleteTarea(id)

    suspend fun getById(id: Int): Post_TareasDto = apiService.getTareaById(id)

    suspend fun update(id: Int, post: Post_TareasDto): Post_TareasDto = apiService.updateTarea(id, post)

}