package mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.model.*
import retrofit2.Response
import retrofit2.http.*
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
    ):Response<Post_MaquinariasYVehiculosDto>//: Post_MaquinariasYVehiculosDto

    // Detalle por id
    @GET("api/maquinaria/{id}")
    suspend fun getMaquinariaById(
        @Path("id") id: Int
    ): Response<Maquinaria>
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
    suspend fun createCategoriaPreventiva(@Body categoriasPreventivas: Create_CategoriasPreventivasDto_2): Post_CategoriasPreventivasDto

    @DELETE("api/CategoriasPreventivas/{id}")
    suspend fun deleteCategoriaPreventiva(@Path("id") id: Int)

    @GET("api/CategoriasPreventivas/{id}")
    suspend fun getCategoriaPreventivaById(@Path("id") id: Int): Post_CategoriasPreventivasDto

    @PUT("api/CategoriasPreventivas/{id}")
    suspend fun updateCategoriaPreventiva(@Path("id") id: Int, @Body categoriasPreventivas: Post_CategoriasPreventivasDto): Post_CategoriasPreventivasDto


    // ----------- DetallesPreventivos -----------
    @GET("api/DetallesPreventivos")
    suspend fun getAllDetallesPreventivos(): List<Post_DetallesPreventivosDto>

    @POST("api/DetallesPreventivos")
    suspend fun createDetallePreventivo(@Body detallesPreventivos: Create_DetallesPreventivosDto_2): Post_DetallesPreventivosDto

    @DELETE("api/DetallesPreventivos/{id}")
    suspend fun deleteDetallePreventivo(@Path("id") id: Int)

    @GET("api/DetallesPreventivos/{id}")
    suspend fun getDetallePreventivoById(@Path("id") id: Int): Post_DetallesPreventivosDto

    @PUT("api/DetallesPreventivos/{id}")
    suspend fun updateDetallePreventivo(@Path("id") id: Int, @Body detallesPreventivos: Post_DetallesPreventivosDto): Post_DetallesPreventivosDto


    // ----------- Empresas -----------
    @GET("api/Empresas")
    suspend fun getAllEmpresas(): List<Post_EmpresasDto>

    @POST("api/Empresas")
    suspend fun createEmpresa(@Body empresas: Create_EmpresasDto_2): Post_EmpresasDto

    @DELETE("api/Empresas/{id}")
    suspend fun deleteEmpresa(@Path("id") id: Int)

    @GET("api/Empresas/{id}")
    suspend fun getEmpresaById(@Path("id") id: Int): Post_EmpresasDto

    @PUT("api/Empresas/{id}")
    suspend fun updateEmpresa(@Path("id") id: Int, @Body empresas: Post_EmpresasDto): Post_EmpresasDto

    // ----------- Equipos -----------  <<<<
    @GET("api/Equipos")
    suspend fun getAllEquipos(): List<Post_EquiposDto>

    @POST("api/Equipos")
    suspend fun createEquipo(@Body equipo: Create_EquiposDto_2): Post_EquiposDto

    // <- CAMBIO: devolver Response<Void> para poder inspeccionar código y body
    @DELETE("api/Equipos/{id}")
    suspend fun deleteEquipo(@Path("id") id: Int):Response<Void>//: Post_EquiposDto

    @GET("api/Equipos/{id}")
    suspend fun getEquipoById(@Path("id") id: Int): Post_EquiposDto

    @PUT("api/Equipos/{id}")
    suspend fun updateEquipo(@Path("id") id: Int, @Body equipo: Post_EquiposDto): Post_EquiposDto //Esto cambie: en vez del create
    // ----------- Estatus -----------  <<<<
    @GET("api/Estatus")
    suspend fun getAllEstatus(): List<Post_EstatusDto>

    @POST("api/Estatus")
    suspend fun createEstatus(@Body estatus: Create_EstatusDto_2): Post_EstatusDto

    @DELETE("api/Estatus/{id}")
    suspend fun deleteEstatus(@Path("id") id: Int)

    @GET("api/Estatus/{id}")
    suspend fun getEstatusById(@Path("id") id: Int): Post_EstatusDto

    @PUT("api/Estatus/{id}")
    suspend fun updateEstatus(@Path("id") id: Int, @Body estatus: Post_EstatusDto): Post_EstatusDto

    // ----------- Lugares -----------  <<<<
    @GET("api/Lugares")
    suspend fun getAllLugares(): List<Post_LugaresDto>

    @POST("api/Lugares")
    suspend fun createLugar(@Body lugares: Create_LugaresDto_2): Post_LugaresDto

    @DELETE("api/Lugares/{id}")
    suspend fun deleteLugar(@Path("id") id: Int)

    @GET("api/Lugares/{id}")
    suspend fun getLugarById(@Path("id") id: Int): Post_LugaresDto

    @PUT("api/Lugares/{id}")
    suspend fun updateLugar(@Path("id") id: Int, @Body lugares: Post_LugaresDto): Post_LugaresDto

    /*/ ----------- PDFs especiales -----------
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
    suspend fun createPrioridad(@Body prioridades: Create_PrioridadesDto_2): Post_PrioridadesDto

    @DELETE("api/Prioridades/{id}")
    suspend fun deletePrioridad(@Path("id") id: Int)

    @GET("api/Prioridades/{id}")
    suspend fun getPrioridadById(@Path("id") id: Int): Post_PrioridadesDto

    @PUT("api/Prioridades/{id}")
    suspend fun updatePrioridad(@Path("id") id: Int, @Body prioridades: Post_PrioridadesDto): Post_PrioridadesDto

    // ----------- QrEquipos -----------  <<<<
    /*
    @GET("api/qr/generarQRyDevolverloComoPNG/{id}")
    suspend fun generarQrEquipo(@Path("id") id: Int): QrResponse

    @GET("api/qr/VerQRenIMG/{id}")
    suspend fun verQrEnImg(@Path("id") id: Int): QrResponse*/

    @GET("api/QrEquipos")
    suspend fun getAllQrEquipos(): List<Post_QrEquiposDto>

    @POST("api/QrEquipos")
    suspend fun createQrEquipo(@Body qrEquipos: Create_QrEquiposDto_2): Post_QrEquiposDto

    @DELETE("api/QrEquipos/{id}")
    suspend fun deleteQrEquipo(@Path("idQrEquipos") id: Int)

    @GET("api/QrEquipos/{id}")
    suspend fun getQrEquipoById(@Path("idQrEquipos") id: Int): Post_QrEquiposDto

    @PUT("api/QrEquipos/{id}")
    suspend fun updateQrEquipo(@Path("idQrEquipos") id: Int, @Body qrEquipos: Post_QrEquiposDto): Post_QrEquiposDto


    // ----------- Recordatorios -----------
    @GET("api/Recordatorios")
    suspend fun getAllRecordatorios(): List<Post_RecordatoriosDto>

    @POST("api/Recordatorios")
    suspend fun createRecordatorio(@Body recordatorios: Create_RecordatoriosDto_2): Post_RecordatoriosDto

    @DELETE("api/Recordatorios/{id}")
    suspend fun deleteRecordatorio(@Path("id") id: Int)

    @GET("api/Recordatorios/{id}")
    suspend fun getRecordatorioById(@Path("id") id: Int): Post_RecordatoriosDto

    @PUT("api/Recordatorios/{id}")
    suspend fun updateRecordatorio(@Path("id") id: Int, @Body recordatorios: Post_RecordatoriosDto): Post_RecordatoriosDto

    // ----------- Refacciones -----------
    @GET("api/Refacciones")
    suspend fun getAllRefacciones(): List<Post_RefaccionesDto>

    @POST("api/Refacciones")
    suspend fun createRefaccion(@Body refacciones: Create_RefaccionesDto_2): Post_RefaccionesDto

    @DELETE("api/Refacciones/{id}")
    suspend fun deleteRefaccion(@Path("id") id: Int)

    @GET("api/Refacciones/{id}")
    suspend fun getRefaccionById(@Path("id") id: Int): Post_RefaccionesDto

    @PUT("api/Refacciones/{id}")
    suspend fun updateRefaccion(@Path("id") id: Int, @Body refacciones: Post_RefaccionesDto): Post_RefaccionesDto

    @GET("api/Refacciones/api/UltimoReporte")
    suspend fun getRefaccionesUltimoReporte(): Post_RefaccionesDto_Numero

    // ----------- Revisiones -----------
    @GET("api/Revisiones")
    suspend fun getAllRevisiones(): List<Post_RevisionesDto>

    @POST("api/Revisiones")
    suspend fun createRevision(@Body revisiones: Create_RevisionesDto_2): Post_RevisionesDto

    @DELETE("api/Revisiones/{id}")
    suspend fun deleteRevision(@Path("id") id: Int)

    @GET("api/Revisiones/{id}")
    suspend fun getRevisionById(@Path("id") id: Int): Post_RevisionesDto

    @PUT("api/Revisiones/{id}")
    suspend fun updateRevision(@Path("id") id: Int, @Body revisiones: Post_RevisionesDto): Post_RevisionesDto

    // ----------- Roles -----------
    @GET("api/Roles")
    suspend fun getAllRoles(): List<Post_RolesDto>

    @POST("api/Roles")
    suspend fun createRol(@Body roles: Create_RolesDto_2): Post_RolesDto

    @DELETE("api/Roles/{id}")
    suspend fun deleteRol(@Path("id") id: Int)

    @GET("api/Roles/{id}")
    suspend fun getRolById(@Path("id") id: Int): Post_RolesDto

    @PUT("api/Roles/{id}")
    suspend fun updateRol(@Path("id") id: Int, @Body roles: Post_RolesDto): Post_RolesDto

    // ----------- Tareas -----------
    @GET("api/Tareas")
    suspend fun getAllTareas(): List<Post_TareasDto>

    @POST("api/Tareas")
    suspend fun createTarea(@Body tareas: Create_TareasDto_2): Post_TareasDto

    @DELETE("api/Tareas/{id}")
    suspend fun deleteTarea(@Path("id") id: Int)

    @GET("api/Tareas/{id}")
    suspend fun getTareaById(@Path("id") id: Int): Post_TareasDto

    @PUT("api/Tareas/{id}")
    suspend fun updateTarea(@Path("id") id: Int, @Body tareas: Post_TareasDto): Post_TareasDto

    // ----------- TiposMantenimientos -----------
    @GET("api/TiposMantenimientos")
    suspend fun getAllTiposMantenimientos(): List<Post_TiposMantenimientosDto>

    @POST("api/TiposMantenimientos")
    suspend fun createTipoMantenimiento(@Body tiposMantenimientos: Create_TiposMantenimientosDto_2): Post_TiposMantenimientosDto

    @DELETE("api/TiposMantenimientos/{id}")
    suspend fun deleteTipoMantenimiento(@Path("id") id: Int)

    @GET("api/TiposMantenimientos/{id}")
    suspend fun getTipoMantenimientoById(@Path("id") id: Int): Post_TiposMantenimientosDto

    @PUT("api/TiposMantenimientos/{id}")
    suspend fun updateTipoMantenimiento(@Path("id") id: Int, @Body tiposMantenimientos: Post_TiposMantenimientosDto): Post_TiposMantenimientosDto

    // ----------- TiposMaquinarias -----------
    @GET("api/TiposMaquinarias")
    suspend fun getAllTiposMaquinarias(): List<Post_TiposMaquinariasDto>

    @POST("api/TiposMaquinarias")
    suspend fun createTipoMaquinaria(@Body tiposMaquinarias: Create_TiposMaquinariasDto_2): Post_TiposMaquinariasDto

    @DELETE("api/TiposMaquinarias/{id}")
    suspend fun deleteTipoMaquinaria(@Path("id") id: Int)

    @GET("api/TiposMaquinarias/{id}")
    suspend fun getTipoMaquinariaById(@Path("id") id: Int): Post_TiposMaquinariasDto

    @PUT("api/TiposMaquinarias/{id}")
    suspend fun updateTipoMaquinaria(@Path("id") id: Int, @Body tiposMaquinarias: Post_TiposMaquinariasDto): Post_TiposMaquinariasDto

    // ----------- Ubicaciones -----------
    @GET("api/Ubicaciones")
    suspend fun getAllUbicaciones(): List<Post_UbicacionesDto>

    @POST("api/Ubicaciones")
    suspend fun createUbicacion(@Body ubicaciones: Create_UbicacionesDto_2): Post_UbicacionesDto

    @DELETE("api/Ubicaciones/{id}")
    suspend fun deleteUbicacion(@Path("id") id: Int)

    @GET("api/Ubicaciones/{id}")
    suspend fun getUbicacionById(@Path("id") id: Int): Post_UbicacionesDto

    @PUT("api/Ubicaciones/{id}")
    suspend fun updateUbicacion(@Path("id") id: Int, @Body ubicaciones: Post_UbicacionesDto): Post_UbicacionesDto

    // ----------- Unidades -----------
    @GET("api/Unidades")
    suspend fun getAllUnidades(): List<Post_UnidadesDto>

    @POST("api/Unidades")
    suspend fun createUnidad(@Body unidades: Create_UnidadesDto_2): Post_UnidadesDto

    @DELETE("api/Unidades/{id}")
    suspend fun deleteUnidad(@Path("id") id: Int)

    @GET("api/Unidades/{id}")
    suspend fun getUnidadById(@Path("id") id: Int): Post_UnidadesDto

    @PUT("api/Unidades/{id}")
    suspend fun updateUnidad(@Path("id") id: Int, @Body unidades: Post_UnidadesDto): Post_UnidadesDto

    // ----------- Usuarios ----------- <<<<
    @GET("api/Usuarios")
    suspend fun getAllUsuarios(): List<Post_UsuariosDto>

    @POST("api/Usuarios")
    suspend fun createUsuario(@Body usuario: Create_UsuariosDto_2): Post_UsuariosDto

    @DELETE("api/Usuarios/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int)

    @GET("api/Usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): Post_UsuariosDto

    @PUT("api/Usuarios/{id}")
    suspend fun updateUsuario(@Path("id") id: Int, @Body usuario: Post_UsuariosDto): Post_UsuariosDto

    // ---------- HISTORIAL DE SERVICIOS ---------------
    @GET("api/historialServicios")
    suspend fun getAllHistorialServicios(): List<Post_HistorialServiciosDto>

    @POST("api/historialServicios")
    suspend fun createHistorialServicio(@Body historial: Create_HistorialServiciosDto): Post_HistorialServiciosDto

    @DELETE("api/historialServicios/{id}")
    suspend fun deleteHistorialServicio(@Path("id") id: Int)

    @GET("api/historialServicios/{id}")
    suspend fun getHistorialServicioById(@Path("id") id: Int): Post_HistorialServiciosDto

    @PUT("api/historialServicios/{id}")
    suspend fun updateHistorialServicio(
        @Path("id") id: Int,
        @Body historial: Create_HistorialServiciosDto
    ): Create_HistorialServiciosDto


    // ------------ PROGRAMAR MANTENIMIENTOS ----------------
    @GET("api/programaciones")
    suspend fun getAllProgramaciones(): List<Post_ProgramacionesDto>

    @POST("api/programaciones")
    suspend fun createProgramacion(@Body programacion: Create_ProgramacionesDto): Post_ProgramacionesDto

    @DELETE("api/programaciones/{id}")
    suspend fun deleteProgramacion(@Path("id") id: Int)

    @GET("api/programaciones/{id}")
    suspend fun getProgramacionById(@Path("id") id: Int): Post_ProgramacionesDto

    @PUT("api/programaciones/{id}")
    suspend fun updateProgramacion(
        @Path("id") id: Int,
        @Body programacion: Create_ProgramacionesDto
    ): Create_ProgramacionesDto

}
