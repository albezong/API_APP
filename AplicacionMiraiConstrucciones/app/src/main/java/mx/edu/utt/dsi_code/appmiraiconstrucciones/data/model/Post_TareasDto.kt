package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_TareasDto(
    val idTareas: Int,
    val idTiposMantenimientos: Int,
    val descripcion: String,
)

data class Create_TareasDto_2(
    val idTiposMantenimientos: Int,
    val descripcion: String,
)