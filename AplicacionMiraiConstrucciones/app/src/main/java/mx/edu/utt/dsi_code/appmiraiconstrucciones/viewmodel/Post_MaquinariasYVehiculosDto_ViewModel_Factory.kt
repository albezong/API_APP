package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Maquinaria
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_MaquinariasYVehiculosDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_MaquinariasYVehiculosDto_Repository

class Post_MaquinariasYVehiculosDto_ViewModel_Factory(private val repository: Post_MaquinariasYVehiculosDto_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Post_MaquinariasYVehiculosDto_ViewModel::class.java)) {
            return Post_MaquinariasYVehiculosDto_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}