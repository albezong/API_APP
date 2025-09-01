package mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel

import android.net.http.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Create_EquiposDto_2
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EquiposDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_EstatusDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_LugaresDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_TiposMaquinariasDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UbicacionesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.Post_UnidadesDto
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_EquiposDto_Repository
import retrofit2.Response
import retrofit2.http.*


class Post_EquiposDto_ViewModel(private val repository: Post_EquiposDto_Repository) : ViewModel() {

    val _posts = MutableStateFlow<List<Post_EquiposDto>>(emptyList())
    val posts: StateFlow<List<Post_EquiposDto>> = _posts

    private val _selectedPost = MutableStateFlow<Post_EquiposDto?>(null)
    val selectedPost: StateFlow<Post_EquiposDto?> = _selectedPost

    // --- Catálogos ---
    private val _ubicaciones = MutableStateFlow<List<Post_UbicacionesDto>>(emptyList())
    val ubicaciones: StateFlow<List<Post_UbicacionesDto>> = _ubicaciones

    private val _unidades = MutableStateFlow<List<Post_UnidadesDto>>(emptyList())
    val unidades: StateFlow<List<Post_UnidadesDto>> = _unidades

    private val _estatus = MutableStateFlow<List<Post_EstatusDto>>(emptyList())
    val estatus: StateFlow<List<Post_EstatusDto>> = _estatus

    private val _tiposMaquinarias = MutableStateFlow<List<Post_TiposMaquinariasDto>>(emptyList())
    val tiposMaquinarias: StateFlow<List<Post_TiposMaquinariasDto>> = _tiposMaquinarias

    private val _lugares = MutableStateFlow<List<Post_LugaresDto>>(emptyList())
    val lugares: StateFlow<List<Post_LugaresDto>> = _lugares

    // estado UI para detalle
    private val _isLoadingSelected = MutableStateFlow(false)
    val isLoadingSelected: StateFlow<Boolean> = _isLoadingSelected.asStateFlow()

    private val _errorSelected = MutableStateFlow<String?>(null)
    val errorSelected: StateFlow<String?> = _errorSelected.asStateFlow()

    // bandera para evitar recargas repetidas de catálogos
    private val _catalogsLoaded = MutableStateFlow(false)
    private val catalogsLoaded: StateFlow<Boolean> = _catalogsLoaded.asStateFlow()

    /*
    init {
        // cargar catálogos una sola vez cuando se cree el ViewModel
        loadCatalogsIfNeeded()
    }*/

    fun fetchPosts() {
        viewModelScope.launch {
            _posts.value = repository.getAll()
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
                //val ok = deletePostAndRefresh(id)
            try {
                repository.delete(id)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // getPostById mejorado (solo pide detalle y maneja estados)
    fun getPostById(id: Int) {
        /*
        viewModelScope.launch {
            _isLoadingSelected.value = true
            _errorSelected.value = null
            try {
                val result = repository.getById(id)
                _selectedPost.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                _errorSelected.value = "Error al obtener detalle: ${e.message ?: "Desconocido"}"
                _selectedPost.value = null
            } finally {
                _isLoadingSelected.value = false
            }
        }*/
        viewModelScope.launch {
            try {
                _selectedPost.value = repository.getById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePost(id: Int, post: Post_EquiposDto) {
        viewModelScope.launch {
            try {
                repository.update(id, post)
                fetchPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Carga de catálogos: función pública que evita recargar si ya fue cargado
    /*
    fun loadCatalogsIfNeeded() {
        viewModelScope.launch {
            if (_catalogsLoaded.value) return@launch
            try { _ubicaciones.value = repository.getAllUbicaciones() } catch (e: Exception) { e.printStackTrace() }
            try { _unidades.value = repository.getAllUnidades() } catch (e: Exception) { e.printStackTrace() }
            try { _estatus.value = repository.getAllEstatus() } catch (e: Exception) { e.printStackTrace() }
            try { _tiposMaquinarias.value = repository.getAllTiposMaquinarias() } catch (e: Exception) { e.printStackTrace() }
            try { _lugares.value = repository.getAllLugares() } catch (e: Exception) { e.printStackTrace() }
            _catalogsLoaded.value = true
        }
    }

    // mantengo la función original por compatibilidad (llama a la versión que chequea)
    fun loadCatalogs() { loadCatalogsIfNeeded() }

*/
    suspend fun deletePostAndRefresh(id: Int): Boolean {
        _isLoadingSelected.value = true
        _errorSelected.value = null
        return try {
            val resp: Response<Void> = withContext(Dispatchers.IO) { repository.delete(id) }
            if (resp.isSuccessful) {
                // recargar lista
                val all = withContext(Dispatchers.IO) { repository.getAll() }
                _posts.value = all
                true
            } else {
                // leer body de error si existe
                val body = try { resp.errorBody()?.string() } catch (ex: Exception) { null }
                val msg = body?.takeIf { it.isNotBlank() } ?: "Error del servidor: ${resp.code()}"
                _errorSelected.value = msg
                // log
                android.util.Log.e("VM", "delete failed code=${resp.code()} body=$body")
                false
            }
        } catch (e: Exception) {
            android.util.Log.e("VM", "Excepción al eliminar", e)
            _errorSelected.value = e.message ?: "Error desconocido"
            false
        } finally {
            _isLoadingSelected.value = false
        }
    }
}