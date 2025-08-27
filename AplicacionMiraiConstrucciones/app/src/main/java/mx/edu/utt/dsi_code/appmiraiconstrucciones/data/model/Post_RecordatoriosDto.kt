package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- RECORDATORIOS ----------------
data class Post_RecordatoriosDto(
    @SerializedName("id_recordatorios") val idRecordatorios: Int,
    @SerializedName("idf_equipos") val idfEquipos: Int,
    @SerializedName("idf_tareas") val idfTareas: Int,
    @SerializedName("idf_usuarios") val idfUsuarios: Int,
    @SerializedName("idf_prioridades") val idfPrioridades: Int,
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int,
    @SerializedName("recordar_s_n") val recordarS_N: Byte,
    @SerializedName("fecha_recordatorio") val fechaRecordatorio: String,
    @SerializedName("numero_reporte") val numeroReporte: Int,
    @SerializedName("descripcion") val descripcion: String,
)

data class Create_RecordatoriosDto_2(
    @SerializedName("idf_equipos") val idfEquipos: Int,
    @SerializedName("idf_tareas") val idfTareas: Int,
    @SerializedName("idf_usuarios") val idfUsuarios: Int,
    @SerializedName("idf_prioridades") val idfPrioridades: Int,
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int,
    @SerializedName("recordar_s_n") val recordarS_N: Byte,
    @SerializedName("fecha_recordatorio") val fechaRecordatorio: String,
    @SerializedName("numero_reporte") val numeroReporte: Int,
    @SerializedName("descripcion") val descripcion: String,
)


