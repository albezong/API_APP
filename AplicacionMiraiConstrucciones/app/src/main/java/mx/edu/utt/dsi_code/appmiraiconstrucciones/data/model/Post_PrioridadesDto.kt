package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- PRIORIDADES ----------------
data class Post_PrioridadesDto(
    @SerializedName("id_prioridades") val idPrioridades: Int,
    @SerializedName("nombre") val nombre: String,
)

data class Create_PrioridadesDto_2(
    @SerializedName("nombre") val nombre: String,
)