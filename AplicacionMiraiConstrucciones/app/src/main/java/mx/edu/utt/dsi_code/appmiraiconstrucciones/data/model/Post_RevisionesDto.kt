package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName


// ---------------- REVISIONES ----------------
data class Post_RevisionesDto(
    @SerializedName("id_revisiones") val idRevisiones: Int,
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int,
    @SerializedName("idf_equipos") val idfEquipos: Int,
    @SerializedName("idf_usuarios") val idfUsuarios: Int,
    @SerializedName("idf_empresas") val idfEmpresas: Int,
    @SerializedName("fecha") val fecha: String, // yyyy-MM-dd'T'HH:mm:ss
    @SerializedName("descripcion") val descripcion: String,
)

data class Create_RevisionesDto_2(
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int,
    @SerializedName("idf_equipos") val idfEquipos: Int,
    @SerializedName("idf_usuarios") val idfUsuarios: Int,
    @SerializedName("idf_empresas") val idfEmpresas: Int,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("descripcion") val descripcion: String,
)
