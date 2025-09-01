package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_MaquinariasYVehiculosDto
import retrofit2.Response

class Post_MaquinariasYVehiculosDto_Repository(private val apiService: ApiService) {
    suspend fun getMaquinarias(
        search: String? = null,
        tipo: String? = null,
        tipoId: Int? = null,
        page: Int? = null,
        pageSize: Int? = null
    ):Response<Post_MaquinariasYVehiculosDto> = apiService.getMaquinarias(search, tipo, tipoId, page, pageSize)

    //suspend fun getMaquinariaById(id: Int): Maquinaria = apiService.getMaquinariaById(id)
    suspend fun getMaquinariaById(id: Int): Response<Maquinaria> =
        apiService.getMaquinariaById(id)
}