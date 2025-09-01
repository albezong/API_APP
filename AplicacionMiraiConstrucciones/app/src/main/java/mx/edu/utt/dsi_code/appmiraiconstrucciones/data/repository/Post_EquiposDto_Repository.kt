package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import retrofit2.Response
import retrofit2.http.*



class Post_EquiposDto_Repository(private val apiService: ApiService){
    // Catálogos
    suspend fun getAllUbicaciones() = apiService.getAllUbicaciones()
    suspend fun getAllUnidades() = apiService.getAllUnidades()
    suspend fun getAllEstatus() = apiService.getAllEstatus()
    suspend fun getAllTiposMaquinarias() = apiService.getAllTiposMaquinarias()
    suspend fun getAllLugares() = apiService.getAllLugares()

    suspend fun getAll(): List<Post_EquiposDto> = apiService.getAllEquipos()

    suspend fun create(post: Create_EquiposDto_2): Post_EquiposDto = apiService.createEquipo(post)

    // ahora retorna Response<Void>
    suspend fun delete(id: Int):Response<Void> = apiService.deleteEquipo(id)

    suspend fun getById(id: Int): Post_EquiposDto = apiService.getEquipoById(id)

    suspend fun update(id: Int, post: Post_EquiposDto): Post_EquiposDto = apiService.updateEquipo(id, post)

}