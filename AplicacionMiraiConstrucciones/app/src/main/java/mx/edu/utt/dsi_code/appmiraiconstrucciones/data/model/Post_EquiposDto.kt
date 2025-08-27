package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

data class Post_EquiposDto(
val idEquipos: Int,
val codigoArticulo: String,
val nombreArticulo: String,
val nombreComercial: String,
val numIdentificador: String,
val descripcion: String,
val marca: String,
val modelo: String,
//yyyy-MM-dd'T'HH:mm:ss
val fechadeRegistro: String,
val idfUbicaciones: Int,
val idfUnidades: Int,
val idfEstatus: Int,
val idfTiposMaquinarias: Int,
)

data class Create_EquiposDto_2(
    val codigoArticulo: String,
    val nombreArticulo: String,
    val nombreComercial: String,
    val numIdentificador: String,
    val descripcion: String,
    val marca: String,
    val modelo: String,
    //yyyy-MM-dd'T'HH:mm:ss
    val fechadeRegistro: String,
    val idfUbicaciones: Int,
    val idfUnidades: Int,
    val idfEstatus: Int,
    val idfTiposMaquinarias: Int,
)