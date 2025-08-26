package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Este es un comentario para ver si se generan cambios en git
    // ----------- Maquinaria -----------
    // Lista con filtros opcionales (search, tipo, tipoId, page, pageSize)
    @GET("api/maquinaria")
    suspend fun getMaquinarias(
        @Query("search") search: String? = null,
        @Query("tipo") tipo: String? = null,
        @Query("tipoId") tipoId: Int? = null,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): Post_MaquinariasYVehiculosDto

    // Detalle por id
    @GET("api/maquinaria/{id}")
    suspend fun getMaquinariaById(
        @Path("id") id: Int
    ): Maquinaria
/*
    // ----------- HistorialServicios -----------<<
    @GET("api/historialServicios")
    suspend fun getAllHistorialServicios(): List<HistorialServicioDto>

    @POST("api/historialServicios")
    suspend fun createHistorialServicio(@Body historial: HistorialServicioCreate): HistorialServicio

    @DELETE("api/historialServicios/{id}")
    suspend fun deleteHistorialServicio(@Path("id") id: Int)

    @GET("api/historialServicios/{id}")
    suspend fun getHistorialServicioById(@Path("id") id: Int): HistorialServicio

    @PUT("api/historialServicios/{id}")
    suspend fun updateHistorialServicio(@Path("id") id: Int, @Body historial: HistorialServicioCreate): HistorialServicioCreate


    // ----------- Programaciones -----------
    @GET("api/programaciones")
    suspend fun getAllProgramaciones(): List<Programacion>

    @POST("api/programaciones")
    suspend fun createProgramacion(@Body programacion: ProgramacionCreate): Programacion

    @DELETE("api/programaciones/{id}")
    suspend fun deleteProgramacion(@Path("id") id: Int)

    @GET("api/programaciones/{id}")
    suspend fun getProgramacionById(@Path("id") id: Int): Programacion

    @PUT("api/programaciones/{id}")
    suspend fun updateProgramacion(@Path("id") id: Int, @Body programacion: ProgramacionCreate): ProgramacionCreate
*/
    // ----------- CategoriasPreventivas -----------
    @GET("api/CategoriasPreventivas")
    suspend fun getAllCategoriasPreventivas(): List<Post_CategoriasPreventivasDto>

    @POST("api/CategoriasPreventivas")
    suspend fun createCategoriaPreventiva(@Body categoria: Create_CategoriasPreventivasDto_2): Post_CategoriasPreventivasDto

    @DELETE("api/CategoriasPreventivas/{id}")
    suspend fun deleteCategoriaPreventiva(@Path("idCategoriasPreventivas") id: Int)

    @GET("api/CategoriasPreventivas/{id}")
    suspend fun getCategoriaPreventivaById(@Path("idCategoriasPreventivas") id: Int): Post_CategoriasPreventivasDto

    @PUT("api/CategoriasPreventivas/{id}")
    suspend fun updateCategoriaPreventiva(@Path("idCategoriasPreventivas") id: Int, @Body categoria: Create_CategoriasPreventivasDto_2): Create_CategoriasPreventivasDto_2


    // ----------- DetallesPreventivos -----------
    @GET("api/DetallesPreventivos")
    suspend fun getAllDetallesPreventivos(): List<Post_DetallesPreventivosDto>

    @POST("api/DetallesPreventivos")
    suspend fun createDetallePreventivo(@Body detalle: Create_DetallesPreventivosDto_2): Post_DetallesPreventivosDto

    @DELETE("api/DetallesPreventivos/{id}")
    suspend fun deleteDetallePreventivo(@Path("idDetallesPreventivos") id: Int)

    @GET("api/DetallesPreventivos/{id}")
    suspend fun getDetallePreventivoById(@Path("idDetallesPreventivos") id: Int): Post_DetallesPreventivosDto

    @PUT("api/DetallesPreventivos/{id}")
    suspend fun updateDetallePreventivo(@Path("idDetallesPreventivos") id: Int, @Body detalle: Create_DetallesPreventivosDto_2): Create_DetallesPreventivosDto_2


    // ----------- Empresas -----------
    @GET("api/Empresas")
    suspend fun getAllEmpresas(): List<Post_EmpresasDto>

    @POST("api/Empresas")
    suspend fun createEmpresa(@Body empresa: Create_EmpresasDto_2): Post_EmpresasDto

    @DELETE("api/Empresas/{id}")
    suspend fun deleteEmpresa(@Path("idEmpresas") id: Int)

    @GET("api/Empresas/{id}")
    suspend fun getEmpresaById(@Path("idEmpresas") id: Int): Post_EmpresasDto

    @PUT("api/Empresas/{id}")
    suspend fun updateEmpresa(@Path("idEmpresas") id: Int, @Body empresa: Create_EmpresasDto_2): Create_EmpresasDto_2

    // ----------- Equipos -----------
    @GET("api/Equipos")
    suspend fun getAllEquipos(): List<Post_EquiposDto>

    @POST("api/Equipos")
    suspend fun createEquipo(@Body equipo: Create_EquiposDto_2): Post_EquiposDto

    @DELETE("api/Equipos/{id}")
    suspend fun deleteEquipo(@Path("idEquipos") id: Int)

    @GET("api/Equipos/{id}")
    suspend fun getEquipoById(@Path("idEquipos") id: Int): Post_EquiposDto

    @PUT("api/Equipos/{id}")
    suspend fun updateEquipo(@Path("idEquipos") id: Int, @Body equipo: Create_EquiposDto_2): Create_EquiposDto_2

    // ----------- Estatus -----------
    @GET("api/Estatus")
    suspend fun getAllEstatus(): List<Post_EstatusDto>

    @POST("api/Estatus")
    suspend fun createEstatus(@Body estatus: Create_EstatusDto_2): Post_EstatusDto

    @DELETE("api/Estatus/{id}")
    suspend fun deleteEstatus(@Path("idEstatus") id: Int)

    @GET("api/Estatus/{id}")
    suspend fun getEstatusById(@Path("idEstatus") id: Int): Post_EstatusDto

    @PUT("api/Estatus/{id}")
    suspend fun updateEstatus(@Path("idEstatus") id: Int, @Body estatus: Create_EstatusDto_2): Create_EstatusDto_2

    // ----------- Lugares -----------
    @GET("api/Lugares")
    suspend fun getAllLugares(): List<Post_LugaresDto>

    @POST("api/Lugares")
    suspend fun createLugar(@Body lugar: Create_LugaresDto_2): Post_LugaresDto

    @DELETE("api/Lugares/{id}")
    suspend fun deleteLugar(@Path("idLugares") id: Int)

    @GET("api/Lugares/{id}")
    suspend fun getLugarById(@Path("idLugares") id: Int): Post_LugaresDto

    @PUT("api/Lugares/{id}")
    suspend fun updateLugar(@Path("idLugares") id: Int, @Body lugar: Create_LugaresDto_2): Create_LugaresDto_2

    // ----------- PDFs especiales -----------
    /*
    @GET("api/pdf_RevisionMantenimientoGETid_PREVENTIVOS/{id}")
    suspend fun getPdfPreventivo(@Path("id") id: Int): PdfResponse

    @GET("api/pdf_RevisionMantenimientoGETid_CORRECTIVO/{id}")
    suspend fun getPdfCorrectivo(@Path("id") id: Int): PdfResponse

    @GET("api/pdf_ProgramarRecordatoriosMantenimientoGETid/{id}")
    suspend fun getPdfRecordatorio(@Path("id") id: Int): PdfResponse

    @GET("api/pdf_porEquipoGETid/{id}")
    suspend fun getPdfPorEquipo(@Path("id") id: Int): PdfResponse*/

    // ----------- Prioridades -----------
    @GET("api/Prioridades")
    suspend fun getAllPrioridades(): List<Post_PrioridadesDto>

    @POST("api/Prioridades")
    suspend fun createPrioridad(@Body prioridad: Create_PrioridadesDto_2): Post_PrioridadesDto

    @DELETE("api/Prioridades/{id}")
    suspend fun deletePrioridad(@Path("idPrioridades") id: Int)

    @GET("api/Prioridades/{id}")
    suspend fun getPrioridadById(@Path("idPrioridades") id: Int): Post_PrioridadesDto

    @PUT("api/Prioridades/{id}")
    suspend fun updatePrioridad(@Path("idPrioridades") id: Int, @Body prioridad: Create_PrioridadesDto_2): Create_PrioridadesDto_2

    // ----------- QrEquipos -----------
    /*
    @GET("api/qr/generarQRyDevolverloComoPNG/{id}")
    suspend fun generarQrEquipo(@Path("id") id: Int): QrResponse

    @GET("api/qr/VerQRenIMG/{id}")
    suspend fun verQrEnImg(@Path("id") id: Int): QrResponse*/

    @GET("api/QrEquipos")
    suspend fun getAllQrEquipos(): List<Post_QrEquiposDto>

    @POST("api/QrEquipos")
    suspend fun createQrEquipo(@Body qrEquipo: Create_QrEquiposDto_2): Post_QrEquiposDto

    @DELETE("api/QrEquipos/{id}")
    suspend fun deleteQrEquipo(@Path("idQrEquipos") id: Int)

    @GET("api/QrEquipos/{id}")
    suspend fun getQrEquipoById(@Path("idQrEquipos") id: Int): Post_QrEquiposDto

    @PUT("api/QrEquipos/{id}")
    suspend fun updateQrEquipo(@Path("idQrEquipos") id: Int, @Body qrEquipo: Create_QrEquiposDto_2): Create_QrEquiposDto_2


    // ----------- Recordatorios -----------
    @GET("api/Recordatorios")
    suspend fun getAllRecordatorios(): List<Post_RecordatoriosDto>

    @POST("api/Recordatorios")
    suspend fun createRecordatorio(@Body recordatorio: Create_RecordatoriosDto_2): Post_RecordatoriosDto

    @DELETE("api/Recordatorios/{id}")
    suspend fun deleteRecordatorio(@Path("idRecordatorios") id: Int)

    @GET("api/Recordatorios/{id}")
    suspend fun getRecordatorioById(@Path("idRecordatorios") id: Int): Post_RecordatoriosDto

    @PUT("api/Recordatorios/{id}")
    suspend fun updateRecordatorio(@Path("idRecordatorios") id: Int, @Body recordatorio: Create_RecordatoriosDto_2): Create_RecordatoriosDto_2

    // ----------- Refacciones -----------
    @GET("api/Refacciones")
    suspend fun getAllRefacciones(): List<Post_RefaccionesDto>

    @POST("api/Refacciones")
    suspend fun createRefaccion(@Body refaccion: Create_RefaccionesDto_2): Post_RefaccionesDto

    @DELETE("api/Refacciones/{id}")
    suspend fun deleteRefaccion(@Path("idRefacciones") id: Int)

    @GET("api/Refacciones/{id}")
    suspend fun getRefaccionById(@Path("idRefacciones") id: Int): Post_RefaccionesDto

    @PUT("api/Refacciones/{id}")
    suspend fun updateRefaccion(@Path("idRefacciones") id: Int, @Body refaccion: Create_RefaccionesDto_2): Create_RefaccionesDto_2

    // ----------- Revisiones -----------
    @GET("api/Revisiones")
    suspend fun getAllRevisiones(): List<Post_RevisionesDto>

    @POST("api/Revisiones")
    suspend fun createRevision(@Body revision: Create_RevisionesDto_2): Post_RevisionesDto

    @DELETE("api/Revisiones/{id}")
    suspend fun deleteRevision(@Path("idRevisiones") id: Int)

    @GET("api/Revisiones/{id}")
    suspend fun getRevisionById(@Path("idRevisiones") id: Int): Post_RevisionesDto

    @PUT("api/Revisiones/{id}")
    suspend fun updateRevision(@Path("idRevisiones") id: Int, @Body revision: Create_RevisionesDto_2): Create_RevisionesDto_2

    // ----------- Roles -----------
    @GET("api/Roles")
    suspend fun getAllRoles(): List<Post_RolesDto>

    @POST("api/Roles")
    suspend fun createRol(@Body rol: Create_RolesDto_2): Post_RolesDto

    @DELETE("api/Roles/{id}")
    suspend fun deleteRol(@Path("idRoles") id: Int)

    @GET("api/Roles/{id}")
    suspend fun getRolById(@Path("idRoles") id: Int): Post_RolesDto

    @PUT("api/Roles/{id}")
    suspend fun updateRol(@Path("idRoles") id: Int, @Body rol: Create_RolesDto_2): Create_RolesDto_2

    // ----------- Tareas -----------
    @GET("api/Tareas")
    suspend fun getAllTareas(): List<Post_TareasDto>

    @POST("api/Tareas")
    suspend fun createTarea(@Body tarea: Create_TareasDto_2): Post_TareasDto

    @DELETE("api/Tareas/{id}")
    suspend fun deleteTarea(@Path("idTareas") id: Int)

    @GET("api/Tareas/{id}")
    suspend fun getTareaById(@Path("idTareas") id: Int): Post_TareasDto

    @PUT("api/Tareas/{id}")
    suspend fun updateTarea(@Path("idTareas") id: Int, @Body tarea: Create_TareasDto_2): Create_TareasDto_2

    // ----------- TiposMantenimientos -----------
    @GET("api/TiposMantenimientos")
    suspend fun getAllTiposMantenimientos(): List<Post_TiposMantenimientosDto>

    @POST("api/TiposMantenimientos")
    suspend fun createTipoMantenimiento(@Body tipo: Create_TiposMantenimientosDto_2): Post_TiposMantenimientosDto

    @DELETE("api/TiposMantenimientos/{id}")
    suspend fun deleteTipoMantenimiento(@Path("idTiposMantenimientos") id: Int)

    @GET("api/TiposMantenimientos/{id}")
    suspend fun getTipoMantenimientoById(@Path("idTiposMantenimientos") id: Int): Post_TiposMantenimientosDto

    @PUT("api/TiposMantenimientos/{id}")
    suspend fun updateTipoMantenimiento(@Path("idTiposMantenimientos") id: Int, @Body tipo: Create_TiposMantenimientosDto_2): Create_TiposMantenimientosDto_2

    // ----------- TiposMaquinarias -----------
    @GET("api/TiposMaquinarias")
    suspend fun getAllTiposMaquinarias(): List<Post_TiposMaquinariasDto>

    @POST("api/TiposMaquinarias")
    suspend fun createTipoMaquinaria(@Body tipo: Create_TiposMaquinariasDto_2): Post_TiposMaquinariasDto

    @DELETE("api/TiposMaquinarias/{id}")
    suspend fun deleteTipoMaquinaria(@Path("idTiposMaquinarias") id: Int)

    @GET("api/TiposMaquinarias/{id}")
    suspend fun getTipoMaquinariaById(@Path("idTiposMaquinarias") id: Int): Post_TiposMaquinariasDto

    @PUT("api/TiposMaquinarias/{id}")
    suspend fun updateTipoMaquinaria(@Path("idTiposMaquinarias") id: Int, @Body tipo: Create_TiposMaquinariasDto_2): Create_TiposMaquinariasDto_2

    // ----------- Ubicaciones -----------
    @GET("api/Ubicaciones")
    suspend fun getAllUbicaciones(): List<Post_UbicacionesDto>

    @POST("api/Ubicaciones")
    suspend fun createUbicacion(@Body ubicacion: Create_UbicacionesDto_2): Post_UbicacionesDto

    @DELETE("api/Ubicaciones/{id}")
    suspend fun deleteUbicacion(@Path("idUbicaciones") id: Int)

    @GET("api/Ubicaciones/{id}")
    suspend fun getUbicacionById(@Path("idUbicaciones") id: Int): Post_UbicacionesDto

    @PUT("api/Ubicaciones/{id}")
    suspend fun updateUbicacion(@Path("idUbicaciones") id: Int, @Body ubicacion: Create_UbicacionesDto_2): Create_UbicacionesDto_2

    // ----------- Unidades -----------
    @GET("api/Unidades")
    suspend fun getAllUnidades(): List<Post_UnidadesDto>

    @POST("api/Unidades")
    suspend fun createUnidad(@Body unidad: Create_UnidadesDto_2): Post_UnidadesDto

    @DELETE("api/Unidades/{id}")
    suspend fun deleteUnidad(@Path("idUnidades") id: Int)

    @GET("api/Unidades/{id}")
    suspend fun getUnidadById(@Path("idUnidades") id: Int): Post_UnidadesDto

    @PUT("api/Unidades/{id}")
    suspend fun updateUnidad(@Path("idUnidades") id: Int, @Body unidad: Create_UnidadesDto_2): Create_UnidadesDto_2

    // ----------- Usuarios -----------
    @GET("api/Usuarios")
    suspend fun getAllUsuarios(): List<Post_UsuariosDto>

    @POST("api/Usuarios")
    suspend fun createUsuario(@Body usuario: Create_UsuariosDto_2): Post_UsuariosDto

    @DELETE("api/Usuarios/{id}")
    suspend fun deleteUsuario(@Path("idUsuarios") id: Int)

    @GET("api/Usuarios/{id}")
    suspend fun getUsuarioById(@Path("idUsuarios") id: Int): Post_UsuariosDto

    @PUT("api/Usuarios/{id}")
    suspend fun updateUsuario(@Path("idUsuarios") id: Int, @Body usuario: Create_UsuariosDto_2): Create_UsuariosDto_2

}
