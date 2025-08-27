package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_UbicacionesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UbicacionesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EquiposDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UbicacionesDto_Repository

class Post_UbicacionesDto_ViewModel(private val repository: Post_UbicacionesDto_Repository): ViewModel() {

    var _posts = MutableStateFlow<List<Post_UbicacionesDto>>(emptyList())
    val posts: StateFlow<List<Post_UbicacionesDto>> = _posts

    val _selectedPost = MutableStateFlow<Post_UbicacionesDto?>(null)
    val selectedPost: StateFlow<Post_UbicacionesDto?> = _selectedPost

    fun fetchPosts() {
        viewModelScope.launch {
            //try {
            _posts.value = repository.getAll()
            //} catch (e: Exception) {
            //   e.printStackTrace()
            //}
        }
    }

    fun addPost(post: Create_UbicacionesDto_2) {
        viewModelScope.launch {
            try {
                repository.create(post)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPostById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedPost.value = repository.getById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePost(id: Int, post: Create_UbicacionesDto_2) {
        viewModelScope.launch {
            try {
                repository.update(id, post)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
