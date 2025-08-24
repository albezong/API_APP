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

    private val _posts = MutableStateFlow<List<Post_MaquinariasYVehiculosDto>>(emptyList())
    val posts: StateFlow<List<Post_MaquinariasYVehiculosDto>> = _posts

    val _selectedPost = MutableStateFlow<Maquinaria?>(null)
    val selectedPost: StateFlow<Maquinaria?> = _selectedPost

    fun fetchPosts(search: String? = null,
                   tipo: String? = null,
                   tipoId: Int? = null,
                   page: Int? = null,
                   pageSize: Int? = null) {
        viewModelScope.launch {
            try {
            //_posts.value =
                repository.getMaquinarias(search, tipo, tipoId, page, pageSize)
                fetchPosts()
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }


    fun getPostById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedPost.value = repository.getMaquinariaById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}