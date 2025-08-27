package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName


// ---------------- EQUIPOS ----------------
data class Post_EquiposDto(
    @SerializedName("id_equipos") val idEquipos: Int,
    @SerializedName("codigo_articulo") val codigoArticulo: String,
    @SerializedName("nombre_articulo") val nombreArticulo: String,
    @SerializedName("nombre_comercial") val nombreComercial: String,
    @SerializedName("num_identificador") val numIdentificador: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("marca") val marca: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("fechade_registro") val fechadeRegistro: String, // yyyy-MM-dd'T'HH:mm:ss
    @SerializedName("idf_ubicaciones") val idfUbicaciones: Int,
    @SerializedName("idf_unidades") val idfUnidades: Int,
    @SerializedName("idf_estatus") val idfEstatus: Int,
    @SerializedName("idf_tipos_maquinarias") val idfTiposMaquinarias: Int,
)

data class Create_EquiposDto_2(
    @SerializedName("codigo_articulo") val codigoArticulo: String,
    @SerializedName("nombre_articulo") val nombreArticulo: String,
    @SerializedName("nombre_comercial") val nombreComercial: String,
    @SerializedName("num_identificador") val numIdentificador: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("marca") val marca: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("fechade_registro") val fechadeRegistro: String,
    @SerializedName("idf_ubicaciones") val idfUbicaciones: Int,
    @SerializedName("idf_unidades") val idfUnidades: Int,
    @SerializedName("idf_estatus") val idfEstatus: Int,
    @SerializedName("idf_tipos_maquinarias") val idfTiposMaquinarias: Int,
)
