package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_RefaccionesDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_RefaccionesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_RefaccionesDto_Repository

class Post_RefaccionesDto_ViewModel (private val repository: Post_RefaccionesDto_Repository): ViewModel() {

    private val _posts = MutableStateFlow<List<Post_RefaccionesDto>>(emptyList())
    val posts: StateFlow<List<Post_RefaccionesDto>> = _posts

    val _selectedPost = MutableStateFlow<Post_RefaccionesDto?>(null)
    val selectedPost: StateFlow<Post_RefaccionesDto?> = _selectedPost

    fun fetchPosts() {
        viewModelScope.launch {
            //try {
            _posts.value = repository.getAll()
            //} catch (e: Exception) {
            //   e.printStackTrace()
            //}
        }
    }

    fun addPost(post: Create_RefaccionesDto_2) {
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

    fun updatePost(id: Int, post: Post_RefaccionesDto) {
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
