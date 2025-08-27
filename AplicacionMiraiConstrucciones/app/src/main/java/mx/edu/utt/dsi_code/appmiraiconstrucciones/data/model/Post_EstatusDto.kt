package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- ESTATUS ----------------
data class Post_EstatusDto(
    @SerializedName("id_estatus") val idEstatus: Int,
    @SerializedName("nombre") val nombre: String,
)

data class Create_EstatusDto_2(
    @SerializedName("nombre") val nombre: String,
)
