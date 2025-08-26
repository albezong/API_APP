package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_LugaresDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TiposMaquinariasDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UbicacionesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EquiposDto_Repository

class Post_EquiposDto_ViewModel (private val repository: Post_EquiposDto_Repository): ViewModel() {

    private val _posts = MutableStateFlow<List<Post_EquiposDto>>(emptyList())
    val posts: StateFlow<List<Post_EquiposDto>> = _posts

    val _selectedPost = MutableStateFlow<Post_EquiposDto?>(null)
    val selectedPost: StateFlow<Post_EquiposDto?> = _selectedPost


    private val _updateState = MutableStateFlow<Result<Post_EquiposDto>?>(null)
    val updateState: StateFlow<Result<Post_EquiposDto>?> = _updateState

    // --- Catálogos ---
    private val _ubicaciones = MutableStateFlow<List<Post_UbicacionesDto>>(emptyList())
    val ubicaciones: StateFlow<List<Post_UbicacionesDto>> = _ubicaciones

    private val _tiposMaquinarias = MutableStateFlow<List<Post_TiposMaquinariasDto>>(emptyList())
    val tiposMaquinarias: StateFlow<List<Post_TiposMaquinariasDto>> = _tiposMaquinarias

    private val _lugares = MutableStateFlow<List<Post_LugaresDto>>(emptyList())
    val lugares: StateFlow<List<Post_LugaresDto>> = _lugares



    fun fetchPosts() {
        viewModelScope.launch {
            //try {
            _posts.value = repository.getAll()
            //} catch (e: Exception) {
            //   e.printStackTrace()
            //}
        }
    }

    fun addPost(post: Create_EquiposDto_2) {
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

    fun updatePost(id: Int, post: Create_EquiposDto_2) {
        viewModelScope.launch {
            try {
                repository.update(id, post)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --- funciones para cargar catálogos ---
    fun loadCatalogs() {
        viewModelScope.launch {
            try {
                _ubicaciones.value = repository.getAllUbicaciones()
            } catch (e: Exception) { e.printStackTrace() }
            try {
                _tiposMaquinarias.value = repository.getAllTiposMaquinarias()
            } catch (e: Exception) { e.printStackTrace() }
            try {
                _lugares.value = repository.getAllLugares()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun clearUpdateState() { _updateState.value = null }
}
