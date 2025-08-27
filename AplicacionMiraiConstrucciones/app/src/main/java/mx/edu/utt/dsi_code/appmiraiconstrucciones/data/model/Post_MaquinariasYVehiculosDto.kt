package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

// ---------------- MAQUINARIAS Y VEHICULOS ----------------
data class Post_MaquinariasYVehiculosDto(
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("page_size") val pageSize: Int,
    @SerializedName("items") val items: List<Maquinaria>
)

data class Maquinaria(
    @SerializedName("id_equipos") val idEquipos: Int,
    @SerializedName("codigo_articulo") val codigoArticulo: String,
    @SerializedName("nombre_articulo") val nombreArticulo: String,
    @SerializedName("nombre_comercial") val nombreComercial: String,
    @SerializedName("num_identificador") val numIdentificador: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("marca") val marca: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("fechade_registro") val fechadeRegistro: String,
    @SerializedName("ubicacion_nombre") val ubicacionNombre: String,
    @SerializedName("unidad_nombre") val unidadNombre: String,
    @SerializedName("estatus_nombre") val estatusNombre: String,
    @SerializedName("tipo_maquinaria_nombre") val tipoMaquinariaNombre: String,
    @SerializedName("nombre_lugar") val nombreLugar: String
)

