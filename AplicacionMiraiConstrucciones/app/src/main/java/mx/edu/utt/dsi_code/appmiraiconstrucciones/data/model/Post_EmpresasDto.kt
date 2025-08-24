package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_EmpresasDto(
    val idEmpresas: Int,
    val nombre: String,
    val reportaOsolicita: String,
)

data class Create_EmpresasDto_2(
    val nombre: String,
    val reportaOsolicita: String,
)