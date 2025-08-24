package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_RecordatoriosDto(
    val idRecordatorios: Int,
    val idfEquipos: Int,//--
    val idfTareas: Int,
    val idfUsuarios: Int,
    val idfPrioridades: Int,
    val idfTiposMantenimientos: Int,
    val recordarS_N: Byte,
    val fechaRecordatorio: String,
    val numeroReporte: Int,
    //yyyy-MM-dd'T'HH:mm:ss
    val descripcion: String
)

data class Create_RecordatoriosDto_2(
    val idfEquipos: Int,//--
    val idfTareas: Int,
    val idfUsuarios: Int,
    val idfPrioridades: Int,
    val idfTiposMantenimientos: Int,
    val recordarS_N: Byte,
    val fechaRecordatorio: String,
    val numeroReporte: Int,
    //yyyy-MM-dd'T'HH:mm:ss
    val descripcion: String
)