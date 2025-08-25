package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UsuariosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_UsuariosDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UsuariosDto_Repository

class Post_UsuariosDto_ViewModel(private val repository: Post_UsuariosDto_Repository): ViewModel() {

    private val _posts = MutableStateFlow<List<Post_UsuariosDto>>(emptyList())
    val posts: StateFlow<List<Post_UsuariosDto>> = _posts

    val _selectedPost = MutableStateFlow<Post_UsuariosDto?>(null)
    val selectedPost: StateFlow<Post_UsuariosDto?> = _selectedPost

    fun getAllUsuarios() {
        viewModelScope.launch {
            //try {
            _posts.value = repository.getAllUsuarios()
            //} catch (e: Exception) {
            //   e.printStackTrace()
            //}
        }
    }

    fun createUsuario(post: Create_UsuariosDto_2) {
        viewModelScope.launch {
            try {
                repository.getUsuarioById(post)
                getAllUsuarios()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUsuarioById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedPost.value = repository.createUsuario(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUsuario(id: Int, post: Create_UsuariosDto_2) {
        viewModelScope.launch {
            try {
                repository.updateUsuario(id, post)
                getAllUsuarios()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteUsuario(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteUsuario(id)
                getAllUsuarios()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}