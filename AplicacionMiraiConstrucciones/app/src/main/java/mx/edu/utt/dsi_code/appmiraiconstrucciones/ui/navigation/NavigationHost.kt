package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.edu.utt.dsi_code.appmiraiconstrucciones.PantallaPrincipal
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.recordatorio.NotificationHelper
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostCreateTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_DetalleMaterialScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_EditMaquinariaScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_PostRecordatorio
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EstatusDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_TiposMaquinariasDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UbicacionesDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_UnidadesDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo

@Composable
fun NavigationHost(
    postMaquinasViewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    postEquiposViewModel: Post_EquiposDto_ViewModel,
    usuariosViewModel: UsuariosViewModelLogeo,
    viewModelUbicaciones: Post_UbicacionesDto_ViewModel,
    viewModelUnidades: Post_UnidadesDto_ViewModel,
    viewModelEstatus: Post_EstatusDto_ViewModel,
    viewModelTiposMaquinarias: Post_TiposMaquinariasDto_ViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Post_LogIn(navListaMaquinaria = navController, usuarioViewModelLogeo = usuariosViewModel)
        }

        composable("lista_maquinarias/{correo}") { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            PantallaPrincipal(
                viewModel = postMaquinasViewModel,
                navController = navController,
                nombre = correo
            )
        }

        composable("post_maquinarias") {
            PostCreateTodasMaquiunarias(
                navController = navController,
                viewModel = postEquiposViewModel,
                viewModelUbicaciones = viewModelUbicaciones,
                viewModelUnidades = viewModelUnidades,
                viewModelEstatus = viewModelEstatus,
                viewModelTiposMaquinarias = viewModelTiposMaquinarias
            )
        }

        composable("detalle_maquinaria/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                Post_DetalleMaterialScreen(navController = navController, id = id, viewModel = postMaquinasViewModel)
            }
        }

        composable("edit_maquinaria/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                Post_EditMaquinariaScreen(navController = navController, id = id, viewModel = postEquiposViewModel, viewModelMaquinariaYVehi = postMaquinasViewModel)
            }
        }

        // dentro de NavHost { ... }
        composable("recordatorio") { backStackEntry ->
            val context = LocalContext.current
            Post_PostRecordatorio(onScheduleClick = {
                // crear canal si hace falta
                NotificationHelper.createChannelIfNeeded(context)

                // chequeo para alarmas exactas en API >= S (31)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val am = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
                    if (!am.canScheduleExactAlarms()) {
                        // intentar abrir settings para que el usuario permita exact alarms
                        try {
                            context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                            Toast.makeText(context, "Otorga permiso para alarmas exactas si es necesario", Toast.LENGTH_LONG).show()
                            return@Post_PostRecordatorio
                        } catch (e: Exception) {
                            // si no se puede abrir settings, continuamos y atrapamos SecurityException al programar
                        }
                    }
                }

                // programar la alarma con manejo de excepciones
                try {
                    NotificationHelper.scheduleNotification(context, 15_000L) // 15s
                    Toast.makeText(context, "Recordatorio programado (15s)", Toast.LENGTH_SHORT).show()
                } catch (se: SecurityException) {
                    Toast.makeText(context, "No se pueden programar alarmas exactas: ${se.message}", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al programar: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}