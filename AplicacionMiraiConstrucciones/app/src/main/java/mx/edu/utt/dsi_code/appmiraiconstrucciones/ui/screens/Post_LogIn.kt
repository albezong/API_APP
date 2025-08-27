package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.R
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.LoginState
import mx.edu.utt.dsi_code.appmiraiconstrucciones.viewmodel.UsuariosViewModelLogeo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun Post_LogIn(
    navListaMaquinaria: NavHostController,
    usuarioViewModelLogeo: UsuariosViewModelLogeo,
) {
    encabezadoLogin()
    cuerpoLogin(navListaMaquinaria, usuarioViewModelLogeo)
}

@Composable
fun encabezadoLogin(
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MIRAI Construcciones México",
                color = Color.DarkGray,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun cuerpoLogin(
    navListaMaquinaria: NavHostController,
    usuariosViewModel: UsuariosViewModelLogeo,
) {
    var nombre by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var contexto = LocalContext.current
    var pass_show by remember { mutableStateOf(false) }

    // Observa el estado de login desde el ViewModel
    val loginState by usuariosViewModel.loginState.collectAsState()

    // Reacciona a cambios de estado (navegar en Success, mostrar Toast en Error)
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                // obtén el correo/usuario del estado Success si lo tienes
                val usuario = (loginState as LoginState.Success).usuario
                // usa el nombre real desde response si existe, si no usa el nombre escrito
                val correoParaRuta = (usuario?.nombre ?: nombre).trim()
                val correoEncoded = URLEncoder.encode(correoParaRuta, StandardCharsets.UTF_8.toString())
                navListaMaquinaria.navigate("lista_maquinarias/$correoEncoded") {
                    popUpTo("login") { inclusive = true }
                    launchSingleTop = true
                }
                usuariosViewModel.reset() // resetea estado para evitar navegación repetida
            }
            is LoginState.Error -> {
                val mensaje = (loginState as LoginState.Error).message
                Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show()
                usuariosViewModel.reset()
            }
            else -> {
                // Idle o Loading: no hacer nada aquí
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = "iconoMIRAI",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "ACCESO | LOGIN",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_accessibility_24),
                    contentDescription = "nombre"
                )
            },
            placeholder = { Text(text = "Nombre de Usuario") },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, shape = RoundedCornerShape(25.dp), color = Color.Gray),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_vpn_key_24),
                    contentDescription = "key"
                )
            },
            placeholder = { Text(text = "Contraseña") },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, shape = RoundedCornerShape(25.dp), color = Color.Gray),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            maxLines = 1,
            visualTransformation = if (pass_show) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                //val icono_show = if(pass_show) painterResource(R.drawable.baseline_visibility_off_24) else painterResource(R.drawable.baseline_visibility_24)
                IconButton(onClick = { pass_show = !pass_show }) {
                    val iconRes =
                        if (pass_show) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                    Icon(painter = painterResource(iconRes), contentDescription = "toggle password")
                }
            },
        )

        Spacer(modifier = Modifier.height(25.dp))

        val loading = loginState is LoginState.Loading

        Button(
            onClick = {
                if (nombre.isBlank() || contraseña.isBlank()) {
                    Toast.makeText(contexto, "Nombre/contraseña inválido(s)", Toast.LENGTH_LONG)
                        .show()
                } else {
                    // sólo llamamos al ViewModel; la navegación ocurrirá cuando loginState cambie a Success
                    usuariosViewModel.authenticate(nombre.trim(), contraseña)
                }
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 8.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
            } else {
                Text(text = "Entrar", fontSize = 18.sp)
            }
        }
    }
}