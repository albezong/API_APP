package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EquiposDto_Repository

class Post_EquiposDto_ViewModel_Factory(private val repository: Post_EquiposDto_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Post_EquiposDto_ViewModel::class.java)) {
            return Post_EquiposDto_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}