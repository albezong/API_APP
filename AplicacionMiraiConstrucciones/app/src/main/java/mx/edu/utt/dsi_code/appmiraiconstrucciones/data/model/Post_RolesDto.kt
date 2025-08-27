package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- ROLES ----------------
data class Post_RolesDto(
    @SerializedName("id_roles") val idRoles: Int,
    @SerializedName("nombre") val nombre: String,
)

data class Create_RolesDto_2(
    @SerializedName("nombre") val nombre: String,
)