package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository

import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.ApiService
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_ProgramacionesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_ProgramacionesDto

class ProgramacionesRepository(
    private val api: ApiService
) {

    suspend fun getAllProgramaciones(): List<Post_ProgramacionesDto> {
        return api.getAllProgramaciones()
    }

    suspend fun getProgramacionById(id: Int): Post_ProgramacionesDto {
        return api.getProgramacionById(id)
    }

    suspend fun createProgramacion(programacion: Create_ProgramacionesDto): Post_ProgramacionesDto {
        return api.createProgramacion(programacion)
    }

    suspend fun updateProgramacion(id: Int, programacion: Create_ProgramacionesDto): Create_ProgramacionesDto {
        return api.updateProgramacion(id, programacion)
    }

    suspend fun deleteProgramacion(id: Int) {
        api.deleteProgramacion(id)
    }
}