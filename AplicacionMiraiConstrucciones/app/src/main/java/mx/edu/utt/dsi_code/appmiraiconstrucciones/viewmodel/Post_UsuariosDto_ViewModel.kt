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

    fun fetchPosts() {
        viewModelScope.launch {
            //try {
            _posts.value = repository.getAll()
            //} catch (e: Exception) {
            //   e.printStackTrace()
            //}
        }
    }

    fun createUsuario(post: Create_UsuariosDto_2) {
        viewModelScope.launch {
            try {
                repository.create(post)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteUsuario(id: Int) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUsuarioById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedPost.value = repository.getById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUsuario(id: Int, post: Create_UsuariosDto_2) {
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

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val usuario: Post_UsuariosDto) : LoginState()
    data class Error(val message: String) : LoginState()
}

class UsuariosViewModelLogeo(private val repository: Post_UsuariosDto_Repository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun authenticate(nombre: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val user = repository.authenticate(nombre.trim(), password)
                if (user != null) {
                    _loginState.value = LoginState.Success(user)
                } else {
                    _loginState.value = LoginState.Error("Correo o contraseña incorrectos")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Error de red")
            }
        }
    }

    fun reset() {
        _loginState.value = LoginState.Idle
    }
}
