package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- DETALLES PREVENTIVOS ----------------
data class Post_DetallesPreventivosDto(
    @SerializedName("id_detalles_preventivos") val idDetallesPreventivos: Int,
    @SerializedName("idf_revisiones") val idfRevisiones: Int,
    @SerializedName("idf_categorias_preventivas") val idfCategoriasPreventivas: Int,
    @SerializedName("nombre_parte") val nombreParte: String,
    @SerializedName("idf_estado_prioridades") val idfEstadoPrioridades: Int,
    @SerializedName("comentarios") val comentarios: String,
    @SerializedName("observaciones") val observaciones: String,
    @SerializedName("fecha") val fecha: String, // yyyy-MM-dd'T'HH:mm:ss
    @SerializedName("numero_reporte") val numeroReporte: Int,
)

data class Create_DetallesPreventivosDto_2(
    @SerializedName("idf_revisiones") val idfRevisiones: Int,
    @SerializedName("idf_categorias_preventivas") val idfCategoriasPreventivas: Int,
    @SerializedName("nombre_parte") val nombreParte: String,
    @SerializedName("idf_estado_prioridades") val idfEstadoPrioridades: Int,
    @SerializedName("comentarios") val comentarios: String,
    @SerializedName("observaciones") val observaciones: String,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("numero_reporte") val numeroReporte: Int,
)



