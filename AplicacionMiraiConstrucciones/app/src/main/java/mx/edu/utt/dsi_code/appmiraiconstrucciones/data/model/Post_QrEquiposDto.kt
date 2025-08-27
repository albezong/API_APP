package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

data class Post_QrEquiposDto(
    @SerializedName("id_qr_equipos") val idQrEquipos: Int,
    @SerializedName("clave_qr") val claveQR: String,
    @SerializedName("id_equipos") val idEquipos: Int,
)

data class Create_QrEquiposDto_2(
    @SerializedName("clave_qr") val claveQR: String,
    @SerializedName("id_equipos") val idEquipos: Int,
)


