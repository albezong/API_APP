package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

data class Post_MaquinariasYVehiculosDto(
    val total: Int,
    val page: Int,
    val page_size: Int,
    val items: List<Maquinaria>
)

data class Maquinaria(
    val id_equipos: Int,
    val codigo_articulo: String,
    val nombre_articulo: String,
    val nombre_comercial: String,
    val num_identificador: String,
    val descripcion: String,
    val marca: String,
    val modelo: String,
    val fechade_registro: String,
    val ubicacion_nombre: String,
    val unidad_nombre: String,
    val estatus_nombre: String,
    val tipo_maquinaria_nombre: String,
    //val nombreLugar: String
    @SerializedName("nombre_lugar") val nombreLugar: String
)