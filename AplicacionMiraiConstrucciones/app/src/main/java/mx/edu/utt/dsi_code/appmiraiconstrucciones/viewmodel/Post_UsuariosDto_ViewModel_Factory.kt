package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UsuariosDto_Repository

class Post_UsuariosDto_ViewModel_Factory(private val repository: Post_UsuariosDto_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Post_UsuariosDto_ViewModel::class.java)) {
            return Post_UsuariosDto_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class UsuariosViewModelFactory(private val repo: Post_UsuariosDto_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuariosViewModelLogeo::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuariosViewModelLogeo(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}