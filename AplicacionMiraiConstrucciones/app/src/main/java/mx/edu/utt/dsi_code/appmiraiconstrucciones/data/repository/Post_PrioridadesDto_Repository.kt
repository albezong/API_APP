package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_PrioridadesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_PrioridadesDto

class Post_PrioridadesDto_Repository(private val apiService: ApiService){
    suspend fun getAll(): List<Post_PrioridadesDto> = apiService.getAllPrioridades()

    suspend fun create(post: Create_PrioridadesDto_2): Post_PrioridadesDto = apiService.createPrioridad(post)

    suspend fun delete(id: Int) = apiService.deletePrioridad(id)

    suspend fun getById(id: Int): Post_PrioridadesDto = apiService.getPrioridadById(id)

    suspend fun update(id: Int, post: Create_PrioridadesDto_2): Create_PrioridadesDto_2 = apiService.updatePrioridad(id, post)

}