package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- EMPRESAS ----------------
data class Post_EmpresasDto(
    @SerializedName("id_empresas") val idEmpresas: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("reporta_osolicita") val reportaOsolicita: String,
)

data class Create_EmpresasDto_2(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("reporta_osolicita") val reportaOsolicita: String,
)
