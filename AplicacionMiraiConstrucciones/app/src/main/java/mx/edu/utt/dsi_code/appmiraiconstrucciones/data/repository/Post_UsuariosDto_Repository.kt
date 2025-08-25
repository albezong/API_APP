package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UsuariosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_UsuariosDto_2

class Post_UsuariosDto_Repository(private val apiService: ApiService) {
    suspend fun getAll(): List<Post_UsuariosDto> = apiService.getAllUsuarios()

    suspend fun create(post: Create_UsuariosDto_2): Post_UsuariosDto = apiService.createUsuario(post)

    suspend fun delete(id: Int) = apiService.deleteUsuario(id)

    suspend fun getById(id: Int): Post_UsuariosDto = apiService.getUsuarioById(id)

    suspend fun update(id: Int, post: Create_UsuariosDto_2): Create_UsuariosDto_2 = apiService.updateUsuario(id, post)

    suspend fun authenticate(nombre: String, password: String): Post_UsuariosDto? {
        val usuarios: List<Post_UsuariosDto> = apiService.getAllUsuarios()
        return usuarios.firstOrNull { u ->
            val nombreMatch = u.nombre.equals(nombre, ignoreCase = true)
            val passMatch = (u.contraseña == password) // usa el campo correcto de tu DTO
            nombreMatch && passMatch
        }
    }
}