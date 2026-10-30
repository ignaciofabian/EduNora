package cl.duoc.tutorvirtual.ui.screens.navegacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.tutorvirtual.ui.navigation.Destino

@Composable
fun InicioScreen(onIr: (Destino) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Bienvenido a EduNora", style = MaterialTheme.typography.headlineMedium)
        Text("Selecciona una sección para comenzar.")
        Destino.entries.filter { it != Destino.INICIO }.forEach { destino ->
            Button(onClick = { onIr(destino) }, modifier = Modifier.fillMaxWidth()) {
                Text(destino.titulo)
            }
        }
    }
}

/** Contenido temporal de la tarjeta 04, sin funcionalidades de las tarjetas siguientes. */
@Composable
fun SeccionProvisionalScreen(destino: Destino) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(destino.titulo, style = MaterialTheme.typography.headlineMedium)
        Text("Sección en construcción", style = MaterialTheme.typography.titleLarge)
        Text("El contenido de esta sección estará disponible en una próxima etapa.")
    }
}
