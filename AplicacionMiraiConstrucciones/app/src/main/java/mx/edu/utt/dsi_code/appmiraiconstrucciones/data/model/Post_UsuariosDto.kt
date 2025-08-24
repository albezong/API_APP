package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_UsuariosDto(
    val idUsuarios: Int,
    val nombre: String,//--
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val contraseña: String,
    val idfRoles: Int,
    val telefono: String,
)

data class Create_UsuariosDto_2(
    val nombre: String,//--
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val contraseña: String,
    val idfRoles: Int,
    val telefono: String,
)