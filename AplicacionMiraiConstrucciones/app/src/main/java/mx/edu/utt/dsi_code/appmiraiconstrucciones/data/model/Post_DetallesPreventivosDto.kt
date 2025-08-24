package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_DetallesPreventivosDto(
    val idDetallesPreventivos: Int,
    val idfRevisiones: Int,
    val idfCategoriasPreventivas: Int,
    val nombreParte: String,
    val idfEstadoPrioridades: Int,
    val comentarios: String,
    val observaciones: String,
    //yyyy-MM-dd'T'HH:mm:ss
    val fecha: String,
    val numeroReporte: Int,
)

data class Create_DetallesPreventivosDto_2(
    val idfRevisiones: Int,
    val idfCategoriasPreventivas: Int,
    val nombreParte: String,
    val idfEstadoPrioridades: Int,
    val comentarios: String,
    val observaciones: String,
    //yyyy-MM-dd'T'HH:mm:ss
    val fecha: String,
    val numeroReporte: Int,
)