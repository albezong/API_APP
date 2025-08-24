package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_LugaresDto(
    val idLugares: Int,
    val nombreLugar: String,
    val idfEquipos: Int,
)

data class Create_LugaresDto_2(
    val nombreLugar: String,
    val idfEquipos: Int,
)