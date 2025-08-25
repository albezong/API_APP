package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_CategoriasPreventivasDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_DetallesPreventivosDto_Repository

class Post_DetallesPreventivosDto_ViewModel_Factory(private val repository: Post_DetallesPreventivosDto_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Post_DetallesPreventivosDto_ViewModel::class.java)) {
            return Post_DetallesPreventivosDto_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
