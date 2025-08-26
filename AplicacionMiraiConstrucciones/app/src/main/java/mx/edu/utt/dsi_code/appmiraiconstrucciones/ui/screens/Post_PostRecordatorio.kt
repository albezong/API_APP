package mx.edu.utt.dsi_code.appmiraiconstrucciones.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import java.util.Calendar

@Composable
fun Post_PostRecordatorio(onScheduleClick: (() -> Unit)? = null) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(onClick = { onScheduleClick?.invoke() }) {
                Text(text = "Programar recordatorio (15s)")
            }
        }
    }
}