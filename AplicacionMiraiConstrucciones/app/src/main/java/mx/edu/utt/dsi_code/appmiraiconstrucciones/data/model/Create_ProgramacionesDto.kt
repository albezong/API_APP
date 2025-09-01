package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model
import com.google.gson.annotations.SerializedName

// MODELO ORIGINAL PARA POST (lo que recibes de la API)
data class Post_HistorialServiciosDto(
    @SerializedName("id_revisiones") val idRevisiones: Int? = null,
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int? = null,
    @SerializedName("idf_equipos") val idfEquipos: Int? = null,
    @SerializedName("idf_usuarios") val idfUsuarios: Int? = null,
    @SerializedName("idf_empresas") val idfEmpresas: Int? = null,
    @SerializedName("fecha") val fecha: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
    // Listas: por defecto vacías para evitar null-checks en UI
    @SerializedName("refacciones") val refacciones: List<RefaccionDto> = emptyList(),
    @SerializedName("detalles_preventivos") val detallesPreventivos: List<DetallePreventivoDto> = emptyList()
)

// MODELO PARA CREATE (lo que envías a la API)
data class Create_HistorialServiciosDto(
    @SerializedName("idf_tipos_mantenimientos") val idfTiposMantenimientos: Int,
    @SerializedName("idf_equipos") val idfEquipos: Int,
    @SerializedName("idf_usuarios") val idfUsuarios: Int,
    @SerializedName("idf_empresas") val idfEmpresas: Int,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("descripcion") val descripcion: String
)

data class RefaccionDt(
    @SerializedName("id_refacciones") val idRefacciones: Int? = null,
    @SerializedName("idf_revisiones") val idfRevisiones: Int? = null,
    @SerializedName("idf_unidades") val idfUnidades: Int? = null,
    @SerializedName("nombre_refaccion") val nombreRefaccion: String? = null,
    @SerializedName("idf_descripcion_prioridades") val idfDescripcionPrioridades: Int? = null,
    @SerializedName("cantidad") val cantidad: Int? = null,
    @SerializedName("observaciones") val observaciones: String? = null,
    @SerializedName("fecha") val fecha: String? = null,
    @SerializedName("numero_reporte") val numeroReporte: Int? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
    // Los objetos anidados vacíos en tu JSON los dejamos como opcionales (null) para no crear ciclos
    @SerializedName("revisiones") val revisiones: Any? = null,
    @SerializedName("unidades") val unidades: Any? = null
)

data class DetallePreventivoDt(
    @SerializedName("id_detalles_preventivos") val idDetallesPreventivos: Int? = null,
    @SerializedName("idf_revisiones") val idfRevisiones: Int? = null,
    @SerializedName("idf_categorias_preventivas") val idfCategoriasPreventivas: Int? = null,
    @SerializedName("nombre_parte") val nombreParte: String? = null,
    @SerializedName("idf_estado_prioridades") val idfEstadoPrioridades: Int? = null,
    @SerializedName("comentarios") val comentarios: String? = null,
    @SerializedName("observaciones") val observaciones: String? = null,
    @SerializedName("fecha") val fecha: String? = null,
    @SerializedName("numero_reporte") val numeroReporte: Int? = null,
    @SerializedName("categorias_preventivas") val categoriasPreventivas: Any? = null,
    @SerializedName("prioridades") val prioridades: Any? = null,
    @SerializedName("revisiones") val revisiones: Any? = null
)