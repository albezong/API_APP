package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_RefaccionesDto(
    val idRefacciones: Int,
    val idfRevisiones: Int,//--
    val idfUnidades: Int,
    val nombreRefaccion: String,
    val idfDescripcionPrioridades: Int,
    val cantidad: Int,
    val observaciones: String,
    //yyyy-MM-dd'T'HH:mm:ss
    val fecha: String,
    val numeroReporte: Int,
    val descripcion: String
)

data class Create_RefaccionesDto_2(
    val idfRevisiones: Int,//--
    val idfUnidades: Int,
    val nombreRefaccion: String,
    val idfDescripcionPrioridades: Int,
    val cantidad: Int,
    val observaciones: String,
    //yyyy-MM-dd'T'HH:mm:ss
    val fecha: String,
    val numeroReporte: Int,
    val descripcion: String
)