package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- REFACCIONES ----------------
data class Post_RefaccionesDto(
    @SerializedName("id_refacciones") val idRefacciones: Int,
    @SerializedName("idf_revisiones") val idfRevisiones: Int,
    @SerializedName("idf_unidades") val idfUnidades: Int,
    @SerializedName("nombre_refaccion") val nombreRefaccion: String,
    @SerializedName("idf_descripcion_prioridades") val idfDescripcionPrioridades: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("observaciones") val observaciones: String,
    @SerializedName("fecha") val fecha: String, // yyyy-MM-dd'T'HH:mm:ss
    @SerializedName("numero_reporte") val numeroReporte: Int,
    @SerializedName("descripcion") val descripcion: String,
)

data class Create_RefaccionesDto_2(
    @SerializedName("idf_revisiones") val idfRevisiones: Int,
    @SerializedName("idf_unidades") val idfUnidades: Int,
    @SerializedName("nombre_refaccion") val nombreRefaccion: String,
    @SerializedName("idf_descripcion_prioridades") val idfDescripcionPrioridades: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("observaciones") val observaciones: String,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("numero_reporte") val numeroReporte: Int,
    @SerializedName("descripcion") val descripcion: String,
)
