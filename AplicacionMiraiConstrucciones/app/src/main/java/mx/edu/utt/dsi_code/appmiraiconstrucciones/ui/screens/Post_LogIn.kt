package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens


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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.edu.utt.dsi_code.appmiraiconstrucciones.R


@Composable
fun Post_LogIn(
    navListaMaquinaria: NavHostController,
    ) {
    encabezadoLogin()
    cuerpoLogin(navListaMaquinaria)
}

@Composable
fun encabezadoLogin(
    ) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "MIRAI",
            color = Color.DarkGray,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Usuario",
            color = Color.DarkGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "icono",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        )
    }
}

@Composable
fun cuerpoLogin(
    navListaMaquinaria: NavHostController,
    ) {
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
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
            value = correo,
            onValueChange = { correo = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_accessibility_24),
                    contentDescription = "email"
                )
            },
            placeholder = { Text(text = "Correo electronico") },
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
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                navListaMaquinaria.navigate("lista_maquinarias"){
                    popUpTo("login") { inclusive = true }
                    launchSingleTop = true
                }
                      },
            modifier = Modifier
                .width(400.dp)
        ) {
            Text(
                text = "Entrar",
                fontSize = 20.sp
            )
        }
    }
}