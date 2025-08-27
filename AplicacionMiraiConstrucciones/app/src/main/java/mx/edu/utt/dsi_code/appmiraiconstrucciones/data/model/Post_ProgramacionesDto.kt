package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model
import com.google.gson.annotations.SerializedName

// MODELO PARA POST (lo que recibes de la API)
data class Post_ProgramacionesDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int? = null,
    @SerializedName("idf_equipos") val idfEquipos: Int? = null,
    @SerializedName("idf_usuarios") val idfUsuarios: Int? = null,
    @SerializedName("fecha_programada") val fechaProgramada: String? = null,
    @SerializedName("fecha_creacion") val fechaCreacion: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("completado") val completado: Boolean? = false,
    @SerializedName("observaciones") val observaciones: String? = null
)

// MODELO PARA CREATE (lo que envías a la API)
data class Create_ProgramacionesDto(
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int,
    @SerializedName("idf_equipos") val idfEquipos: Int,
    @SerializedName("idf_usuarios") val idfUsuarios: Int,
    @SerializedName("fecha_programada") val fechaProgramada: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("observaciones") val observaciones: String? = null
)