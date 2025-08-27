package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_MaquinariasYVehiculosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_MaquinariasYVehiculosDto_Repository

class Post_MaquinariasYVehiculosDto_ViewModel(private val repository: Post_MaquinariasYVehiculosDto_Repository): ViewModel() {

    private val _posts = MutableStateFlow<List<Maquinaria>>(emptyList())
    val posts: StateFlow<List<Maquinaria>> = _posts

    private val _selectedPost = MutableStateFlow<Maquinaria?>(null)
    val selectedPost: StateFlow<Maquinaria?> = _selectedPost

    fun fetchPosts(
        search: String? = null,
        tipo: String? = null,
        tipoId: Int? = null,
        page: Int? = null,
        pageSize: Int? = null
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getMaquinarias(search, tipo, tipoId, page, pageSize)
                _posts.value = response.items  // asumiendo que tu DTO tiene `items: List<Maquinaria>`
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPostById(id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getMaquinariaById(id) // aquí vas a tu API o DB
                _selectedPost.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}