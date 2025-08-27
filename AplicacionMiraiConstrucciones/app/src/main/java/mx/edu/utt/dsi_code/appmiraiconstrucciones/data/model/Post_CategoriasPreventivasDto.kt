package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model

import com.google.gson.annotations.SerializedName

data class Post_CategoriasPreventivasDto(
    @SerializedName("id_categorias_preventivas") val idCategoriasPreventivas: Int,
    @SerializedName("nombre_categorias") val nombreCategorias: String,
)

data class Create_CategoriasPreventivasDto_2(
    @SerializedName("nombreCategorias") val nombreCategorias: String,
)
