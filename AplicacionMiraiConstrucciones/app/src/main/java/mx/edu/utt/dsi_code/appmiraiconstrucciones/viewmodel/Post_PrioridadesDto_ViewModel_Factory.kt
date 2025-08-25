package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EquiposDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_PrioridadesDto_Repository

class Post_PrioridadesDto_ViewModel_Factory(private val repository: Post_PrioridadesDto_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Post_PrioridadesDto_ViewModel::class.java)) {
            return Post_PrioridadesDto_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}