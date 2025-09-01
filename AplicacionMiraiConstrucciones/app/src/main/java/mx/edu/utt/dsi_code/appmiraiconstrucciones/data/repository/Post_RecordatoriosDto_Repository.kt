package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_RecordatoriosDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_RecordatoriosDto

class Post_RecordatoriosDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_RecordatoriosDto> = apiService.getAllRecordatorios()

    suspend fun create(post: Create_RecordatoriosDto_2): Post_RecordatoriosDto = apiService.createRecordatorio(post)

    suspend fun delete(id: Int) = apiService.deleteRecordatorio(id)

    suspend fun getById(id: Int): Post_RecordatoriosDto = apiService.getRecordatorioById(id)

    suspend fun update(id: Int, post: Post_RecordatoriosDto): Post_RecordatoriosDto = apiService.updateRecordatorio(id, post)

}