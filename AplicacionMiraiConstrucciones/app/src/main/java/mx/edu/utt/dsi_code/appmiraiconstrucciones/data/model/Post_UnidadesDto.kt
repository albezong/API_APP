package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName


data class Post_UnidadesDto(
    @SerializedName("id_unidades") val idUnidades: Int,
    @SerializedName("nombre") val nombre: String,
)

data class Create_UnidadesDto_2(
    @SerializedName("nombre") val nombre: String,
)
