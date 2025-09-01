package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EmpresasDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EmpresasDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto

class Post_EmpresasDto_Repository (private val apiService: ApiService){
    suspend fun getAll(): List<Post_EmpresasDto> = apiService.getAllEmpresas()

    suspend fun create(post: Create_EmpresasDto_2): Post_EmpresasDto = apiService.createEmpresa(post)

    suspend fun delete(id: Int) = apiService.deleteEmpresa(id)

    suspend fun getById(id: Int): Post_EmpresasDto = apiService.getEmpresaById(id)

    suspend fun update(id: Int, post: Post_EmpresasDto): Post_EmpresasDto = apiService.updateEmpresa(id, post)

}