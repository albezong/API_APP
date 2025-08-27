package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- TAREAS ----------------
data class Post_TareasDto(
    @SerializedName("id_tareas") val idTareas: Int,
    @SerializedName("id_tipos_mantenimientos") val idTiposMantenimientos: Int,
    @SerializedName("descripcion") val descripcion: String,
)

data class Create_TareasDto_2(
    @SerializedName("id_tipos_mantenimientos") val idTiposMantenimientos: Int,
    @SerializedName("descripcion") val descripcion: String,
)
