package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_RevisionesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_RevisionesDto

class Post_RevisionesDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_RevisionesDto> = apiService.getAllRevisiones()

    suspend fun create(post: Create_RevisionesDto_2): Post_RevisionesDto = apiService.createRevision(post)

    suspend fun delete(id: Int) = apiService.deleteRevision(id)

    suspend fun getById(id: Int): Post_RevisionesDto = apiService.getRevisionById(id)

    suspend fun update(id: Int, post: Post_RevisionesDto): Post_RevisionesDto = apiService.updateRevision(id, post)

}