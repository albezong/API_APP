package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.utils

import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes

object NavigationUtils {

    /**
     * Verifica si la ruta actual debe mostrar el drawer
     */
    fun shouldShowDrawer(currentRoute: String?): Boolean {
        return when (currentRoute) {
            Routes.LOGIN.name -> false
            else -> currentRoute?.startsWith(Routes.LISTA_MAQUINARIAS.name) == true ||
                    currentRoute == Routes.INFO.name ||
                    currentRoute == Routes.SEARCH.name ||
                    currentRoute == Routes.SERVICE_HISTORIAL.name
        }
    }

    /**
     * Verifica si la ruta actual debe mostrar el TopBar
     */
    fun shouldShowTopBar(currentRoute: String?): Boolean {
        return currentRoute != Routes.LOGIN.name
    }

    /**
     * Obtiene el título del TopBar basado en la ruta actual
     */
    fun getTopBarTitle(currentRoute: String?): String {
        return when {
            currentRoute?.startsWith(Routes.LISTA_MAQUINARIAS.name) == true -> "MIRAI CONSTRUCCIONES"
            currentRoute?.startsWith(Routes.DETALLE_MAQUINARIA.name) == true -> "Detalle de Maquinaria"
            currentRoute == Routes.SERVICE_HISTORIAL.name -> "Historial de Servicio"
            currentRoute == Routes.INFO.name -> "Información"
            currentRoute == Routes.SEARCH.name -> "Búsqueda"
            else -> "MIRAI CONSTRUCCIONES"
        }
    }

    /**
     * Construye rutas con parámetros
     */
    fun buildRouteWithParam(route: Routes, param: String): String {
        return "${route.name}/$param"
    }

    /**
     * Construye la ruta de lista de maquinarias con correo
     */
    fun buildListaMaquinariasRoute(correo: String): String {
        return buildRouteWithParam(Routes.LISTA_MAQUINARIAS, correo)
    }

    /**
     * Construye la ruta de detalle de maquinaria con ID
     */
    fun buildDetalleMaquinariaRoute(id: Int): String {
        return buildRouteWithParam(Routes.DETALLE_MAQUINARIA, id.toString())
    }
}