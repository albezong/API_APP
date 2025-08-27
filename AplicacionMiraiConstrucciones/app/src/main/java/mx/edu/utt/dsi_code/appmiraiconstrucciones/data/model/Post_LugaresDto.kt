package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- LUGARES ----------------
data class Post_LugaresDto(
    @SerializedName("id_lugares") val idLugares: Int,
    @SerializedName("nombre_lugar") val nombreLugar: String,
    @SerializedName("idf_equipos") val idfEquipos: Int,
)

data class Create_LugaresDto_2(
    @SerializedName("nombre_lugar") val nombreLugar: String,
    @SerializedName("idf_equipos") val idfEquipos: Int,
)
