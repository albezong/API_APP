package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_RefaccionesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_RefaccionesDto

class Post_RefaccionesDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_RefaccionesDto> = apiService.getAllRefacciones()

    suspend fun create(post: Create_RefaccionesDto_2): Post_RefaccionesDto = apiService.createRefaccion(post)

    suspend fun delete(id: Int) = apiService.deleteRefaccion(id)

    suspend fun getById(id: Int): Post_RefaccionesDto = apiService.getRefaccionById(id)

    suspend fun update(id: Int, post: Post_RefaccionesDto): Post_RefaccionesDto = apiService.updateRefaccion(id, post)

}