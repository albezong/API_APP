package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

data class Post_UbicacionesDto(
    @SerializedName("id_ubicaciones")
    val idUbicaciones: Int,

    @SerializedName("nombre")
    val nombre: String
)

data class Create_UbicacionesDto_2(
    @SerializedName("nombre")
    val nombre: String
)