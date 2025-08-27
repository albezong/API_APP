package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

data class Post_UsuariosDto(
    @SerializedName("id_usuarios") val idUsuarios: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido_paterno") val apellidoPaterno: String,
    @SerializedName("apellido_materno") val apellidoMaterno: String,
    @SerializedName("contraseña") val contraseña: String,
    @SerializedName("idf_roles") val idfRoles: Int,
    @SerializedName("telefono") val telefono: String,
)

data class Create_UsuariosDto_2(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido_paterno") val apellidoPaterno: String,
    @SerializedName("apellido_materno") val apellidoMaterno: String,
    @SerializedName("contraseña") val contraseña: String,
    @SerializedName("idf_roles") val idfRoles: Int,
    @SerializedName("telefono") val telefono: String,
)
