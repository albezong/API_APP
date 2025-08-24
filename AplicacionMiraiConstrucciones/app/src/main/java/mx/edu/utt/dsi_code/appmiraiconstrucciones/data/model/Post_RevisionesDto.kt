package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_RevisionesDto(
    val idRevisiones: Int,
    val idfTiposMantenimientos: Int,//--
    val idfEquipos: Int,
    val idfUsuarios: Int,
    val idfEmpresas: Int,
    //yyyy-MM-dd'T'HH:mm:ss
    val fecha: String,
    val descripcion: String,
)

data class Create_RevisionesDto_2(
    val idfTiposMantenimientos: Int,
    val idfEquipos: Int,
    val idfUsuarios: Int,
    val idfEmpresas: Int,
    //yyyy-MM-dd'T'HH:mm:ss
    val fecha: String,
    val descripcion: String,
)