package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- TIPOS MANTENIMIENTOS ----------------
data class Post_TiposMantenimientosDto(
    @SerializedName("id_tipos_mantenimientos") val idTiposMantenimientos: Int,
    @SerializedName("nombre") val nombre: String,
)

data class Create_TiposMantenimientosDto_2(
    @SerializedName("nombre") val nombre: String,
)

