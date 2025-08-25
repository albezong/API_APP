package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UsuariosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_UsuariosDto_2

class Post_UsuariosDto_Repository(private val apiService: ApiService) {
    suspend fun getAllUsuarios(): List<Post_UsuariosDto> = apiService.getAllUsuarios()

    suspend fun getUsuarioById(id: Int): Post_UsuariosDto = apiService.getUsuarioById(id)

    suspend fun createUsuario(post: Create_UsuariosDto_2): Post_UsuariosDto = apiService.createUsuario(post)

    suspend fun updateUsuario(id: Int, post: Create_UsuariosDto_2): Create_UsuariosDto_2 = apiService.updateUsuario(id, post)

    suspend fun deleteUsuario(id: Int) = apiService.deleteUsuario(id)
}