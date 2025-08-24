package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_QrEquiposDto(
    val idQrEquipos: Int,
    val claveQR: String,//--
    val idEquipos: Int,
)

data class Create_QrEquiposDto_2(
    val claveQR: String,//--
    val idEquipos: Int,
)