package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mx.edu.utt.dsi_code.appmiraiconstrucciones.R
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.NavigationItem
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.enums.Routes
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.navigation.navigationItems
import mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.theme.Grey80

@Composable
fun AppDrawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    nombre: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Grey80)
            .padding(16.dp)
    ) {
        // Header del drawer con información del usuario
        UserHeader(
            nombre = nombre,
            onLogoutClick = {
                navController.navigate(Routes.LOGIN.name) {
                    popUpTo(Routes.LOGIN.name) { inclusive = true }
                    launchSingleTop = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Items de navegación
        navigationItems.forEach { item ->
            DrawerNavigationItem(
                item = item,
                onItemClick = { selectedItem ->
                    navController.navigate(selectedItem.route) {
                        launchSingleTop = true
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        }
    }
}

@Composable
private fun UserHeader(
    nombre: String,
    onLogoutClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono de usuario
        Icon(
            painter = painterResource(id = R.drawable.baseline_account_circle_24),
            contentDescription = "Usuario",
            tint = Color.Black,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Texto del usuario
        Text(
            text = "Hola, $nombre",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        // Botón salir
        IconButton(onClick = onLogoutClick) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                contentDescription = "Salir",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun DrawerNavigationItem(
    item: NavigationItem,
    onItemClick: (NavigationItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onItemClick(item) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.title,
            style = MaterialTheme.typography.body1,
            color = Color.Black
        )

        // Indicadores de badge o news
        if (item.hasBadge != null) {
            Spacer(modifier = Modifier.weight(1f))
            Badge(
                backgroundColor = MaterialTheme.colors.error
            ) {
                Text(
                    text = item.hasBadge.toString(),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        } else if (item.hasNews) {
            Spacer(modifier = Modifier.weight(1f))
            Badge(backgroundColor = MaterialTheme.colors.error)
        }
    }
}