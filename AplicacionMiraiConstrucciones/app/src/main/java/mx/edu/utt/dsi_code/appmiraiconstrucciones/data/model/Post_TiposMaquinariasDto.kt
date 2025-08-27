package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

data class Post_TiposMaquinariasDto(
    @SerializedName("id_tipos_maquinarias") val idTiposMaquinarias: Int,
    @SerializedName("nombre") val nombre: String,
)

data class Create_TiposMaquinariasDto_2(
    @SerializedName("nombre") val nombre: String,
)
