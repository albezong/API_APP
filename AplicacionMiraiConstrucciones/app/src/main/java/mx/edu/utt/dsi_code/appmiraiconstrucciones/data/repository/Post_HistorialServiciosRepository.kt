package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_HistorialServiciosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_HistorialServiciosDto

class HistorialServiciosRepository(
    private val api: ApiService
) {

    suspend fun getAllHistorialServicios(): List<Post_HistorialServiciosDto> {
        return api.getAllHistorialServicios()
    }

    suspend fun getHistorialServicioById(id: Int): Post_HistorialServiciosDto {
        return api.getHistorialServicioById(id)
    }

    suspend fun createHistorialServicio(historial: Create_HistorialServiciosDto): Post_HistorialServiciosDto {
        return api.createHistorialServicio(historial)
    }

    suspend fun updateHistorialServicio(id: Int, historial: Create_HistorialServiciosDto): Create_HistorialServiciosDto {
        return api.updateHistorialServicio(id, historial)
    }

    suspend fun deleteHistorialServicio(id: Int) {
        api.deleteHistorialServicio(id)
    }
}