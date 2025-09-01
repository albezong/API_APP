package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import android.util.Log
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchPosts(
        search: String? = null,
        tipo: String? = null,
        tipoId: Int? = null,
        page: Int? = null,
        pageSize: Int? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try{
                val response = repository.getMaquinarias(search, tipo, tipoId, page, pageSize)
                if (response.isSuccessful) {
                    _posts.value = response.body()?.items ?: emptyList()
                } else {
                    val body = response.errorBody()?.string().orEmpty()
                    _error.value = "Error ${response.code()}: ${body.take(200)}"
                    _posts.value = emptyList()
                }
            }catch (e: Exception) {
                Log.e("VM", "fetchPosts error", e)
                _error.value = e.message ?: "Error de red"
                _posts.value = emptyList()
            } finally { _isLoading.value = false }
        }
    }

    fun getPostById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _selectedPost.value = null
            try {
                val response = repository.getMaquinariaById(id)
                if (response.isSuccessful) {
                    _selectedPost.value = response.body()
                } else {
                    val body = response.errorBody()?.string().orEmpty()
                    _error.value = "Error ${response.code()}: ${body.take(200)}"
                    Log.e("VM", "getPostById server error ${response.code()} body=$body")
                }
            } catch (e: Exception) {
                Log.e("VM", "getPostById error", e)
                _error.value = e.message ?: "Error de red"
            } finally {
                _isLoading.value = false
            }
        }
    }
}