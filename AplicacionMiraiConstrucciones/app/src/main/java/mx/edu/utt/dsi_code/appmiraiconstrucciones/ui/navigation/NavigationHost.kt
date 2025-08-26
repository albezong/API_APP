package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation

import DetalleMaterialScreen
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.PantallaPrincipal
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.api.RetrofitClient
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.recordatorio.NotificationHelper
import mx.edu.utt.dsi_code.appmiraiconstrucciones.data.repository.Post_UsuariosDto_Repository
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_MaquinariasYVehiculosDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_info
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.Destinos.ico_search
//import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.DetalleMaterialScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.PostListaTodasMaquiunarias
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_EditMaquinariaScreen
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_LogIn
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.Post_PostRecordatorio
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.Post_EquiposDto_ViewModel
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelFactory
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo


@Composable
fun NavigationHost(
    postMaquinasViewModel: Post_MaquinariasYVehiculosDto_ViewModel,
    postEquiposViewModel: Post_EquiposDto_ViewModel,
    usuariosViewModel: UsuariosViewModelLogeo,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "login") {
        // login (usa usuariosViewModel)
        composable("login") {
            Post_LogIn(navListaMaquinaria = navController, usuarioViewModelLogeo = usuariosViewModel)
        }

        // lista principal recibe correo en la ruta
        composable("lista_maquinarias/{correo}") { backStackEntry ->
            //val correo = backStackEntry.arguments?.getString("correo") ?: ""
            PantallaPrincipal(
                viewModel = postMaquinasViewModel,
                navController = navController,
                nombre = ""//correo // o "correo" según param en tu PantallaPrincipal
            )
        }

        // detalle Maquinaria
        composable("detalle_maquinaria/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                DetalleMaterialScreen(navController = navController, id = id, viewModel = postMaquinasViewModel)
            }
        }

        // edit Maquinaria
        composable("edit_maquinaria/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                Post_EditMaquinariaScreen(
                    /*navController = navController,
                    id = id,
                    viewModel = postEquiposViewModel,
                    viewModelMaquinariaYVehi = postMaquinasViewModel*/
                )
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