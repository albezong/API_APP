package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_RolesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_RolesDto

class Post_RolesDto_Repository (private val apiService: ApiService){
    suspend fun getAll(): List<Post_RolesDto> = apiService.getAllRoles()

    suspend fun create(post: Create_RolesDto_2): Post_RolesDto = apiService.createRol(post)

    suspend fun delete(id: Int) = apiService.deleteRol(id)

    suspend fun getById(id: Int): Post_RolesDto = apiService.getRolById(id)

    suspend fun update(id: Int, post: Create_RolesDto_2): Create_RolesDto_2 = apiService.updateRol(id, post)

}