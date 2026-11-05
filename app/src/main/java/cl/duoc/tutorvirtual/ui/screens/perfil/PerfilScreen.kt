package cl.duoc.tutorvirtual.ui.screens.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.tutorvirtual.ui.components.CampoTexto

@Composable
fun PerfilScreen(vm: PerfilViewModel = viewModel()) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    LaunchedEffect(Unit) { vm.mensajes.collect { snackbar.showSnackbar(it) } }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Mi perfil", style = MaterialTheme.typography.headlineSmall)
            CampoTexto("Nombre completo", ui.nombre, vm::onNombre, Icons.Filled.Person)
            CampoTexto("RUT (12.345.678-5)", ui.rut, vm::onRut, Icons.Filled.Badge)
            CampoTexto("Correo", ui.correo, vm::onCorreo, Icons.Filled.Email, teclado = KeyboardType.Email)

            Text("Nivel", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("TT I", "TT II", "TT III").forEach { nivel ->
                    FilterChip(
                        selected = ui.nivel.valor == nivel,
                        onClick = { vm.onNivel(nivel) },
                        label = { Text(nivel) }
                    )
                }
            }
            ui.nivel.error?.let { MensajeError(it) }   // Row con Icon(Error) + Text en color error

            CampoTexto("Profesor guía", ui.profesorGuia, vm::onProfesor, Icons.Filled.School)

            Button(
                onClick = vm::guardar,
                enabled = !ui.guardando,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                if (ui.guardando) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text("Guardar perfil")
            }
        }
        SnackbarHost(snackbar, Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun MensajeError(x0: Any) {
    TODO("Not yet implemented")
}